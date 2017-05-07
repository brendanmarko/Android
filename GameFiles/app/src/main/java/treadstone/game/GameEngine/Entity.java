package treadstone.game.GameEngine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public abstract class Entity
{
    private Position        curr_pos;
    private Position        max_bounds;

    private int             layer_num;
    private Bitmap          image;
    private GameObject      object_type;
    private float           height;
    private float           width;
    private float           ppm_x, ppm_y;
    private boolean         visible;
    private boolean         moving;
    private float           speed;
    private boolean         active;
    private boolean         movable;

    public abstract void update();

    Entity(Context c, Position pos, Position max, Position ppm, char t)
    {
        // temp ppm
        ppm_x = ppm.getX();
        ppm_y = ppm.getY();

        // Allows Entity to move to the edge of the map
        max_bounds = new Position((max.getX() * ppm_x), (max.getY() * ppm_y));
        Log.d("Entity/max", "Max bounds from Entity: " + max_bounds.toString());
        curr_pos = scaleToPixel(pos);

        if (t == 'p')
            Log.d("Entity/PlayerDims", "Player max location = " + max.toString());

        object_type = new GameObject(t);
        speed = object_type.getSpeed();
        layer_num = object_type.getLayer();

        visible = false;
        movable = false;

        width = object_type.getDimensions().getX() * ppm_x;
        height = object_type.getDimensions().getY() * ppm_y;
        active = true;
    }

    public Bitmap createBitmap(Context c, String s)
    {
        int id = c.getResources().getIdentifier(s, "drawable", c.getPackageName());
        image = BitmapFactory.decodeResource(c.getResources(), id);
        image = Bitmap.createScaledBitmap(image, (int) (width * object_type.getAnimateFrameCount()), (int) (height * object_type.getAnimateFrameCount()), false);
        return image;
    }

    public Position scaleToPixel(Position p)
    {
        return new Position(p.getX() * ppm_x, p.getY() * ppm_y);
    }

    public int getLayer()
    {
        return layer_num;
    }

    public float getX()
    {
        return curr_pos.getX();
    }

    public float getY()
    {
        return curr_pos.getY();
    }

    public Position getPosition()
    {
        return curr_pos;
    }

    // setPosition(float, float)
    // This function takes 2 float inputs and saves them as pixel (x,y) co-ordinates for later use
    public void setPosition(float x, float y)
    {
        curr_pos = new Position(x, y);
    }

    public float getXMax()
    {
        return max_bounds.getX();
    }

    public float getYMax()
    {
        return max_bounds.getY();
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
        return object_type.getImageName();
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

    public void setMovable()
    {
        movable = true;
    }

    public float getSpeed()
    {
        return speed;
    }

    public boolean isActive()
    {
        return active;
    }

    public GameObject getGameObject()
    {
        return object_type;
    }

    public String toString()
    {
        Log.d("entity_to_string", "CURR_POS: " + curr_pos.toString() + " SPEED: " + getSpeed() + " DIMENSIONS: " + object_type.getDimensions() + " isMoving: " + moving + " Type: " + object_type.getType());
        return "CURR_POS: " + curr_pos.toString() + " SPEED: " + getSpeed() + " DIMENSIONS: " + object_type.getDimensions() + " isMoving: " + moving + " Type: " + object_type.getType();
    }

}