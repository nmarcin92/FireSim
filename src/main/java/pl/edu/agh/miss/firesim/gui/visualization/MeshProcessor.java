package pl.edu.agh.miss.firesim.gui.visualization;

import com.google.common.collect.Table;
import pl.edu.agh.miss.firesim.logic.LayerContainer;
import pl.edu.agh.miss.firesim.logic.LayerProcessor;
import pl.edu.agh.miss.firesim.logic.layers.wind.WindField;
import pl.edu.agh.miss.firesim.logic.layers.wind.WindLayer;

import java.awt.*;

/**
 * @author mnowak
 */
public class MeshProcessor extends Thread {

    private final LayerProcessor layerProcessor;
    private final LayerContainer layerContainer;
    private final MeshCanvas meshCanvas;


    public MeshProcessor(int sizeX, int sizeY) {
        meshCanvas = new MeshCanvas(sizeX, sizeY);
        layerProcessor = new LayerProcessor(sizeX, sizeY);
        layerContainer = layerProcessor.getLayerContainer();

    }

    public MeshCanvas getMeshCanvas() {
        return meshCanvas;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(layerProcessor.getSimulationState().getValue(LayerProcessor.StateParameter.SIMULATION_SPEED));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            layerProcessor.iterate();
            updateMesh();
        }
    }

    private void updateMesh() {
        Table<Integer, Integer, Color> colorTable = meshCanvas.getColorTable();
        Table<Integer, Integer, WindField> windLayer = layerProcessor.getLayerContainer().getWindLayer().getLayerTable();
        for (Table.Cell<Integer, Integer, WindField> cell : windLayer.cellSet()) {
            float strengthLength = (float) cell.getValue().getStrength().getLength();
            colorTable.put(cell.getRowKey(), cell.getColumnKey(),
                    new Color(0.5f, 0.5f, strengthLength / (float) WindLayer.MAX_WIND_STRENGTH, 1.0f));

        }

        meshCanvas.repaint();
    }

}
