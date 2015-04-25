package pl.edu.agh.miss.firesim.logic.layers;

import com.google.common.collect.Maps;
import pl.edu.agh.miss.firesim.enums.Direction;
import pl.edu.agh.miss.firesim.logic.LayerContainer;
import pl.edu.agh.miss.firesim.logic.LayerProcessor.DynamicState;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * @author mnowak
 */
public abstract class AbstractField<T extends AbstractAction> {

    private final Map<Direction, AbstractField> neighbours = Maps.newEnumMap(Direction.class);
    private final Queue<T> futureActions = new LinkedList<>();
    private final DynamicState simulationState;

    public AbstractField(DynamicState simulationState) {
        this.simulationState = simulationState;
    }

    public AbstractField getNeighbour(Direction dir) {
        return neighbours.containsKey(dir) ? neighbours.get(dir) : this;
    }

    public void setNeighbour(Direction dir, AbstractField neighbour) {
        neighbours.put(dir, neighbour);
    }

    public void addFutureAction(T action) {
        futureActions.add(action);
    }

    protected Queue<T> getFutureActions() {
        return futureActions;
    }

    protected abstract void processFutureActions();

    public void update() {
        processFutureActions();
        futureActions.clear();
    }

    public abstract void propagate(LayerContainer layerContainer);

    protected DynamicState getSimulationState() {
        return simulationState;
    }
}