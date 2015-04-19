package pl.edu.agh.miss.firesim.logic.layers.humidity;

import pl.edu.agh.miss.firesim.logic.layers.AbstractLayer;

/**
 * @author mnowak
 */
public class HumidityLayer extends AbstractLayer<HumidityField> {

    public HumidityLayer(int sizeX, int sizeY) {
        super(sizeX, sizeY);
    }

    @Override
    protected HumidityField createEmptyField() {
        return null;
    }
}
