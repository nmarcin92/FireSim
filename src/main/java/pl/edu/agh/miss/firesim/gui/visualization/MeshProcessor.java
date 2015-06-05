package pl.edu.agh.miss.firesim.gui.visualization;

import com.google.common.collect.Table;
import pl.edu.agh.miss.firesim.logic.LayerContainer;
import pl.edu.agh.miss.firesim.logic.LayerProcessor;
import pl.edu.agh.miss.firesim.logic.layers.ground.GroundField;
import pl.edu.agh.miss.firesim.logic.layers.ground.GroundLayer;
import pl.edu.agh.miss.firesim.logic.layers.ground.GroundType;
import pl.edu.agh.miss.firesim.logic.layers.humidity.HumidityField;
import pl.edu.agh.miss.firesim.logic.layers.wind.WindField;
import pl.edu.agh.miss.firesim.logic.layers.wind.WindLayer;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

/**
 * @author mnowak
 */
public class MeshProcessor extends Thread {

    private final LayerProcessor layerProcessor;
    private final LayerContainer layerContainer;
    private final MeshCanvas meshCanvas;
    private final static Random RAND = new Random();


    public MeshProcessor(int sizeX, int sizeY) {
        meshCanvas = new MeshCanvas(sizeX, sizeY);
        meshCanvas.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX() / Math.round((float) meshCanvas.getWidth() / sizeX);
                int y = e.getY() / Math.round((float) meshCanvas.getHeight() / sizeY);
                GroundField groundField = layerContainer.getGroundLayer().getLayerTable().get(y, x);
                if (groundField.getGroundType() == GroundType.TREE) {
                    groundField.setGroundType(GroundType.BURNING_TREE);
                    System.out.println("Burning tree on " + x + ", " + y);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        layerProcessor = new LayerProcessor(sizeX, sizeY);
        layerContainer = layerProcessor.getLayerContainer();

    }

    public LayerProcessor getLayerProcessor() {
        return layerProcessor;
    }


    public MeshCanvas getMeshCanvas() {
        return meshCanvas;
    }

    @Override
    public void run() {
        int timeToSleep = 0;
        while (true) {
            try {
                timeToSleep = layerProcessor.getSimulationState().getValue(LayerProcessor.StateParameter.SIMULATION_SPEED);
                Thread.sleep(timeToSleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            layerProcessor.iterate();
            updateMesh();
        }
    }

    private void updateMesh() {
        Table<Integer, Integer, Color> colorTable = meshCanvas.getColorTable();
        Table<Integer, Integer, WindField> windLayer = layerContainer.getWindLayer().getLayerTable();
        Table<Integer, Integer, HumidityField> humidityLayer = layerContainer.getHumidityLayer().getLayerTable();
        Table<Integer, Integer, GroundField> groundLayer = layerContainer.getGroundLayer().getLayerTable();
        for (int row = 0; row < windLayer.rowKeySet().size(); ++row) {
            for (int col = 0; col < windLayer.columnKeySet().size(); ++col) {
                Boolean showWind = layerProcessor.getSimulationState().getValue(LayerProcessor.StateParameter.SHOW_WIND_LAYER);
                Boolean showHumidity = layerProcessor.getSimulationState().getValue(LayerProcessor.StateParameter.SHOW_HUMIDITY_LAYER);
                Boolean showGround = layerProcessor.getSimulationState().getValue(LayerProcessor.StateParameter.SHOW_GROUND_LAYER);

                float windStrengthFactor = showWind ? (float) windLayer.get(row, col).getStrength().getLength() / (float) WindLayer.MAX_WIND_STRENGTH : 0.5f;
                float humidityFactor = showHumidity ? humidityLayer.get(row, col).getHumidity() / 100.0f : 0.5f;
                float groundFactor = 0.5f;
                if (showGround) {
                    GroundField groundField = groundLayer.get(row, col);
                    if (groundField.getGroundType() == GroundType.BURNING_TREE) {
                        groundFactor = (float) groundField.getDensity() / GroundLayer.MAX_DENSITY;
                    } else {
                        groundFactor = groundField.getGroundType() == GroundType.TREE ? 0.5f : 0.8f;
                    }
                }
                colorTable.put(row, col, new Color(humidityFactor, groundFactor, windStrengthFactor));
                //colorTable.put(row, col, new Color(groundFactor, 0.5f, 0.5f));
            }
        }

        meshCanvas.repaint();
    }

    public LayerContainer getLayerContainer() {
        return layerContainer;
    }

}
