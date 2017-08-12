package treadstone.game.GameEngine;

import android.util.Log;

public class RefractionHandler
{
    // Debug info
    private String      DEBUG_TAG = "RefractionHndlr/";
    private int         DEBUG = 1;

    RefractionHandler()
    {
        if (DEBUG == 1)
            Log.d(DEBUG_TAG + "CTOR", "RefractionHandler created.");
    }

    public void refractionChange(Projectile p, int q)
    {
        if (q == 0)
            return;

        else
        {
            double temp = 0.0d;
            double move_angle = p.getMovementAngle();

            if (DEBUG == 1)
                Log.d(DEBUG_TAG + "refract", "Movement angle: " + move_angle);

            // Tests angle 0<x<90
            if (move_angle > 0.0d && move_angle < 90.0d)
            {
                if (DEBUG == 1)
                    Log.d(DEBUG_TAG + "refract", "Q=" + q + " passed into Q1");

                if (q == 2)
                    temp = move_angle + 90.0d;

                else if (q == 3)
                    temp = move_angle - 90.0d;
            }

            // Tests angle 90<x<180
            else if (move_angle > 90.0d && move_angle < 180.0d)
            {
                if (DEBUG == 1)
                    Log.d(DEBUG_TAG + "refract", "Q=" + q + " passed into Q2");

                if (q == 1)
                    temp = move_angle - 90.0d;

                else if (q == 3)
                    temp = move_angle + 90.0d;
            }

            // Tests angle 180<x<270
            else if (move_angle > 180.0d && move_angle < 270.0d)
            {
                if (DEBUG == 1)
                    Log.d(DEBUG_TAG + "refract", "Q=" + q + " passed into Q3");

                if (q == 1)
                    temp = move_angle + 90.0d;

                else if (q == 4)
                    temp = move_angle - 90.0d;
            }

            // Tests angle 270<x<360
            else if (move_angle > 270.0d && move_angle < 360.0d)
            {
                if (DEBUG == 1)
                    Log.d(DEBUG_TAG + "refract", "Q=" + q + " passed into Q4");

                if (q == 2)
                    temp = move_angle - 90.0d;

                else if (q == 4)
                    temp = move_angle + 90.0d;
            }

            // Tests angles 0, 90, 180, 270, 360
            else
            {
                if (DEBUG == 1)
                    Log.d(DEBUG_TAG + "90ref", "Factor of 90 entered.");

                for (double d = 0.0d; d <= 360.0d; d += 90.0d)
                {
                    if (d == move_angle)
                        temp = wrapAroundValue(move_angle + 180.0d);
                }

            }

            if (DEBUG == 1)
                Log.d(DEBUG_TAG + "tempPre", "Tmp: " + temp + " vs. Move Angle: " + move_angle);

            // This statement handles the case where no collision changes must be handled
            if (temp == 0.0d)
                temp = move_angle;

            if (DEBUG == 1)
                Log.d(DEBUG_TAG + "tempPos", "Resulting temp value: " + temp);

            p.setMovementAngle(wrapAroundValue(temp));

            if (DEBUG == 1)
                Log.d(DEBUG_TAG + "result", "Resulting movement angle: " + p.getMovementAngle());
        }
    }

    public double wrapAroundValue(double x)
    {
        if (x < 0.0d)
            return 360.0d + x;

        else if (x >= 360.0d)
            return x - 360.0d;

        else
            return x;
    }

}