package pl.edu.agh.miss.firesim.logic.layers.ground;

import pl.edu.agh.miss.firesim.logic.layers.AbstractLayer;

/**
 * @author mnowak
 */
public class GroundLayer extends AbstractLayer<GroundField> {

    public GroundLayer(int sizeX, int sizeY) {
        super(sizeX, sizeY);
    }

    @Override
    protected GroundField createEmptyField() {
        return null;
    }
}
