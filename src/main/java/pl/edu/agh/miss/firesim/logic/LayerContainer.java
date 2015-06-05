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
    private final int sizeX, sizeY;

    private LayerContainer(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        layers = new TreeMap<>((o1, o2) -> o1.getOrder().compareTo(o2.getOrder()));
    }

    public static Builder builder(int sizeX, int sizeY) {
        return new Builder(sizeX, sizeY);
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

        private Builder(int sizeX, int sizeY) {
            container = new LayerContainer(sizeX, sizeY);
        }

        public Builder addLayer(LayerType type, AbstractLayer layer) {
            container.layers.put(type, layer);
            return this;
        }

        public LayerContainer build() {
            return container;
        }
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }
}
