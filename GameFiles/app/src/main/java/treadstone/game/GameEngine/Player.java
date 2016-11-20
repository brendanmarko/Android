package treadstone.game.GameEngine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import treadstone.game.R;

public class Player extends Entity
{

    private int         speed;
    private Bitmap      image;
    private boolean     running;

    Player(Context context, String name, int x, int y)
    {
        super(name, x, y);
        speed = 0;
        image = BitmapFactory.decodeResource(context.getResources(), R.drawable.bob);
        running = false;
    }

    public int getSpeed()
    {
        return speed;
    }

    public void changePace()
    {

        if (running)
        {
            running = false;
            changeSpeed(getSpeed()-10);
        }

        else
        {
            running = true;
            changeSpeed(getSpeed()+10);
        }

    }

    public boolean isRunning()
    {
        return running;
    }

    private void changeSpeed(int new_speed)
    {
        speed = new_speed;
    }

    public void update()
    {

        setPosition(getX()+getSpeed(), getY());
        boundsCheck(getX(), getY());
    }

    public void boundsCheck(int x, int y)
    {

        if (getX() < 0)
        {
            setPosition(0, getY());
        }

        if (getX() > getXMax())
        {
            setPosition(getXMax(), getY());
        }

        if (getY() < 0)
        {
            setPosition(getX(), 0);
        }

        if (getY() > getYMax())
        {
            setPosition(getX(), getYMax());
        }

    }

    public Bitmap getImage()
    {
        return image;
    }

    public int getImageHeight()
    {
        return image.getHeight();
    }

}