package treadstone.game.GameEngine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.Log;

public abstract class Entity
{
    // Debug info
    private int                 DEBUG = 0;
    private String              DEBUG_TAG = "Entity";

    private Position            pixels_per_metre;
    private Position            position;
    private Position            max_bounds;

    private int                 layer;
    private Bitmap              image;
    private GameObject          info;
    private boolean             active, visible;
    private float               height, width;

    private RectangleHitbox     hitbox_object;

    // Rotation angle
    private double              angle_of_rotation;

    // Abstract functions
    public abstract void update();

    Entity()
    {
        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "Empty Entity created.");
    }

    Entity(Position pos, Position max, Position p, char t)
    {
        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "Entity created @ " + pos.toString() + ", type = " + t);

        max_bounds = new Position(max.getX() * p.getX(), max.getY() * p.getY());
        pixels_per_metre = p;
        info = new GameObject(t);
        layer = info.getLayer();
        width = info.getDimensions().getX() * p.getX();
        height = info.getDimensions().getY() * p.getY();
        active = true;
        visible = false;
        angle_of_rotation = 0.0d;

        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "Info For Object: " + info.toString());

        position = pos;

        if (t == 'p')
            Log.d(DEBUG_TAG, "Player max location = " + max.toString());

        // Set hitbox
        hitbox_object = new RectangleHitbox(pos, pixels_per_metre, info.getDimensions());

        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "Hitbox set properly");
    }

    public Bitmap createBitmap(Context c, String s)
    {
        int id = c.getResources().getIdentifier(s, "drawable", c.getPackageName());
        image = BitmapFactory.decodeResource(c.getResources(), id);
        image = Bitmap.createScaledBitmap(image, (int) (width * info.getFrameCount()), (int) (height * info.getFrameCount()), false);
        return image;
    }

    public Bitmap rotateBitmap(double angle)
    {
        if (DEBUG == 2)
            Log.d(DEBUG_TAG, "Enter rotateBitmap with " + angle);

        Matrix m = new Matrix();
        m.postRotate((int) angle);
        return Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), m, true);
    }

    public double getRotationAngle()
    {
        return angle_of_rotation;
    }

    public void setRotationAngle(double a)
    {
        angle_of_rotation = a;
        if (DEBUG == 2)
            Log.d(DEBUG_TAG, "Rotation angle: " + angle_of_rotation);
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

    public Bitmap getBitmap()
    {
        return image;
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

    public boolean isActive()
    {
        return active;
    }

    public GameObject getObjInfo()
    {
        return info;
    }

    public String movementType()
    {
        return info.getMovementType();
    }

    public String toString()
    {
        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "POS: " + position.toString() + " isVisible = " + isVisible() + " Type: " + info.getType());

        return "POS: " + position.toString() + "isVisible = " + visible + " Type: " + info.getType();
    }

    public Rect getHitbox()
    {
        return hitbox_object.getHitbox();
    }

    public void updateHitbox(float x, float y)
    {
        if (DEBUG == 1)
        {
            Log.d(DEBUG_TAG, "Values within updateHB: " + "POS: " + getPosition().toString());
            Log.d(DEBUG_TAG, "Values within updateHB: " + "PPM: " + getPPM().toString());
            Log.d(DEBUG_TAG, "Values within updateHB: " + "DIMENS: " + getObjInfo().getDimensions().toString());
        }

        Position temp = new Position(getPosition().getX() - x, getPosition().getY() - y);
        hitbox_object = new RectangleHitbox(temp, pixels_per_metre, info.getDimensions());
    }

    public void setHitbox(RectangleHitbox r)
    {
        hitbox_object = r;
    }

}