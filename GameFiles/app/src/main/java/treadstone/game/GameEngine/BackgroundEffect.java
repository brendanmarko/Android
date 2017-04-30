package treadstone.game.GameEngine;


import android.content.Context;

public class BackgroundEffect extends MovableEntity
{

    BackgroundEffect(Context context, Position s, Position m, Position ppm, char t)
    {
        super(context, s, m , ppm, t);
    }

    public void update()
    {
        setPosition(getX()-getSpeed(), getY());
        boundsCheck(getX(), getY());
    }

    @Override
    public void boundsCheck(float x, float y)
    {

        if (x < 0.0f)
        {
            //random_spawn();
        }

        if (x > getXMax())
        {
            setPosition(getXMax(), getY());
        }

        if (y < 0.0f)
        {
            //random_spawn();
        }

        if (y > getYMax())
        {
            setPosition(getX(), getYMax());
        }

    }

}