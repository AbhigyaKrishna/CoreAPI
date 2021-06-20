package me.Abhigya.core.metrics;

import me.Abhigya.core.plugin.Plugin;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.CustomChart;

import java.util.HashSet;
import java.util.Set;

public abstract class MetricsAdaptor {

    private final Plugin plugin;
    private final int serviceId;
    private final Metrics metrics;

    /**
     * Set of all {@link CustomChart} added to the metrics of this plugin.
     */
    private Set<CustomChart> chartTypes;

    /**
     * Constructs the class
     * <p>
     *
     * @param plugin    Plugin for which the metrics is
     * @param serviceId Id of the plugin metrics
     */
    public MetricsAdaptor(Plugin plugin, int serviceId) {
        this.plugin = plugin;
        this.serviceId = serviceId;
        this.metrics = new Metrics(plugin, serviceId);

        this.chartTypes = new HashSet<>();
    }

    /**
     * Register customs charts and stuffs
     */
    public abstract void register();

    /**
     * Add {@link CustomChart} to metrics.
     * <p>
     *
     * @param chart {@link CustomChart} to add
     */
    public void addChart(CustomChart chart) {
        metrics.addCustomChart(chart);
        this.chartTypes.add(chart);
    }

    public int getServiceId() {
        return serviceId;
    }

    /**
     * Get all registered charts.
     * <p>
     *
     * @return Set of registered charts
     */
    public Set<CustomChart> getChartTypes() {
        return chartTypes;
    }
}
