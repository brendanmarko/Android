package treadstone.game.GameEngine;

import android.util.Log;
import android.view.MotionEvent;

public class MultiTouchManager implements AngleFinder
{
    // Debug info
    private int         DEBUG = 1;
    private String      DEBUG_TAG = "MultiTouchMgr";

    private Position    top_finger, bot_finger;
    private Player      curr_player;
    private ViewPort    viewport;
    private float       displacementX, displacementY, spanX, spanY, spanZ;
    private double      angle_of_rotation;
    private Position    pointerPos[] = {new Position(), new Position(), new Position()};

    MultiTouchManager(Player p, ViewPort v)
    {
        curr_player = p;
        viewport = v;
        displacementX = 0.0f;
        displacementY = 0.0f;
        top_finger = new Position();
        bot_finger = new Position();
    }

    public boolean handleEvent(MotionEvent event)
    {
        int action = event.getActionMasked();

        switch(action)
        {
            case MotionEvent.ACTION_MOVE:
            {
                for (int i = 0; i < event.getPointerCount(); i++)
                {
                    if (DEBUG == 1)
                    {
                        Log.d(DEBUG_TAG, "=========================================================================================================");
                        Log.d(DEBUG_TAG, "Pre-change co-ordinates: " + pointerPos[i].toString());
                    }

                    pointerPos[i] = new Position(event.getX(i) + displacementX, event.getY(i) + displacementY);

                    if (DEBUG == 1)
                    {
                        Log.d(DEBUG_TAG, "Post-change co-ordinates: " + pointerPos[i].toString());
                        Log.d(DEBUG_TAG, "=========================================================================================================");
                    }

                }

                calcRotationTheta(pointerPos);
                applyRotation();

            }

        }

        return true;
        
    }

    private void applyRotation()
    {
        curr_player.updateAimBounds((float) angle_of_rotation);
        curr_player.setAimAngle(curr_player.currentAimBounds().getX() - 90.0f);

        if (DEBUG == 1)
        {
            Log.d(DEBUG_TAG, "New aim bounds = " + curr_player.currentAimBounds().toString());
            Log.d(DEBUG_TAG, "New aim angle  = " + curr_player.getAimAngle());
        }

        curr_player.rotateBitmap(angle_of_rotation);

    }

    private void calcRotationTheta(Position[] touches)
    {
        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "calcRotationTheta with " + touches.length + " touches.");

        spanX = touches[0].getX() - touches[1].getX();
        spanY = touches[0].getY() - touches[1].getY();
        spanZ = (float) Math.sqrt((spanX * spanX) + (spanY * spanY));

        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "spanX: " + spanX + ", spanY: " + spanY + ", spanZ: " + spanZ);

        // Find angle for movement
        angle_of_rotation = adjustAngle(Math.toDegrees(Math.asin(Math.abs(radianFinder(spanX, spanY, spanZ)))));
        curr_player.setRotationAngle(angle_of_rotation);

        if (DEBUG == 1)
        {
            Log.d(DEBUG_TAG, "Angle found wrt Player: " + angle_of_rotation);
            Log.d(DEBUG_TAG, "Player aim angle: " + curr_player.getAimAngle());
        }

    }

        // adjustAngle(double)
        // This function takes an angle as a parameter and rounds it to the closest value of the 8 cardinal
        // directions to move the Player.
        public double adjustAngle(double f)
        {
            // Between [22.5, 67.5]
            if (22.5d < f  && f <= 67.5d)
            {
                return 45.0d;
            }

            // Between [67.5, 112.5]
            else if (67.5d < f && f <= 112.5d)
            {
                return 90.0d;
            }

            // Between [112.5, 157.5]
            else if (112.5d < f && f <= 157.5d)
            {
                return 135.0d;
            }

            // Between [157.5, 202.5]
            else if (157.5d < f && f <= 202.5d)
            {
                return 180.0d;
            }

            // Between [202.5, 247.5]
            else if (202.5d < f && f <= 247.5d)
            {
                return 225.0d;
            }

            // Between [247.5, 292.5]
            else if (247.5d < f && f <= 292.5d)
            {
                return 270.0d;
            }

            // Between [292.5, 337.5]
            else if (292.5d < f && f <= 337.5d)
            {
                return 315.0d;
            }

            // Between [337.5, 22.5]
            else
            {
                return 0.0d;
            }
        }

        public double radianFinder(float x, float y, float z)
        {
            if (x >= y)
                return y/z;

            else
                return x/z;
        }

        public double calcAngle(double f)
        {
            if (spanX >= 0 && spanY <= 0)
            {
                if (DEBUG == 1)
                    Log.d("Player.procMove", "Tapped into Q1");

                f += 0.0d;
            }

            else if (spanX < 0 && spanY < 0)
            {
                if (DEBUG == 1)
                    Log.d("Player.procMove", "Tapped into Q2");

                if (Math.abs(spanX) < Math.abs(spanY))
                    f = 180.0d - f;

                else
                    f = 90.0d + f;
            }

            else if (spanX <= 0 && spanY >= 0)
            {
                if (DEBUG == 1)
                    Log.d("Player.procMove", "Tapped into Q3");

                if (Math.abs(spanX) < Math.abs(spanY))
                    f = 180.0d + f;

                else
                    f = 270.0d - f;
            }

            else if (spanX > 0 & spanY > 0)
            {
                if (DEBUG == 1)
                    Log.d("Player.procMove", "Tapped into Q4");

                if (Math.abs(spanX) < Math.abs(spanY))
                    f = 270.0d + f;

                else
                    f = 360.0d - f;
            }

            return f;

        }

    public void updateTouchDisplacement(float dX, float dY)
    {
        displacementX = dX;
        displacementY = dY;
    }


}
