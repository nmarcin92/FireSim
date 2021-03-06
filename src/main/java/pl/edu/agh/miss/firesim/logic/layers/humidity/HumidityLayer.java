package pl.edu.agh.miss.firesim.logic.layers.humidity;

import pl.edu.agh.miss.firesim.logic.LayerProcessor.DynamicState;
import pl.edu.agh.miss.firesim.logic.layers.AbstractLayer;

/**
 * @author mnowak
 */
public class HumidityLayer extends AbstractLayer<HumidityField> {

    public static final int MAX_HUMIDITY = 100;

    public HumidityLayer(int sizeX, int sizeY, DynamicState simulationState) {
        super(sizeX, sizeY, simulationState);
    }

    @Override
    protected HumidityField createEmptyField(Integer row, Integer col) {
        return new HumidityField(col, row, getSimulationState());
    }
}
