package treadstone.game.GameEngine;

import android.graphics.Rect;
import android.util.Log;

public class ViewPort
{
    private Position    screen_resolution;
    private Position    screen_centre;
    private Position    pixels_per_metre;
    private Position    viewable_size;
    private Position    viewport_centre;
    private Position    max_view_bounds;

    private float view_l, view_t, view_r, view_b;

    private Rect scaled_view_space;

    private boolean locked_t, locked_r, locked_b, locked_l;

    ViewPort(Position resolution)
    {
        screen_resolution = resolution;
        init();
    }

    public void init()
    {
        initializeCentres(screen_resolution);
        setPixelsPerMetre(50.0f, 35.0f);
        setRenderSpace(new Position(20.0f, 15.0f));
        scaled_view_space = new Rect();
        locked_t = false;
        locked_r = false;
        locked_b = false;
        locked_l = false;
    }

    // worldToScreen(Position, Position)
    // This function takes 2 positions as parameters:
    // 1) p = Position of World co-ordinates
    // 2) d = Position of Entity dimensions [Width, Height]
    // Purpose of function is finding left, right, bottom, and top dimensions of a Rect
    // Returns Rect with (l, t, r, b)
    public Rect worldToScreen(Position p, Position d)
    {
        int l, t, r, b;
        l = (int) (screen_centre.getX() - (viewport_centre.getX() - p.getX()));
        t = (int) (screen_centre.getY() - (viewport_centre.getY() - p.getY()));
        r = (int) (l + (pixels_per_metre.getX() * d.getX()));
        b = (int) (t + (pixels_per_metre.getY() * d.getY()));

        scaled_view_space.set(l, t, r, b);
        return scaled_view_space;
    }

    public Position getCentre()
    {
        return screen_centre;
    }

    // clipObjects(Position, Position)
    // This function takes the 2 same Positions as above and test them to see if an object has to be
    // clipped or not from the view.
    public boolean clipObject(Position p, Position d)
    {
        if (p.getX() < (viewport_centre.getX() - viewable_size.getX()) + (5 * pixels_per_metre.getX()))
        {
            return true;
        }

        if (p.getY() > (viewport_centre.getY() + viewable_size.getX()) + (5 * pixels_per_metre.getY()))
        {
            return true;
        }

        if (p.getX() > viewport_centre.getX() + (viewable_size.getX()) + (5 * pixels_per_metre.getX()))
        {
            return true;
        }

        if (p.getY() < (viewport_centre.getY() - viewable_size.getX()) + (5 * pixels_per_metre.getY()))
        {
            return true;
        }

        return false;

    }

    public void setPixelsPerMetre(float x, float y)
    {
        pixels_per_metre = new Position(x, y);
    }

    public Position getPixelsPerMetre()
    {
        return pixels_per_metre;
    }

    public Position getViewPortCentre()
    {
        return viewport_centre;
    }

    public void setRenderSpace(Position p)
    {
        viewable_size = new Position(p.getX() * pixels_per_metre.getX(), p.getY() * pixels_per_metre.getY());
        // Log.d("vp.setRenderSize", "Size of Viewport: " + viewable_size.toString());
    }

    public void initializeCentres(Position r)
    {
        screen_centre = new Position(r.getX()/5, r.getY()/2);
        viewport_centre = new Position(r.getX()/5, r.getY()/2);
    }

    // setViewPortCentre(Position)
    // This function moves the ViewPort centre of our targeted viewable space
    // If the centre moves to a place that would expose boundaries beyond the map, it locks
    // This allows the player to continue to move in the space, but will not scroll beyond bounds
    public void setViewPortCentre(Position p)
    {
        buildViewportEdges(p);

        if (edgeCheck(p))
        {
            Log.d("viewport.svpc", "edgeCheck passed TRUE");
            viewport_centre = adjustViewportEdges(p);
        }

        else
        {
            viewport_centre = p;
        }

    }

    public void buildViewportEdges(Position p)
    {
        Log.d("viewport.bvpe","Position passed: " + p.toString() + ", viewport dimens: " + viewable_size.toString());
        view_t = p.getY() - viewable_size.getY();
        view_b = p.getY() + viewable_size.getY();
        view_l = p.getX() - viewable_size.getX()/2;
        view_r = view_l + viewable_size.getX();
        Log.d("viewport.bvpe", "4 corners: [(" + view_l + " -> , " + view_r + "), (" + view_t + " -> " + view_b + ")]");
    }

    public void setMapDimens(Position b)
    {
        max_view_bounds = new Position(b.getX() * pixels_per_metre.getX(), b.getY() * pixels_per_metre.getY());
        // Log.d("vp.setmapdim", "Max dimens: " + max_view_bounds.toString());
    }

    // getMapMaxX()
    // This function returns the max 'X' value of the Level's dimensions based upon the rendered
    // level file.
    public float getMapMaxX()
    {
        return max_view_bounds.getX();
    }

    // getMapMaxX()
    // This function returns the max 'Y' value of the Level's dimensions based upon the rendered
    // level file.
    public float getMapMaxY()
    {
        return max_view_bounds.getY();
    }

    // checkT()
    // This functions checks the TOP value of the ViewPort
    private boolean checkT()
    {
        if (view_t <= 0.0f)
        {
            locked_t = true;
            return true;
        }

        else
        {
            locked_t = false;
            return false;
        }

    }

    private boolean checkB()
    {
        Log.d("viewport.checkb", "value of view_b: " + view_b);
        if (view_b >= max_view_bounds.getY())
        {
            locked_b = true;
            return true;
        }

        else
        {
            locked_b = false;
            return false;
        }
    }

    private boolean checkL()
    {
        // Log.d("viewport.checkl", "value of view_l: " + view_l);
        if (view_l <= 0.0f)
        {
            locked_l = true;
            return true;
        }

        else
        {
            locked_l = false;
            return false;
        }
    }

    // edgeCheck()
    // This function checks if any of the ViewPort edges violate boundary constraints
    public boolean edgeCheck(Position p)
    {
        Log.d("Viewport.edgeC", "Position passed into edgeCheck: " + p.toString());
        if (checkB()|| checkT() || checkL())
            return true;

        else
            return false;

    }

    // adjustViewportEdges()
    // This function is called from edgeCheck() and this indicates that one of the ViewPort edges
    // would be beyond the playable space featured in the game. By checking which edge is locked
    // when can apply a lock to the ViewPort's center based on the 1 or 2 edges extending too far.
    public Position adjustViewportEdges(Position p)
    {
        float x = 0.0f;
        float y = 0.0f;

        Log.d("vp.adjustEdges", "Viewport Centre before adjust: " + p.toString());

        if (locked_t)
        {
            Log.d("Viewport.ave", "Locked top found!");
            y = viewable_size.getX()/2;
        }

        else if (locked_b)
        {
            Log.d("Viewport.ave", "Locked bot found!");
            y = max_view_bounds.getY() - viewable_size.getX()/2;
        }

        if (locked_l)
        {
            Log.d("Viewport.ave", "Locked left found!");
            x = getCentre().getX();
            Log.d("viewport.avpe", "X co-ordinate: " + x);
        }

        if (x == 0.0f)
        {
            Log.d("Viewport.ave", "Default X used");
            x = p.getX();
        }

        if (y == 0.0f)
        {
            Log.d("Viewport.ave", "Default Y used");
            y = p.getY();
        }

        Log.d("Viewport.ave", "Checking position values: " + x + ", " + y);
        return new Position(x, y);

    }

}