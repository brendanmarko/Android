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
    private int                 DEBUG = 4;
    private String              DEBUG_TAG = "Entity/";

    private Position            pixels_per_metre;
    private Position            position, center;
    private Position            max_bounds;

    private int                 layer;
    private Bitmap              image;
    private GameObject          info;
    private boolean             active, visible;
    private double              height, width;

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
        if (DEBUG == 2)
            Log.d(DEBUG_TAG + "creation", "Entity created @ " + pos.toString() + ", type = " + t);

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

        // Initialize center of Entity
        initCenter(position);

        if (t == 'p')
            Log.d(DEBUG_TAG, "Player max location = " + max.toString());

        // Set hitbox
        hitbox_object = new RectangleHitbox(position, pixels_per_metre, info.getDimensions());

        if (DEBUG == 4)
            Log.d(DEBUG_TAG, "Center of Entity: " + center.toString());
    }

    // initCenter(pos, ppm)
    // This function locates the center of the specified Entity; used later for FPH
    public void initCenter(Position p)
    {
        if (DEBUG == 4)
            Log.d(DEBUG_TAG + "initCenter/", "Position: " + p.toString() + " && dims: " + width + ", " + height);
        center = new Position(p.getX() + (width/2), p.getY() + (height/2));
        if (DEBUG == 4)
            Log.d(DEBUG_TAG + "initCenter/", "Init value of Center: " + center.toString());
    }

    // rebuildCenter()
    // Resets the center of the Entity at the current position
    public void rebuildCenter()
    {
        center = new Position(position.getX() + (width/2), position.getY() + (height/2));
    }

    // updateCenter(x, y)
    // This function updates the value of the center of the Entity
    public void updateCenter(double x, double y)
    {
        if (DEBUG == 4)
            Log.d(DEBUG_TAG + "UpdateCenter/", "Value of Center: " + center.toString() + " with x/y changes of " + x + ", " + y);
        center.updatePosition(x, y);
    }

    // getCenter()
    // Returns the center of the Entity that was previously assigned
    public Position getCenter()
    {
        return center;
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

    public double getX()
    {
        return position.getX();
    }

    public double getY()
    {
        return position.getY();
    }

    public Position getPosition()
    {
        return position;
    }
    
    // setPosition(Position)
    // Assigns a position to the Entity
    public void setPosition(Position p)
    {
        position = p;
    }

    // setPosition(float, float)
    // This function takes 2 float inputs and saves them as pixel (x,y) co-ordinates for later use
    public void setPosition(double x, double y)
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

    public double getHeight()
    {
        return height;
    }

    public double getWidth()
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

    public void updateHitbox(double displacement_x, double displacement_y)
    {
        if (DEBUG == 1)
        {
            Log.d(DEBUG_TAG, "Values within updateHB: " + "POS: " + getPosition().toString());
            Log.d(DEBUG_TAG, "Values within updateHB: " + "PPM: " + getPPM().toString());
            Log.d(DEBUG_TAG, "Values within updateHB: " + "DIMENS: " + getObjInfo().getDimensions().toString());
        }

        Position temp = new Position(getPosition().getX() - displacement_x, getPosition().getY() - displacement_y);
        hitbox_object = new RectangleHitbox(temp, pixels_per_metre, info.getDimensions());
    }

    public void setHitbox(RectangleHitbox r)
    {
        hitbox_object = r;
    }

    public void destroy()
    {
        if (DEBUG == 3)
            Log.d(DEBUG_TAG + "destroy/", "Destroying entity @ " + getPosition().toString());
        image = null;
        hitbox_object = null;
        active = false;
    }

    // resetEntityAt(Position p)
    // This function takes a position and assigns an Entity to it as well as helper info
    public void resetEntityAt(Position p)
    {
        position = p;
        center = new Position(p.getX() + (width/2), p.getY() + (height/2));
    }

}