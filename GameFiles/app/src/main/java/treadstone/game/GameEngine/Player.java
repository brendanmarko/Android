package treadstone.game.GameEngine;

import android.content.Context;

public class Player extends MovableImage
{

    private float spanX, spanY, spanZ;

    Player(Context context, String name, float x, float y)
    {
        super(context, name, x, y, 20, "bob");
        setStatic();
    }

    public void update()
    {

        if (isMoving())
        {
            boundsCheck(getX(), getY());
            getHitRect().updateHitbox((int) getX(), (int) getY(), getImage());
        }

    }

    public void processMovement(float x_location, float y_location)
    {
        System.out.println("curr_pos: " + getX() + ", " + getY());
        float div_factor;
        spanX = x_location - getX();
        spanY = y_location - getY();
        spanZ = (float) Math.sqrt((spanX * spanX) + (spanY * spanY));
        div_factor = spanZ/getSpeed();
        setPosition(getX() + spanX/div_factor, getY() + spanY/div_factor);
        System.out.println("new_pos: " + getX() + ", " + getY());
    }

}