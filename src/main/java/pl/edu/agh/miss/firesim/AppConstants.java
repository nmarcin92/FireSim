package pl.edu.agh.miss.firesim;

import pl.edu.agh.miss.firesim.logic.layers.wind.WindType;
import pl.edu.agh.miss.firesim.utils.Vector;

/**
 * @author mnowak
 */
public interface AppConstants {

    public static final double ONE_OVER_SQRT_2 = 1.0 / Math.sqrt(2);

    public static final int INITIAL_SIZE_X = 100;
    public static final int INITIAL_SIZE_Y = 100;
    public static final int INITIAL_SIMULATION_SPEED = 100;
    public static final float INITIAL_WIND_RANDOMNESS = 0.00f;
    public static final WindType INITIAL_WIND_TYPE = WindType.SINUSOIDAL;
    public static final int INITIAL_AIR_HUMIDITY = 20;
    public static final int INITIAL_WATER_HUMIDITY = 80;
    public static final float INITIAL_WIND_SIN_FREQ = 0.05f;
    public static final Vector INITIAL_WIND_DIRECTION = new Vector(0, 0);
    public static final Float INITIAL_HUMIDITY_CONVERGENCE = 0.1f;
    public static final Float INITIAL_BURNING_DESCENT = 0.1f;
    public static final Integer INITIAL_DENSITY_DESCENT = 1;
}
