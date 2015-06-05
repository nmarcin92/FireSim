package pl.edu.agh.miss.firesim.logic.layers.wind;

import pl.edu.agh.miss.firesim.logic.LayerContainer;
import pl.edu.agh.miss.firesim.logic.LayerProcessor;
import pl.edu.agh.miss.firesim.logic.LayerProcessor.DynamicState;
import pl.edu.agh.miss.firesim.logic.layers.AbstractField;
import pl.edu.agh.miss.firesim.utils.DirectionBounds;
import pl.edu.agh.miss.firesim.utils.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author mnowak
 */
public class WindField extends AbstractField<WindAction> {

    private static final Random RAND = new Random();

    private final Vector strength;
    private final List<WindAction> influences;

    public WindField(int x, int y, DynamicState simulationState) {
        super(x, y, simulationState);
        strength = new Vector();
        influences = new ArrayList<>();
    }

    public List<WindAction> getInfluences() {
        return influences;
    }

    @Override
    protected void processFutureActions(LayerContainer layerContainer) {
        influences.clear();
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

        double a, b;
        Vector mainVector = bounds.getMainDirection().getNormalVector();
        Vector siteVector = bounds.getSiteDirection().getNormalVector();
        if (mainVector.getY() == 0) {
            b = y / siteVector.getY();
            a = (x - siteVector.getX() * b) * Math.signum(mainVector.getX());
        } else {
            b = x / siteVector.getX();
            a = (y - siteVector.getY() * b) * Math.signum(mainVector.getY());

        }

        if (a + b > 0) {
            double main_factor = a / (a + b);
            double site_factor = b / (a + b);

            WindAction mainNeighbourAction = new WindAction(Vector.scaled(strength, main_factor), (WindField) getNeighbour(bounds.getMainDirection()));
            WindAction siteNeighbourAction = new WindAction(Vector.scaled(strength, site_factor), (WindField) getNeighbour(bounds.getSiteDirection()));

            influences.add(mainNeighbourAction);
            influences.add(siteNeighbourAction);

            getNeighbour(bounds.getMainDirection()).addFutureAction(mainNeighbourAction);
            getNeighbour(bounds.getSiteDirection()).addFutureAction(siteNeighbourAction);
        }
    }

    public Vector getStrength() {
        return strength;
    }
}
