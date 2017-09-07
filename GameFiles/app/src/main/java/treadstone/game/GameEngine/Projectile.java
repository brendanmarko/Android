package treadstone.game.GameEngine;

import android.util.Log;

public abstract class Projectile extends MovableEntity
{
    // Debug info
    private int                 DEBUG = 0;
    private String              DEBUG_TAG = "Projectile/";

    private Entity              owner;
    private double              range;
    private double              angle_of_movement;
    private Position            travel_vector;

    // Abstract functions
    public abstract void        update();

    public Projectile(ArmedEntity o, Position pos, Position max, Position p, char t)
    {
        super(pos, max, p, t);
        owner = o;
        range = getObjInfo().getEffectiveRange();
        angle_of_movement = o.getAimAngle();
        setPosition(o.getFiringPosition().getX(), o.getFiringPosition().getY());
        buildTravelVector(angle_of_movement);
        startMovement();

        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "Projectile created!");
    }

    public Entity getOwner()
    {
        return owner;
    }

    @Override
    public String toString()
    {
        return "Projectile info: " + "Owner: " + owner.toString() + ", Position: " + getPosition().toString();
    }

    public double getRange()
    {
        return range;
    }

    public double getMovementAngle()
    {
        return angle_of_movement;
    }

    public void setMovementAngle(double a)
    {
        angle_of_movement = a;
    }

    public int inBounds()
    {
        if (DEBUG == 2)
        {
            Log.d(DEBUG_TAG + "inBounds", "Position inBounds: " + getPosition().toString());
            Log.d(DEBUG_TAG + "hitbox", "Current hitbox: " + getHitbox().toString());
        }

        if (getX() < 0.0f)
        {
            if (DEBUG == 2)
                Log.d(DEBUG_TAG + "inBounds", "MIN_X exceeded");

            return 1;
        }

        else if (getX() + getWidth() > getMaxBounds().getX())
        {
            if (DEBUG == 2)
                Log.d(DEBUG_TAG + "inBounds", "MAX_X exceeded");

            return 2;
        }

        else if (getY() < 0.0f)
        {
            if (DEBUG == 2)
                Log.d(DEBUG_TAG + "inBounds", "MIN_Y exceeded");

            return 3;
        }

        else if (getY() + getHeight() > getMaxBounds().getY())
        {
            if (DEBUG == 2)
                Log.d(DEBUG_TAG + "inBounds", "MAX_Y exceeded");

            return 4;
        }

        else
            return 0;
    }

    public void buildTravelVector(double a)
    {
        if (DEBUG == 3)
            Log.d(DEBUG_TAG + "travelV", "Input angle into buildTravelVector " + a);

        double x, y;

        if ((a >= 0.0d && a <= 90.0d) || (a >= 270.0d && a <= 360.0d)) // Q1 && Q4
        {
            x = Math.cos(Math.toRadians(a)) * getSpeed();
            if (DEBUG == 3)
                Log.d(DEBUG_TAG, "[X] Angle is within (0<x<90) OR (270<x<360) -> POSITIVE: " + x);
        }

        else // Q2 && Q3
        {
            x = Math.cos(Math.toRadians(a)) * getSpeed();
            if (DEBUG == 3)
                Log.d(DEBUG_TAG, "[X] Angle is within (90<x<270) -> NEGATIVE: " + x);
        }

        // Checks if x consumes entire displacement
        if (Math.abs(x) == getSpeed())
        {
            y = 0.0d;
            if (DEBUG == 3)
                Log.d(DEBUG_TAG + "S=TV", "Projectile speed is focused in one direction; y = 0");
        }

        else
        {
            if (a >= 180.0d && a <= 360.0d) // Q3 && Q4
            {
                y = Math.abs(Math.sin(Math.toRadians(a)) * getSpeed());
                if (DEBUG == 3)
                    Log.d(DEBUG_TAG, "[Y] Angle is within (180<x<360) -> POSITIVE: " + y);
            }

            else // Q1 && Q2
            {
                y = 0 - Math.sin(Math.toRadians(a)) * getSpeed();
                if (DEBUG == 3)
                    Log.d(DEBUG_TAG, "[Y] Angle is within (0<x<180) -> NEGATIVE: " + y);
            }

        }

        // Checks if y consumes entire displacement
        if (Math.abs(y) == getSpeed())
        {
            x = 0.0d;
            if (DEBUG == 3)
                Log.d(DEBUG_TAG + "S=TV", "Projectile speed is focused in one direction; x = 0");
        }

        if (DEBUG == 3)
        {
            Log.d(DEBUG_TAG, "calcDisplacement value X: " + x + " converted into float: " + (float) x);
            Log.d(DEBUG_TAG, "calcDisplacement value Y: " + y + " converted into float: " + (float) y);
        }

        travel_vector = new Position((float) x, (float) y);
    }

    public Position getTravelVector()
    {
        return travel_vector;
    }

}