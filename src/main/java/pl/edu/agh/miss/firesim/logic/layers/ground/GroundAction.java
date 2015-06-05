package pl.edu.agh.miss.firesim.logic.layers.ground;

import pl.edu.agh.miss.firesim.logic.layers.AbstractAction;

/**
 * @author mnowak
 */
public class GroundAction extends AbstractAction {

    private final double windFactor;
    private final double humidityFactor;
    private final double densityFactor;

    public GroundAction(double windFactor, double humidityFactor, double densityFactor) {
        this.windFactor = windFactor;
        this.humidityFactor = humidityFactor;
        this.densityFactor = densityFactor;
    }

    public double getWindFactor() {
        return windFactor;
    }

    public double getHumidityFactor() {
        return humidityFactor;
    }

    public double getDensityFactor() {
        return densityFactor;
    }
}
