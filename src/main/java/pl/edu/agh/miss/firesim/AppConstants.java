package pl.edu.agh.miss.firesim;

import pl.edu.agh.miss.firesim.logic.layers.wind.WindType;
import pl.edu.agh.miss.firesim.utils.Vector;

/**
 * @author mnowak
 */
public class AppConstants {

    public static final int INITIAL_SIZE_X = 100;
    public static final int INITIAL_SIZE_Y = 100;
    public static final long INITIAL_SIMULATION_SPEED = 40l;
    public static final float INITIAL_WIND_RANDOMNESS = 0.00f;
    public static final WindType INITIAL_WIND_TYPE = WindType.SINUSOIDAL;
    public static final int INITIAL_AIR_HUMIDITY = 30;
    public static final float INITIAL_WIND_SIN_FREQ = 0.01f;
    public static final Vector INITIAL_WIND_DIRECTION = new Vector(1, 0);

    private AppConstants() {
    }


}
