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
}
