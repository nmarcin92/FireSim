package pl.edu.agh.miss.firesim.enums;

import pl.edu.agh.miss.firesim.AppConstants;
import pl.edu.agh.miss.firesim.utils.Vector;

/**
 * @author mnowak
 */
public enum Direction {

    N(0, -1), S(0, 1), W(-1, 0), E(1, 0),
    NE(AppConstants.ONE_OVER_SQRT_2, -AppConstants.ONE_OVER_SQRT_2),
    SE(AppConstants.ONE_OVER_SQRT_2, AppConstants.ONE_OVER_SQRT_2),
    SW(-AppConstants.ONE_OVER_SQRT_2, AppConstants.ONE_OVER_SQRT_2),
    NW(-AppConstants.ONE_OVER_SQRT_2, -AppConstants.ONE_OVER_SQRT_2);

    private final Vector normalVector;

    private Direction(double x, double y) {
        normalVector = new Vector(x, y);
    }

    public Vector getNormalVector() {
        return normalVector;
    }

    public Vector getProjection(Vector vec) {
        Vector proj = new Vector(0, 0);
        if (normalVector.getX() == 0) {
            proj.setX(vec.getY());
        } else if (normalVector.getY() == 0) {
            proj.setX(vec.getX());
        } else if (normalVector.getX() == normalVector.getY()) {
            double a = (vec.getX() + vec.getY()) / 2.0;
            proj.setX(a);
            proj.setY(a);
        } else {
            double a = (vec.getX() - vec.getY()) / 2.0;
            proj.setX(a);
            proj.setY(-a);
        }
        return proj;
    }

    public static Direction getByNormal(double a, double b) {
        for (Direction dir : values()) {
            if (dir.getNormalVector().getX() == a && dir.getNormalVector().getY() == b) {
                return dir;
            }
        }
        throw new UnsupportedOperationException("Unsupported normal vector");
    }
}
