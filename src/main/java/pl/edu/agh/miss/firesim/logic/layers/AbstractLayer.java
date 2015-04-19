package pl.edu.agh.miss.firesim.logic.layers;

import com.google.common.collect.*;
import pl.edu.agh.miss.firesim.enums.Direction;
import pl.edu.agh.miss.firesim.logic.LayerContainer;

/**
 * @author mnowak
 */
public abstract class AbstractLayer<T extends AbstractField> {

    private final int sizeX, sizeY;
    private final Table<Integer, Integer, T> layer;

    public AbstractLayer(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;

        layer = ArrayTable.create(ContiguousSet.create(Range.closed(0, sizeX), DiscreteDomain.integers()).asList(),
                ContiguousSet.create(Range.closed(0, sizeY), DiscreteDomain.integers()).asList());

        for (Integer row : layer.rowKeySet()) {
            for (Integer col : layer.columnKeySet()) {
                layer.put(row, col, createEmptyField());
            }
        }

        setFieldsNeighbours();

    }


    public void propagateFields(LayerContainer layerContainer) {
        for (Table.Cell<Integer, Integer, T> cell : layer.cellSet()) {
            cell.getValue().propagate(layerContainer);
        }
    }

    public void updateFields() {
        for (Table.Cell<Integer, Integer, T> cell : layer.cellSet()) {
            cell.getValue().update();
        }
    }

    protected Table<Integer, Integer, T> getLayerTable() {
        return layer;
    }

    private void setFieldsNeighbours() {
        for (Integer row : layer.rowKeySet()) {
            for (Integer col : layer.columnKeySet()) {
                if (row > 0) {
                    layer.get(row, col).setNeighbour(Direction.NORTH, layer.get(row - 1, col));
                    layer.get(row - 1, col).setNeighbour(Direction.SOUTH, layer.get(row, col));
                }
                if (col > 0) {
                    layer.get(row, col).setNeighbour(Direction.WEST, layer.get(row, col - 1));
                    layer.get(row, col - 1).setNeighbour(Direction.EAST, layer.get(row, col));
                }
            }
        }
    }

    protected abstract T createEmptyField();


}
