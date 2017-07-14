package treadstone.game.GameEngine;

import android.util.Log;

public class AimBoundHandler
{
    // Debug info
    private String      DEBUG_TAG = "AimBounds";
    private int         DEBUG     = 1;

    private double      aim_angle;
    private Position    aim_bounds;

    AimBoundHandler()
    {
        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "AimBounds init (blank)");
        init();
    }

    private void init()
    {
        aim_bounds = new Position();
        aim_angle = 0.0f;
    }

    public double getAimAngle()
    {
        return aim_angle;
    }

    public Position getAimBounds()
    {
        return aim_bounds;
    }

    //
    public boolean angleBoundsTest(double test_angle)
    {
        return true;
    }

    //
    public void updateAimBounds(double new_angle)
    {
        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "Direction to update aim bounds wrt (angle = " + new_angle + ")");

        if (new_angle < 0.0f)
        {
            new_angle = getAimAngle() - new_angle;

            if (new_angle < 0.0f)
                new_angle = 360.0d + new_angle;

            if (DEBUG == 1)
                Log.d(DEBUG_TAG, "New aim_dir value: " + new_angle);
        }

        else
        {
            if (DEBUG == 1)
                Log.d(DEBUG_TAG, "Aim_dir > 0");
            new_angle = wrapAroundValue((float) (getAimAngle() + new_angle));

            if (DEBUG == 1)
                Log.d(DEBUG_TAG, "New aim_dir value: " + new_angle);
        }

        aim_angle = new_angle;
        aim_bounds = new Position(wrapAroundValue((float) new_angle + 90.0f), wrapAroundValue((float) new_angle - 90.0f));
    }

    //
    private float wrapAroundValue(float x)
    {
        if (x < 0)
            return 360.0f + x;

        else if (x > 360)
            return x - 360.0f;

        else
            return x;
    }

}