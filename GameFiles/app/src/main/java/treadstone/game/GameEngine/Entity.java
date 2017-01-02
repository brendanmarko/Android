package treadstone.game.GameEngine;

import java.util.Random;

import android.util.Log;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public abstract class Entity
{
    private Position        curr_pos;
    private Position        max_bounds;
    private Position        start_pos;
    private int             layer_num;
    private Bitmap          image;
    private GameObject      type;
    private float           height;
    private float           width;
    private int             ppm_x, ppm_y;
    private boolean         visible;
    private boolean         moving;
    private float           speed;

    Entity(Context c, Position s, Position max, char t)
    {
        // temp ppm
        ppm_x = 40;
        ppm_y = 20;

        curr_pos = s;
        start_pos = s;
        max_bounds = max;
        type = new GameObject(t);
        speed = type.getSpeed();
        layer_num = type.getLayer();
        visible = true;
        width = type.getDimensions().getX();
        height = type.getDimensions().getY();
        createBitmap(c, type.getImageName());
    }

    public void createBitmap(Context c, String s)
    {
        int id = c.getResources().getIdentifier(s, "drawable", c.getPackageName());
        image = BitmapFactory.decodeResource(c.getResources(), id);
        image = Bitmap.createScaledBitmap(image, (int) (getWidth() * type.getAnimateFrameCount()), (int) (getHeight() * type.getAnimateFrameCount()), false);
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

    public void setPosition(float x, float y)
    {
        curr_pos.setAs(x, y);
    }

    public Position getMax()
    {
        return max_bounds;
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
        return height * ppm_y;
    }

    public float getWidth()
    {
        return width * ppm_x;
    }

    public void random_spawn()
    {
        Random generator = new Random();
        int random_factor = generator.nextInt(20);
        curr_pos.setAs(max_bounds.getX(), (max_bounds.getY() * random_factor)/20);
    }

    public void respawn()
    {
        curr_pos.setAs(start_pos.getX(), start_pos.getY());
    }

    public Bitmap getImage()
    {
        return image;
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

    public String toString()
    {
        Log.d("entity_to_string", "CURR_POS: " + curr_pos.toString() + " SPEED: " + getSpeed() + " TYPE: " + type.getDimensions() + " isMoving: " + moving);
        return "CURR_POS: " + curr_pos.toString() + " SPEED: " + getSpeed() + " TYPE: " + type.getDimensions() + " isMoving: " + moving;
    }

}