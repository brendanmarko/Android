package treadstone.game.GameEngine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.Log;

public abstract class Projectile
{
    // Debug toggle
    private int                 DEBUG = 1;

    private Position            ppm;
    private Position            position;
    private Position            max_bounds;

    private int                 damage;
    private Bitmap              image;
    private Entity              owner;
    private GameObject          info;
    private boolean             active, visible;
    private float               height, width;

    private RectangleHitbox     hitbox_object;

    // Abstract functions
    public abstract boolean inBounds();
    public abstract void update();

    public Projectile(Entity o, Position pos, Position p, Position max, char t)
    {
        owner = o;
        position = new Position(pos.getX(), pos.getY());
        active = true;
        ppm = p;
        max_bounds = max;
        info = new GameObject(t);
        width = info.getDimensions().getX() * ppm.getX();
        height = info.getDimensions().getY() * ppm.getY();

        // Set hitbox
        hitbox_object = new RectangleHitbox(pos, ppm, info.getDimensions());

        if (DEBUG == 1)
            Log.d("Projectile/CTOR", "Projectile created!");
    }

    public Entity getOwner()
    {
        return owner;
    }

    public String toString()
    {
        return "Projectile info: " + "Owner: " + owner.toString() + ", Position: " + position.toString();
    }

    public Position getPosition()
    {
        return position;
    }

    public boolean isActive()
    {
        return active;
    }

    public void setInvisible()
    {
        visible = false;
    }

    public void setVisible()
    {
        visible = true;
    }

    public boolean isVisible()
    {
        return visible;
    }

    public GameObject getObjInfo()
    {
        return info;
    }

    public int getLayer()
    {
        return info.getLayer();
    }

    public float getHeight()
    {
        return height;
    }

    public float getWidth()
    {
        return width;
    }

    public void setPosition(float x, float y)
    {
        position = new Position(x, y);
    }

    public Bitmap createBitmap(Context c, String s)
    {
        int id = c.getResources().getIdentifier(s, "drawable", c.getPackageName());
        image = BitmapFactory.decodeResource(c.getResources(), id);
        image = Bitmap.createScaledBitmap(image, (int) (width * info.getAnimateFrameCount()), (int) (height * info.getAnimateFrameCount()), false);
        return image;
    }

    public Position getMaxBounds()
    {
        return max_bounds;
    }

    public Rect getHitbox()
    {
        return hitbox_object.getHitbox();
    }

}