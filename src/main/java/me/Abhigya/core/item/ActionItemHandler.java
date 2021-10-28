package me.Abhigya.core.item;

import me.Abhigya.core.handler.PluginHandler;
import me.Abhigya.core.main.CoreAPI;
import me.Abhigya.core.util.EventUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventException;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.EventExecutor;

import java.util.HashSet;
import java.util.Set;

/** Handler Class for Action Items. */
public class ActionItemHandler extends PluginHandler {

    /** Stores the active {@link ActionItem}s. */
    protected static final Set<ActionItem> ACTION_ITEMS = new HashSet<>();

    public ActionItemHandler(CoreAPI api) {
        super(api.getHandlingPlugin());
        register();

        // registering executors
        for (EventPriority priority : EventPriority.values()) {
            Bukkit.getPluginManager()
                    .registerEvent(
                            PlayerInteractEvent.class,
                            this,
                            priority,
                            new EventExecutor() {

                                @Override
                                public void execute(Listener listener, final Event uncast_event)
                                        throws EventException {
                                    if (uncast_event instanceof PlayerInteractEvent) {
                                        PlayerInteractEvent event =
                                                (PlayerInteractEvent) uncast_event;
                                        Player player = event.getPlayer();
                                        ItemStack item = event.getItem();

                                        if (item != null && event.getAction() != Action.PHYSICAL) {
                                            ActionItem action_item =
                                                    ACTION_ITEMS.stream()
                                                            .filter(
                                                                    value ->
                                                                            value.getPriority()
                                                                                    == priority)
                                                            .filter(value -> value.isThis(item))
                                                            .findAny()
                                                            .orElse(null);

                                            if (action_item != null) {
                                                ActionItem.EnumAction action_type = null;
                                                boolean sneaking = player.isSneaking();
                                                boolean sprinting = player.isSprinting();
                                                boolean left_click =
                                                        EventUtils.isLeftClick(event.getAction());
                                                boolean right_click =
                                                        EventUtils.isRightClick(event.getAction());

                                                if (sneaking) {
                                                    action_type =
                                                            left_click
                                                                    ? ActionItem.EnumAction
                                                                            .LEFT_CLICK_SNEAKING
                                                                    : (right_click
                                                                            ? ActionItem.EnumAction
                                                                                    .RIGHT_CLICK_SNEAKING
                                                                            : null);
                                                } else if (sprinting) {
                                                    action_type =
                                                            left_click
                                                                    ? ActionItem.EnumAction
                                                                            .LEFT_CLICK_SPRINTING
                                                                    : (right_click
                                                                            ? ActionItem.EnumAction
                                                                                    .RIGHT_CLICK_SPRINTING
                                                                            : null);
                                                } else {
                                                    action_type =
                                                            left_click
                                                                    ? ActionItem.EnumAction
                                                                            .LEFT_CLICK
                                                                    : (right_click
                                                                            ? ActionItem.EnumAction
                                                                                    .RIGHT_CLICK
                                                                            : null);
                                                }

                                                if (action_type != null) {
                                                    action_item.onActionPerform(
                                                            event.getPlayer(), action_type, event);
                                                } else {
                                                    throw new IllegalStateException(
                                                            "couldn't determine performed action");
                                                }
                                            }
                                        }
                                    }
                                }
                            },
                            plugin,
                            false);
        }
    }

    /**
     * Registers the specified {@link ActionItem}.
     *
     * <p>
     *
     * @param item Action item to register.
     * @return true if the provided item is not already registered.
     */
    public static boolean register(ActionItem item) {
        return ACTION_ITEMS.add(item);
    }

    /**
     * Unregisters the specified {@link ActionItem}.
     *
     * <p>
     *
     * @param item Action item to unregister.
     * @return true if the provided item was unregistered successfully.
     */
    public static boolean unregister(ActionItem item) {
        return ACTION_ITEMS.remove(item);
    }

    @Override
    protected boolean isAllowMultipleInstances() {
        return false;
    }
}
