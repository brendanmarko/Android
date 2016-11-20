package treadstone.game.GameEngine;

import android.content.Context;

public class Player extends MovableImage
{

    Player(Context context, String name, int x, int y)
    {
        super(context, name, x, y, 10, "bob");
        setSpeed(0);
        startStatic();
    }

    public void update()
    {
        setPosition(getX()+getSpeed(), getY());
        boundsCheck(getX(), getY());
    }

}