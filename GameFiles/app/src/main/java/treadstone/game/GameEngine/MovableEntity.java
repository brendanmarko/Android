package treadstone.game.GameEngine;

import android.util.Log;

public abstract class MovableEntity extends Entity
{
    // Debug toggle
    private int                     DEBUG = 0;
    private String                  DEBUG_TAG = "MovableEntity";

    private float                   speed, x_dir, y_dir;
    private String                  direction;
    private boolean                 moving;

    public abstract void update();

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
            moving = false;
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

    public void setDirection(String d)
    {
        direction = d;
    }

    public void calcDisplacement(String d)
    {
        if (d.equals(("E")))
        {
            x_dir = getSpeed();
            y_dir = 0.0f;
        }

        else if (d.equals("NE"))
        {
            y_dir = (0 - getSpeed()) * 0.75f;
            x_dir = getSpeed() * 0.75f;
        }

        else if (d.equals("SE"))
        {
            x_dir = getSpeed() * 0.75f;
            y_dir = getSpeed() * 0.75f;
        }

        else if (d.equals("N"))
        {
            x_dir = 0.0f;
            y_dir = 0 - getSpeed();
        }

        else if (d.equals("S"))
        {
            x_dir = 0.0f;
            y_dir = getSpeed();
        }

        else if (d.equals("NW"))
        {
            x_dir = (0 - getSpeed()) * 0.75f;
            y_dir = (0 - getSpeed()) * 0.75f;
        }

        else if (d.equals("W"))
        {
            x_dir = 0 - getSpeed();
            y_dir = 0.0f;
        }

        else if (d.equals("SW"))
        {
            x_dir = (0 - getSpeed()) * 0.75f;
            y_dir = getSpeed() * 0.75f;
        }

        if (DEBUG == 1)
            Log.d("MovableE/calcD", "New Direction: " + direction);

        // Update position with movement increases
        setPosition(getPosition().getX() + x_dir, getPosition().getY() + y_dir);
    }

    public float directionX()
    {
        return x_dir;
    }

    public float directionY()
    {
        return y_dir;
    }

    public void boundsCheck(float x, float y)
    {
        if (DEBUG == 1)
            Log.d("MovableE.boundsCheck", "Checking bounds for Player with [X, Y]: " + x + ", " + y);

        float new_x = 0.0f;
        float new_y = 0.0f;

        if (x < 0.0f)
        {
            if (DEBUG == 1)
                Log.d("MovableE.boundsCheck", "X < 0");

            new_x = 0.0f;
        }

        else if (x + getWidth() > getMaxBounds().getX())
        {
            if (DEBUG == 1)
                Log.d("MovableE.boundsCheck", "X > max");

            new_x = getMaxBounds().getX() - getWidth();
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

        else if (y + getHeight() > getMaxBounds().getY())
        {
            if (DEBUG == 1)
                Log.d("Player.boundsCheck", "Y > max");

            new_y = getMaxBounds().getY() - getHeight();
        }

        else if (new_y == 0.0f)
        {
            new_y = y;
        }

        if (DEBUG == 1)
            Log.d("Player.boundsCheck", "Checking bounds for Player with [X, Y]: " + new_x + ", " + new_y);

        setPosition(new_x, new_y);
    }

    public void stopMovement()
    {
        moving = false;
        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "Stopping MovableEntity movement!, moving = " + moving);
    }

    public void startMovement()
    {
        moving = true;
        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "Starting MovableEntity movement, moving = " + moving);
    }

    public boolean isMoving()
    {
        return moving;
    }

}