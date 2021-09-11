package me.Abhigya.core.util.npc.npclib.api.state;

import java.util.Collection;

public enum NPCState {

    ON_FIRE((byte) 1),
    CROUCHED((byte) 2),
    INVISIBLE((byte) 32);

    private final byte b;

    NPCState(byte b) {
        this.b = b;
    }

    public byte getByte() {
        return b;
    }

    public static byte getMasked(me.Abhigya.core.util.npc.npclib.api.state.NPCState... states) {
        byte mask = 0;
        for (me.Abhigya.core.util.npc.npclib.api.state.NPCState state : states) mask |= state.getByte();
        return mask;
    }

    public static byte getMasked(Collection<me.Abhigya.core.util.npc.npclib.api.state.NPCState> states) {
        byte mask = 0;
        for (me.Abhigya.core.util.npc.npclib.api.state.NPCState state : states) mask |= state.getByte();
        return mask;
    }
}
