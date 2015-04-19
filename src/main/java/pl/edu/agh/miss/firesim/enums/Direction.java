package pl.edu.agh.miss.firesim.enums;

/**
 * @author mnowak
 */
public enum Direction {

    NORTH(false, true), SOUTH(false, false), WEST(true, false), EAST(true, true);

    private final boolean isHorizontal;
    private final boolean isPositive;

    private Direction(boolean isHorizontal, boolean isPositive) {
        this.isHorizontal = isHorizontal;
        this.isPositive = isPositive;
    }

    public static Direction getByParams(boolean isHorizontal, boolean isPositive) {
        for (Direction dir : values()) {
            if (dir.isHorizontal == isHorizontal && dir.isPositive == isPositive) {
                return dir;
            }
        }
        throw new UnsupportedOperationException("Unsupported parameters");
    }

    public boolean isHorizontal() {
        return isHorizontal;
    }

    public boolean isPositive() {
        return isPositive;
    }
}
