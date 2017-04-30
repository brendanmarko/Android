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
    private Position    factor;

    // Height and Width of ViewPort wrt in-game Metres
    private final int   viewport_width = 30;
    private final int   viewport_height = 20;

    private float       view_l, view_t, view_r, view_b;

    private Rect        scaled_view_space;

    private boolean     locked_t, locked_r, locked_b, locked_l;

    ViewPort(Position resolution)
    {
        screen_resolution = resolution;
        init();
    }

    public void init()
    {
        initializeParam(screen_resolution);

        scaled_view_space = new Rect();

        locked_t = false;
        locked_r = false;
        locked_b = false;
        locked_l = false;

        // Set factor between size between Window and ViewPort
        setScreenViewPortFactor();
    }

    public void initializeParam(Position r)
    {
        viewable_size = new Position(r.getX(), r.getY());
        screen_centre = new Position(r.getX()/4, r.getY()/2);

        // Set the ViewPort centre initially as the Screen Centre
        viewport_centre = screen_centre;

        pixels_per_metre = new Position(r.getX()/viewport_width, r.getY()/viewport_height);
    }

    public void buildViewportEdges(Position p)
    {
        Log.d("viewport.bvpe","Position passed: " + p.toString() + ", viewport dimens: " + viewable_size.toString());
        view_t = p.getY() - viewable_size.getY()/2;
        view_b = p.getY() + viewable_size.getY()/2;
        view_l = p.getX() - viewable_size.getX()/4;
        view_r = view_l + viewable_size.getX();
        Log.d("viewport.bvpe", "4 corners: [(" + view_l + " -> , " + view_r + "), (" + view_t + " -> " + view_b + ")]");
    }

    // worldToScreen(Position, Position)
    // This function takes 2 positions as parameters:
    // 1) p = Position of World co-ordinates
    // 2) d = Position of Entity dimensions [Width, Height]
    // Purpose of function is finding left, right, bottom, and top dimensions of a Rect
    // Returns Rect with (l, t, r, b)
    public Rect worldToScreen(Position p, Position d)
    {
        Log.d("Viewport/W2S", "Position passed into W2S: " + p.toString() + " with ppm: " + pixels_per_metre.toString());
        Log.d("Viewport/W2S", "Viewport centre: " + viewport_centre.toString());
        Log.d("Viewport/W2S", "Screen centre: " + screen_centre.toString());
        int l, t, r, b;
        l = (int) calcScaledLeft(p);
        t = (int) calcScaledTop(p);
        //l = (int) (screen_centre.getX() - (viewport_centre.getX() - p.getX()));
        //t = (int) (screen_centre.getY() - (viewport_centre.getY() - p.getY()));
        r = (int) (l + (pixels_per_metre.getX() * d.getX()));
        b = (int) (t + (pixels_per_metre.getY() * d.getY()));
        Log.d("ViewPort/W2S", "Values of worldToScreen: " + l + ", " + t + ", " + r + ", " + b);
        Log.d("Viewport/W2S", "===========================================================================================");

        scaled_view_space.set(l, t, r, b);
        return scaled_view_space;
    }

    private float calcScaledLeft(Position p)
    {
        Log.d("ViewPort/CalcScaledL", "Values within calcScaled: [p.getX() = " + p.getX() + "], VPC.getX() = " + viewport_centre.getX() + ", screen_centre.getX() " + screen_centre.getX());
        Log.d("ViewPort/CalcScaledL", "Result: " + (screen_centre.getX() + (p.getX() - viewport_centre.getX())));
        return (screen_centre.getX() + (p.getX() - viewport_centre.getX()));
    }

    private float calcScaledTop(Position p)
    {
        Log.d("ViewPort/CalcScaledL", "Values within calcScaled: [p.getY() = " + p.getY() + "], VPC.getY() = " + viewport_centre.getY() + ", screen_centre.getY() " + screen_centre.getY());
        Log.d("ViewPort/CalcScaledL", "Result: " + (screen_centre.getY() + (p.getY() - viewport_centre.getY())));
        return (screen_centre.getY() + (p.getY() - viewport_centre.getY()));
    }
    public Position getCentre()
    {
        return screen_centre;
    }

    // clipObjects(Position, Position)
    // This function takes the 2 same Positions as above and test them to see if an object has to be
    // clipped or not from the view.
    public boolean clipObject(Position p)
    {
        if (p.getX() < (viewport_centre.getX() - viewable_size.getX()/4))
        {
            return true;
        }

        if (p.getY() > (viewport_centre.getY() + viewable_size.getX()/2))
        {
            return true;
        }

        if (p.getX() > viewport_centre.getX() + (viewable_size.getX() - viewable_size.getX()/4))
        {
            return true;
        }

        if (p.getY() < (viewport_centre.getY() - viewable_size.getX()/2))
        {
            return true;
        }

        return false;

    }

    public Position getPixelsPerMetre()
    {
        return pixels_per_metre;
    }

    public Position getViewPortCentre()
    {
        return viewport_centre;
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
            viewport_centre = adjustViewportCentre(p);
        }

        else
        {
            viewport_centre = p;
        }

    }

    public void setMapDimens(Position b)
    {
        max_view_bounds = new Position(b.getX() * pixels_per_metre.getX(), b.getY() * pixels_per_metre.getY());
        // Log.d("vp.setmapdim", "Max dimens: " + max_view_bounds.toString());
    }

    // checkT()
    // This functions checks the TOP value of the ViewPort
    private boolean checkT()
    {
        Log.d("viewport.checkt", "value of view_t: " + view_t);
        if (view_t <= 0.0f)
        {
            locked_t = true;

            if (checkL() || checkR())
            {
                return true;
            }

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
        if (view_b > max_view_bounds.getY())
        {
            locked_b = true;

            if (checkL() || checkR())
            {
                return true;
            }

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
        Log.d("viewport.checkl", "value of view_l: " + view_l);
        if (view_l < 0)
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

    private boolean checkR()
    {
        Log.d("viewport.checkr", "value of view_r: " + view_r);
        if (view_r > max_view_bounds.getX())
        {
            locked_r = true;
            return true;
        }

        else
        {
            locked_r = false;
            return false;
        }
    }

    // edgeCheck()
    // This function checks if any of the ViewPort edges violate boundary constraints
    public boolean edgeCheck(Position p)
    {
        Log.d("Viewport.edgeC", "Position passed into edgeCheck: " + p.toString());
        if (checkT() || checkB() || checkL() || checkR())
            return true;

        else
            return false;
    }

    // adjustViewportEdges()
    // This function is called from edgeCheck() and this indicates that one of the ViewPort edges
    // would be beyond the playable space featured in the game. By checking which edge is locked
    // when can apply a lock to the ViewPort's center based on the 1 or 2 edges extending too far.
    public Position adjustViewportCentre(Position p)
    {
        float x = 0.0f;
        float y = 0.0f;

        Log.d("vp.adjustEdges", "Viewport Centre before adjust: " + p.toString());

        if (locked_t)
        {
            Log.d("Viewport.ave", "Locked top found!");
            y = viewable_size.getY()/2;
        }

        else if (locked_b)
        {
            Log.d("Viewport.ave", "Locked bot found!");
            y = max_view_bounds.getY() - viewable_size.getY()/2;
        }

        if (locked_l)
        {
            Log.d("Viewport.ave", "Locked left found!");
            x = screen_centre.getX();
            Log.d("viewport.avpe", "X co-ordinate: " + x);
        }

        else if (locked_r)
        {
            Log.d("Viewport.ave", "Locked right found!");
            x = screen_centre.getX() + max_view_bounds.getX() - viewable_size.getX();
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

    private void setScreenViewPortFactor()
    {
        factor = new Position(screen_resolution.getX()/viewable_size.getX(), screen_resolution.getY()/viewable_size.getY());
    }

    public Position getFactor()
    {
        return factor;
    }

}