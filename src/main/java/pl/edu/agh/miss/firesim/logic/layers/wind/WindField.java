package pl.edu.agh.miss.firesim.logic.layers.wind;

import java.util.Random;

import pl.edu.agh.miss.firesim.logic.LayerContainer;
import pl.edu.agh.miss.firesim.logic.LayerProcessor;
import pl.edu.agh.miss.firesim.logic.LayerProcessor.DynamicState;
import pl.edu.agh.miss.firesim.logic.layers.AbstractField;
import pl.edu.agh.miss.firesim.utils.DirectionBounds;
import pl.edu.agh.miss.firesim.utils.Vector;

/**
 * @author mnowak
 */
public class WindField extends AbstractField<WindAction> {

	private static final Random RAND = new Random();

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

			getNeighbour(bounds.getMainDirection()).addFutureAction(new WindAction(Vector.scaled(strength, main_factor)));
			getNeighbour(bounds.getMainDirection()).getNeighbour(bounds.getSiteDirection()).addFutureAction(
					new WindAction(Vector.scaled(strength, site_factor)));
		}
	}

	public Vector getStrength() {
		return strength;
	}
}
