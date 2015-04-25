package pl.edu.agh.miss.firesim.utils;

import pl.edu.agh.miss.firesim.AppConstants;
import pl.edu.agh.miss.firesim.enums.Direction;

/**
 * @author mnowak
 */
public class DirectionBounds {
	private final Direction siteDirection;
	private final Direction mainDirection;

	public static DirectionBounds ofVector(Vector vector) {

		double sa = Utils.signum(vector.getX()), sb = Utils.signum(vector.getY());

		Direction main, site;
		if (Math.abs(vector.getX()) >= Math.abs(vector.getY())) {
			main = Direction.getByNormal(sa, 0);
			site = Direction.getByNormal(sa * AppConstants.ONE_OVER_SQRT_2, sb * AppConstants.ONE_OVER_SQRT_2);
		} else {
			main = Direction.getByNormal(0, sb);
			site = Direction.getByNormal(sa * AppConstants.ONE_OVER_SQRT_2, sb * AppConstants.ONE_OVER_SQRT_2);
		}

		return new DirectionBounds(main, site);
	}

	private DirectionBounds(Direction main, Direction site) {
		this.mainDirection = main;
		this.siteDirection = site;
	}

	public Direction getSiteDirection() {
		return siteDirection;
	}

	public Direction getMainDirection() {
		return mainDirection;
	}
}