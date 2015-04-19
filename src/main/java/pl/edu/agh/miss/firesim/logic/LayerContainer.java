package pl.edu.agh.miss.firesim.logic;

import pl.edu.agh.miss.firesim.logic.layers.AbstractLayer;
import pl.edu.agh.miss.firesim.logic.layers.LayerType;
import pl.edu.agh.miss.firesim.logic.layers.ground.GroundLayer;
import pl.edu.agh.miss.firesim.logic.layers.humidity.HumidityLayer;
import pl.edu.agh.miss.firesim.logic.layers.wind.WindLayer;

import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author mnowak
 */
public class LayerContainer {

    private SortedMap<LayerType, AbstractLayer> layers;

    private LayerContainer() {
        layers = new TreeMap<>((o1, o2) -> o1.getOrder().compareTo(o2.getOrder()));
    }

    public static Builder builder() {
        return new Builder();
    }

    public WindLayer getWindLayer() {
        return (WindLayer) layers.get(LayerType.WIND);
    }

    public HumidityLayer getHumidityLayer() {
        return (HumidityLayer) layers.get(LayerType.HUMIDITY);
    }

    public GroundLayer getGroundLayer() {
        return (GroundLayer) layers.get(LayerType.GROUND);
    }

    public Collection<AbstractLayer> getLayersOrdered() {
        return layers.values();
    }

    public static class Builder {
        private LayerContainer container;

        private Builder() {
            container = new LayerContainer();
        }

        public Builder addLayer(LayerType type, AbstractLayer layer) {
            container.layers.put(type, layer);
            return this;
        }

        public LayerContainer build() {
            return container;
        }
    }
}
