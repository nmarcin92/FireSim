package pl.edu.agh.miss.firesim.logic.layers;

import pl.edu.agh.miss.firesim.enums.Direction;
import pl.edu.agh.miss.firesim.logic.LayerContainer;
import pl.edu.agh.miss.firesim.logic.LayerProcessor.DynamicState;

import com.google.common.collect.ArrayTable;
import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Range;
import com.google.common.collect.Table;

/**
 * @author mnowak
 */
public abstract class AbstractLayer<T extends AbstractField> {

	private final int sizeX, sizeY;
	private final Table<Integer, Integer, T> layer;
	private final DynamicState simulationState;

	public AbstractLayer(int sizeX, int sizeY, DynamicState simulationState) {
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.simulationState = simulationState;
		layer = ArrayTable.create(ContiguousSet.create(Range.closed(0, sizeX), DiscreteDomain.integers()).asList(),
				ContiguousSet.create(Range.closed(0, sizeY), DiscreteDomain.integers()).asList());

		for (Integer row : layer.rowKeySet()) {
			for (Integer col : layer.columnKeySet()) {
				layer.put(row, col, createEmptyField(row, col));
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

	public Table<Integer, Integer, T> getLayerTable() {
		return layer;
	}

	private void setFieldsNeighbours() {
		for (Integer row : layer.rowKeySet()) {
			for (Integer col : layer.columnKeySet()) {
				if (row > 0) {
					layer.get(row, col).setNeighbour(Direction.N, layer.get(row - 1, col));
					layer.get(row - 1, col).setNeighbour(Direction.S, layer.get(row, col));
					if (col > 0) {
						layer.get(row, col).setNeighbour(Direction.NW, layer.get(row - 1, col - 1));
						layer.get(row - 1, col - 1).setNeighbour(Direction.SE, layer.get(row, col));
					}
					if (col < sizeX - 1) {
						layer.get(row, col).setNeighbour(Direction.NE, layer.get(row - 1, col + 1));
						layer.get(row - 1, col + 1).setNeighbour(Direction.SW, layer.get(row, col));
					}
				}
				if (col > 0) {
					layer.get(row, col).setNeighbour(Direction.W, layer.get(row, col - 1));
					layer.get(row, col - 1).setNeighbour(Direction.E, layer.get(row, col));
				}
			}
		}
	}

	protected int getSizeX() {
		return sizeX;
	}

	protected int getSizeY() {
		return sizeY;
	}

	protected DynamicState getSimulationState() {
		return simulationState;
	}

	protected abstract T createEmptyField(Integer row, Integer col);

}
