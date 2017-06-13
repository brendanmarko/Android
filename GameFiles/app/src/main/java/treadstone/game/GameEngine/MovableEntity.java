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
    public Position                 travel_vector;

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

    public void calcDirDisplacement(String d)
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

    public double convertDirectionToAngle(String s)
    {
        if (s.equals("E"))
        {
            return 0.0d;
        }

        else if (s.equals("NE"))
        {
            return 45.0d;
        }

        else if (s.equals("N"))
        {
            return 90.0d;
        }

        else if (s.equals("NW"))
        {
            return 135.0d;
        }

        else if (s.equals("W"))
        {
            return 180.0d;
        }

        else if (s.equals("SW"))
        {
            return 225.0d;
        }

        else if (s.equals("S"))
        {
            return 270.0d;
        }

        else if (s.equals("SE"))
        {
            return 315.0d;
        }

        else
            return 0.0d;

    }

    public void calcAngleDisplacement(double a)
    {
        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "Input angle into calcDisp: " + a);

        double x;

        // Q1 && Q4
        if ((a >= 0.0d && a <= 90.0d) || (a >= 270.0d && a <= 360.0d))
        {
            x = Math.cos(Math.toRadians(a)) * getSpeed();
            if (DEBUG == 1)
                Log.d(DEBUG_TAG, "[X] Angle is within (0<x<90) OR (270<x<360) -> POSITIVE: " + x);
        }

        // Q2 && Q3
        else
        {
            x = 0 - Math.cos(Math.toRadians(a)) * getSpeed();
            if (DEBUG == 1)
                Log.d(DEBUG_TAG, "[X] Angle is within (90<x<270) -> NEGATIVE: " + x);
        }

        double y;

        // Q3 && Q4
        if (a >= 180.0d && a <= 360.0d)
        {
            y = Math.abs(Math.sin(Math.toRadians(a)) * getSpeed());
            if (DEBUG == 1)
                Log.d(DEBUG_TAG, "[Y] Angle is within (180<x<360) -> POSITIVE: " + y);
        }

        // Q1 && Q2
        else
        {
            y = 0 - Math.sin(Math.toRadians(a)) * getSpeed();
            if (DEBUG == 1)
                Log.d(DEBUG_TAG, "[Y] Angle is within (0<x<180) -> NEGATIVE: " + y);
        }

        if (DEBUG == 1)
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