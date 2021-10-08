package me.Abhigya.core.util.npc.modifier;

import io.github.retrooper.packetevents.packetwrappers.WrappedPacket;
import io.github.retrooper.packetevents.packetwrappers.play.out.entity.WrappedPacketOutEntity;
import io.github.retrooper.packetevents.packetwrappers.play.out.entityheadrotation.WrappedPacketOutEntityHeadRotation;
import io.github.retrooper.packetevents.packetwrappers.play.out.entityteleport.WrappedPacketOutEntityTeleport;
import io.github.retrooper.packetevents.utils.server.ServerVersion;
import me.Abhigya.core.util.npc.NPC;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

/**
 * A modifier for modifying the rotation of a player.
 */
public class RotationModifier extends NPCModifier {

    /**
     * Creates a new modifier.
     *
     * @param npc The npc this modifier is for.
     * @see NPC#rotation()
     */
    public RotationModifier( @NotNull NPC npc ) {
        super( npc );
    }

    /**
     * Queues the change of the current rotation of the wrapped npc.
     *
     * @param yaw   The yaw of the target rotated location.
     * @param pitch The pitch of the target rotated location.
     * @return The same instance of this class, for chaining.
     */
    @NotNull
    public RotationModifier queueRotate( float yaw, float pitch ) {
        byte yawAngle = (byte) ( yaw * 256F / 360F );
        byte pitchAngle = (byte) ( pitch * 256F / 360F );

        WrappedPacket entityHeadLookContainer = new WrappedPacketOutEntityHeadRotation( this.npc.getEntityId( ), yawAngle );
        this.packetContainers.add( entityHeadLookContainer );
//        entityHeadLookContainer.getBytes().write(0, yawAngle);

        WrappedPacket bodyRotateContainer;
        if ( MINECRAFT_VERSION.isOlderThan( ServerVersion.v_1_9 ) ) {
            Location location = super.npc.getLocation( );
            bodyRotateContainer = new WrappedPacketOutEntityTeleport( this.npc.getEntityId( ), location.getX( ),
                    location.getY( ), location.getZ( ), yaw, pitch, true );


//            bodyRotateContainer.getIntegers()
//                    .write(1, (int) Math.floor(location.getX() * 32.0D))
//                    .write(2, (int) Math.floor(location.getY() * 32.0D))
//                    .write(3, (int) Math.floor(location.getZ() * 32.0D));
        } else {
            bodyRotateContainer = new WrappedPacketOutEntity.WrappedPacketOutEntityLook( this.npc.getEntityId( ), yawAngle, pitchAngle, true );
        }
        this.packetContainers.add( bodyRotateContainer );

        return this;
    }

    /**
     * Queues the change of the current rotation of the wrapped npc.
     *
     * @param location the target location the npc should look to.
     * @return The same instance of this class, for chaining.
     */
    @NotNull
    public RotationModifier queueLookAt( @NotNull Location location ) {
        double xDifference = location.getX( ) - super.npc.getLocation( ).getX( );
        double yDifference = location.getY( ) - super.npc.getLocation( ).getY( );
        double zDifference = location.getZ( ) - super.npc.getLocation( ).getZ( );

        double r = Math
                .sqrt( Math.pow( xDifference, 2 ) + Math.pow( yDifference, 2 ) + Math.pow( zDifference, 2 ) );

        float yaw = (float) ( -Math.atan2( xDifference, zDifference ) / Math.PI * 180D );
        yaw = yaw < 0 ? yaw + 360 : yaw;

        float pitch = (float) ( -Math.asin( yDifference / r ) / Math.PI * 180D );

        return this.queueRotate( yaw, pitch );
    }

}