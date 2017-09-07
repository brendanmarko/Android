package treadstone.game.GameEngine;

import android.util.Log;

public abstract class MovableEntity extends Entity
{
    // Debug info
    private int                     DEBUG = 3;
    private String                  DEBUG_TAG = "MovableEntity/";

    private double                  speed, x_dir, y_dir;
    private String                  direction;
    private boolean                 moving;
    private float                   SPEED_REDUCTION_FACTOR = 0.75f;

    // Extensions of MovableEntity can implement update()
    public abstract void update();

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
        return "Current MovableE @ " + getPosition().toString() + " w/ speed " + getSpeed();
    }

    public double getSpeed()
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
            y_dir = (0 - getSpeed()) * SPEED_REDUCTION_FACTOR;
            x_dir = getSpeed() * SPEED_REDUCTION_FACTOR;
        }

        else if (d.equals("SE"))
        {
            x_dir = getSpeed() * SPEED_REDUCTION_FACTOR;
            y_dir = getSpeed() * SPEED_REDUCTION_FACTOR;
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
            x_dir = (0 - getSpeed()) * SPEED_REDUCTION_FACTOR;
            y_dir = (0 - getSpeed()) * SPEED_REDUCTION_FACTOR;
        }

        else if (d.equals("W"))
        {
            x_dir = 0 - getSpeed();
            y_dir = 0.0f;
        }

        else if (d.equals("SW"))
        {
            x_dir = (0 - getSpeed()) * SPEED_REDUCTION_FACTOR;
            y_dir = getSpeed() * SPEED_REDUCTION_FACTOR;
        }

        if (DEBUG == 3)
            Log.d(DEBUG_TAG + "calcD", "New Direction: " + direction + " & values: " + x_dir + ", " + y_dir);

        // Update position with movement increases
        setPosition(getPosition().getX() + x_dir, getPosition().getY() + y_dir);
    }

    public void calcDisplacementBoosted(String d)
    {
        double boost_speed = speed * getObjInfo().getBoostFactor();

        if (d.equals(("E")))
        {
            x_dir = boost_speed;
            y_dir = 0.0f;
        }

        else if (d.equals("NE"))
        {
            y_dir = (0 - boost_speed) * SPEED_REDUCTION_FACTOR;
            x_dir = boost_speed * SPEED_REDUCTION_FACTOR;
        }

        else if (d.equals("SE"))
        {
            x_dir = boost_speed * SPEED_REDUCTION_FACTOR;
            y_dir = boost_speed * SPEED_REDUCTION_FACTOR;
        }

        else if (d.equals("N"))
        {
            x_dir = 0.0f;
            y_dir = 0 - boost_speed;
        }

        else if (d.equals("S"))
        {
            x_dir = 0.0f;
            y_dir = boost_speed;
        }

        else if (d.equals("NW"))
        {
            x_dir = (0 - boost_speed) * SPEED_REDUCTION_FACTOR;
            y_dir = (0 - boost_speed) * SPEED_REDUCTION_FACTOR;
        }

        else if (d.equals("W"))
        {
            x_dir = 0 - boost_speed;
            y_dir = 0.0f;
        }

        else if (d.equals("SW"))
        {
            x_dir = (0 - boost_speed) * SPEED_REDUCTION_FACTOR;
            y_dir = speed * SPEED_REDUCTION_FACTOR;
        }

        if (DEBUG == 3)
            Log.d(DEBUG_TAG + "calcDB", "New Direction: " + direction + " & values: " + x_dir + ", " + y_dir);

        // Update position with movement changes
        setPosition(getPosition().getX() + x_dir, getPosition().getY() + y_dir);
    }

    public double directionX()
    {
        return x_dir;
    }

    public double directionY()
    {
        return y_dir;
    }

    public boolean boundsCheck(double x, double y)
    {
        if (DEBUG == 2)
            Log.d(DEBUG_TAG + "bounds", "Checking bounds for MovableEntity with [X, Y]: " + x + ", " + y);

        double      new_x = 0.0f;
        double      new_y = 0.0f;
        boolean     in_bounds = true;

        // Handles the X co-ordinate of the updated Position
        if (x < 0.0f)
        {
            if (DEBUG == 2)
                Log.d(DEBUG_TAG + "bounds", "X < 0");
            new_x = 0.0f;
            in_bounds = false;
        }

        else if (x + getWidth() > getMaxBounds().getX())
        {
            if (DEBUG == 2)
                Log.d(DEBUG_TAG + "bounds", "X > max");
            new_x = getMaxBounds().getX() - getWidth();
            in_bounds = false;
        }

        else if (new_x == 0.0f)
        {
            new_x = x;
        }

        // Handles the Y co-ordinate of the updated Position
        if (y < 0.0f)
        {
            if (DEBUG == 2)
                Log.d(DEBUG_TAG + "bounds", "Y < 0");
            new_y = 0.0f;
            in_bounds = false;
        }

        else if (y + getHeight() > getMaxBounds().getY())
        {
            if (DEBUG == 2)
                Log.d(DEBUG_TAG + "bounds", "Y > max");
            new_y = getMaxBounds().getY() - getHeight();
            in_bounds = false;
        }

        else if (new_y == 0.0f)
        {
            new_y = y;
        }

        if (DEBUG == 2)
            Log.d(DEBUG_TAG + "bounds", "Checking bounds for Player with [X, Y]: " + new_x + ", " + new_y);

        setPosition(new_x, new_y);
        return in_bounds;
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
        if (angle == 0.0d || angle == 360.0d)
            setDirection("E");

        else if (angle == 45.0d)
            setDirection("NE");

        else if (angle == 90.0d)
            setDirection("N");

        else if (angle == 135.0d)
            setDirection("NW");

        else if (angle == 180.0d)
            setDirection("W");

        else if (angle == 225.0d)
            setDirection("SW");

        else if (angle == 270.0d)
            setDirection("S");

        else if (angle == 315.0d)
            setDirection("SE");

        return getDirection();
    }

    public double convertDirectionToAngle(String s)
    {
        if (s.equals("E"))
            return 0.0d;

        else if (s.equals("NE"))
            return 45.0d;

        else if (s.equals("N"))
            return 90.0d;

        else if (s.equals("NW"))
            return 135.0d;

        else if (s.equals("W"))
            return 180.0d;

        else if (s.equals("SW"))
            return 225.0d;

        else if (s.equals("S"))
            return 270.0d;

        else if (s.equals("SE"))
            return 315.0d;

        else
            return 0.0d;
    }

}