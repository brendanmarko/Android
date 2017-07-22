package treadstone.game.GameEngine;

import android.util.Log;

public abstract class Projectile extends MovableEntity
{
    // Debug info
    private int                 DEBUG = 0;
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

    @Override
    public void updateHitbox(float x, float y)
    {
        if (DEBUG == 1)
        {
            Log.d(DEBUG_TAG + "updateHB", "Values within updateHB: " + "POS: " + getPosition().toString());
            Log.d(DEBUG_TAG + "updateHB", "Values within updateHB: " + "PPM: " + getPPM().toString());
            Log.d(DEBUG_TAG + "updateHB", "Values within updateHB: " + "DIMENS: " + getObjInfo().getDimensions().toString());
        }

        Position temp = new Position(getPosition().getX() - x + owner.getWidth(), getPosition().getY() - y + owner.getHeight()/3);
        setHitbox(new RectangleHitbox(temp, getPPM(), getObjInfo().getDimensions()));
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
        if (DEBUG == 1)
            Log.d("Bullet/inBounds", "Position inBounds: " + getPosition().toString());

            if (getPosition().getX() < 0.0f)
            {
                if (DEBUG == 1)
                    Log.d(DEBUG_TAG + "inBounds", "MIN_X exceeded");

                return 1;
            }

        if (getPosition().getX() + getWidth() > getMaxBounds().getX())
        {
            if (DEBUG == 1)
                Log.d(DEBUG_TAG + "inBounds", "MAX_X exceeded");

            return 2;
        }

        if (getPosition().getY() < 0.0f)
        {
            if (DEBUG == 1)
                Log.d(DEBUG_TAG + "inBounds", "MIN_Y exceeded");

            return 3;
        }

        if (getPosition().getY() + getHeight() > getMaxBounds().getY())
        {
            if (DEBUG == 1)
                Log.d(DEBUG_TAG + "inBounds", "MAX_Y exceeded");

            return 4;
        }

        return 0;
    }

}