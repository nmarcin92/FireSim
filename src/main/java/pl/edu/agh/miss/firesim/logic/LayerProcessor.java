package pl.edu.agh.miss.firesim.logic;

import pl.edu.agh.miss.firesim.logic.layers.AbstractLayer;
import pl.edu.agh.miss.firesim.logic.layers.LayerType;
import pl.edu.agh.miss.firesim.logic.layers.ground.GroundLayer;
import pl.edu.agh.miss.firesim.logic.layers.humidity.HumidityLayer;
import pl.edu.agh.miss.firesim.logic.layers.wind.WindLayer;

/**
 * @author mnowak
 */
public class LayerProcessor {

    private final LayerContainer layerContainer;
    private final int sizeX;
    private final int sizeY;

    public LayerProcessor(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        layerContainer = LayerContainer.builder()
                .addLayer(LayerType.WIND, new WindLayer(sizeX, sizeY))
                .addLayer(LayerType.HUMIDITY, new HumidityLayer(sizeX, sizeY))
                .addLayer(LayerType.GROUND, new GroundLayer(sizeX, sizeY))
                .build();
    }

    public void iterate() {
        for (AbstractLayer layer : layerContainer.getLayersOrdered()) {
            layer.propagateFields(layerContainer);
        }

        for (AbstractLayer layer : layerContainer.getLayersOrdered()) {
            layer.updateFields();
        }
    }

}
