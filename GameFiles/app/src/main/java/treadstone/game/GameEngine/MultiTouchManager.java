package treadstone.game.GameEngine;

import android.util.Log;
import android.view.MotionEvent;

public class MultiTouchManager
{
    // Debug info
    private int         DEBUG = 1;
    private String      DEBUG_TAG = "MultiTouchMgr";

    private Position    left_finger, right_finger;
    private Player      curr_player;
    private ViewPort    viewport;
    private float       displacementX, displacementY, displacement_l_finger, displacement_r_finger, rotation_increment;
    private Position    prevPoints[] = {new Position(), new Position(), new Position()};
    private Position    pointerPos[] = {new Position(), new Position(), new Position()};
    private boolean     active_touch, direction_found;
    private String      movement_direction;
    private int         l_finger_index, r_finger_index, num_rotations;

    MultiTouchManager(Player p, ViewPort v)
    {
        // One setup
        curr_player = p;
        viewport = v;
        displacementX = 0.0f;
        displacementY = 0.0f;
        rotation_increment = viewport.getViewableSize().getY()/4;

        // Differs wrt each Touch
        fingerReset();
        touchReset();
    }

    public boolean handleEvent(MotionEvent event)
    {
        int action = event.getActionMasked();

        if (DEBUG == 1)
        {
            Log.d(DEBUG_TAG, "Event: " + event.toString());
        }

        switch(action)
        {
            case MotionEvent.ACTION_MOVE:
            {
                // 0) Position update
                positionUpdates(event);

                // 1) Capture fingers
                if (!active_touch)
                fingerAnalysis(pointerPos[0], pointerPos[1]);

                // 2) Calculate direction of rotation
                if (!direction_found)
                {
                    if (movement_direction.equals("TBD"))
                    {
                        directionSolver(prevPoints, pointerPos);
                        displacementHandler();
                    }

                    else
                        directionFinder(pointerPos);
                }

                // 3) Direction found, use displacement Handler
                else if (direction_found)
                    displacementHandler();

                updateTouchPositions();
                return true;
            }

            case MotionEvent.ACTION_POINTER_UP:
            {
                if (DEBUG == 1)
                    Log.d(DEBUG_TAG, "Testing ACTION_POINTER_UP");
                fingerReset();
                touchReset();
                return true;
            }

            case MotionEvent.ACTION_POINTER_DOWN:
            {
                if (DEBUG == 1)
                    Log.d(DEBUG_TAG, "Testing ACTION_POINTER_DOWN");

                positionUpdates(event);
                fingerAnalysis(pointerPos[0], pointerPos[1]);
                curr_player.adjustAimDirection(right_finger.getX() + displacementX, right_finger.getY() + displacementY);

                return false;
            }

        }

        return false;
    }

    private void fingerAnalysis(Position p0, Position p1)
    {
        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "Fingers captured: " + p0.toString() + ", " + p1.toString());

        if (p0.getX() > p1.getX())
        {
            left_finger = p1;
            right_finger = p0;
            l_finger_index = 1;
            r_finger_index = 0;
        }

