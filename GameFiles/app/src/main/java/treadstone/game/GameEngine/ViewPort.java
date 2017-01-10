package treadstone.game.GameEngine;

import android.graphics.Rect;
import android.util.Log;

public class ViewPort
{
    private Position screen_resolution;
    private Position screen_centre;
    private Position pixels_per_metre;
    private Position viewable_size;
    private Position viewport_centre;

    private int clipped_num;
    private Rect scaled_view_space;

    ViewPort(Position resolution)
    {
        screen_resolution = resolution;
        init();
    }

    public void init()
    {
        initializeCentres(screen_resolution);
        setPixelsPerMetre(50.0f, 35.0f);
        setViewSize(new Position(25.0f, 15.0f));
        scaled_view_space = new Rect();
        clipped_num = 0;
    }

    // worldToScreen(Position, Position)
    // This function takes 2 positions as parameters:
    // 1) p = Position of World co-ordinates
    // 2) d = Position of Entity dimensions [Width, Height]
    // Purpose of function is finding left, right, bottom, and top dimensions of a Rect
    // Returns Rect with (l, t, r, b)
    public Rect worldToScreen(Position p, Position d)
    {
        /*
        Log.d("WorldToScreen/WTS", "Current viewport_centre: " + viewport_centre.getX() + ", " + viewport_centre.getY());
        Log.d("WorldToScreen/WTS", "Current p.position = " + p.toString());
        Log.d("WorldToScreen/WTS", "Current d.position = " + d.toString());
        Log.d("WorldToScreen/WTS", "Screen Centre: " + screen_centre.toString());
        Log.d("WorldToScreen/WTS", "PPM Values: " + pixels_per_metre.toString());
        */

        int l, t, r, b;
        l = (int) (screen_centre.getX() - ((viewport_centre.getX() - p.getX())));
        t = (int) (screen_centre.getY() - ((viewport_centre.getY() - p.getY())));
        r = (int) (l + (d.getX()));
        b = (int) (t + (d.getY()));

        // Log.d("WorldToScreen/WTS", "Testing 4 ints [" + l + ", " + t + ", " + r + ", " + b + "]");
        scaled_view_space.set(l, t, r, b);
        return scaled_view_space;
    }

    // clipObjects(Position, Position)
    // This function takes the 2 same Positions as above and test them to see if an object has to be
    // clipped or not from the view.
    public boolean clipObject(Position p, Position d)
    {
        if (p.getX() < viewport_centre.getX() - viewable_size.getX())
        {
            return true;
        }

        if (p.getY() > viewport_centre.getY() + viewable_size.getY())
        {
            return true;
        }

        if (p.getX() > viewport_centre.getX() + viewable_size.getX() + d.getX())
        {
            return true;
        }

        if (p.getY() < viewport_centre.getY() - viewable_size.getY() - d.getY())
        {
            return true;
        }

        // Log.d("ViewPort/clipObjects", "Current object appears in Viewport, NOT clipped.");
        return false;
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

    public void setPixelsPerMetre(float x, float y)
    {
        pixels_per_metre = new Position(x, y);
    }

    public Position getPixelsPerMetre()
    {
        return pixels_per_metre;
    }

    public Position getCentre()
    {
        return screen_centre;
    }

    public Position getViewPortCentre()
    {
        return viewport_centre;
    }

    public void setViewSize(Position p)
    {
        viewable_size = new Position(p.getX() * pixels_per_metre.getX(), p.getY() * pixels_per_metre.getY());
    }

    public void initializeCentres(Position r)
    {
        screen_centre = new Position(r.getX()/5, r.getY()/2);
        viewport_centre = new Position(r.getX()/5, r.getY()/2);
    }

    public void setViewPortCentre(Position p)
    {
        viewport_centre = p;
    }

}