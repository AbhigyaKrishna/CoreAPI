package me.Abhigya.core.placeholder.managers;

import me.Abhigya.core.main.CoreAPI;
import me.Abhigya.core.placeholder.Placeholder;
import me.Abhigya.core.placeholder.PlaceholderRegistry;
import me.Abhigya.core.placeholder.managers.customs.PlayerPlaceholder;
import me.Abhigya.core.placeholder.managers.customs.VaultPlaceholder;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

public class CustomPlaceholderManager extends BasePlaceholderManager {

    private final PlaceholderRegistry<?> registry = new CustomPlaceholderRegistry();
    private Map<String, Placeholder> placeholders = new HashMap<>();

    public CustomPlaceholderManager() {
        addDefaults();
    }

    @Override
    public void register(Plugin plugin, Placeholder placeholder) {
        placeholders.put(placeholder.getId(), placeholder);
    }

    @Override
    protected Placeholder find(String id) {
        return placeholders.get(id);
    }


    public void addDefaults() {
        Plugin plugin = CoreAPI.getInstance();
        register(plugin, new PlayerPlaceholder());
        register(plugin, new VaultPlaceholder());
    }

    @Override
    public PlaceholderRegistry<?> getRegistry() {
        return registry;
    }

    private class CustomPlaceholderRegistry implements PlaceholderRegistry<CustomPlaceholderRegistry> {
        public PlaceholderRegistry<?> getParent() {
            return null;
        }

        public void setParent(PlaceholderRegistry<?> parent) {
            throw new UnsupportedOperationException();
        }

        public Placeholder getLocal(String key) {
            return placeholders.get(key);
        }

        public Placeholder get(String key) {
            return placeholders.get(key);
        }

        public CustomPlaceholderRegistry set(Placeholder placeholder) {
            throw new UnsupportedOperationException("Use PlaceholderUtil.register o PlaceholderManager#register instead!");
        }

        public boolean has(String id) {
            return placeholders.containsKey(id);
        }


        public boolean hasLocal(String id) {
            return placeholders.containsKey(id);
        }
    }

}
