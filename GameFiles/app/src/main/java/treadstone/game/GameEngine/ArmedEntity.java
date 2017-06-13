package treadstone.game.GameEngine;

import android.util.Log;

import java.util.ArrayList;

public abstract class ArmedEntity extends MovableEntity
{
    // Debug info
    private int                     DEBUG = 1;
    private String                  DEBUG_TAG = "ArmedEntity";

    private Position                aim_bounds;
    private double                  aim_angle;
    private String                  aim_direction;
    private ArrayList<Projectile>   projectiles;

    public ArmedEntity(Position p, Position m, Position ppm, char t)
    {
        super(p, m, ppm, t);
        aim_bounds = new Position(0.0f, 0.0f);
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

    public void setAimDirection(String s)
    {
        aim_direction = s;
    }

    public double quadrantAngleAdjust(double f, int quadrant)
    {
        double temp = 0.0d;

        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "Angle passed to CalcAngle: " + f);

        if (quadrant == 1)
        {
            if (DEBUG == 1)
                Log.d(DEBUG_TAG, "Tapped into Q1");
            temp = f;
        }

        else if (quadrant == 2)
        {
            if (DEBUG == 1)
                Log.d(DEBUG_TAG, "Tapped into Q2");
            temp = 180.0d - f;
        }

        else if (quadrant == 3)
        {
            if (DEBUG == 1)
                Log.d(DEBUG_TAG, "Tapped into Q3");
            temp = 180.0d + f;
        }

        else if (quadrant == 4)
        {
            if (DEBUG == 1)
                Log.d(DEBUG_TAG, "Tapped into Q4");
            temp = 360.0d - f;
        }

        else if (quadrant == 5)
        {
            if (DEBUG == 1)
                Log.d(DEBUG_TAG, "Tapped into Q4 with special case");
            temp = 270.0d + f;
        }

        return temp;
    }

    public double calcAimAngle(float x, float y)
    {
        double  xDiff, yDiff, temp;
        int     quadrant = 0;

        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "Current direction for aim: " + aim_direction + " @ " + convertDirectionToAngle(aim_direction));

        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "Aim bounds = " + currentAimBounds().toString());

        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "Input values into calcAimAngle: " + x + ", " + y + ", Position: " + getPosition().toString());

        xDiff = x - (getX() + getWidth());
        yDiff = y - (getY() + getHeight()/2);

        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "Span values in calcAimAngle: " + xDiff + ", " + yDiff);

        // Decides which Q the tap occurred in
        if (xDiff > 0 && yDiff < 0)
            quadrant = 1;

        else if (xDiff < 0 && yDiff < 0)
            quadrant = 2;

        else if (xDiff < 0 && yDiff > 0)
            quadrant = 3;

        else if (xDiff > 0 && yDiff > 0)
            quadrant = 4;

        temp = Math.toDegrees(Math.atan(Math.abs(yDiff/xDiff)));
        temp = quadrantAngleAdjust(temp, quadrant);

        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "calcWorldAngle value (point): " + temp);

        return temp;
    }

    public Position currentAimBounds()
    {
        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "Current aiming boundaries: " + aim_bounds.toString());
        return aim_bounds;
    }

    public void updateAimBounds(String s)
    {
        float aim_dir = (float) convertDirectionToAngle(s);

        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "Direction to update aim bounds wrt: " + s + " (angle = " + aim_dir + ")");

        aim_bounds = new Position(wrapAroundValue(aim_dir + 90.0f), wrapAroundValue(aim_dir - 90.0f));
    }

    public float wrapAroundValue(float x)
    {
        if (x < 0)
            return 360.0f + x;

        else if (x > 360)
            return x - 360.0f;

        else
            return x;
    }

    public boolean withinAimBounds(double a)
    {
        if (a > aim_bounds.getX() && a < aim_bounds.getY())
            return false;

        else
            return true;
    }

    public ArrayList<Projectile> getProjectiles()
    {
        return projectiles;
    }

}