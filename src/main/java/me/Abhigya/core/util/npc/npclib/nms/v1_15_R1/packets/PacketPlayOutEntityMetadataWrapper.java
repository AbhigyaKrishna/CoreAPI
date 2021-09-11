package me.Abhigya.core.util.npc.npclib.nms.v1_15_R1.packets;

import me.Abhigya.core.util.npc.npclib.api.state.NPCState;
import net.minecraft.server.v1_15_R1.*;

import java.util.Collection;

public class PacketPlayOutEntityMetadataWrapper {

    public PacketPlayOutEntityMetadata create(Collection<NPCState> activateStates, int entityId) {
        DataWatcher dataWatcher = new DataWatcher(null);
        byte masked = NPCState.getMasked(activateStates);
        
        dataWatcher.register(new DataWatcherObject<EntityPose>(6, DataWatcherRegistry.s), getMaskedPose(activateStates));
        dataWatcher.register(new DataWatcherObject<>(0, DataWatcherRegistry.a), masked);
        
        return new PacketPlayOutEntityMetadata(entityId, dataWatcher, true);
    }
    
    private EntityPose getMaskedPose(Collection<NPCState> states) {
    	return states.contains(NPCState.CROUCHED) ? EntityPose.CROUCHING : EntityPose.STANDING;
    }
}
