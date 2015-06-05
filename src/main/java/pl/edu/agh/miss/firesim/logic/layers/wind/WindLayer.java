package pl.edu.agh.miss.firesim.logic.layers.wind;

import pl.edu.agh.miss.firesim.logic.LayerProcessor.DynamicState;
import pl.edu.agh.miss.firesim.logic.layers.AbstractLayer;

/**
 * @author mnowak
 */
public class WindLayer extends AbstractLayer<WindField> {

    public static final double MAX_WIND_STRENGTH = 100.0;

    public WindLayer(int sizeX, int sizeY, DynamicState simulationState) {
        super(sizeX, sizeY, simulationState);
    }

    @Override
    protected WindField createEmptyField(Integer row, Integer col) {
        if (row == 0 || row == getSizeY() - 1 || col == 0 || col == getSizeX() - 1) {
            return new WindGeneratorField(col, row, getSimulationState());
        }
        return new WindField(col, row, getSimulationState());
    }


}
