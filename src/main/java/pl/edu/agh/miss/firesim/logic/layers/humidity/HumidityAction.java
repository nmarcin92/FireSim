package pl.edu.agh.miss.firesim.logic.layers.humidity;

import pl.edu.agh.miss.firesim.logic.layers.AbstractAction;

/**
 * @author mnowak
 */
public class HumidityAction extends AbstractAction {

    private final int delta;

    public HumidityAction(int delta) {
        this.delta = delta;
    }

    public int getDelta() {
        return delta;
    }
}
