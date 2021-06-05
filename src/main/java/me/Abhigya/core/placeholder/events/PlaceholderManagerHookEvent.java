package me.Abhigya.core.placeholder.events;

import me.Abhigya.core.placeholder.PlaceholderManager;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlaceholderManagerHookEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private PlaceholderManager placeholderManager = null;

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public void setPlaceholderManager(PlaceholderManager placeholderManager) {
        this.placeholderManager = placeholderManager;
    }

    public PlaceholderManager getPlaceholderManager() {
        return placeholderManager;
    }
}
