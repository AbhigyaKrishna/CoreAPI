package me.Abhigya.core.util.packet;

import org.bukkit.entity.Player;

/**
 * Simple event called whenever a packet will be sent-received.
 */
public class PacketEvent {

    protected final Player player;
    protected final Object packet;

    protected boolean cancelled;

    /**
     * Constructs the packet event.
     * <p>
     *
     * @param player the player sending-receiving the packet.
     * @param packet the packet.
     */
    public PacketEvent(final Player player, final Object packet) {
        this.player = player;
        this.packet = packet;
    }

    /**
     * Gets the player sending-receiving the packet.
     * <p>
     *
     * @return the player.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * The packet, that is an instance of a nms packet like "PacketPlayInSteerVehicle".
     * <p>
     *
     * @return the packet.
     */
    public Object getPacket() {
        return packet;
    }

    /**
     * Gets whether this event has been cancelled.
     * <p>
     *
     * @return true if cancelled.
     */
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * Canceling this event will avoid the packet to be sent-received.
     * <p>
     *
     * @param flag true to cancel.
     */
    public void setCancelled(boolean flag) {
        this.cancelled = flag;
    }

}
