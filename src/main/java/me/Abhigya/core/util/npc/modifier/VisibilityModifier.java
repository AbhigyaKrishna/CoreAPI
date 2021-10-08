package me.Abhigya.core.util.npc.modifier;

import io.github.retrooper.packetevents.packetwrappers.WrappedPacket;
import io.github.retrooper.packetevents.packetwrappers.play.out.entitydestroy.WrappedPacketOutEntityDestroy;
import io.github.retrooper.packetevents.packetwrappers.play.out.namedentityspawn.WrappedPacketOutNamedEntitySpawn;
import io.github.retrooper.packetevents.packetwrappers.play.out.playerinfo.WrappedPacketOutPlayerInfo;
import io.github.retrooper.packetevents.utils.vector.Vector3d;
import me.Abhigya.core.util.npc.NPC;
import org.bukkit.GameMode;
import org.jetbrains.annotations.NotNull;

/**
 * A modifier for modifying the visibility of a player.
 */
public class VisibilityModifier extends NPCModifier {

    /**
     * Creates a new modifier.
     *
     * @param npc The npc this modifier is for.
     * @see NPC#visibility()
     */
    public VisibilityModifier( @NotNull NPC npc ) {
        super( npc );
    }

    /**
     * Enqueues the change of the player list for the wrapped npc.
     *
     * @param action The action of the player list change.
     * @return The same instance of this class, for chaining.
     * @since 2.5-SNAPSHOT
     */
    @NotNull
    public VisibilityModifier queuePlayerListChange( @NotNull PlayerInfoAction action ) {
        return this.queuePlayerListChange( action.handle );
    }

    /**
     * Enqueues the change of the player list for the wrapped npc.
     *
     * @param action The action of the player list change as a protocol lib wrapper.
     * @return The same instance of this class, for chaining.
     */
    @NotNull
    public VisibilityModifier queuePlayerListChange( @NotNull WrappedPacketOutPlayerInfo.PlayerInfoAction action ) {
        WrappedPacketOutPlayerInfo.PlayerInfo playerInfoData = new WrappedPacketOutPlayerInfo.PlayerInfo( "", super.npc.getGameProfile( ), GameMode.CREATIVE, 20 );
        WrappedPacket packetContainer = new WrappedPacketOutPlayerInfo( action, playerInfoData );
        this.packetContainers.add( packetContainer );
//        packetContainer.getPlayerInfoAction().write(0, action);

//        PlayerInfoData playerInfoData = new PlayerInfoData(
//                super.npc.getGameProfile(),
//                20,
//                EnumWrappers.NativeGameMode.CREATIVE,
//                WrappedChatComponent.fromText(""));
//        packetContainer.getPlayerInfoDataLists().write(0, new ArrayList<>(Arrays.asList(playerInfoData)));

        return this;
    }

    /**
     * Enqueues the spawn of the wrapped npc.
     *
     * @return The same instance of this class, for chaining.
     */
    @NotNull
    public VisibilityModifier queueSpawn( ) {
//        packetContainer.getUUIDs().write(0, super.npc.getProfile().getUniqueId());

        double x = super.npc.getLocation( ).getX( );
        double y = super.npc.getLocation( ).getY( );
        double z = super.npc.getLocation( ).getZ( );

        Vector3d vector = new Vector3d( x, y, z );
//        if (MINECRAFT_VERSION.isOlderThan(ServerVersion.v_1_9)) {
//            packetContainer.getIntegers()
//                    .write(1, (int) Math.floor(x * 32.0D))
//                    .write(2, (int) Math.floor(y * 32.0D))
//                    .write(3, (int) Math.floor(z * 32.0D));
//            vector = new Vector3d(Math.floor(x * 32.0D), Math.floor(y * 32.0D), Math.floor(z * 32.0D));
//        } else {
//            packetContainer.getDoubles()
//                    .write(0, x)
//                    .write(1, y)
//                    .write(2, z);
//        }

        WrappedPacketOutNamedEntitySpawn packetContainer = new WrappedPacketOutNamedEntitySpawn( this.npc.getEntityId( ), super.npc.getProfile( ).getUniqueId( ),
                vector, super.npc.getLocation( ).getYaw( ), super.npc.getLocation( ).getPitch( ) );
        this.packetContainers.add( packetContainer );

//        packetContainer.getBytes()
//                .write(0, (byte) (super.npc.getLocation().getYaw() * 256F / 360F))
//                .write(1, (byte) (super.npc.getLocation().getPitch() * 256F / 360F));

//        if (MINECRAFT_VERSION < 15) {
//            packetContainer.getDataWatcherModifier().write(0, new WrappedDataWatcher());
//        }

        return this;
    }

    /**
     * Enqueues the de-spawn of the wrapped npc.
     *
     * @return The same instance of this class, for chaining.
     */
    @NotNull
    public VisibilityModifier queueDestroy( ) {
        WrappedPacket packetContainer = new WrappedPacketOutEntityDestroy( this.npc.getEntityId( ) );
        this.packetContainers.add( packetContainer );

//        if (MINECRAFT_VERSION >= 17) {
//            packetContainer.getIntLists().write(0, new ArrayList<>(Arrays.asList(super.npc.getEntityId())));
//        } else {
//            packetContainer.getIntegerArrays().write(0, new int[]{super.npc.getEntityId()});
//        }
        return this;
    }

    /**
     * A wrapper for all available player info actions.
     *
     * @since 2.5-SNAPSHOT
     */
    public enum PlayerInfoAction {
        /**
         * Adds a player to the player list.
         */
        ADD_PLAYER( WrappedPacketOutPlayerInfo.PlayerInfoAction.ADD_PLAYER ),
        /**
         * Updates the game mode of a player.
         */
        UPDATE_GAME_MODE( WrappedPacketOutPlayerInfo.PlayerInfoAction.UPDATE_GAME_MODE ),
        /**
         * Updates the latency of a player.
         */
        UPDATE_LATENCY( WrappedPacketOutPlayerInfo.PlayerInfoAction.UPDATE_LATENCY ),
        /**
         * Updates the display name of a player.
         */
        UPDATE_DISPLAY_NAME( WrappedPacketOutPlayerInfo.PlayerInfoAction.UPDATE_DISPLAY_NAME ),
        /**
         * Removes a specific player from the player list.
         */
        REMOVE_PLAYER( WrappedPacketOutPlayerInfo.PlayerInfoAction.REMOVE_PLAYER );

        /**
         * The protocol lib wrapper for the action.
         */
        private final WrappedPacketOutPlayerInfo.PlayerInfoAction handle;

        /**
         * Creates a new action.
         *
         * @param handle The protocol lib wrapper for the action.
         */
        PlayerInfoAction( WrappedPacketOutPlayerInfo.PlayerInfoAction handle ) {
            this.handle = handle;
        }
    }

}