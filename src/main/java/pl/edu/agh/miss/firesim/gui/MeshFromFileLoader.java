package pl.edu.agh.miss.firesim.gui;

import pl.edu.agh.miss.firesim.gui.visualization.MeshProcessor;
import pl.edu.agh.miss.firesim.logic.LayerContainer;
import pl.edu.agh.miss.firesim.logic.layers.ground.GroundField;
import pl.edu.agh.miss.firesim.logic.layers.ground.GroundLayer;
import pl.edu.agh.miss.firesim.logic.layers.ground.GroundType;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author mnowak
 */
public class MeshFromFileLoader {

    public static MeshProcessor loadFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            String[] parts = br.readLine().split(",");
            int sizeX = Integer.parseInt(parts[0]) + 2;
            int sizeY = Integer.parseInt(parts[1]) + 2;

            MeshProcessor meshProcessor = new MeshProcessor(sizeX, sizeY);

            LayerContainer layerContainer = meshProcessor.getLayerContainer();
            GroundLayer groundLayer = layerContainer.getGroundLayer();
            for (int y = 1; y < sizeY - 2; ++y) {
                parts = br.readLine().split(",");
                for (int x = 1; x < sizeX - 2; ++x) {
                    GroundType groundType = GroundType.getByShortcut(parts[x - 1].charAt(0));
                    GroundField groundField = groundLayer.getLayerTable().get(y, x);
                    if (groundType == GroundType.TREE || groundType == GroundType.BURNING_TREE) {
                        groundField.initTree(200, groundType == GroundType.BURNING_TREE);
                    } else {
                        groundField.setGroundType(groundType);
                    }
                }
            }

            return meshProcessor;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        // #TODO
        return null;
    }
}
