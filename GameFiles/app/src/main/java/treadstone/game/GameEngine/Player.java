package treadstone.game.GameEngine;

import android.content.Context;

import java.util.ArrayList;

public class Player extends MovableEntity
{
    private float                       spanX, spanY, spanZ;
    private ArrayList<Projectile>       projectiles;

    Player(Context context, Position s, Position m, char t)
    {
        super(context, s, m, t);
        projectiles = new ArrayList<>();
        setMovable();
    }

    public void update()
    {
        if (isMoving())
        {
            boundsCheck(getX(), getY());
            getHitBox().update((int) getX(), (int) getY(), getImage());
        }

    }

    public void processMovement(float x_location, float y_location)
    {
        float div_factor;
        spanX = x_location - getX();
        spanY = y_location - getY();
        spanZ = (float) Math.sqrt((spanX * spanX) + (spanY * spanY));
        div_factor = spanZ/getSpeed();
        setPosition(getX() + spanX/div_factor, getY() + spanY/div_factor);
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