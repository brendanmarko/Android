package treadstone.game.GameEngine;

import android.util.Log;

import java.util.ArrayList;

public abstract class ArmedEntity extends MovableEntity
{
    // Debug info
    private int                     DEBUG = 1;
    private String                  DEBUG_TAG = "ArmedEntity";

    private float                   spanX, spanY, spanZ;
    private double                  aim_angle;
    private ArrayList<Projectile>   projectiles;


    public ArmedEntity(Position p, Position m, Position ppm, char t)
    {
        super(p, m, ppm, t);
        projectiles = new ArrayList<>();
    }

    public double getAimAngle()
    {
        return aim_angle;
    }

    public void setAimAngle(double d)
    {
        aim_angle = d;
    }

    public double calcWorldAngle(float x, float y)
    {
        spanX = x - getX();
        spanY = y - getY() + (0.5f * getHeight());
        spanZ = (float) Math.sqrt((spanX * spanX) + (spanY * spanY));
        aim_angle = calcAngle(Math.toDegrees(Math.asin(Math.abs(getRadians(spanX, spanY, spanZ)))));

        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "calcWorldAngle value (point): " + aim_angle);

        return aim_angle;
    }

    public double calcWorldAngle(Entity e)
    {
        spanX = e.getX() - getX();
        spanY = e.getY() - getY() + (0.5f * getHeight());
        spanZ = (float) Math.sqrt((spanX * spanX) + (spanY * spanY));
        aim_angle = calcAngle(Math.toDegrees(Math.asin(Math.abs(getRadians(spanX, spanY, spanZ)))));

        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "calcWorldAngle value (entity): " + aim_angle);
        return aim_angle;
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

    public double calcAngle(double f)
    {
        if (spanX >= 0 && spanY <= 0)
        {
            if (DEBUG == 1)
                Log.d(DEBUG_TAG, "Tapped into Q1");

            f += 0.0d;
        }

        else if (spanX < 0 && spanY < 0)
        {
            if (DEBUG == 1)
                Log.d(DEBUG_TAG, "Tapped into Q2");

            if (Math.abs(spanX) < Math.abs(spanY))
                f = 180.0d - f;

            else
                f = 90.0d + f;
        }

        else if (spanX <= 0 && spanY >= 0)
        {
            if (DEBUG == 1)
                Log.d(DEBUG_TAG, "Tapped into Q3");

            if (Math.abs(spanX) < Math.abs(spanY))
                f = 180.0d + f;

            else
                f = 270.0d - f;
        }

        else if (spanX > 0 & spanY > 0)
        {
            if (DEBUG == 1)
                Log.d(DEBUG_TAG, "Tapped into Q4");

            if (Math.abs(spanX) < Math.abs(spanY))
                f = 270.0d + f;

            else
                f = 360.0d - f;
        }

        return f;

    }

    public ArrayList<Projectile> getProjectiles()
    {
        return projectiles;
    }

}
