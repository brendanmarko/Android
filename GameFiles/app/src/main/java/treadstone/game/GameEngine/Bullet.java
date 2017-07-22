package treadstone.game.GameEngine;

import android.util.Log;

public class Bullet extends Projectile
{
    // Debug info
    private int         DEBUG = 1;
    private String      DEBUG_TAG = "Bullet/";

    public Bullet(ArmedEntity o, Position pos, Position max, Position p, char t)
    {
        super(o, pos, max, p, t);
        startMovement();

        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "Angle [bullet creation]: " + getMovementAngle());
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
