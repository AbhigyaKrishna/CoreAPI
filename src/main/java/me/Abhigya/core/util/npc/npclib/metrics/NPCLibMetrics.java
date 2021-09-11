package me.Abhigya.core.util.npc.npclib.metrics;

import me.Abhigya.core.util.npc.npclib.NPCLib;
import me.Abhigya.core.util.npc.npclib.internal.NPCManager;
import me.Abhigya.core.util.npc.npclib.bstats.Metrics;

public class NPCLibMetrics {

    private static final int BSTATS_PLUGIN_ID = 7214;

    public NPCLibMetrics(NPCLib instance) {
        Metrics metrics = new Metrics(instance.getPlugin(), BSTATS_PLUGIN_ID);
        metrics.addCustomChart(new Metrics.SingleLineChart("npcs", () -> NPCManager.getAllNPCs().size()));
    }
}
