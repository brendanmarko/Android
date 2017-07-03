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
    private Position    prevPoints[] = {new Position(), new Position(), new Position()};
    private Position    pointerPos[] = {new Position(), new Position(), new Position()};
    private boolean     active_touch, direction_found;
    private String      movement_direction;
    private int         top_finger_index;

    MultiTouchManager(Player p, ViewPort v)
    {
        curr_player = p;
        viewport = v;
        displacementX = 0.0f;
        displacementY = 0.0f;
        top_finger = new Position();
        bot_finger = new Position();
        active_touch = false;
        direction_found = false;
        movement_direction = "None";
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
                    prevPoints[i] = pointerPos[i];
                    pointerPos[i] = new Position(event.getX(i) + displacementX, event.getY(i) + displacementY);

                    if (DEBUG == 1)
                    {
                        Log.d(DEBUG_TAG, "=========================================================================================================");
                        Log.d(DEBUG_TAG, "Pre-change co-ordinates: " + prevPoints[i].toString());
                        Log.d(DEBUG_TAG, "Post-change co-ordinates: " + pointerPos[i].toString());
                        Log.d(DEBUG_TAG, "=========================================================================================================");
                    }

                }

                calcRotationTheta();
                if (rotationNeeded())
                    applyRotation();

            }

        }

        // Terminate touch action
        // active_touch = false;

        return true;
        
    }

    // This functions checks that a rotation of at least 45 degrees occurred, else no rotation is needed
    private boolean rotationNeeded()
    {
        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "Entering rotationNeeded with angle: " + angle_of_rotation + " and aim_bounds: " + curr_player.currentAimBounds().toString());

        // check rotation against aim_bounds
        if (Math.abs(angle_of_rotation - curr_player.currentAimBounds().getX()) == 45.0d)
        {
            if (DEBUG == 1)
                Log.d(DEBUG_TAG, "Change in angle from bounds.X == 45");
            curr_player.setRotationAngle(matrixRotationConversion(angle_of_rotation));
            return true;
        }

        else if (Math.abs(angle_of_rotation - curr_player.currentAimBounds().getY()) == 45.0d)
        {
            if (DEBUG == 1)
                Log.d(DEBUG_TAG, "Change in angle from bounds.Y == 45");
            curr_player.setRotationAngle(matrixRotationConversion(angle_of_rotation));
            return true;
        }

        else
            return false;
    }

    private void applyRotation()
    {
        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "Entering applyRotation with direction: " + movement_direction);

        // If motion is CW: Angle +, CCW: Angle -
        if (movement_direction.equals("CCW"))
            curr_player.updateAimBounds((float) angle_of_rotation, movement_direction);

        else if (movement_direction.equals("CW"))
            curr_player.updateAimBounds((float) (0.0f - angle_of_rotation), movement_direction);

        if (DEBUG == 1)
        {
            Log.d(DEBUG_TAG, "New aim bounds = " + curr_player.currentAimBounds().toString());
            Log.d(DEBUG_TAG, "New aim angle  = " + curr_player.getAimAngle());
        }

        // If motion is CW: Angle +, CCW: Angle -
        if (movement_direction.equals("CW"))
            curr_player.rotateBitmap(matrixRotationConversion(angle_of_rotation));

        else if (movement_direction.equals("CCW"))
            curr_player.rotateBitmap(matrixRotationConversion(angle_of_rotation));

    }

    public double matrixRotationConversion(double a)
    {
        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "Entered matrixRotationConversion with: " + a);

        if (a == 0.0d || a == 360.0d)
            return 90;

        else
        {
            if (DEBUG == 1)
                Log.d(DEBUG_TAG, "Value other than 0/360 entered.");

            double result = 90 - (45 * (a/45));

            if (result < 0.0d)
                result += 360.0d;

            if (DEBUG == 1)
                Log.d(DEBUG_TAG, "Value before adjustAngle: " + result);

            result = adjustAngle(result);

            if (DEBUG == 1)
                Log.d(DEBUG_TAG, "Value after adjustAngle: " + result);

            return result;
        }

    }

    private void calcRotationTheta()
    {
        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "calcRotationTheta entered.");

        // Determine finger positions
        if (!active_touch)
            fingerAnalysis(pointerPos[0], pointerPos[1]);

            // Determine direction of rotation
        else if (active_touch)
        {
            updateTopFinger();
            calcDirectionOfMovement(prevPoints[0], pointerPos[0]);
        }

        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "Top finger: " + top_finger.toString());

        spanX = top_finger.getX() - curr_player.getX();
        spanY = top_finger.getY() - curr_player.getY();
        spanZ = (float) Math.sqrt((spanX * spanX) + (spanY * spanY));

        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "spanX: " + spanX + ", spanY: " + spanY + ", spanZ: " + spanZ);

        // Find angle for movement
        angle_of_rotation = adjustAngle(calcAngle(Math.toDegrees(Math.asin(Math.abs(radianFinder(spanX, spanY, spanZ))))));

        if (DEBUG == 1)
        {
            Log.d(DEBUG_TAG, "Angle found wrt Player: " + angle_of_rotation);
            Log.d(DEBUG_TAG, "Player aim angle: " + curr_player.getAimAngle());
        }

    }

    public void updateTopFinger()
    {
        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "Updating top_finger value: " + top_finger.toString());
        top_finger = pointerPos[top_finger_index];
        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "Updated top_finger value: " + top_finger.toString());
    }

    public void calcDirectionOfMovement(Position p0, Position p1)
    {
        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "Inside calcDirectionOfMovement()");

        if (p1.getX() - p0.getX() < 0 && p1.getY() - p0.getY() > 0)
        {
            if (DEBUG == 1)
                Log.d(DEBUG_TAG, "CCW found.");
            movement_direction = "CCW";
        }

        else if (p1.getX() - p0.getX() > 0 && p1.getY() - p0.getY() < 0)
        {
            if (DEBUG == 1)
                Log.d(DEBUG_TAG, "CCW found.");
            movement_direction = "CCW";
        }

        else
        {
            if (DEBUG == 1)
                Log.d(DEBUG_TAG, "CW found.");
            movement_direction = "CW";
        }

    }

    public void fingerAnalysis(Position p0, Position p1)
    {
        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "Positions entered: " + p0.toString() + ", " + p1.toString());

        if (p0.getY() > p1.getY())
        {
            top_finger = p1;
            bot_finger = p0;
            top_finger_index = 1;
        }

        else
        {
            top_finger = p0;
            bot_finger = p1;
            top_finger_index = 0;
        }

        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "Top finger: " + top_finger.toString() + " Bot finger: " + bot_finger.toString());

        active_touch = true;

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
