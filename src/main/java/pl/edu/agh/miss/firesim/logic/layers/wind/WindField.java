package pl.edu.agh.miss.firesim.logic.layers.wind;

import pl.edu.agh.miss.firesim.logic.LayerContainer;
import pl.edu.agh.miss.firesim.logic.layers.AbstractField;
import pl.edu.agh.miss.firesim.utils.DirectionBounds;
import pl.edu.agh.miss.firesim.utils.Vector;

/**
 * @author mnowak
 */
public class WindField extends AbstractField<WindAction> {

    private static final double ONE_OVER_SQRT_2 = 1.0 / Math.sqrt(2);

    private final Vector strength;

    public WindField() {
        strength = new Vector();
    }

    @Override
    protected void processFutureActions() {
        strength.setZero()
                .add(getFutureActions()
                        .parallelStream()
                        .map(a -> a.getDelta())
                        .reduce((vector, vector2) -> vector.add(vector2))
                        .get());
    }

    @Override
    public void propagate(LayerContainer layerContainer) {
        DirectionBounds bounds = DirectionBounds.ofVector(strength);

        double x = strength.getX();
        double y = strength.getY();

        double a = (bounds.getMainDirection().isHorizontal() ? y : x) * (bounds.getMainDirection().isPositive() ? 1.0 : -1.0)
                + (bounds.getMainDirection().isHorizontal() ? x : y) * (bounds.getMainDirection().isPositive() && bounds.getSiteDirection().isPositive() ? 1.0 : -1.0);
        double b = (bounds.getMainDirection().isHorizontal() ? x : y) / ONE_OVER_SQRT_2
                * (bounds.getMainDirection().isPositive() ? 1.0 : -1.0);

        double main_factor = a / (a + b);
        double site_factor = b / (a + b);

        getNeighbour(bounds.getMainDirection()).addFutureAction(new WindAction(Vector.scaled(strength, main_factor)));
        getNeighbour(bounds.getMainDirection()).getNeighbour(bounds.getSiteDirection()).addFutureAction(
                new WindAction(Vector.scaled(strength, site_factor)));
    }
}
