package treadstone.game.GameEngine;

import android.util.Log;

public class Missile extends Projectile
{
    // Debug toggle
    private int     DEBUG = 0;

    public Missile(Entity o, Position pos, Position p, Position max, char t)
    {
        super(o, pos, p, max, t);
    }

    public boolean inBounds()
    {
        if (getPosition().getX() + getWidth() > getMaxBounds().getX())
        {
            if (DEBUG == 1)
                Log.d("Bullet/checkBounds", "Beyond max bound of level, removing.");
            return false;
        }

        return true;
    }
     
    public void updateProjectile()
    {
        if (DEBUG == 1)
            Log.d("Projectile/CTOR", "Before update: " + getPosition().toString());

        setPosition(getPosition().getX() + getObjInfo().getSpeed(), getPosition().getY());
        inBounds();

        if (DEBUG == 1)
            Log.d("Prj/update", " === New position: " + getPosition().toString());
    }
}
