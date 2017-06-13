package treadstone.game.GameEngine;

import android.util.Log;

public class Bullet extends Projectile
{
    // Debug info
    private int         DEBUG = 1;
    private String      DEBUG_TAG = "Bullet";

    private double      init_direction;

    public Bullet(ArmedEntity o, Position pos, Position max, Position p, char t)
    {
        super(o, pos, max, p, t);
        startMovement();
        init_direction = o.getAimAngle();
        calcAngleDisplacement(init_direction);
        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "Angle bullet is created with: " + init_direction);
    }

    public boolean inBounds()
    {
        if (DEBUG == 1)
            // Log.d("Bullet/inBounds", "Position inBounds: " + getPosition().toString());

        if (getPosition().getX() + getWidth() > getMaxBounds().getX())
        {
            if (DEBUG == 1)
                Log.d("Bullet/inBounds", "Beyond max bound of level, removing.");

            return false;
        }

        return true;
    }

    public void update()
    {
        //if (DEBUG == 1)
            //Log.d("Projectile/CTOR", "Before update: " + getPosition().toString());

        setPosition(getPosition().getX() + getTravelVector().getX(), getPosition().getY() + getTravelVector().getY());

        //if (DEBUG == 1)
            //Log.d("Prj/update", " === New position: " + getPosition().toString());
    }

}
