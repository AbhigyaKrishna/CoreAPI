package me.Abhigya.core.item;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface ActionItem {

    /**
     * TODO: Description
     */
    public enum EnumAction {

        LEFT_CLICK,
        LEFT_CLICK_SNEAKING,
        LEFT_CLICK_SPRINTING,

        RIGHT_CLICK,
        RIGHT_CLICK_SNEAKING,
        RIGHT_CLICK_SPRINTING,
        ;
    }

    public String getDisplayName();

    public List<String> getLore();

    public Material getMaterial();

    public EventPriority getPriority();

    public ItemStack toItemStack();

    public boolean isThis(ItemStack item);

    public void onActionPerform(Player player, EnumAction action, PlayerInteractEvent event);

}
