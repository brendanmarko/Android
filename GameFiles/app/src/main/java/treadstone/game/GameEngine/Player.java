package treadstone.game.GameEngine;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

public class Player extends MovableEntity
{
    private float                       spanX, spanY, spanZ, x_dir, y_dir;
    private ArrayList<Projectile>       projectiles;
    private Position                    center;
    private double                      angle_of_movement;
    private int                         curr_qaudrant;

    Player(Context context, Position s, Position m, char t)
    {
        super(context, s, m, t);
        projectiles = new ArrayList<>();
    }

    public void update()
    {
        if (isMoving())
        {
            Log.d("Player.update", "inside update and continueMovement");
            continueMovement();
        }

    }

    public void initCenter(Position p)
    {
        center = p;
        setPosition(p.getX(), p.getY());
    }

    public void processMovement(float x_location, float y_location)
    {
        Log.d("Player/processMove", "Target location: " + x_location + ", " + y_location);
        Log.d("Player/processMove", "Current Player location: " + getX() + ", " + getY());

        // Set Player to moving
        setMovable();

        // Get lengths of sides
        spanX = x_location - center.getX();
        spanY = y_location - center.getY();
        spanZ = (float) Math.sqrt((spanX * spanX) + (spanY * spanY));

        // Find angle for movement
        angle_of_movement = adjustAngle(Math.toDegrees(Math.asin(Math.abs(getRadians(spanX, spanY, spanZ)))));
        Log.d("player.movement", "Angle: " + angle_of_movement);

        // Apply to Object using move speed
        calcDisplacement(spanX, spanY, angle_of_movement);
        boundsCheck(getX(), getY());
    }

    public void continueMovement()
    {
        setPosition(getPosition().getX() + x_dir, getPosition().getY() + y_dir);
    }

    public void calcDisplacement(float x, float y, double angle)
    {
        x_dir = (float) Math.sin(angle) * getSpeed();
        y_dir = (float) Math.sqrt(((getSpeed() * getSpeed()) - (x_dir * x_dir)));

        // Factor in directions of x/y span
        x_dir = Math.copySign(x_dir, x);
        y_dir = Math.copySign(y_dir, y);

        // Change position
        setPosition(getPosition().getX() + x_dir, getPosition().getY() + y_dir);
    }

    public void boundsCheck(float x, float y)
    {
        Log.d("Player.boundsCheck", "Checking bounds for Player");
        if (x < 0.0f)
        {
            Log.d("Player.boundsCheck", "X < 0");
            setPosition(0.0f, y);
        }

        if (y < 0.0f)
        {
            Log.d("Player.boundsCheck", "Y < 0");
            setPosition(x, 0.0f);
        }

        if (y + getHeight() > getYMax())
        {
            Log.d("Player.boundsCheck", "Y > max");
            setPosition(x, getYMax() - getHeight());
        }

        if (x > getXMax())
        {
            Log.d("Player.boundsCheck", "X > max");
            setPosition(getXMax() - getWidth(), y);
        }
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

    private double adjustAngle(double f)
    {
        if (spanX >= 0 && spanY <= 0)
        {
            Log.d("Player.procMove", "Tapped into Q1");
            curr_qaudrant = 1;
            f += 0.0f;
        }

        else if (spanX < 0 && spanY < 0)
        {
            Log.d("Player.procMove", "Tapped into Q2");
            curr_qaudrant = 2;
            if (Math.abs(spanX) < Math.abs(spanY))
                f = 180.0f - f;

            else
                f = 90.0f + f;
        }

        else if (spanX <= 0 && spanY >= 0)
        {
            Log.d("Player.procMove", "Tapped into Q3");
            curr_qaudrant = 3;
            if (Math.abs(spanX) < Math.abs(spanY))
                f = 270.0f - f;

            else
                f = 180.0f + f;
        }

        else if (spanX > 0 & spanY > 0)
        {
            Log.d("Player.procMove", "Tapped into Q4");
            curr_qaudrant = 4;
            if (Math.abs(spanX) < Math.abs(spanY))
                f = 270.0f + f;

            else
                f = 360.0f - f;
        }

        return f;

    }

    public int getCurrQuadrant()
    {
        return curr_qaudrant;
    }

}