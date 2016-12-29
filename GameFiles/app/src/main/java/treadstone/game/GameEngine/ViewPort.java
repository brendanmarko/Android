package treadstone.game.GameEngine;

import android.graphics.Rect;

public class ViewPort
{
    private int             clipped_num;
    private Rect            scaled_view_space;
    private Position        pixels_per_metre;
    private Position        screen_resolution;
    private Position        screen_centre;
    private Position        viewSize;

    ViewPort(Position resolution)
    {
        screen_resolution = resolution;
        init();
    }

    public void init()
    {
        setCentre();
        setPixelsPerMetre();
        setViewSize();
        scaled_view_space = new Rect();
    }

    public void setCentre()
    {
        screen_centre = new Position(screen_resolution.getX()/2, screen_resolution.getY()/2);
    }

    public void setPixelsPerMetre()
    {
        pixels_per_metre = new Position(screen_resolution.getX()/32, screen_resolution.getY()/18);
    }

    public float getScreenSizeX()
    {
        return screen_resolution.getX();
    }

    public float getScreenSizeY()
    {
        return screen_resolution.getY();
    }

    public float getCentreX()
    {
        return screen_resolution.getX();
    }

    public float getCentreY()
    {
        return screen_resolution.getY();
    }

    public float getPixelsPerMetreX()
    {
        return pixels_per_metre.getX();
    }

    public float getPixelsPerMetreY()
    {
        return pixels_per_metre.getY();
    }

    public void setViewSize()
    {
        viewSize = new Position(34.0f, 20.0f);
    }

}