package treadstone.game.GameEngine;

import android.util.Log;

public class MathHelper
{
    // Debug info
    private int         DEBUG = 1;
    private String      DEBUG_TAG = "MathHelper/";

    public double findHypotenuse(double width, double height)
    {
        if (DEBUG == 1)
            Log.d(DEBUG_TAG + "findHyp", "Hypotenuse: " + Math.sqrt((width * width) + (height * height)));
        return Math.sqrt((width * width) + (height * height));
    }

    public double sineLawCalc(double picked_side, double hypotenuse)
    {
        if (DEBUG == 1)
            Log.d(DEBUG_TAG + "sineLaw", "Width angle: " + Math.toDegrees(Math.asin(picked_side/hypotenuse)) + " from w/h: " + picked_side + ", " + hypotenuse);
        return Math.toDegrees(Math.asin(picked_side/hypotenuse));
    }

    public boolean cardinalCheck(double angle)
    {
        if (angle % 45.0d == 0)
            return true;
        else
            return false;
    }

    public double wrapAroundValue(double x)
    {
        if (x < 0)
            return 360.0f + x;

        else if (x > 360)
            return x - 360.0f;

        else
            return x;
    }

    public double findFiringPositionTheta(double angle)
    {
        return (180 - angle)/2;
    }

    public double simplifiedCosineLaw(double width, double angle)
    {
        if (DEBUG == 1)
            Log.d(DEBUG_TAG + "cosineLaw/", "Inputs; width = " + width + " and angle: " + angle);

        double displacement = ((2 * (width * width)) * (1 - Math.cos(Math.toRadians(angle))));
        displacement = Math.sqrt(displacement);

        if (DEBUG == 1)
            Log.d(DEBUG_TAG + "result/", "Result: " + displacement);

        return displacement;
    }

    public double displacementFinder(char axis, double displacement_value, double rotation_angle, double theta)
    {
        double angle = wrapAroundValue(135.0d - theta + rotation_angle);

        if (DEBUG == 1)
            Log.d(DEBUG_TAG + "DF/", "Angle to test: " + angle);

        if (axis == 'x')
            return Math.cos(Math.toRadians(angle)) * displacement_value;
    
        else if (axis == 'y')
            return Math.sin(0 - Math.toRadians(angle)) * displacement_value;

        else 
            return 0.0d;
    }
    
}
