package pl.edu.agh.miss.firesim.logic.layers.wind;

import pl.edu.agh.miss.firesim.logic.layers.AbstractAction;
import pl.edu.agh.miss.firesim.utils.Vector;

/**
 * @author mnowak
 */
public class WindAction extends AbstractAction {

    private final Vector delta;
    private final WindField destField;

    public WindAction(Vector delta, WindField destField) {
        this.delta = delta;
        this.destField = destField;
    }

    public Vector getDelta() {
        return delta;
    }

    public WindField getDestField() {
        return destField;
    }

}
