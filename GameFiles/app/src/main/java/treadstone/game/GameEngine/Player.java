package treadstone.game.GameEngine;

import android.content.Context;

import java.util.ArrayList;

public class Player extends MovableImage
{
    private float                       spanX, spanY, spanZ;
    private ArrayList<Projectile>       projectiles;

    Player(Context context, float x, float y)
    {
        super(context, x, y, 20, "bob");
        projectiles = new ArrayList<>();
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
        float div_factor;
        spanX = x_location - getX();
        spanY = y_location - getY();
        spanZ = (float) Math.sqrt((spanX * spanX) + (spanY * spanY));
        div_factor = spanZ/getSpeed();
        setPosition(getX() + spanX/div_factor, getY() + spanY/div_factor);
    }

    public ArrayList<Projectile> getProjectiles()
    {
        return projectiles;
    }

}