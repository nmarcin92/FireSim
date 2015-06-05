package pl.edu.agh.miss.firesim.logic.layers.humidity;

import pl.edu.agh.miss.firesim.logic.LayerContainer;
import pl.edu.agh.miss.firesim.logic.layers.AbstractField;
import pl.edu.agh.miss.firesim.logic.layers.ground.GroundField;
import pl.edu.agh.miss.firesim.logic.layers.ground.GroundType;
import pl.edu.agh.miss.firesim.logic.layers.wind.WindAction;
import pl.edu.agh.miss.firesim.logic.layers.wind.WindLayer;

import java.util.List;

import static pl.edu.agh.miss.firesim.logic.LayerProcessor.DynamicState;
import static pl.edu.agh.miss.firesim.logic.LayerProcessor.StateParameter.*;

/**
 * @author mnowak
 */
public class HumidityField extends AbstractField<HumidityAction> {

    private int humidity;

    public HumidityField(int x, int y, DynamicState simulationState) {
        super(x, y, simulationState);
        humidity = simulationState.getValue(AIR_HUMIDITY);
    }

    public int getHumidity() {
        return humidity;
    }

    @Override
    protected void processFutureActions(LayerContainer layerContainer) {
        int humidityDelta = 0;
        for (HumidityAction humidityAction : getFutureActions()) {
            humidityDelta += Math.round((humidityAction.getDelta() - humidity) * humidityAction.getFactor());

        }

        humidity += humidityDelta;

        GroundField groundField = layerContainer.getGroundLayer().getLayerTable().get(getRowKey(), getColumnKey());
        int baseHumidity = groundField.getGroundType() == GroundType.WATER ? getSimulationState().getValue(WATER_HUMIDITY) : getSimulationState().getValue(AIR_HUMIDITY);
        float convergenceFactor;

        if (groundField.getGroundType() == GroundType.BURNING_TREE) {
            convergenceFactor = getSimulationState().getValue(BURNING_HUMIDITY_DESCENT);
            humidity -= Math.round(convergenceFactor * humidity);

        }

        convergenceFactor = getSimulationState().getValue(HUMIDITY_CONVERGENCE_FACTOR);
        humidity += Math.round((baseHumidity - humidity) * convergenceFactor);

        humidity = Math.max(Math.min(humidity, 100), 0);
    }

    @Override
    public void propagate(LayerContainer layerContainer) {
        if (getRowKey() != 0 && getColumnKey() != 0 && getRowKey() != layerContainer.getSizeX() + 1 && getColumnKey() != layerContainer.getSizeY() + 1) {
            List<WindAction> windActions = layerContainer.getWindLayer().getLayerTable().get(getRowKey(), getColumnKey()).getInfluences();
            for (WindAction windAction : windActions) {
                HumidityField humidityField = layerContainer.getHumidityLayer().getLayerTable().get(windAction.getDestField().getRowKey(),
                        windAction.getDestField().getColumnKey());
                humidityField.addFutureAction(new HumidityAction(getHumidity(), windAction.getDelta().getLength() / WindLayer.MAX_WIND_STRENGTH));
            }
        }
    }
}
