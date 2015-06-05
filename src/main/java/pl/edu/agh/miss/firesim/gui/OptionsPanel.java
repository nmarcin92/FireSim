package pl.edu.agh.miss.firesim.gui;

import com.google.common.collect.Maps;
import pl.edu.agh.miss.firesim.AppConstants;
import pl.edu.agh.miss.firesim.logic.LayerProcessor.DynamicState;
import pl.edu.agh.miss.firesim.logic.LayerProcessor.StateParameter;
import pl.edu.agh.miss.firesim.utils.Vector;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

/**
 * @author mnowak
 */
public class OptionsPanel extends JPanel {

    private Map<StateParameter, JSlider> slidersMap = Maps.newEnumMap(StateParameter.class);
    private final DynamicState STATE;
    private final Box BOX;

    private void addSliderOption(int def, int min, int max, StateParameter param, String label) {
        JSlider slider = new JSlider(JSlider.HORIZONTAL, min, max, def);
        slider.addChangeListener(e -> {
            STATE.requestChange(param, slider.getValue());
        });
        BOX.add(new JLabel(label));
        BOX.add(slider);
        slidersMap.put(param, slider);
    }

    private void addSliderOption(double def, double min, double max, StateParameter param, String label) {
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        slider.addChangeListener(e -> {
            float scalled = (float) (min + slider.getValue() / 100.0 * (max - min));
            STATE.requestChange(param, scalled);
        });
        BOX.add(new JLabel(label));
        BOX.add(slider);
        slidersMap.put(param, slider);
    }

    public OptionsPanel(final DynamicState state) {
        STATE = state;
        BOX = Box.createVerticalBox();

        addSliderOption(AppConstants.INITIAL_AIR_HUMIDITY, 0, 100, StateParameter.AIR_HUMIDITY, "Air humidity");
        addSliderOption(AppConstants.INITIAL_WIND_SIN_FREQ, 0.0, 0.1, StateParameter.WIND_SINUSOIDAL_FREQ, "Wind frequency");
        addSliderOption(AppConstants.INITIAL_WIND_RANDOMNESS, 0.0, 0.3, StateParameter.WIND_RANDOMNESS, "Wind randomness");
        addSliderOption(AppConstants.INITIAL_SIMULATION_SPEED, 0, 300, StateParameter.SIMULATION_SPEED, "Simulation speed");
        addSliderOption(AppConstants.INITIAL_HUMIDITY_CONVERGENCE, 0.0, 0.5, StateParameter.HUMIDITY_CONVERGENCE_FACTOR, "Humidity convergence speed");
        addSliderOption(AppConstants.INITIAL_BURNING_DESCENT, 0.0, 0.3, StateParameter.BURNING_HUMIDITY_DESCENT, "Burning humidity descent");
        addSliderOption(AppConstants.INITIAL_WATER_HUMIDITY, 0, 100, StateParameter.WATER_HUMIDITY, "Water humidity");
        addSliderOption(AppConstants.INITIAL_DENSITY_DESCENT, 0, 20, StateParameter.DENSITY_DESCENT, "Burning speed");

        JSpinner xWindTextField = new JSpinner();
        JSpinner yWindTextField = new JSpinner();

        xWindTextField.setModel(new SpinnerNumberModel(AppConstants.INITIAL_WIND_DIRECTION.getX(), 0, 1.0, 0.05));
        xWindTextField.addChangeListener(e -> {
            Vector newDirection = new Vector(((Number) xWindTextField.getValue()).doubleValue(), ((Number) yWindTextField.getValue()).doubleValue());
            STATE.requestChange(StateParameter.WIND_DIRECTION, newDirection);
        });
        BOX.add(new JLabel("Wind x-coordinate"));
        BOX.add(xWindTextField);

        yWindTextField.setModel(new SpinnerNumberModel(AppConstants.INITIAL_WIND_DIRECTION.getY(), 0, 1.0, 0.05));
        yWindTextField.addChangeListener(e -> {
            Vector newDirection = new Vector(((Number) xWindTextField.getValue()).doubleValue(), ((Number) yWindTextField.getValue()).doubleValue());
            STATE.requestChange(StateParameter.WIND_DIRECTION, newDirection);
        });
        BOX.add(new JLabel("Wind y-coordinate"));
        BOX.add(yWindTextField);

        JCheckBox showWindBox = new JCheckBox();
        showWindBox.setText("Show wind layer");
        showWindBox.setSelected(true);
        showWindBox.addChangeListener(e -> STATE.requestChange(StateParameter.SHOW_WIND_LAYER, showWindBox.isSelected()));
        BOX.add(showWindBox);

        JCheckBox showHumidityBox = new JCheckBox();
        showHumidityBox.setText("Show humidity layer");
        showHumidityBox.setSelected(true);
        showHumidityBox.addChangeListener(e -> STATE.requestChange(StateParameter.SHOW_HUMIDITY_LAYER, showHumidityBox.isSelected()));
        BOX.add(showHumidityBox);

        JCheckBox showGroundBox = new JCheckBox();
        showGroundBox.setText("Show ground layer");
        showGroundBox.setSelected(true);
        showGroundBox.addChangeListener(e -> STATE.requestChange(StateParameter.SHOW_GROUND_LAYER, showGroundBox.isSelected()));
        BOX.add(showGroundBox);

        add(BOX);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
    }

}
