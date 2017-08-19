package treadstone.game.GameEngine;

import android.util.Log;

public class FiringPositionHandler
{
    // Debug info
    private int         DEBUG = 1;
    private String      DEBUG_TAG = "FPH/";

    private Position    firing_position;
    private double      width_angle, displacement, rotation_increment, displacement_x, displacement_y;
    private MathHelper  math_helper;
    private boolean     firing_pos_set;

    // Builds the initial firing position parameters that are needed to solve updates wrt FP
    FiringPositionHandler(ArmedEntity a)
    {
        if (DEBUG == 1)
            Log.d(DEBUG_TAG + "CTOR", "w|h = " + a.getWidth() + ", " + a.getHeight());

        math_helper = new MathHelper();
        rotation_increment = 45.0d;
        firing_pos_set = false;
    }

    public void buildFiringPosition(String aim_direction, ArmedEntity a)
    {
        if (DEBUG == 1)
            Log.d(DEBUG_TAG + "buildFP/", "Passed aim_direction: " + aim_direction);
        firing_position = new Position(a.getPosition().getX() + a.getWidth(), a.getPosition().getY() + a.getHeight()/2);
        firing_pos_set = true;
    }

    public Position getFiringPosition()
    {
        return firing_position;
    }

    public void updateFiringPosition(ArmedEntity a)
    {
        // Find displacement value
        displacement = math_helper.simplifiedCosineLaw(a.getWidth()/2, rotation_increment);

        // Find theta for sides opposite the rotation
        width_angle = math_helper.findFiringPositionTheta(rotation_increment);

        if (DEBUG == 1)
            Log.d(DEBUG_TAG + "updateFP/", "Rotation angle: " + a.getRotationAngle() + " displacement: " + displacement);

        // Find the displacements in X|Y
        displacement_x = math_helper.displacementFinder('x', displacement, a.getRotationAngle(), width_angle);
        displacement_y = math_helper.displacementFinder('y', displacement, a.getRotationAngle(), width_angle);

        if (DEBUG == 1)
            Log.d(DEBUG_TAG + "updateFP/", "Displacement values = [" + displacement_x + ", " + displacement_y + "]");
    }

    public boolean priorInit()
    {
        return firing_pos_set;
    }

}
