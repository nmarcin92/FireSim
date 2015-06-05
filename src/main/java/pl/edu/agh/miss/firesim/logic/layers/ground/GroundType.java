package pl.edu.agh.miss.firesim.logic.layers.ground;

/**
 * @author mnowak
 */
public enum GroundType {
    GRASS('G'), BURNING_TREE('B'), TREE('T'), BURNED_TREE('X'), WATER('W');

    private final char shortcut;

    private GroundType(char shortcut) {
        this.shortcut = shortcut;
    }

    public static GroundType getByShortcut(char shortcut) {
        for (GroundType groundType : GroundType.values()) {
            if (groundType.shortcut == shortcut) {
                return groundType;
            }
        }

        throw new UnsupportedOperationException("Invalid shortcut: " + shortcut);

    }
}
