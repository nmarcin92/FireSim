package pl.edu.agh.miss.firesim.logic.layers.wind;

import pl.edu.agh.miss.firesim.logic.LayerContainer;
import pl.edu.agh.miss.firesim.logic.LayerProcessor.DynamicState;
import pl.edu.agh.miss.firesim.logic.LayerProcessor.StateParameter;
import pl.edu.agh.miss.firesim.utils.Vector;

/**
 * @author mnowak
 */
public class WindGeneratorField extends WindField {

    private int di = 0;

    public WindGeneratorField(DynamicState simulationState) {
        super(simulationState);
    }

    @Override
    protected void processFutureActions() {

    }

    @Override
    public void propagate(LayerContainer layerContainer) {
        Vector generated = new Vector(getSimulationState().getValue(StateParameter.WIND_DIRECTION))
                .scale(WindLayer.MAX_WIND_STRENGTH);
        WindType type = getSimulationState().getValue(StateParameter.WIND_TYPE);

        switch (type) {
            case SINUSOIDAL:
                float frequency = getSimulationState().getValue(StateParameter.WIND_SINUSOIDAL_FREQ);
                generated.scale(Math.abs(Math.sin(di++ * Math.PI * frequency)));
                break;
            case CONSTANT:
                break;
            default:
                throw new UnsupportedOperationException("Unsupported wind type: " + type);
        }

        getStrength().setX(generated.getX());
        getStrength().setY(generated.getY());

        super.propagate(layerContainer);
    }
}
