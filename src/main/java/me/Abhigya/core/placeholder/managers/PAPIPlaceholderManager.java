package me.Abhigya.core.placeholder.managers;

import me.Abhigya.core.placeholder.Placeholder;
import me.Abhigya.core.placeholder.PlaceholderRegistry;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderHook;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Map;

/** PlaceholderManager that hooks into PlaceholderAPI (Papi) and works with it. */
public class PAPIPlaceholderManager extends BasePlaceholderManager {

    private final PapiPlaceholderRegistry registry = new PapiPlaceholderRegistry();

    @Override
    public void register(Plugin plugin, Placeholder placeholder) {
        new OfficialPlaceholderAdapter(plugin, placeholder).register();
    }

    @SuppressWarnings("deprecation")
    public Map<String, PlaceholderHook> getPlaceholders() {
        return PlaceholderAPI.getPlaceholders();
    }

    @Override
    public PapiPlaceholderRegistry getRegistry() {
        return registry;
    }

    public Placeholder find(String id) {
        PlaceholderHook hook = getPlaceholders().get(id);
        return hook == null
                ? null
                : new Placeholder() {
                    @Override
                    public String getId() {
                        return id;
                    }

                    @Override
                    public String resolve(Player player, String arg) {
                        return hook.onPlaceholderRequest(player, arg);
                    }
                };
    }

    private static class OfficialPlaceholderAdapter extends PlaceholderExpansion {

        private final Placeholder placeholder;
        private final Plugin plugin;

        public OfficialPlaceholderAdapter(Plugin plugin, Placeholder placeholder) {
            this.plugin = plugin;
            this.placeholder = placeholder;
        }

        @Override
        public String onPlaceholderRequest(Player player, String identifier) {
            return placeholder.resolve(player, identifier);
        }

        @Override
        public String getIdentifier() {
            return placeholder.getId();
        }

        @Override
        public String getAuthor() {
            return String.join(", ", plugin.getDescription().getAuthors());
        }

        @Override
        public String getVersion() {
            return plugin.getDescription().getVersion();
        }

        /**
         * Because this is an internal class, you must override this method to let PlaceholderAPI
         * know to not unregister your expansion class when PlaceholderAPI is reloaded
         *
         * @return true to persist through reloads
         */
        @Override
        public boolean persist() {
            return true;
        }

        /**
         * Because this is a internal class, this check is not needed and we can simply return
         * {@code true}
         *
         * @return Always true since it's an internal class.
         */
        @Override
        public boolean canRegister() {
            return true;
        }
    }

    private static class PlaceholderHookWrapper implements Placeholder {

        private final String id;
        private final PlaceholderHook hook;

        private PlaceholderHookWrapper(String id, PlaceholderHook hook) {
            this.id = id;
            this.hook = hook;
        }

        @Override
        public String resolve(Player player, String arg) {
            return hook.onRequest(player, arg);
        }

        @Override
        public String getId() {
            return id;
        }
    }

    private class PapiPlaceholderRegistry implements PlaceholderRegistry<PapiPlaceholderRegistry> {

        public PlaceholderRegistry<?> getParent() {
            return null;
        }

        public void setParent(PlaceholderRegistry<?> parent) {
            throw new UnsupportedOperationException();
        }

        public Placeholder getLocal(String key) {
            PlaceholderHook hook = getPlaceholders().get(key);
            return hook == null ? null : new PlaceholderHookWrapper(key, hook);
        }

        public Placeholder get(String key) {
            PlaceholderHook hook = getPlaceholders().get(key);
            return hook == null ? null : new PlaceholderHookWrapper(key, hook);
        }

        public PapiPlaceholderRegistry set(Placeholder placeholder) {
            throw new UnsupportedOperationException(
                    "Use PlaceholderUtil.register or PlaceholderManager#register instead!");
        }

        public boolean has(String id) {
            return getPlaceholders().containsKey(id);
        }

        public boolean hasLocal(String id) {
            return getPlaceholders().containsKey(id);
        }
    }
}
