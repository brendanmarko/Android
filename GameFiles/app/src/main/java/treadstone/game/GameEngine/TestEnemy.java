package treadstone.game.GameEngine;

import android.content.Context;

public class TestEnemy extends MovableEntity
{

    TestEnemy(Context context, Position s, Position m, char t)
    {
        super(context, s, m, t);
    }

    public void update()
    {
        setPosition(getX()-getSpeed(), getY());
        boundsCheck(getX(), getY());
        getHitBox().update((int) getX(), (int) getY(), getImage());
    }

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

        else if (y + getHeight() > getYMax())
        {
            setPosition(getX(), getYMax() - getHeight());
        }

    }

}