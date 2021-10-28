package me.Abhigya.core.util.npc;

import io.github.retrooper.packetevents.event.PacketListenerAbstract;
import io.github.retrooper.packetevents.event.impl.PacketPlayReceiveEvent;
import io.github.retrooper.packetevents.packettype.PacketType;
import io.github.retrooper.packetevents.packetwrappers.play.in.useentity.WrappedPacketInUseEntity;
import io.github.retrooper.packetevents.utils.player.Hand;
import io.github.retrooper.packetevents.utils.server.ServerVersion;
import me.Abhigya.core.util.npc.event.PlayerNPCInteractEvent;
import me.Abhigya.core.util.npc.modifier.NPCModifier;
import org.bukkit.Bukkit;

public class PacketListener extends PacketListenerAbstract {

    private NPCPool pool;

    public PacketListener(NPCPool pool) {
        this.pool = pool;
    }

    @Override
    public void onPacketPlayReceive(PacketPlayReceiveEvent event) {
        if (event.getPacketId() == PacketType.Play.Client.USE_ENTITY) {
            WrappedPacketInUseEntity packet = new WrappedPacketInUseEntity(event.getNMSPacket());
            int targetId = packet.getEntityId();

            if (this.pool.npcMap.containsKey(targetId)) {
                NPC npc = this.pool.npcMap.get(targetId);

                WrappedPacketInUseEntity.EntityUseAction action = packet.getAction();
                Hand usedHand;

                if (NPCModifier.MINECRAFT_VERSION.isNewerThanOrEquals(ServerVersion.v_1_17)) {
                    // the hand is only available when not attacking
                    usedHand =
                            action == WrappedPacketInUseEntity.EntityUseAction.ATTACK
                                    ? Hand.MAIN_HAND
                                    : packet.getHand().orElse(Hand.MAIN_HAND);
                } else {
                    // the hand is only available when not attacking
                    usedHand =
                            action == WrappedPacketInUseEntity.EntityUseAction.ATTACK
                                    ? Hand.MAIN_HAND
                                    : packet.getHand().orElse(Hand.MAIN_HAND);
                }

                Bukkit.getScheduler()
                        .runTask(
                                this.pool.plugin,
                                () ->
                                        Bukkit.getPluginManager()
                                                .callEvent(
                                                        new PlayerNPCInteractEvent(
                                                                event.getPlayer(),
                                                                npc,
                                                                action,
                                                                usedHand)));
            }
        }
    }
}
