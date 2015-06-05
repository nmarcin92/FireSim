package pl.edu.agh.miss.firesim.logic.layers.ground;

import pl.edu.agh.miss.firesim.enums.Direction;
import pl.edu.agh.miss.firesim.logic.LayerContainer;
import pl.edu.agh.miss.firesim.logic.LayerProcessor;
import pl.edu.agh.miss.firesim.logic.layers.AbstractField;
import pl.edu.agh.miss.firesim.logic.layers.humidity.HumidityField;
import pl.edu.agh.miss.firesim.logic.layers.humidity.HumidityLayer;
import pl.edu.agh.miss.firesim.logic.layers.wind.WindField;
import pl.edu.agh.miss.firesim.utils.Utils;
import pl.edu.agh.miss.firesim.utils.Vector;

import java.util.Random;

import static pl.edu.agh.miss.firesim.logic.LayerProcessor.DynamicState;

/**
 * @author mnowak
 */
public class GroundField extends AbstractField<GroundAction> {

    private static final Random RAND = new Random();
    private GroundType groundType;
    private int baseDensity;
    private int density;

    public GroundField(int x, int y, DynamicState simulationState) {
        super(x, y, simulationState);
        groundType = GroundType.GRASS;
        baseDensity = density = 0;
    }

    public void initTree(int baseDensity, boolean isBurning) {
        this.density = this.baseDensity = baseDensity;
        groundType = isBurning ? GroundType.BURNING_TREE : GroundType.TREE;
    }

    @Override
    protected void processFutureActions(LayerContainer layerContainer) {

        if (groundType == GroundType.BURNING_TREE) {
            int densityDescent = getSimulationState().getValue(LayerProcessor.StateParameter.DENSITY_DESCENT);
            density -= densityDescent;
            if (density <= 0) {
                density = 0;
                groundType = GroundType.BURNED_TREE;
            }
        } else if (groundType == GroundType.TREE) {
            for (GroundAction groundAction : getFutureActions()) {
                double fireProbability = groundAction.getDensityFactor() * groundAction.getHumidityFactor() * groundAction.getWindFactor();
                if (RAND.nextDouble() < fireProbability) {
                    groundType = GroundType.BURNING_TREE;
                    density = baseDensity;
                }
            }
        }

    }

    @Override
    @SuppressWarnings("unchecked")
    public void propagate(LayerContainer layerContainer) {
        if (groundType == GroundType.BURNING_TREE) {
            WindField windField = layerContainer.getWindLayer().getLayerTable().get(getRowKey(), getColumnKey());
            HumidityField humidityField = layerContainer.getHumidityLayer().getLayerTable().get(getRowKey(), getColumnKey());
            for (Direction dir : Direction.values()) {
                Vector proj = dir.getProjection(windField.getStrength());
                double windFactor = 0.5 + proj.getLength() / windField.getStrength().getLength() * (Utils.signum(proj.getX()) == Utils.signum(dir.getNormalVector().getX()) ? 1.0 : -1.0) / 2.0;
                double humidityFactor = (double) humidityField.getHumidity() / HumidityLayer.MAX_HUMIDITY;
                double densityFactor = (double) density / baseDensity;
                getNeighbour(dir).addFutureAction(new GroundAction(windFactor, humidityFactor, densityFactor));
            }
        }
    }

    public GroundType getGroundType() {
        return groundType;
    }

    public void setGroundType(GroundType groundType) {
        this.groundType = groundType;
    }

    public int getDensity() {
        return density;
    }

    public void setDensity(int density) {
        this.density = density;
    }
}
