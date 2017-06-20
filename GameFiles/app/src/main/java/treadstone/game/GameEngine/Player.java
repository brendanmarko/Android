package treadstone.game.GameEngine;

import android.util.Log;

public class Player extends ArmedEntity implements Shooter, AngleFinder
{
    // Debug info
    private int                         DEBUG = 0;
    private String                      DEBUG_TAG = "Player";

    private float                       spanX, spanY, spanZ;
    private double                      angle_of_movement;

    Player(Position s, Position m, Position ppm, char t)
    {
        super(s, m, ppm, t);
    }

    public void initCenter(Position p, Position ppm)
    {
        setPosition(p.getX() - (getObjInfo().getDimensions().getX() * ppm.getX())/2, p.getY() - (getObjInfo().getDimensions().getY() * ppm.getY())/2);
    }

    public void processMovement(float x_location, float y_location)
    {
        if (DEBUG == 1)
        {
            Log.d("Player/processMove", "Target location: " + x_location + ", " + y_location);
            Log.d("Player/processMove", "Current Player location: " + getX() + ", " + getY());
        }

        startMovement();

        // Get lengths of sides
        spanX = x_location - getX();
        spanY = y_location - getY() + (0.5f * getHeight());
        spanZ = (float) Math.sqrt((spanX * spanX) + (spanY * spanY));

        // Find angle for movement
        angle_of_movement = calcAngle(Math.toDegrees(Math.asin(Math.abs(radianFinder(spanX, spanY, spanZ)))));

        if (DEBUG == 1)
            Log.d("player.movement", "Angle before adjust: " + angle_of_movement);

        angle_of_movement = adjustAngle(angle_of_movement);

        if (DEBUG == 1)
            Log.d("player.movement", "Angle after adjust: " + angle_of_movement);

        // Apply to Object using move speed
        calcDirDisplacement(convertAngleToString(angle_of_movement));
        boundsCheck(getX(), getY());
    }

    @Override
    public void update()
    {
        if (isMoving())
        {
            setPosition(getPosition().getX() + directionX(), getPosition().getY() + directionY());
            boundsCheck(getX(), getY());
        }

        else
        {
            setPosition(getPosition().getX(), getPosition().getY());
            boundsCheck(getX(), getY());
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

    public void adjustAimDirection(float x, float y)
    {
        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "Setting player aim direction wrt: " + x + ", " + y);

        double angle = calcAimAngle(x, y);
        if (withinAimBounds(angle))
        {
            if (DEBUG == 1)
                Log.d(DEBUG_TAG, "Angle within bounds, re-assigning aim_dir!");
            setAimAngle(angle);
        }
    }

}