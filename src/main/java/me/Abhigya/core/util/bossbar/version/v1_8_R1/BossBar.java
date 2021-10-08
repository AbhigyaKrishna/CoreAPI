package me.Abhigya.core.util.bossbar.version.v1_8_R1;

import me.Abhigya.core.util.bossbar.version.oldest.BossBarOldest;
import me.Abhigya.core.util.reflection.bukkit.BukkitReflection;
import me.Abhigya.core.util.server.Version;
import net.minecraft.server.v1_8_R1.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R1.CraftWorld;
import org.bukkit.entity.Player;

import java.util.Objects;

/**
 * A {@code BossBar} intended for the server version {@link Version#v1_8_R1}.
 */
public class BossBar extends BossBarOldest {

    /**
     * The wither entity that holds the bar.
     */
    protected volatile EntityWither handle;

    /**
     * Constructs the {@code BossBar}.
     * <p>
     *
     * @param title    Initial title for the bar
     * @param progress Initial progress for the bar
     * @param player   Player viewer
     */
    public BossBar( String title, double progress, Player player ) {
        super( title, progress, player );
    }

    @Override
    protected synchronized void create( ) {
        if ( this.handle != null ) {
            this.destroy( );
        }

        // starts the updater executor.
        super.create( );

        this.handle = new EntityWither( ( (CraftWorld) player.get( ).getWorld( ) ).getHandle( ) );

        final DataWatcher data = this.handle.getDataWatcher( );
        data.watch( 0, (byte) 0x20 );
        data.watch( 2, getTitle( ) );
        data.watch( 3, (byte) 1 );
        data.watch( 6, Math.min( Math.max( (float) getProgress( ), MINIMUM_PROGRESS ), MAXIMUM_PROGRESS ) * handle.getMaxHealth( ) );
        data.watch( 8, (byte) 0 );
        data.watch( 17, 0 );
        data.watch( 18, 0 );
        data.watch( 19, 0 );
        data.watch( 20, 881 );

        final Location location = calculateHandleLocation( );
        final double x = location.getX( );
        final double y = location.getY( );
        final double z = location.getZ( );
        final float yaw = location.getYaw( );
        final float pitch = location.getPitch( );

        this.handle.setLocation( x, y, z, yaw, pitch );
        this.handle.setInvisible( true );
        this.handle.canPickUpLoot = false;
        this.handle.removeAllEffects( );

        BukkitReflection.sendPacket( getPlayer( ), new PacketPlayOutSpawnEntityLiving( this.handle ) );
    }

    @Override
    protected synchronized void update( ) {
        if ( this.handle == null || ( handle != null
                && !Objects.equals( getPlayer( ).getWorld( ).getUID( ), handle.world.getWorld( ).getUID( ) ) ) ) {
            this.create( );
            return;
        }

        final DataWatcher data = this.handle.getDataWatcher( );
        data.watch( 2, getTitle( ) );
        data.watch( 6, Math.min( Math.max( (float) getProgress( ), MINIMUM_PROGRESS ), MAXIMUM_PROGRESS ) * handle.getMaxHealth( ) );

        /* sending title/progress updater packet */
        BukkitReflection.sendPacket( getPlayer( ),
                new PacketPlayOutEntityMetadata( this.handle.getId( ), data, true ) );

        final Location location = calculateHandleLocation( );
        final int x = location.getBlockX( );
        final int y = location.getBlockY( );
        final int z = location.getBlockZ( );
        final float yaw = location.getYaw( );
        final float pitch = location.getPitch( );

        /* sending location updater packet */
        BukkitReflection.sendPacket( getPlayer( ), new PacketPlayOutEntityTeleport( this.handle.getId( ),
                x * 32,
                y * 32,
                z * 32,
                (byte) ( (int) yaw * 256 / 360 ),
                (byte) ( (int) pitch * 256 / 360 ), false ) );
    }

    @Override
    protected synchronized void destroy( ) {
        super.destroy( );

        if ( this.handle == null ) {
            return;
        }

        final Player player = getPlayer( );
        if ( player != null && player.isOnline( ) ) {
            BukkitReflection.sendPacket( player, new PacketPlayOutEntityDestroy( this.handle.getId( ) ) );
        }

        this.handle = null;
    }

}
