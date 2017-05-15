package treadstone.game.GameEngine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.Log;

public abstract class Projectile extends Entity
{
    // Debug toggle
    private int                 DEBUG = 0;

    private Position            pixels_per_metre;
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
    public abstract boolean     inBounds();
    public abstract void        update();

    public Projectile(Entity o, Position pos, Position p, Position max, char t)
    {
        // super(pos, p, max, t);
        owner = o;
        position = new Position(pos.getX(), pos.getY());
        active = true;
        max_bounds = new Position(max.getX() * p.getX(), max.getY() * p.getY());
        pixels_per_metre = p;
        info = new GameObject(t);
        width = info.getDimensions().getX() * pixels_per_metre.getX();
        height = info.getDimensions().getY() * pixels_per_metre.getY();

        // Set hitbox
        hitbox_object = new RectangleHitbox(pos, pixels_per_metre, info.getDimensions());

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

    public void updateHitbox(float x, float y)
    {
        if (DEBUG == 1)
        {
            Log.d("Prj/updateHB", "Values within updateHB: " + "POS: " + getPosition().toString());
            Log.d("Prj/updateHB", "Values within updateHB: " + "PPM: " + pixels_per_metre.toString());
            Log.d("Prj/updateHB", "Values within updateHB: " + "DIMENS: " + info.getDimensions().toString());
        }

        Position temp = new Position(getPosition().getX() - x + owner.getWidth(), getPosition().getY() - y + owner.getHeight()/3);
        hitbox_object = new RectangleHitbox(temp, pixels_per_metre, info.getDimensions());
    }

}