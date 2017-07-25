package treadstone.game.GameEngine;

import android.util.Log;

import java.util.ArrayList;

public abstract class ArmedEntity extends MovableEntity
{
    // Debug info
    private int                     DEBUG = 1;
    private String                  DEBUG_TAG = "ArmedEntity";

    private double                  aim_angle;
    private String                  aim_direction;
    private ArrayList<Projectile>   projectiles;

    // AimBound Handler
    private AimBoundHandler         aim_handler;

    public ArmedEntity(Position p, Position m, Position ppm, char t)
    {
        super(p, m, ppm, t);
        aim_handler = new AimBoundHandler();
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

    // currentAimBounds()
    // Returns the current aim bounds the ArmedEntity can aim, form: [L, R]
    public Position currentAimBounds()
    {
        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "Current aiming boundaries: " + aim_handler.getAimBounds().toString());
        return aim_handler.getAimBounds();
    }

    public void updateAimBounds(String s)
    {
        double aim_dir = convertDirectionToAngle(s);
        setAimAngle(aim_dir);
        aim_handler.updateAimBounds(aim_dir);
    }

    private float wrapAroundValue(float x)
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
        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "Current bounds: " + aim_handler.getAimBounds().toString() + ", with angle passed: " + a + " and aim-angle = " + aim_angle + " with result: " + aim_handler.angleBoundsTest(a));

        if (aim_handler.angleBoundsTest(a))
        {
            if (DEBUG == 1)
                Log.d(DEBUG_TAG, "Aim updated successfully with angle: " + a);
            aim_angle = a;
            return true;
        }

        else
        {
            if (DEBUG == 1)
                Log.d(DEBUG_TAG, "Aim angle outside of acceptable range, not changing value.");
            return false;
        }

    }

    public ArrayList<Projectile> getProjectiles()
    {
        return projectiles;
    }

    // void continueRotation
    // This function performs a 45-degree rotation of the selected Entity in the same
    // direction it was moving previously.
    public void continueRotation(String dir)
    {
        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "CNT: Value of rotation " + getRotationAngle() + " and aim_bounds: " + aim_handler.getAimBounds().toString());

        if (dir.equals("CW"))
        {
            setRotationAngle(wrapAroundValue((float) getRotationAngle() - 45.0f));
        }

        else if (dir.equals("CCW"))
        {
            setRotationAngle(wrapAroundValue((float) getRotationAngle() + 45.0f));
        }

        aim_handler.updateAimBounds(getRotationAngle());
        setAimAngle(getRotationAngle());

        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "CNT: Value of rotation " + getRotationAngle() + " and aim_bounds: " +aim_handler.getAimBounds().toString() + " aim = " + getAimAngle());

    }

    // void reverseRotation
    // This function performs a 45-degree rotation of the selected Entity in the opposite
    // direction it was moving previously, caused by switching rotation direction.
    public void reverseRotation(String dir)
    {
        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "REV: Value of aim_angle " + getRotationAngle() + " and aim_bounds: " + aim_handler.getAimBounds().toString());

        if (dir.equals("CW"))
        {
            setRotationAngle(wrapAroundValue((float) getRotationAngle() + 45.0f));
        }

        else if (dir.equals("CCW"))
        {
            setRotationAngle(wrapAroundValue((float) getRotationAngle() - 45.0f));
        }

        aim_handler.updateAimBounds(getRotationAngle());
        setAimAngle(getRotationAngle());

        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "REV: Value of aim_angle " + getRotationAngle() + " and aim_bounds: " + aim_handler.getAimBounds().toString() + " aim = " + getAimAngle());
    }

    public Position getFiringPosition()
    {
        return new Position();
    }

}