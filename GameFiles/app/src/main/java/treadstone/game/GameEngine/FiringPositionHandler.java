package treadstone.game.GameEngine;

import android.util.Log;

public class FiringPositionHandler
{
    // Debug info
    private int         DEBUG = 1;
    private String      DEBUG_TAG = "FPH";

    private Position    firing_position;

    // Angles
    private float       width_angle, height_angle;

    FiringPositionHandler(float width, float height)
    {

    }

    // void buildFiringPosition()
    // This function builds the initial firing position parameters that are needed to solve updates wrt FP
    public void buildFiringPosition()
    {
        double angle_x, angle_y, fp_x, fp_y;
    }

    public Position updateFiringPosition(double a)
    {
        if (DEBUG == 2)
            Log.d(DEBUG_TAG + "updateFP", "Movement angle to update FP with: " + a);

        double x = 0.0d, y = 0.0d;

        if ((a >= 0.0d && a <= 90.0d) || (a >= 270.0d && a <= 360.0d)) // Q1 && Q4
        {
            // x = Math.cos(Math.toRadians(a)) * getSpeed();
            if (DEBUG == 2)
                Log.d(DEBUG_TAG, "[X] Angle is within (0<x<90) OR (270<x<360) -> POSITIVE: " + x);
        }

        else // Q2 && Q3
        {
            // x = Math.cos(Math.toRadians(a)) * getSpeed();
            if (DEBUG == 2)
                Log.d(DEBUG_TAG, "[X] Angle is within (90<x<270) -> NEGATIVE: " + x);
        }

        if (a >= 180.0d && a <= 360.0d) // Q3 && Q4
        {
            // y = Math.abs(Math.sin(Math.toRadians(a)) * getSpeed());
            if (DEBUG == 2)
                Log.d(DEBUG_TAG, "[Y] Angle is within (180<x<360) -> POSITIVE: " + y);
        }

        else // Q1 && Q2
        {
            // y = 0 - Math.sin(Math.toRadians(a)) * getSpeed();
            if (DEBUG == 2)
                Log.d(DEBUG_TAG, "[Y] Angle is within (0<x<180) -> NEGATIVE: " + y);
        }

        if (DEBUG == 2)
        {
            Log.d(DEBUG_TAG, "calcDisplacement value X: " + x + " converted into float: " + (float) x);
            Log.d(DEBUG_TAG, "calcDisplacement value Y: " + y + " converted into float: " + (float) y);
        }

        return new Position();

    }

    public Position getFiringPosition()
    {
        return firing_position;
    }

}
