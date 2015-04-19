package pl.edu.agh.miss.firesim.logic.layers;

/**
 * @author mnowak
 */
public enum LayerType {
    WIND(1), HUMIDITY(2), GROUND(3);

    private int order;

    private LayerType(int order) {
        this.order = order;
    }

    public Integer getOrder() {
        return order;
    }
}
