package pl.edu.agh.miss.firesim.logic.layers.ground;

import pl.edu.agh.miss.firesim.logic.LayerProcessor.DynamicState;
import pl.edu.agh.miss.firesim.logic.layers.AbstractLayer;

/**
 * @author mnowak
 */
public class GroundLayer extends AbstractLayer<GroundField> {

    public GroundLayer(int sizeX, int sizeY, DynamicState simulationState) {
        super(sizeX, sizeY, simulationState);
    }

    @Override
    protected GroundField createEmptyField(Integer row, Integer col) {
        return new GroundField(getSimulationState());
    }
}
