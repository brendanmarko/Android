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
    private Bitmap image;
    private GameObject      type;
    private float           height;
    private float           width;
    private int             ppm_x, ppm_y;
    private boolean         visible;
    private boolean         moving;
    private float           speed;
    private boolean         active;

    Entity(Context c, Position s, Position max, char t)
    {
        // temp ppm
        ppm_x = 50;
        ppm_y = 35;

        curr_pos = scaleToPixel(s);

        if (t == 't')
        {
            Log.d("Entity/PlayerDims", "Player max location = " + max.toString());
        }

        max_bounds = max;

        if (t == 't')
        {
            Log.d("Entity/PlayerDims", "Player max location = " + max_bounds.toString());
        }

        type = new GameObject(t);
        speed = type.getSpeed();
        layer_num = type.getLayer();

        // Try it both TRUE and FALSE
        visible = false;

        width = type.getDimensions().getX();
        height = type.getDimensions().getY();
        active = true;
    }

    public Bitmap createBitmap(Context c, String s)
    {
        int id = c.getResources().getIdentifier(s, "drawable", c.getPackageName());
        image = BitmapFactory.decodeResource(c.getResources(), id);
        image = Bitmap.createScaledBitmap(image, (int) (width * ppm_x * type.getAnimateFrameCount()), (int) (height * ppm_y * type.getAnimateFrameCount()), false);
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
        return type.getImageName();
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

    public boolean isMoving()
    {
        return moving;
    }

    public void setMovable()
    {
        moving = true;
    }

    public void setUnmovable()
    {
        moving = false;
    }

    public float getSpeed()
    {
        return speed;
    }

    public boolean isActive()
    {
        return active;
    }

    public GameObject getType()
    {
        return type;
    }

    public String toString()
    {
        Log.d("entity_to_string", "CURR_POS: " + curr_pos.toString() + " SPEED: " + getSpeed() + " TYPE: " + type.getDimensions() + " isMoving: " + moving);
        return "CURR_POS: " + curr_pos.toString() + " SPEED: " + getSpeed() + " TYPE: " + type.getDimensions() + " isMoving: " + moving;
    }

}