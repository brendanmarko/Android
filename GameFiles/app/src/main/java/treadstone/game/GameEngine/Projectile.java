package treadstone.game.GameEngine;

import android.content.Context;
import android.util.Log;

public class Projectile extends MovableImage
{
    private float               damage;

    Projectile(Context c, ControllerFragment.ProjectileType p, float x, float y, float speed)
    {
        super(c, x, y, speed, p.toString().toLowerCase());
        setMoving();

        switch (p)
        {
            case BULLET:
            damage = 1.0f;
            Log.d("bullet_created", "Bullet projectile created from Projectile!");
            break;

            case MISSILE:
            damage = 2.0f;
            Log.d("missile_created", "Missile projectile created from Projectile!");
            break;

            case SHIELD:
            damage = 5.0f;
            Log.d("shield_created", "Shield triggered from Projectile!");
            break;
        }

    }

    public String toString()
    {
        return "Speed: " + getSpeed() + " Damage: " + damage  + "Position: " + getPosition().toString() + ".\n";
    }

    public void update()
    {
        Log.d("proj_pos_update", "Updating Projectile position " + getX() + ", " + getY() + " with speed " + getSpeed());
        setPosition(getX() + getSpeed(), getY());
    }

    @Override
    public void boundsCheck(float x, float y)
    {

        if (x < 0.0f)
        {
            Log.d("x < 0.0f", "Should be deleted for X < 0.0f");
        }

        else if (x > getXMax())
        {
            Log.d("X > MAX", "Should be deleted for X > MAX");
        }

        else if (y < 0.0f)
        {
            Log.d("Y < 0.0f", "Should be reflected back");
        }

        else if (y > getYMax())
        {
            Log.d("Y > MAX", "Should be reflected back");
        }

        else if (y + getImageHeight() > getYMax())
        {
            setPosition(getX(), getYMax() - getImageHeight());
        }

    }

}