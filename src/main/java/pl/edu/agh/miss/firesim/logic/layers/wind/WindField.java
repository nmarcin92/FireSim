package pl.edu.agh.miss.firesim.logic.layers.wind;

import pl.edu.agh.miss.firesim.logic.layers.AbstractField;
import pl.edu.agh.miss.firesim.utils.Vector;

/**
 * @author mnowak
 */
public class WindField extends AbstractField<WindAction> {

    private final Vector strength;

    public WindField() {
        strength = new Vector();
    }

    @Override
    protected void processFutureActions() {
        strength.setZero()
                .add(getFutureActions()
                        .parallelStream()
                        .map(a -> a.getDelta())
                        .reduce((vector, vector2) -> vector.add(vector2))
                        .get());
    }

}
