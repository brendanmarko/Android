package treadstone.game.GameEngine;


import android.content.Context;

public class BackgroundEffect extends MovableImage
{

    BackgroundEffect(Context context, String name, int x, int y, int speed)
    {
        super(context, name, x, y, speed, name);
        setSpeed(speed);
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
            random_spawn();
        }

        if (x > getXMax())
        {
            setPosition(getXMax(), getY());
        }

        if (y < 0.0f)
        {
            random_spawn();
        }

        if (y > getYMax())
        {
            setPosition(getX(), getYMax());
        }

    }

}