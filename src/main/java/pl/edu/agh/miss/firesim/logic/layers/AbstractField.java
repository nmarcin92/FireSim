package pl.edu.agh.miss.firesim.logic.layers;

import pl.edu.agh.miss.firesim.enums.Direction;

import java.util.EnumMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * @author mnowak
 */
public abstract class AbstractField<T extends AbstractAction> {

    private final Map<Direction, AbstractField> neighbours = new EnumMap<>(Direction.class);
    private final Queue<T> futureActions = new LinkedList<>();

    public AbstractField getNeighbour(Direction dir) {
        return neighbours.get(dir);
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

}