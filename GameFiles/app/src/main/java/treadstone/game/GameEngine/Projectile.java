package treadstone.game.GameEngine;

import android.util.Log;
import android.graphics.Rect;
import android.graphics.Bitmap;

public abstract class Projectile extends MovableEntity
{
    // Debug toggle
    private int                 DEBUG = 1;

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

    public Projectile(Entity o, Position pos, Position max, Position p, char t)
    {
        super(pos, max, p, t);
        owner = o;

        position = new Position(pos.getX(), pos.getY());
        active = true;
        max_bounds = new Position(max.getX() * p.getX(), max.getY() * p.getY());
        pixels_per_metre = p;
        info = new GameObject(t);
        width = info.getDimensions().getX() * pixels_per_metre.getX();
        height = info.getDimensions().getY() * pixels_per_metre.getY();

        // Set hitbox
        hitbox_object = new RectangleHitbox(pos, getPPM(), getObjInfo().getDimensions());

        if (DEBUG == 1)
            Log.d("Projectile/CTOR", "Projectile created!");
    }

    public Entity getOwner()
    {
        return owner;
    }

    @Override
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

    public Position getMaxBounds()
    {
        return max_bounds;
    }

    public Rect getHitbox()
    {
        return hitbox_object.getHitbox();
    }

    @Override
    public void updateHitbox(float x, float y)
    {
        if (DEBUG == 1)
        {
            Log.d("Prj/updateHB", "Values within updateHB: " + "POS: " + getPosition().toString());
            Log.d("Prj/updateHB", "Values within updateHB: " + "PPM: " + getPPM().toString());
            Log.d("Prj/updateHB", "Values within updateHB: " + "DIMENS: " + getObjInfo().getDimensions().toString());
        }

        Position temp = new Position(getPosition().getX() - x + owner.getWidth(), getPosition().getY() - y + owner.getHeight()/3);
        hitbox_object = new RectangleHitbox(temp, pixels_per_metre, info.getDimensions());
    }

}