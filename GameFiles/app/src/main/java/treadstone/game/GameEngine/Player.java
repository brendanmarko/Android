package treadstone.game.GameEngine;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

public class Player extends MovableEntity
{
    private float                       spanX, spanY, spanZ, x_dir, y_dir;
    private ArrayList<Projectile>       projectiles;
    private double                      angle_of_movement;

    private int                         DEBUG = 0;

    Player(Context context, Position s, Position m, Position ppm, char t)
    {
        super(context, s, m, ppm, t);
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

        // Set Player to moving
        setMovable();

        // Get lengths of sides
        spanX = x_location - getX();
        spanY = y_location - getY() + (0.5fb * getHeight());
        spanZ = (float) Math.sqrt((spanX * spanX) + (spanY * spanY));

        // Find angle for movement
        angle_of_movement = calcAngle(Math.toDegrees(Math.asin(Math.abs(getRadians(spanX, spanY, spanZ)))));

        if (DEBUG == 1)
            Log.d("player.movement", "Angle before adjust: " + angle_of_movement);

        angle_of_movement = adjustAngle(angle_of_movement);

        if (DEBUG == 1)
            Log.d("player.movement", "Angle after adjust: " + angle_of_movement);

        // Apply to Object using move speed
        calcDisplacement(angle_of_movement);
        boundsCheck(getX(), getY());
    }

    @Override
    public void update()
    {
        if (DEBUG == 1)
            Log.d("Player_update", "update() called in Player");

        setPosition(getPosition().getX() + x_dir, getPosition().getY() + y_dir);
        boundsCheck(getX(), getY());
    }

    public void calcDisplacement(double angle)
    {

        if (angle == 0.0d)
        {
            x_dir = getSpeed();
            y_dir = 0.0f;
        }

        else if (angle == 45.0d)
        {
            y_dir = (0 - getSpeed()) * 0.75f;
            x_dir = getSpeed() * 0.75f;
        }

        else if (angle == 90.0d)
        {
            x_dir = 0.0f;
            y_dir = 0 - getSpeed();
        }

        else if (angle == 135.0d)
        {
            x_dir = (0 - getSpeed()) * 0.75f;
            y_dir = (0 - getSpeed()) * 0.75f;
        }

        else if (angle == 180.0d)
        {
            x_dir = 0 - getSpeed();
            y_dir = 0.0f;
        }

        else if (angle == 225.0d)
        {
            x_dir = (0 - getSpeed()) * 0.75f;
            y_dir = getSpeed() * 0.75f;
        }

        else if (angle == 270.0d)
        {
            x_dir = 0.0f;
            y_dir = getSpeed();
        }

        else if (angle == 315.0d)
        {
            x_dir = getSpeed() * 0.75f;
            y_dir = getSpeed() * 0.75f;
        }

        if (DEBUG == 1)
            Log.d("Player/calcD", "x_dir, y_dir: " + x_dir + ", " + y_dir);

        // Change position
        setPosition(getPosition().getX() + x_dir, getPosition().getY() + y_dir);
    }

    public void boundsCheck(float x, float y)
    {
        if (DEBUG == 1)
            Log.d("Player.boundsCheck", "Checking bounds for Player with [X, Y]: " + x + ", " + y);

        float new_x = 0.0f;
        float new_y = 0.0f;

        if (x < 0.0f)
        {
            if (DEBUG == 1)
                Log.d("Player.boundsCheck", "X < 0");

            new_x = 0.0f;
        }

        else if (x + getWidth() > getXMax())
        {
            if (DEBUG == 1)
                Log.d("Player.boundsCheck", "X > max");

            new_x = getXMax() - getWidth();
        }

        else if (new_x == 0.0f)
        {
            new_x = x;
        }

        if (y < 0.0f)
        {
            if (DEBUG == 1)
                Log.d("Player.boundsCheck", "Y < 0");

            new_y = 0.0f;
        }

        else if (y + getHeight() > getYMax())
        {
            if (DEBUG == 1)
                Log.d("Player.boundsCheck", "Y > max");

            new_y = getYMax() - getHeight();
        }

        else if (new_y == 0.0f)
        {
            new_y = y;
        }

        if (DEBUG == 1)
            Log.d("Player.boundsCheck", "Checking bounds for Player with [X, Y]: " + new_x + ", " + new_y);

        setPosition(new_x, new_y);

    }

    public double getRadians(float x, float y, float z)
    {
        double radians = 0.0d;

            if (x >= y)
            {
                radians = y/z;
            }

            else if (x < y)
            {
                radians = x/z;
            }

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

    } // end : adjustAngle

}