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
        // System.out.println("curr_pos: " + getX() + ", " + getY());
        float div_factor;
        spanX = x_location - getX();
        spanY = y_location - getY();
        spanZ = (float) Math.sqrt((spanX * spanX) + (spanY * spanY));
        div_factor = spanZ/getSpeed();
        setPosition(getX() + spanX/div_factor, getY() + spanY/div_factor);
        //System.out.println("new_pos: " + getX() + ", " + getY());
    }

    public void shootProjectile()
    {

    }

    public void addProjectile(Projectile p)
    {
        projectiles.add(p);
    }

    public void viewProjectiles()
    {

        if (projectiles.isEmpty())
        {
        return;
        }

        for (Projectile p : projectiles)
        {
            p.toString();
        }

    }

    public ArrayList<Projectile> getProjectiles()
    {
        return projectiles;
    }

}