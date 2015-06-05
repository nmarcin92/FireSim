package pl.edu.agh.miss.firesim.gui;

import pl.edu.agh.miss.firesim.AppConstants;
import pl.edu.agh.miss.firesim.gui.visualization.MeshCanvas;
import pl.edu.agh.miss.firesim.gui.visualization.MeshProcessor;

import javax.swing.*;
import java.awt.*;

/**
 * @author mnowak
 */
public class MainFrame extends JFrame {

    private MeshCanvas meshCanvas;
    private MeshProcessor meshProcessor;

    public static void main(String[] args) {
        new MainFrame().initGui();
    }


    public void initGui() {


        setSize(785, 615);
        setTitle("Forest fire simulator");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }


        setLayout(new BorderLayout(3, 3));

        meshProcessor = new MeshProcessor(AppConstants.INITIAL_SIZE_X, AppConstants.INITIAL_SIZE_Y);
        meshProcessor = MeshFromFileLoader.loadFromFile("E:/simple.csv");
        meshCanvas = meshProcessor.getMeshCanvas();
        meshCanvas.setSize(new Dimension(500, 500));
        meshProcessor.start();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        add(meshCanvas, BorderLayout.CENTER);
        add(new OptionsPanel(meshProcessor.getLayerProcessor().getSimulationState()), BorderLayout.EAST);

        setVisible(true);

    }

}
