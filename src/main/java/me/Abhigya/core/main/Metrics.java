package me.Abhigya.core.main;

import me.Abhigya.core.metrics.MetricsAdaptor;
import org.bstats.charts.SingleLineChart;

public class Metrics extends MetricsAdaptor {

    protected Metrics(CoreAPI api, int pluginId) {
        super(api.getHandlingPlugin(), pluginId);
    }

    @Override
    public void register() {
        this.addChart(new SingleLineChart("servers", () -> 1));
    }
}
