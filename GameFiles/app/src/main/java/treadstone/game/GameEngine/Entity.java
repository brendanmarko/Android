package treadstone.game.GameEngine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.Log;

public abstract class Entity
{
    // Debug toggle
    private int                 DEBUG = 0;

    private Position            pixels_per_metre;
    private Position            position;
    private Position            max_bounds;

    private int                 layer;
    private Bitmap              image;
    private GameObject          info;
    private boolean             active, visible;
    private float               height, width, speed;

    private RectangleHitbox     hitbox_object;

    // Abstract functions
    public abstract void update();

    Entity()
    {
        if (DEBUG == 1)
            Log.d("Entity/CTOR", "Empty Entity created.");
    }

    Entity(Position pos, Position max, Position p, char t)
    {
        if (DEBUG == 1)
            Log.d("Entity/CTOR", "Entity created @ " + pos.toString() + ", type = " + t);

        max_bounds = new Position(max.getX() * p.getX(), max.getY() * p.getY());
        pixels_per_metre = p;
        info = new GameObject(t);
        layer = info.getLayer();
        speed = info.getSpeed();
        width = info.getDimensions().getX() * p.getX();
        height = info.getDimensions().getY() * p.getY();
        active = true;
        visible = false;

        if (DEBUG == 1)
            Log.d("Entity/obj", "Info For Object: " + info.toString());

        position = pos;

        if (t == 'p')
            Log.d("Entity/PlayerDims", "Player max location = " + max.toString());

        // Set hitbox
        hitbox_object = new RectangleHitbox(pos, pixels_per_metre, info.getDimensions());

        if (DEBUG == 1)
            Log.d("Entity/obj", "Hitbox set properly");
    }

    public Bitmap createBitmap(Context c, String s)
    {
        int id = c.getResources().getIdentifier(s, "drawable", c.getPackageName());
        image = BitmapFactory.decodeResource(c.getResources(), id);
        image = Bitmap.createScaledBitmap(image, (int) (width * info.getAnimateFrameCount()), (int) (height * info.getAnimateFrameCount()), false);
        return image;
    }

    public int getLayer()
    {
        return layer;
    }

    public float getX()
    {
        return position.getX();
    }

    public float getY()
    {
        return position.getY();
    }

    public Position getPosition()
    {
        return position;
    }

    // setPosition(float, float)
    // This function takes 2 float inputs and saves them as pixel (x,y) co-ordinates for later use
    public void setPosition(float x, float y)
    {
        position = new Position(x, y);
    }

    public Position getPPM()
    {
        return pixels_per_metre;
    }

    public Position getMaxBounds()
    {
        return max_bounds;
    }

    public float getHeight()
    {
        return height;
    }

    public float getWidth()
    {
        return width;
    }

    public Bitmap getImage()
    {
        return image;
    }

    public String getImageName()
    {
        return info.getImageName();
    }

    public boolean isVisible()
    {
        return visible;
    }

    public void setInvisible()
    {
        visible = false;
    }

    public void setVisible()
    {
        visible = true;
    }

    public float getSpeed()
    {
        return speed;
    }

    public boolean isActive()
    {
        return active;
    }

    public GameObject getObjInfo()
    {
        return info;
    }

    public String toString()
    {
        if (DEBUG == 1)
            Log.d("entity_to_string", "POS: " + position.toString() + " SPEED: " + getSpeed() + "isVisible = " + isVisible() + " Type: " + info.getType());

        return "POS: " + position.toString() + " SPEED: " + getSpeed() + "isVisible = " + visible + " Type: " + info.getType();
    }

    public Rect getHitbox()
    {
        return hitbox_object.getHitbox();
    }

    public void updateHitbox(float x, float y)
    {
        if (DEBUG == 1)
        {
            Log.d("Entity/updateHB", "Values within updateHB: " + "POS: " + getPosition().toString());
            Log.d("Entity/updateHB", "Values within updateHB: " + "PPM: " + getPPM().toString());
            Log.d("Entity/updateHB", "Values within updateHB: " + "DIMENS: " + getObjInfo().getDimensions().toString());
        }

        Position temp = new Position(getPosition().getX() - x, getPosition().getY() - y);
        hitbox_object = new RectangleHitbox(temp, pixels_per_metre, info.getDimensions());
    }

    public void setHitbox(RectangleHitbox r)
    {
        hitbox_object = r;
    }

}