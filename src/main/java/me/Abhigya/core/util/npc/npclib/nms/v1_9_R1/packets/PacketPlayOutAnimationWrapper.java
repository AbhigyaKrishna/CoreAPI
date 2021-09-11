package me.Abhigya.core.util.npc.npclib.nms.v1_9_R1.packets;

import me.Abhigya.core.util.npc.npclib.tinyprotocol.Reflection;
import me.Abhigya.core.util.npc.npclib.api.state.NPCAnimation;
import net.minecraft.server.v1_9_R1.PacketPlayOutAnimation;

public class PacketPlayOutAnimationWrapper {

    public PacketPlayOutAnimation create(NPCAnimation npcAnimation, int entityId)  {
        PacketPlayOutAnimation packetPlayOutAnimation = new PacketPlayOutAnimation();

        Reflection.getField(packetPlayOutAnimation.getClass(), "a", int.class)
                .set(packetPlayOutAnimation, entityId);
        Reflection.getField(packetPlayOutAnimation.getClass(), "b", int.class)
                .set(packetPlayOutAnimation, npcAnimation.getId());

        return packetPlayOutAnimation;
    }

}
