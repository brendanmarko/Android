package treadstone.game.GameEngine;

public abstract class MovableEntity extends Entity
{

    private int             speed, max_speed;
    private boolean         moving;

    MovableEntity(String name, float x, float y, int speed)
    {
        super(name, x, y);
        max_speed = speed;
    }

    public int getSpeed()
    {
        return speed;
    }

    public void setSpeed(int new_speed)
    {
        speed = new_speed;
    }

    public void setStatic()
    {
        speed = 0;
        moving = false;
    }

    public void boundsCheck(float x, float y)
    {

        if (x < 0.0f)
        {
            setPosition(0.0f, getY());
        }

        if (x > getXMax())
        {
            setPosition(getXMax(), getY());
        }

        if (y < 0.0f)
        {
            setPosition(getX(), 0.0f);
        }

        if (y > getYMax())
        {
            setPosition(getX(), getYMax());
        }

    }

    @Override
    public void setPosition(float x, float y)
    {

        if (getX() == x && getY() == y)
        {
            moving = false;
            return;
        }

        getPosition().setPosition(x, y);
        moving = true;

    }

    public boolean isMoving()
    {
        return moving;
    }

    public void toggleMoving(boolean move)
    {

        moving = move;

        if (moving)
        {
            speed = max_speed;
        }

        else
        {
            speed = 0;
        }

    }

}