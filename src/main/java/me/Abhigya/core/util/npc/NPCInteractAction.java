package me.Abhigya.core.util.npc;

import org.bukkit.entity.Player;

public interface NPCInteractAction {

    void onInteract(NPC npc, Player player, ClickType clickType);

    enum ClickType {
        LEFT_CLICK,
        RIGHT_CLICK;
    }
}