        else
        {
            left_finger = p0;
            right_finger = p1;
            l_finger_index = 0;
            r_finger_index = 1;
        }

        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "Left finger: " + left_finger.toString() + " Right finger: " + right_finger.toString());

        active_touch = true;
    }

    private void directionSolver(Position[] prevPoints, Position[] pointerPos)
    {
        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "Entering directionSolver with direction " + movement_direction);

        if (prevPoints[l_finger_index].getY() > pointerPos[l_finger_index].getY() || prevPoints[r_finger_index].getY() < pointerPos[r_finger_index].getY())
            movement_direction = "CW";

        else
            movement_direction = "CCW";

        direction_found = true;
    }

    private void directionFinder(Position[] pointerPos)
    {
        if (DEBUG == 1)
        {
            Log.d(DEBUG_TAG, "Entering directionFinder with vp-dims: " + viewport.getViewableSize().toString());
            Log.d(DEBUG_TAG, "Left finger: " + pointerPos[l_finger_index]);
            Log.d(DEBUG_TAG, "Right finger: " + pointerPos[r_finger_index]);
            Log.d(DEBUG_TAG, "Buffer: " + viewport.getViewableSize().toString());
            Log.d(DEBUG_TAG, "Buffer: " + rotation_increment);
        }

        if (Math.abs(pointerPos[l_finger_index].getY() - pointerPos[r_finger_index].getY()) <= rotation_increment)
        {
            movement_direction = "TBD";
            direction_found = false;
        }

        else if (pointerPos[l_finger_index].getY() > pointerPos[r_finger_index].getY())
        {
            movement_direction = "CW";
            direction_found = true;
        }

        else if (pointerPos[l_finger_index].getY() < pointerPos[r_finger_index].getY())
        {
            movement_direction = "CCW";
            direction_found = true;
        }

        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "Exiting directionFinder with: " + movement_direction);
    }

    // void displacementHandler()
    private void displacementHandler()
    {
        // Calculate displacement values
        displacement_l_finger += prevPoints[l_finger_index].getY() - pointerPos[l_finger_index].getY();
        displacement_r_finger += prevPoints[r_finger_index].getY() - pointerPos[r_finger_index].getY();

        // Calculate value to scale displacement
        float result_l = displacement_l_finger/rotation_increment;
        float result_r = displacement_r_finger/rotation_increment;

        int curr_rotation_num = 0;

        if (DEBUG == 1)
        {
            Log.d(DEBUG_TAG, "Displacements: L = " + displacement_l_finger + ", R = " + displacement_r_finger);
            Log.d(DEBUG_TAG, "Displacements: L/RI = " + result_l + ", R/VP = " + result_r);
        }

        // Check if displacement per finger triggers rotation
        if (Math.abs(result_l) >= 1.0f)
        {
            if (DEBUG == 1)
                Log.d(DEBUG_TAG, "More than 1/4 moved on LEFT finger");
            curr_rotation_num += Math.round(Math.abs(result_l));
        }

        if (Math.abs(result_r) >= 1.0f)
        {
            if (DEBUG == 1)
                Log.d(DEBUG_TAG, "More than 1/4 moved on RIGHT finger");
            curr_rotation_num += Math.round(Math.abs(result_r));
        }

        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "Num of rotations " + curr_rotation_num + " in the direction: " + movement_direction + " against " + getNumRotations() + " rotations.");

        if (curr_rotation_num != getNumRotations())
        {
            adjustRotationAmount(curr_rotation_num);
        }
    }

    // void calculateRotationAmount()
    // This function calculates the necessary rotation wrt finger movement
    private void adjustRotationAmount(int current_rotations)
    {
        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "Inside calculateRotationAmount with direction: " + movement_direction + " and rotations: " + current_rotations + ", against: " + num_rotations);

        if (current_rotations - num_rotations > 0)
        {
            for (int i = 0; i < current_rotations - num_rotations; i++)
            {
                curr_player.continueRotation(movement_direction);
                num_rotations++;
            }
        }

        else if (current_rotations - num_rotations <= 0)
        {
            for (int i = 0; i > current_rotations - num_rotations; i--)
            {
                curr_player.reverseRotation(movement_direction);
                num_rotations--;
            }
        }

    }

    private void updateTouchPositions()
    {
        if (DEBUG == 1)
        {
            Log.d(DEBUG_TAG, "Updating left_finger value: " + left_finger.toString());
            Log.d(DEBUG_TAG, "Updating right_finger value: " + right_finger.toString());
        }

        left_finger = pointerPos[l_finger_index];
        right_finger = pointerPos[r_finger_index];

        if (DEBUG == 1)
        {
            Log.d(DEBUG_TAG, "Updating left_finger value: " + left_finger.toString());
            Log.d(DEBUG_TAG, "Updating right_finger value: " + right_finger.toString());
        }

    }

    public void updateTouchDisplacement(float dX, float dY)
    {
        displacementX = dX;
        displacementY = dY;
    }

    private void positionUpdates(MotionEvent event)
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
    }

    private void fingerReset()
    {
        left_finger = new Position();
        l_finger_index = 0;
        displacement_l_finger = 0.0f;
        right_finger = new Position();
        r_finger_index = 0;
        displacement_r_finger = 0.0f;
    }

    private void touchReset()
    {
        active_touch = false;
        direction_found = false;
        movement_direction = "None";
        num_rotations = 0;
        refreshArrays();
    }

    public void refreshArrays()
    {
        for (int i = 0; i < prevPoints.length; i++)
            prevPoints[i] = new Position();

        for (int j = 0; j < pointerPos.length; j++)
            pointerPos[j] = new Position();
    }

    public int getNumRotations()
    {
        return num_rotations;
    }

}
