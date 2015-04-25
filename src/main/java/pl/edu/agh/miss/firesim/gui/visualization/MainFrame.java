package pl.edu.agh.miss.firesim.gui.visualization;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import pl.edu.agh.miss.firesim.AppConstants;

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
		meshProcessor = new MeshProcessor(AppConstants.INITIAL_SIZE_X, AppConstants.INITIAL_SIZE_Y);
		meshCanvas = meshProcessor.getMeshCanvas();

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		add(meshCanvas);
		setSize(800, 600);
		setVisible(true);
		createBufferStrategy(2);

		meshProcessor.start();

	}

}
