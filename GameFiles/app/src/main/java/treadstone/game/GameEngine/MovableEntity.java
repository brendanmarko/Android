package treadstone.game.GameEngine;

import android.util.Log;

public abstract class MovableEntity extends Entity
{
    // Debug toggle
    private int                     DEBUG = 1;

    private float                   speed;
    private String                  direction;

    public MovableEntity()
    {
        if (DEBUG == 1)
            Log.d("MovableE/CTOR", "Empty MovableE created.");
    }

    public MovableEntity(Position p, Position m, Position ppm, char t)
    {
        super(p, m, ppm, t);

        if (getObjInfo().getMovementType().equals("dynamic"))
        {
            speed = getObjInfo().getSpeed();
            direction = "";
        }
    }

    @Override
    public String toString()
    {
        if (DEBUG == 1)
            Log.d("MovableE/toString", "Current MovableE @" + getPosition().toString() + " w/ speed " + getSpeed());
        return "Current MovableE @" + getPosition().toString() + " w/ speed " + getSpeed();
    }

    public float getSpeed()
    {
        return speed;
    }

    public String getDirection()
    {
        return direction;
    }

    public void setDirection()
    {
        return;
    }

}