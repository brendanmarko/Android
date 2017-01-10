package treadstone.game.GameEngine;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

public class Player extends MovableEntity
{
    private float                       spanX, spanY, spanZ;
    private ArrayList<Projectile>       projectiles;

    Player(Context context, Position s, Position m, char t)
    {
        super(context, s, m, t);
        projectiles = new ArrayList<>();
    }

    public void update()
    {
        if (isMoving())
        {
            // boundsCheck(getX(), getY());
        }

    }

    public void processMovement(float x_location, float y_location)
    {
        Log.d("Player/processMove", "Target location: " + x_location + ", " + y_location);
        Log.d("Player/processMove", "Current Player location: " + getX() + ", " + getY());

        float div_factor;
        spanX = directionHandler(x_location, getX());
        spanY = directionHandler(y_location, getY());
        spanZ = (float) Math.sqrt((spanX * spanX) + (spanY * spanY));
        div_factor = spanZ * (getSpeed()/100);

        // Variable check
        Log.d("Player/processMove", "Testing span values: " + spanX + ", " + spanY + ", " + spanZ + "(" + div_factor + ")");
        Log.d("Player/processMove", "New location: " + getX() + ", " + getY());

        float resultX = 0.0f;
        float resultY = 0.0f;

        if (getX() > x_location)
        {
            resultX = getX() - spanX/div_factor;

            if (getY() > y_location)
            {
                resultY = getY() - spanY/div_factor;
            }

            else
            {
                resultY = getY() + spanY/div_factor;
            }

            setPosition(resultX, resultY);

        }

        else if (getY() > y_location)
        {
            resultY = getY() - spanY/div_factor;

            if (getX() > x_location)
            {
                resultX = getX() - spanX/div_factor;
            }

            else
            {
                resultX = getX() + spanX/div_factor;
            }

            setPosition(resultX, resultY);

        }

        else
        {
            resultX = getX() + spanX/div_factor;
            resultY = getY() + spanY/div_factor;
            setPosition(resultX, resultY);
        }

        Log.d("Player/processMove", "New location: " + getPosition().toString());
        Log.d("Player/processMove", "Current Player location: " + getX() + ", " + getY());
    }

    public float directionHandler(float target, float centre)
    {
        if (target > centre)
            return target - centre;

        else
            return centre - target;
    }

    public void boundsCheck(float x, float y)
    {
        if (x < 0.0f)
        {
            setPosition(0.0f, y);
        }

        if (y < 0.0f)
        {
            setPosition(x, 0.0f);
        }

        if (y + getHeight() > getYMax())
        {
            setPosition(x, getYMax() - getHeight());
        }

        if (x > getXMax())
        {
            setPosition(getXMax() - getWidth(), y);
        }
    }

    public ArrayList<Projectile> getProjectiles()
    {
        return projectiles;
    }

}