package treadstone.game.GameEngine;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

public class Player extends MovableEntity
{
    private float                       spanX, spanY, spanZ;
    private ArrayList<Projectile>       projectiles;
    private Position                    center;
    private float                       angle_of_movement;
    private double                      radian_handler;

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

    public void initCenter(Position p)
    {
        center = p;
        setPosition(p.getX(), p.getY());
    }

    public void processMovement(float x_location, float y_location)
    {
        Log.d("Player/processMove", "Target location: " + x_location + ", " + y_location);
        //Log.d("Player/processMove", "Current Player location: " + getX() + ", " + getY());

        // Get lengths of sides
        spanX = x_location - center.getX();
        spanY = y_location - center.getY();
        spanZ = (float) Math.sqrt((spanX * spanX) + (spanY * spanY));
        Log.d("player.spans", "Span values: " + spanX + ", " + spanY + ", " + spanZ + ", spanY/spanZ = " + spanY/spanZ);

        // Find angle for movement
        radian_handler = Math.toDegrees(Math.asin(Math.abs(getRadians(spanX, spanY, spanZ))));
        Log.d("player.movement", "Angle: " + radian_handler);

        // Adjust angle wrt Cartesian Plain
        angle_of_movement = adjustAngle((float) radian_handler);
        Log.d("Player.processMove", "Current angle_of_movement: " + angle_of_movement);




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

    public double getRadians(float x, float y, float z)
    {
        double radians = 0.0d;

            if (x >= y)
            {
                radians = y/z;
                Log.d("Player.adjustAngle", "X >= Y, radian value : " + radians);
            }

            else if (x < y)
            {
                radians = x/z;
                Log.d("Player.adjustAngle", "X < Y, radian value : " + radians);
            }

        return radians;
    }

    public ArrayList<Projectile> getProjectiles()
    {
        return projectiles;
    }

    private float adjustAngle(float f)
    {
        if (spanX >= 0 && spanY <= 0)
        {
            Log.d("Player.procMove", "Tapped into Q1");
            f += 0.0f;
        }

        else if (spanX < 0 && spanY < 0)
        {
            Log.d("Player.procMove", "Tapped into Q2");
            if (Math.abs(spanX) < Math.abs(spanY))
            {
                Log.d("Player.adjustAngle", "X < Y, angle needs to be adjusted from : " + f);
                f = 180.0f - f;
                Log.d("===", "New angle: " + f);
            }

            else
            {
                Log.d("Player.adjustAngle", "X >= Y, angle does not have to be adjusted from : " + f);
                f = 90.0f + f;
            }
        }

        else if (spanX <= 0 && spanY >= 0)
        {
            Log.d("Player.procMove", "Tapped into Q3");
            if (Math.abs(spanX) < Math.abs(spanY))
            {
                Log.d("Player.adjustAngle", "X < Y, angle needs to be adjusted from : " + f);
                f = 270.0f - f;
                Log.d("===", "New angle: " + f);
            }

            else
            {
                Log.d("Player.adjustAngle", "X >= Y, angle does not have to be adjusted from : " + f);
                f = 180.0f + f;
            }
        }

        else if (spanX > 0 & spanY > 0)
        {
            Log.d("Player.procMove", "Tapped into Q4");
            if (Math.abs(spanX) < Math.abs(spanY))
            {
                Log.d("Player.adjustAngle", "X < Y, angle needs to be adjusted from : " + f);
                f = 270.0f + f;
                Log.d("===", "New angle: " + f);
            }

            else
            {
                Log.d("Player.adjustAngle", "X >= Y, angle does not have to be adjusted from : " + f);
                f = 360.0f - f;
            }
        }

        return f;

    }

}