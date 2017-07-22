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

            // Tests angle 0<x<90
            if (p.getMovementAngle() > 0.0f && p.getMovementAngle() < 90.0f)
            {
                if (DEBUG == 1)
                    Log.d(DEBUG_TAG + "refract", "Q=" + q + " passed into Q1");

                if (q == 2)
                    temp = p.getMovementAngle() + 90.0f;

                if (q == 3)
                    temp = p.getMovementAngle() - 90.0f;
            }

            // Tests angle 90<x<180
            else if (p.getMovementAngle() > 90.0f && p.getMovementAngle() < 180.0f)
            {
                if (DEBUG == 1)
                    Log.d(DEBUG_TAG + "refract", "Q=" + q + " passed into Q2");

                if (q == 1)
                    temp = p.getMovementAngle() - 90.0f;

                if (q == 3)
                    temp = p.getMovementAngle() + 90.0f;

            }

            // Tests angle 180<x<270
            else if (p.getMovementAngle() > 180.0f && p.getMovementAngle() < 270.0f)
            {
                if (DEBUG == 1)
                    Log.d(DEBUG_TAG + "refract", "Q=" + q + " passed into Q3");

                if (q == 1)
                    temp = p.getMovementAngle() + 90.0f;

                if (q == 4)
                    temp = p.getMovementAngle() - 90.0f;
            }

            // Tests angle 270<x<360
            else if (p.getMovementAngle() > 270.0f && p.getMovementAngle() < 360.0f)
            {
                if (DEBUG == 1)
                    Log.d(DEBUG_TAG + "refract", "Q=" + q + " passed into Q4");

                if (q == 2)
                    temp = p.getMovementAngle() - 90.0f;

                if (q == 4)
                    temp = p.getMovementAngle() + 90.0f;
            }

            p.setMovementAngle(wrapAroundValue(temp));

            if (DEBUG == 1)
                Log.d(DEBUG_TAG + "result", "Resulting movement angle: " + p.getMovementAngle());
        }
    }

    public double wrapAroundValue(double x)
    {
        if (x < 0.0d)
            return 360.0d + x;

        else if (x > 360.0d)
            return x - 360.0d;

        else
            return x;
    }

}
