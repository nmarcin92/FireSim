package pl.edu.agh.miss.firesim.logic.layers.wind;

import pl.edu.agh.miss.firesim.logic.layers.AbstractLayer;

/**
 * @author mnowak
 */
public class WindLayer extends AbstractLayer<WindField> {

    private static final double MAX_WIND_STRENGTH = 100.0;

    public WindLayer(int sizeX, int sizeY) {
        super(sizeX, sizeY);
    }

    @Override
    protected WindField createEmptyField() {
        return new WindField();
    }
}
