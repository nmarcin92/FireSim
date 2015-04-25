package pl.edu.agh.miss.firesim.utils;

import pl.edu.agh.miss.firesim.enums.Direction;

/**
 * @author mnowak
 */
public class DirectionBounds {
    private final Direction siteDirection;
    private final Direction mainDirection;

    public static DirectionBounds ofVector(Vector vector) {
        boolean isHorizontal = Math.abs(vector.getX()) >= Math.abs(vector.getY());
        boolean isPositiveMain = isHorizontal ? vector.getY() >= 0 : vector.getX() >= 0;
        boolean isPositiveSite = isHorizontal ? vector.getX() >= 0 : vector.getY() >= 0;

        Direction main = Direction.getByParams(isHorizontal, isPositiveMain);
        Direction site = Direction.getByParams(!isHorizontal, isPositiveSite);

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