package pl.edu.agh.miss.firesim.logic.layers.wind;

import pl.edu.agh.miss.firesim.logic.layers.AbstractAction;
import pl.edu.agh.miss.firesim.utils.Vector;

/**
 * @author mnowak
 */
public class WindAction extends AbstractAction {

    private final Vector delta;

    public WindAction(Vector delta) {
        this.delta = delta;
    }

    public Vector getDelta() {
        return delta;
    }

}
