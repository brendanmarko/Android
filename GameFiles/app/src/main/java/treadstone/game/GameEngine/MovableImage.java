package treadstone.game.GameEngine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public abstract class MovableImage extends MovableEntity
{

    private Bitmap              image;
    private int                 image_id;
    private RectangleHitbox     hitbox;

    MovableImage(Context context, String name, float x, float y, int speed, String image_name)
    {
        super(name, x, y, speed);
        image_id = context.getResources().getIdentifier(image_name, "drawable", context.getPackageName());
        image = BitmapFactory.decodeResource(context.getResources(), image_id);
        hitbox = new RectangleHitbox((int) x, (int) y, image);
    }

    public Bitmap getImage()
    {
        return image;
    }

    public float getImageHeight()
    {
        return image.getHeight();
    }

    public RectangleHitbox getHitRect()
    {
        return hitbox;
    }

    @Override
    public void boundsCheck(float x, float y)
    {

        if (x < 0.0f)
        {
            setPosition(0.0f, getY());
        }

        else if (x > getXMax())
        {
            setPosition(getXMax(), getY());
        }

        else if (y < 0.0f)
        {
            setPosition(getX(), 0.0f);
        }

        else if (y > getYMax())
        {
            setPosition(getX(), getYMax());
        }

        else if (y + getImageHeight() > getYMax())
        {
            setPosition(getX(), getYMax() - getImageHeight());
        }

    }

}