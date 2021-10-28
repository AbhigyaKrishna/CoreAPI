package me.Abhigya.core.util.packet;

/**
 * Convenience implementation of {@link PacketListener}. Derive from this and only override the
 * needed.
 */
public class PacketAdapter implements PacketListener {

    @Override
    public void onReceiving(final PacketEvent event) {
        /* to override */
    }

    @Override
    public void onSending(final PacketEvent event) {
        /* to override */
    }
}
