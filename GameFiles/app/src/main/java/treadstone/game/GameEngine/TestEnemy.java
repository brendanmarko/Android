package treadstone.game.GameEngine;

import android.content.Context;

public class TestEnemy extends MovableImage
{

    TestEnemy(Context context, String name, int x, int y)
    {
        super(context, name, x, y, 15, name);
        setSpeed(10);
        startStatic();
    }

    public void update()
    {
        setPosition(getX()-getSpeed(), getY());
        boundsCheck(getX(), getY());
    }

    @Override
    public void boundsCheck(int x, int y)
    {

        if (x < 0)
        {
            respawn();
        }

        if (x > getXMax())
        {
            setPosition(getXMax(), getY());
        }

        if (y < 0)
        {
            respawn();
        }

        if (y > getYMax())
        {
            setPosition(getX(), getYMax());
        }

    }



}
