package treadstone.game.GameEngine;

import android.util.Log;

import java.util.ArrayList;

public class Player extends ArmedEntity implements Shooter
{
    // Debug info
    private int                         DEBUG = 1;
    private String                      DEBUG_TAG = "Player";
    private double                      angle_of_movement;

    Player(Position s, Position m, Position ppm, char t)
    {
        super(s, m, ppm, t);
    }

    public void initCenter(Position p)
    {
        setPosition(p.getX(), p.getY());
    }

    public void processMovement(float x_location, float y_location)
    {
        if (DEBUG == 1)
        {
            Log.d("Player/processMove", "Target location: " + x_location + ", " + y_location);
            Log.d("Player/processMove", "Current Player location: " + getX() + ", " + getY());
        }

        startMovement();

        /*
        // Get lengths of sides
        spanX = x_location - getX();
        spanY = y_location - getY() + (0.5f * getHeight());
        spanZ = (float) Math.sqrt((spanX * spanX) + (spanY * spanY));

        // Find angle for movement
        angle_of_movement = calcAngle(Math.toDegrees(Math.asin(Math.abs(getRadians(spanX, spanY, spanZ)))));
        */
        angle_of_movement = calcWorldAngle(x_location, y_location);

        if (DEBUG == 1)
            Log.d("player.movement", "Angle before adjust: " + angle_of_movement);

        angle_of_movement = adjustAngle(angle_of_movement);

        if (DEBUG == 1)
            Log.d("player.movement", "Angle after adjust: " + angle_of_movement);

        // Apply to Object using move speed
        calcDisplacement(convertAngleToString(angle_of_movement));
        boundsCheck(getX(), getY());
    }

    @Override
    public void update()
    {
        if (DEBUG == 1)
            //Log.d("Player_update", "update() called in Player");

        if (isMoving())
        {
            setPosition(getPosition().getX() + directionX(), getPosition().getY() + directionY());
            boundsCheck(getX(), getY());
        }

        else
        {
            if (DEBUG == 1)
                Log.d(DEBUG_TAG, "Player is stopped currently!");
            setPosition(getPosition().getX(), getPosition().getY());
            boundsCheck(getX(), getY());
        }

    }

    public void adjustAimDirection(float x, float y)
    {
        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "Adjusting player aim direction wrt: " + x + ", " + y);
        setAimAngle(calcWorldAngle(x, y));
    }

}