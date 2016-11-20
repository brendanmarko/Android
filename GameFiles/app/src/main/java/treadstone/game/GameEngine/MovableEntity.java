package treadstone.game.GameEngine;

public abstract class MovableEntity extends Entity
{

    private int             speed;
    private boolean         running;
    private int             running_factor;

    MovableEntity(String name, int x, int y, int run_speed)
    {
        super(name, x, y);
        running_factor = run_speed;
    }

    public int getSpeed()
    {
        return speed;
    }

    private void changeSpeed(int new_speed)
    {
        speed = new_speed;
    }

    public void changePace()
    {

        if (running)
        {
            running = false;
            changeSpeed(speed - running_factor);
        }

        else
        {
            running = true;
            changeSpeed(speed + running_factor);
        }

    }

    public boolean isRunning()
    {
        return running;
    }

    public void setSpeed(int new_speed)
    {
        speed = new_speed;
    }

    public void startStatic()
    {
        running = false;
    }

    public int getRunningFactor()
    {
    return running_factor;
    }

    public void boundsCheck(int x, int y)
    {

        if (x < 0)
        {
            setPosition(0, getY());
        }

        if (x > getXMax())
        {
            setPosition(getXMax(), getY());
        }

        if (y < 0)
        {
            setPosition(getX(), 0);
        }

        if (y > getYMax())
        {
            setPosition(getX(), getYMax());
        }

    }

}