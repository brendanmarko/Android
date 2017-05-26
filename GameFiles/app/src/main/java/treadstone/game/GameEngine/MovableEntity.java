package treadstone.game.GameEngine;

import android.util.Log;

public abstract class MovableEntity extends Entity
{
    // Debug toggle
    private int                     DEBUG = 1;
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


    /
    public void calcDisplacement(double a)
    {
<<<<<<< HEAD
        float x = 0.0f;
        float y = 0.0f;

        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "calcDisplacement Angle: " + a);

        if (0.0d <= a && a <= 90.0d)
        {
            y = (float) Math.asin(a) * getSpeed();
            x = (float) Math.sqrt(getSpeed() * getSpeed() - (y * y));
=======
        double x;

        if ((90.0d <= a && a >= 0) || (a >= 270.0d && a <= 360.0d))
        {
            if (DEBUG == 1)
                Log.d(DEBUG_TAG, "[X] Angle is within (0<x<90) || (270<x<360) -> POSITIVE");
            x = Math.abs(Math.cos(a)) * getSpeed();
        }

        else
        {
            if (DEBUG == 1)
                Log.d(DEBUG_TAG, "[X] Angle is within (90<x<270) -> NEGATIVE");
            x = 0 - Math.abs(Math.cos(a)) * getSpeed();
        }

        double y;

        if (180.0d <= a && a <= 360)
        {
            if (DEBUG == 1)
                Log.d(DEBUG_TAG, "[Y] Angle is within (180<x<360) -> POSITIVE");
            y = Math.abs(Math.sin(a)) * getSpeed();
        }

        else
        {
            if (DEBUG == 1)
                Log.d(DEBUG_TAG, "[Y] Angle is within (0<x<180) -> NEGATIVE");
            y = 0 - Math.abs(Math.sin(a)) * getSpeed();
>>>>>>> b41371fbd1fef4fd42d9466e2db87ce186f38d54
        }

        if (DEBUG == 1)
        {
            Log.d(DEBUG_TAG, "calcDisplacement value X: " + x);
            Log.d(DEBUG_TAG, "calcDisplacement value Y: " + y);
        }

        setPosition(getPosition().getX() + (float) x, getPosition().getY() + (float) y);
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
            Log.d(DEBUG_TAG, "New Direction: " + direction + " & values: " + x_dir + ", " + y_dir);

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

}