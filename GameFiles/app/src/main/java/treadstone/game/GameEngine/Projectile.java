package treadstone.game.GameEngine;

import android.content.Context;
import android.util.Log;

public class Projectile extends MovableEntity
{
    private int         damage;

    public Projectile(Context c, Position s, Position m, Position ppm, char t)
    {
        super(c, s, m, ppm, t);
        setMovable();
    }

    public void update()
    {
        setPosition(getX() + getSpeed(), getY());
        boundsCheck(getX(), getY());
        getHitBox().update((int) getX(), (int) getY(), getImage());
        toString();
    }

    public void boundsCheck(float x, float y)
    {

        if (x < 0.0f)
        {
            Log.d("x < 0.0f", "Should be deleted for X < 0.0f");
        }

        else if (x > getXMax())
        {
            Log.d("X > MAX", "Should be deleted for X > MAX");
        }

        else if (y < 0.0f)
        {
            Log.d("Y < 0.0f", "Should be reflected back");
        }

        else if (y > getYMax())
        {
            Log.d("Y > MAX", "Should be reflected back");
        }

        else if (y + getHeight() > getYMax())
        {
            setPosition(getX(), getYMax() - getHeight());
        }

    }

}