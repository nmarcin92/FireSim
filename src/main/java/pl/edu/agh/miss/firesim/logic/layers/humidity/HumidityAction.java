package pl.edu.agh.miss.firesim.logic.layers.humidity;

import pl.edu.agh.miss.firesim.logic.layers.AbstractAction;

/**
 * @author mnowak
 */
public class HumidityAction extends AbstractAction {

    private final int delta;
    private final double factor;

    public HumidityAction(int delta, double factor) {
        this.delta = delta;
        this.factor = factor;
    }

    public int getDelta() {
        return delta;
    }

    public double getFactor() {
        return factor;
    }
}
