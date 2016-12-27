package treadstone.game.GameEngine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import treadstone.game.R;

public class Projectile
{
    private float               speed;
    private float               damage;
    private Bitmap              image;
    private RectangleHitbox     hitbox;
    private Position            position;

    Projectile(Context context, ControllerFragment.ProjectileType projectile)
    {
        switch (projectile)
        {
            case BULLET:
            speed = 1.0f;
            damage = 1.0f;
            image = BitmapFactory.decodeResource(context.getResources(), R.drawable.bullet);
            Log.d("bullet_created", "Bullet projectile created from Projectile!");
            break;

            case MISSILE:
            speed = 1.5f;
            damage = 2.0f;
            image = BitmapFactory.decodeResource(context.getResources(), R.drawable.missile);
            Log.d("missile_created", "Missile projectile created from Projectile!");
            break;

            case SHIELD:
            speed = 0.0f;
            damage = 3.0f;
            image = BitmapFactory.decodeResource(context.getResources(), R.drawable.bullet);
            Log.d("shield_created", "Shield triggered from Projectile!");
            break;
        }

    }

    public String toString()
    {
        return "Speed: " + speed + " Damage: " + damage  + "Position: " + position.toString() + ".\n";
    }

    public void setPosition(Position p)
    {
        position = p;
        hitbox = new RectangleHitbox((int) p.getX(), (int) p.getY(), image);
    }

    public Position getPosition()
    {
        return position;
    }

    public float getX()
    {
        return position.getX();
    }

    public float getY()
    {
        return position.getY();
    }

    public void moveProjectile()
    {
       position. setPosition(position.getX() + speed, position.getY() + speed);
    }

    public Bitmap getImage()
    {
        return image;
    }

}