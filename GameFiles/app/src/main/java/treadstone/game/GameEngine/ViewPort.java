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
        setPixelsPerMetre(32.0f, 18.0f);
        setViewSize(34.0f, 20.0f);
        scaled_view_space = new Rect();
        clipped_num = 0;
    }

    // worldToScreen(Position, Position)
    // This function takes 2 positions as parameters:
    // 1) p = Position of World co-ordinates
    // 2) d = Position of Entity dimensions [Width, Height]
    // Purpose of function is finding left, right, bottom, and top dimensions of a Rect
    //   -> returns Rect after being set with (l, t, r, b)
    public Rect worldToScreen(Position p, Position d)
    {
        int l, t, r, b;
        l = (int) (screen_centre.getX() - (screen_centre.getX() - p.getX() * pixels_per_metre.getX()));
        t = (int) (screen_centre.getY() - (screen_centre.getY() - p.getY() * pixels_per_metre.getY()));
        r = (int) (l + (d.getX() * p.getX()));
        b = (int) (t + (d.getY() * p.getY()));

        scaled_view_space.set(l, t, r, b);
        return scaled_view_space;
    }

    // clipObjects(Position, Position)
    // This function takes the 2 same Positions as above and test them to see if an object has to be
    // clipped or not from the view.
    public boolean clipObjects(Position p, Position d)
    {
        if (p.getX() - d.getX() < screen_centre.getX() + (viewSize.getX()/2))
        {
            if (p.getX() + d.getX() < screen_centre.getX() - (viewSize.getX()/2))
            {
                if  (p.getY() - d.getY() < screen_centre.getY() + (viewSize.getY()/2))
                {
                    if  (p.getY() + d.getY() < screen_centre.getY() - (viewSize.getY()/2))
                    {
                        return false;
                    }

                }

            }

        }

        return true;
    }

    public void setCentre(Position p)
    {
        screen_centre = new Position(p.getX(), p.getY());
    }

    public void resetClipped()
    {
        clipped_num = 0;
    }

    public void incClipped()
    {
        clipped_num++;
    }

    public void decClipped()
    {
        clipped_num--;
    }

    public int getNumClipped()
    {
        return clipped_num;
    }

    public void setCentre()
    {
        screen_centre = new Position(screen_resolution.getX()/2, screen_resolution.getY()/2);
    }

    public void setPixelsPerMetre(float x, float y)
    {
        pixels_per_metre = new Position(screen_resolution.getX()/x, screen_resolution.getY()/y);
    }

    public Position getPixelsPerMetre()
    {
        return pixels_per_metre;
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

    public float getSizeX()
    {
        return viewSize.getX();
    }

    public float getSizeY()
    {
        return viewSize.getY();
    }

    public void setViewSize(float x, float y)
    {
        viewSize = new Position(x, y);
    }

}