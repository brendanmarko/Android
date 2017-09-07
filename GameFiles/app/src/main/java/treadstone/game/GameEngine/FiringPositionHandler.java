package treadstone.game.GameEngine;

import android.util.Log;

public class FiringPositionHandler
{
    // Debug info
    private int         DEBUG = 2;
    private String      DEBUG_TAG = "FPH/";

    private Position    firing_position;
    private double      width_angle, displacement, rotation_increment, displacement_x, displacement_y;
    private MathHelper  math_helper;

    // Builds the initial firing position parameters that are needed to solve updates wrt FP
    FiringPositionHandler(ArmedEntity a)
    {
        if (DEBUG == 1)
            Log.d(DEBUG_TAG + "CTOR", "w|h = " + a.getWidth() + ", " + a.getHeight());

        math_helper = new MathHelper();
        rotation_increment = 45.0d;
        displacement_x = 0.0d;
        displacement_y = 0.0d;
        firing_position = new Position(a.getCenter().getX() + (a.getWidth()/2), a.getCenter().getY());
    }

    public Position getFiringPosition()
    {
        return firing_position;
    }

    public void rotateFiringPosition(ArmedEntity a)
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

    public void updateFiringPosition(ArmedEntity a)
    {
        // In this case; firing_pos can easily be calculated w/o angle finding techniques
        if (a.getRotationAngle() == 0.0d)
        {
            if (DEBUG == 2)
                Log.d(DEBUG_TAG + "UFP/=0", "Position prior to update: " + firing_position.toString() + " & Center value: " + a.getCenter().toString());

            firing_position = new Position(a.getCenter().getX() + a.getWidth()/2, a.getCenter().getY());   

            if (DEBUG == 2)
                Log.d(DEBUG_TAG + "UFP/=0", "Position after update: " + firing_position.toString());         
        }

        // In this case; must calculate a new firing_pos wrt the rotation angle of the Entity
        else
        {
            if (DEBUG == 2)
                Log.d(DEBUG_TAG + "UFP/!0", "Position prior to update: " + firing_position.toString());

            // The displacement_x/y values will already be calculated when rotations wrt the Player occur
            // This might NOT hold true for AI controlled entities, but, we will worry about that later!
            firing_position = new Position(a.getCenter().getX() + displacement_x, a.getCenter().getY() + displacement_y);

            if (DEBUG == 2)
                Log.d(DEBUG_TAG + "updateFP/", "Displacement values = [" + displacement_x + ", " + displacement_y + "]");

            if (DEBUG == 2)
                Log.d(DEBUG_TAG + "UFP/!0", "Position after update: " + firing_position.toString());
        }
    }

}
