package treadstone.game.GameEngine;

import android.content.Context;

public class TestEnemy extends MovableImage
{

    TestEnemy(Context context, String name, float x, float y)
    {
        super(context, name, x, y, 15, name);
        setSpeed(10);
    }

    public void update()
    {
        setPosition(getX()-getSpeed(), getY());
        boundsCheck(getX(), getY());
        getHitRect().updateHitbox((int) getX(), (int) getY(), getImage());
    }

    @Override
    public void boundsCheck(float x, float y)
    {

        if (x < 0)
        {
            random_spawn();
        }

        else if (x > getXMax())
        {
            setPosition(getXMax(), getY());
        }

        else if (y + getImageHeight() > getYMax())
        {
            setPosition(getX(), getYMax() - getImageHeight());
        }

    }

}