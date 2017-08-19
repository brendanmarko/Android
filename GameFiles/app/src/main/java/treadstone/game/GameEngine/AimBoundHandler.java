package treadstone.game.GameEngine;

import android.util.Log;

public class AimBoundHandler
{
    // Debug info
    private String      DEBUG_TAG = "AimBounds/";
    private int         DEBUG     = 1;

    private double      rotation_angle;
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
        rotation_angle = 0.0f;
    }

    public double getRotationAngle()
    {
        return rotation_angle;
    }

    public Position getAimBounds()
    {
        return aim_bounds;
    }

    //
    public boolean angleBoundsTest(double test_angle)
    {
        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "Angle testing within ABT: " + test_angle);

        if (aim_bounds.getY() > aim_bounds.getX())
        {
            if (DEBUG == 1)
                Log.d(DEBUG_TAG, "[Special case where (ab.y > ab.x)]");

            if (test_angle > 0.0f && test_angle < aim_bounds.getX())
                return true;

            else if (test_angle < 360.0f && test_angle > aim_bounds.getY())
                return true;
        }

        if (test_angle > aim_bounds.getY() && test_angle < aim_bounds.getX())
            return true;

        return false;
    }

    public void updateAimBounds(double new_angle)
    {
        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "Direction to update aim bounds wrt (angle = " + new_angle + ")");

        rotation_angle = new_angle;
        aim_bounds = new Position(wrapAroundValue((float) new_angle + 90.0f), wrapAroundValue((float) new_angle - 90.0f));

        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "New aim_dir value: " + rotation_angle);
    }

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