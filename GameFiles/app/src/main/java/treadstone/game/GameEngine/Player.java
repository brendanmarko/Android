package treadstone.game.GameEngine;

import android.util.Log;

import java.util.ArrayList;

public class Player extends MovableEntity implements Shooter
{
    // Debug info
    private int                         DEBUG = 1;
    private String                      DEBUG_TAG = "Player";

    private float                       spanX, spanY, spanZ;
    private ArrayList<Projectile>       projectiles;
    private double                      angle_of_movement;

    Player(Position s, Position m, Position ppm, char t)
    {
        super(s, m, ppm, t);
        projectiles = new ArrayList<>();
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

        // Get lengths of sides
        spanX = x_location - getX();
        spanY = y_location - getY() + (0.5f * getHeight());
        spanZ = (float) Math.sqrt((spanX * spanX) + (spanY * spanY));

        // Find angle for movement
        angle_of_movement = calcAngle(Math.toDegrees(Math.asin(Math.abs(getRadians(spanX, spanY, spanZ)))));

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

    public double getRadians(float x, float y, float z)
    {
        double radians = 0.0d;

            if (x >= y)
                radians = y/z;

            else if (x < y)
                radians = x/z;

        return radians;
    }

    public ArrayList<Projectile> getProjectiles()
    {
        return projectiles;
    }

    private double calcAngle(double f)
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

    public String convertAngleToString(double angle)
    {
        if (angle == 0.0d)
        {
            setDirection("E");
        }

        else if (angle == 45.0d)
        {
            setDirection("NE");
        }

        else if (angle == 90.0d)
        {
            setDirection("N");
        }

        else if (angle == 135.0d)
        {
            setDirection("NW");
        }

        else if (angle == 180.0d)
        {
            setDirection("W");
        }

        else if (angle == 225.0d)
        {
            setDirection("SW");
        }

        else if (angle == 270.0d)
        {
            setDirection("S");
        }

        else if (angle == 315.0d)
        {
            setDirection("SE");
        }

        return getDirection();
    }

    // adjustAngle(double)
    // This function takes an angle as a parameter and rounds it to the closest value of the 8 cardinal
    // directions to move the Player.
    private double adjustAngle(double f)
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

    public void adjustDirection(float x, float y)
    {
        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "Adjustting player aim direction wrt: " + x + ", " + y);
    }

    public void fireProjectile()
    {
        //
    }

}