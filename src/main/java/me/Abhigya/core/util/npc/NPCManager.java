package me.Abhigya.core.util.npc;

import me.Abhigya.core.handler.PluginHandler;
import me.Abhigya.core.util.tasks.Workload;
import me.Abhigya.core.util.tasks.WorkloadThread;
import net.jitse.npclib.NPCLib;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class NPCManager extends PluginHandler {
    private List<NPC> npcList;
    private NPCLib library;
    private NPCManager instance;
    private WorkloadThread thread;

    public NPCManager(Plugin plugin,@Nullable WorkloadThread thread){
        super(plugin);
        if(thread != null){
            this.thread = thread;
            thread.add(getWorkload());
        }
        this.library = new NPCLib((JavaPlugin) plugin);
        this.register();
        instance = this;
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

    public NPC createNPC(List<String> linesAboveNPC, List<UUID> playersToShowNPCto, Location NPClocation){
        NPC npc = new PacketNPC(getInstance(),linesAboveNPC,playersToShowNPCto,NPClocation,this.thread);
        npcList.add(npc);
        return npc;
    }

    public Workload getWorkload(){
        return new Workload() {

            long timestamp = System.currentTimeMillis();

            @Override
            public void compute() {
                timestamp = System.currentTimeMillis();
                npcList.forEach(npc -> {
                    HashMap<Double,UUID> playerUUIDS = new HashMap<>();
                    npc.getPlayersLookingAt().forEach(uuid -> {
                        if(Bukkit.getPlayer(uuid) != null && Bukkit.getPlayer(uuid).getWorld().equals(npc.getLibNPC().getWorld())){
                            playerUUIDS.put(Bukkit.getPlayer(uuid).getLocation().distance(npc.getLibNPC().getLocation()),uuid);
                        }
                    });
                    List<Double> doubles = new ArrayList<>(playerUUIDS.keySet());
                    Collections.sort(doubles);
                    npc.lookAtLocation(Bukkit.getPlayer(playerUUIDS.get(doubles.get(0))).getLocation());
                });
            }

            @Override
            public boolean reSchedule(){
                return true;
            }

            @Override
            public boolean shouldExecute() {
                if(System.currentTimeMillis() - timestamp <=50){
                    return false;
                }
                return true;
            }
        };
    }


    public NPCLib getNPCLib() {
        return library;
    }

    private NPCManager getInstance(){
        return this.instance;
    }
}

