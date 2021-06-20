package me.Abhigya.core.main;

import me.Abhigya.core.metrics.MetricsAdaptor;
import me.Abhigya.core.plugin.Plugin;
import org.bstats.charts.SingleLineChart;

public class Metrics extends MetricsAdaptor {

    protected Metrics(Plugin plugin, int pluginId) {
        super(plugin, pluginId);
    }

    @Override
    public void register() {
        this.addChart(new SingleLineChart("servers", () -> 1));
    }
}
