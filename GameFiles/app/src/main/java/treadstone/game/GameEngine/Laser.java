package treadstone.game.GameEngine;

import android.util.Log;

public class Laser extends Projectile
{
    // Debug toggle
    private int     DEBUG = 1;

    public Laser(Entity o, Position pos, Position p, Position max, char t)
    {
        super(o, pos, p, max, t);
    }

    public boolean inBounds()
    {
        if (getPosition().getX() + getWidth() > getMaxBounds().getX())
        {
            if (DEBUG == 1)
                Log.d("Laser/checkBounds", "Beyond max bound of level, removing.");
            return false;
        }

        return true;
    }

    public void updateProjectile()
    {
        if (DEBUG == 1)
            Log.d("Laser/update", "Before update: " + getPosition().toString());

        setPosition(getPosition().getX() + getObjInfo().getSpeed(), getPosition().getY());
        inBounds();

        if (DEBUG == 1)
            Log.d("Laser/update", " === New position: " + getPosition().toString());
    }
}