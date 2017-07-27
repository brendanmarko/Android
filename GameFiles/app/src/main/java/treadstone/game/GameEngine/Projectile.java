package treadstone.game.GameEngine;

import android.util.Log;

public abstract class Projectile extends MovableEntity
{
    // Debug info
    private int                 DEBUG = 2;
    private String              DEBUG_TAG = "Projectile/";

    private Entity              owner;
    private float               range;
    private double              angle_of_movement;

    // Abstract functions
    public abstract void        update();

    public Projectile(ArmedEntity o, Position pos, Position max, Position p, char t)
    {
        super(pos, max, p, t);
        owner = o;
        range = getObjInfo().getEffectiveRange();
        angle_of_movement = o.getAimAngle();
        setPosition(getX(), adjustedFiringPosition(getPosition()).getY());
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

    public float getRange()
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

        if (getX() <= 0.0f)
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

    public Position adjustedFiringPosition(Position p)
    {
        p.changeY(p.getY() - getHeight()/2);
        return p;
    }

}