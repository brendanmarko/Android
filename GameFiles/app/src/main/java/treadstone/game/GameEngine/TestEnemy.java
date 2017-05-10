package treadstone.game.GameEngine;


public class TestEnemy extends MovableEntity
{
    TestEnemy(Position s, Position m, Position ppm, char t)
    {
        super(s, m, ppm, t);
    }

    @Override
    public void update()
    {
        setPosition(getX()-getSpeed(), getY());
        boundsCheck(getX(), getY());
    }

    public void boundsCheck(float x, float y)
    {

        if (x < 0)
        {
            // random_spawn()
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