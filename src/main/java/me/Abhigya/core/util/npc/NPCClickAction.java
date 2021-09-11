package me.Abhigya.core.util.npc;

import org.bukkit.entity.Player;

public interface NPCClickAction {

    void onInteract(NPC npc, Player player, NPCManager.ClickType clickType);

}
