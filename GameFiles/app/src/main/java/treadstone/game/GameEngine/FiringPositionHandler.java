package treadstone.game.GameEngine;

import android.util.Log;

public class FiringPositionHandler
{
    // Debug info
    private int         DEBUG = 1;
    private String      DEBUG_TAG = "FPH/";

    private Position    firing_position;
    private double      hyp, width_angle, height_angle;
    private MathHelper  math_helper;

    // Builds the initial firing position parameters that are needed to solve updates wrt FP
    FiringPositionHandler(ArmedEntity a)
    {
        if (DEBUG == 1)
            Log.d(DEBUG_TAG + "CTOR", "w|h = " + a.getWidth() + ", " + a.getHeight());

        math_helper = new MathHelper();
        hyp = math_helper.findHypotenuse(a.getWidth(), a.getHeight()/2);

        // Angles for w|h
        width_angle = math_helper.sineLawCalc(a.getWidth(), hyp);
        height_angle = 90.0d - width_angle;

        if (DEBUG == 1)
            Log.d(DEBUG_TAG + "CTOR", "FPH Created with angles: " + width_angle + ", " + height_angle);
    }

    public void buildFiringPosition(String aim_direction, ArmedEntity a)
    {
        if (DEBUG == 1)
            Log.d(DEBUG_TAG + "buildFP", "Passed aim_direction: " + aim_direction);
        firing_position = new Position(a.getPosition().getX() + a.getWidth(), a.getPosition().getY() + a.getHeight()/2);
    }

    public Position getFiringPosition()
    {
        return firing_position;
    }

}
