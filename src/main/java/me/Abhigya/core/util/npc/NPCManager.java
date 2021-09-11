package me.Abhigya.core.util.npc;

import me.Abhigya.core.handler.PluginHandler;
import me.Abhigya.core.util.npc.npclib.api.events.NPCInteractEvent;
import me.Abhigya.core.util.tasks.Workload;
import me.Abhigya.core.util.tasks.WorkloadThread;
import me.Abhigya.core.util.npc.npclib.NPCLib;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class NPCManager extends PluginHandler {

    private final List<NPC> npcList;
    private final NPCLib library;
    private WorkloadThread thread;

    public NPCManager(Plugin plugin, @Nullable WorkloadThread thread) {
        super(plugin);
        if (thread != null) {
            this.thread = thread;
            this.thread.add(this.getWorkload());
        }
        this.library = new NPCLib((JavaPlugin) plugin);
        this.register();
        npcList = new ArrayList<>();

    }

    @Override
    protected boolean isAllowMultipleInstances() {
        return false;
    }

    @Override
    public void unregister() {
        super.unregister();
    }

    public List<NPC> getNpcList() {
        return Collections.unmodifiableList(npcList);
    }

    public NPC createNPC(List<String> linesAboveNPC, Location NPClocation) {
        NPC npc = new PacketNPC(this, linesAboveNPC, NPClocation, this.thread);
        this.npcList.add(npc);
        return npc;
    }

    @EventHandler
    private void handleNPCInteract(NPCInteractEvent event) {
        for (NPC npc : this.npcList) {
            if (npc.getLibNPC().getId().equals(event.getNPC().getId())) {
                if (npc instanceof PacketNPC)
                    ((PacketNPC) npc).onInteract(event.getWhoClicked(), ClickType.valueOf(event.getClickType().name()));
            }
        }
    }

    @EventHandler
    private void handlePlayerJoin(PlayerJoinEvent event) {
        for (NPC npc : this.npcList) {
            if (npc.getLibNPC().getLocation().getWorld().getName().equals(event.getPlayer().getWorld().getName()) &&
                    npc.getShown().contains(event.getPlayer().getUniqueId())) {
                npc.getLibNPC().show(event.getPlayer());
            }
        }
    }

    public Workload getWorkload() {
        return new Workload() {

            long timestamp = System.currentTimeMillis();

            @Override
            public void compute() {
                timestamp = System.currentTimeMillis();
                npcList.forEach(npc -> {
                    HashMap<Double, UUID> playerUUIDS = new HashMap<>();
                    npc.getPlayersLookingAt().forEach(uuid -> {
                        if (Bukkit.getPlayer(uuid) != null && Bukkit.getPlayer(uuid).getWorld().equals(npc.getLibNPC().getWorld())) {
                            playerUUIDS.put(Bukkit.getPlayer(uuid).getLocation().distance(npc.getLibNPC().getLocation()), uuid);
                        }
                    });
                    List<Double> doubles = new ArrayList<>(playerUUIDS.keySet());
                    Collections.sort(doubles);
                    npc.lookAtLocation(Bukkit.getPlayer(playerUUIDS.get(doubles.get(0))).getLocation());
                });
            }

            @Override
            public boolean reSchedule() {
                return true;
            }

            @Override
            public boolean shouldExecute() {
                return System.currentTimeMillis() - timestamp > 50;
            }
        };
    }

    public NPCLib getNPCLib() {
        return library;
    }

    public enum ClickType {
        LEFT_CLICK,
        RIGHT_CLICK;
    }
}

