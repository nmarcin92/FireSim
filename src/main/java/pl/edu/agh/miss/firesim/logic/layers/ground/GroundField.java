package pl.edu.agh.miss.firesim.logic.layers.ground;

import pl.edu.agh.miss.firesim.logic.LayerContainer;
import pl.edu.agh.miss.firesim.logic.layers.AbstractField;

import static pl.edu.agh.miss.firesim.logic.LayerProcessor.DynamicState;

/**
 * @author mnowak
 */
public class GroundField extends AbstractField<GroundAction> {

    public GroundField(DynamicState simulationState) {
        super(simulationState);
    }

    @Override
    protected void processFutureActions() {

    }

    @Override
    public void propagate(LayerContainer layerContainer) {

    }
}
