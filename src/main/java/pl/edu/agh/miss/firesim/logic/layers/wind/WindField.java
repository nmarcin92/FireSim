package pl.edu.agh.miss.firesim.logic.layers.wind;

import pl.edu.agh.miss.firesim.logic.LayerContainer;
import pl.edu.agh.miss.firesim.logic.LayerProcessor;
import pl.edu.agh.miss.firesim.logic.LayerProcessor.DynamicState;
import pl.edu.agh.miss.firesim.logic.layers.AbstractField;
import pl.edu.agh.miss.firesim.utils.DirectionBounds;
import pl.edu.agh.miss.firesim.utils.Vector;

import java.util.Random;

/**
 * @author mnowak
 */
public class WindField extends AbstractField<WindAction> {

    private static final Random RAND = new Random();
    private static final double ONE_OVER_SQRT_2 = 1.0 / Math.sqrt(2);

    private final Vector strength;

    public WindField(DynamicState simulationState) {
        super(simulationState);
        strength = new Vector();
    }

    @Override
    protected void processFutureActions() {
        strength.setZero();
        if (getFutureActions().size() > 0) {
            strength.add(getFutureActions()
                    .parallelStream()
                    .map(WindAction::getDelta)
                    .reduce(Vector::sum)
                    .get());
        }

        float windRandomness = getSimulationState().getValue(LayerProcessor.StateParameter.WIND_RANDOMNESS);
        strength.add((RAND.nextDouble() - 0.5) * strength.getX() * windRandomness,
                (RAND.nextDouble() - 0.5) * strength.getY() * windRandomness);

        if (strength.getLength() > WindLayer.MAX_WIND_STRENGTH) {
            strength.scale(WindLayer.MAX_WIND_STRENGTH / strength.getLength());
        }

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

        if (a + b > 0) {
            double main_factor = a / (a + b);
            double site_factor = b / (a + b);

            getNeighbour(bounds.getMainDirection()).addFutureAction(new WindAction(Vector.scaled(strength, main_factor)));
            getNeighbour(bounds.getMainDirection()).getNeighbour(bounds.getSiteDirection()).addFutureAction(
                    new WindAction(Vector.scaled(strength, site_factor)));
        }
    }

    public Vector getStrength() {
        return strength;
    }
}
