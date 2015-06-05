package pl.edu.agh.miss.firesim.logic;

import com.google.common.collect.Maps;
import pl.edu.agh.miss.firesim.AppConstants;
import pl.edu.agh.miss.firesim.logic.layers.AbstractLayer;
import pl.edu.agh.miss.firesim.logic.layers.LayerType;
import pl.edu.agh.miss.firesim.logic.layers.ground.GroundLayer;
import pl.edu.agh.miss.firesim.logic.layers.humidity.HumidityLayer;
import pl.edu.agh.miss.firesim.logic.layers.wind.WindLayer;
import pl.edu.agh.miss.firesim.logic.layers.wind.WindType;
import pl.edu.agh.miss.firesim.utils.Vector;

import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author mnowak
 */
public class LayerProcessor {

    private final LayerContainer layerContainer;
    private final int sizeX;
    private final int sizeY;
    private final DynamicState simulationState;

    public LayerProcessor(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        simulationState = new DynamicState();
        layerContainer = LayerContainer.builder(sizeX, sizeY)
                .addLayer(LayerType.WIND, new WindLayer(sizeX, sizeY, simulationState))
                .addLayer(LayerType.HUMIDITY, new HumidityLayer(sizeX, sizeY, simulationState))
                .addLayer(LayerType.GROUND, new GroundLayer(sizeX, sizeY, simulationState))
                .build();
    }

    public void iterate() {
        for (AbstractLayer layer : layerContainer.getLayersOrdered()) {
            layer.propagateFields(layerContainer);
        }

        for (AbstractLayer layer : layerContainer.getLayersOrdered()) {
            layer.updateFields(layerContainer);
        }

        simulationState.updateRegistered();
    }

    public LayerContainer getLayerContainer() {
        return layerContainer;
    }

    public DynamicState getSimulationState() {
        return simulationState;
    }

    public static class DynamicState {

        private final Map<StateParameter, Object> parameters = Maps.newEnumMap(StateParameter.class);

        private final Lock registeredLock = new ReentrantLock();
        private final Map<StateParameter, Object> registeredChanges = Maps.newEnumMap(StateParameter.class);

        public DynamicState() {
            // set default values
            setValue(StateParameter.AIR_HUMIDITY, AppConstants.INITIAL_AIR_HUMIDITY);
            setValue(StateParameter.WATER_HUMIDITY, AppConstants.INITIAL_WATER_HUMIDITY);
            setValue(StateParameter.SIMULATION_SPEED, AppConstants.INITIAL_SIMULATION_SPEED);
            setValue(StateParameter.WIND_RANDOMNESS, AppConstants.INITIAL_WIND_RANDOMNESS);
            setValue(StateParameter.WIND_RANDOMNESS, AppConstants.INITIAL_WIND_RANDOMNESS);
            setValue(StateParameter.WIND_SINUSOIDAL_FREQ, AppConstants.INITIAL_WIND_SIN_FREQ);
            setValue(StateParameter.WIND_DIRECTION, AppConstants.INITIAL_WIND_DIRECTION);
            setValue(StateParameter.WIND_TYPE, AppConstants.INITIAL_WIND_TYPE);
            setValue(StateParameter.HUMIDITY_CONVERGENCE_FACTOR, AppConstants.INITIAL_HUMIDITY_CONVERGENCE);
            setValue(StateParameter.BURNING_HUMIDITY_DESCENT, AppConstants.INITIAL_BURNING_DESCENT);
            setValue(StateParameter.DENSITY_DESCENT, AppConstants.INITIAL_DENSITY_DESCENT);
            setValue(StateParameter.SHOW_GROUND_LAYER, Boolean.TRUE);
            setValue(StateParameter.SHOW_HUMIDITY_LAYER, Boolean.TRUE);
            setValue(StateParameter.SHOW_WIND_LAYER, Boolean.TRUE);

        }

        @SuppressWarnings("unchecked")
        public <T> T getValue(StateParameter param) {
            return (T) param.getValueClass().cast(parameters.get(param));
        }

        public <T> void setValue(StateParameter param, T value) {
            if (!param.getValueClass().isInstance(value)) {
                throw new IllegalArgumentException("Value of key " + param +
                        " must be instance of type " + param.getValueClass().getName());
            }
            parameters.put(param, value);
        }

        private void updateRegistered() {
            registeredLock.lock();
            for (Map.Entry<StateParameter, Object> change : registeredChanges.entrySet()) {
                parameters.put(change.getKey(), change.getValue());
            }
            registeredChanges.clear();
            registeredLock.unlock();
        }

        public <T> void requestChange(StateParameter param, T value) {
            if (!param.getValueClass().isInstance(value)) {
                throw new IllegalArgumentException("Value of key " + param +
                        " must be instance of type " + param.getValueClass().getName());
            }
            registeredLock.lock();
            registeredChanges.put(param, value);
            registeredLock.unlock();
        }

    }

    public static enum StateParameter {
        AIR_HUMIDITY(Integer.class),
        WIND_DIRECTION(Vector.class),
        WIND_TYPE(WindType.class),
        WIND_SINUSOIDAL_FREQ(Float.class),
        WIND_RANDOMNESS(Float.class),
        SIMULATION_SPEED(Integer.class),
        HUMIDITY_CONVERGENCE_FACTOR(Float.class),
        BURNING_HUMIDITY_DESCENT(Float.class),
        WATER_HUMIDITY(Integer.class),
        DENSITY_DESCENT(Integer.class),
        SHOW_GROUND_LAYER(Boolean.class),
        SHOW_WIND_LAYER(Boolean.class),
        SHOW_HUMIDITY_LAYER(Boolean.class);

        private final Class valueClass;

        StateParameter(Class valueClass) {
            this.valueClass = valueClass;
        }

        @SuppressWarnings("unchecked")
        private <T> Class<T> getValueClass() {
            return valueClass;
        }

    }


}
