package treadstone.game.GameEngine;

import android.util.Log;

public class Bullet extends Projectile
{
    // Debug toggle
    private int         DEBUG = 0;
    private String      init_direction;

    public Bullet(MovableEntity o, Position pos, Position max, Position p, char t)
    {
        super(o, pos, max, p, t);
        init_direction = o.getDirection();
    }

    public boolean inBounds()
    {
        if (DEBUG == 1)
            Log.d("Bullet/inBounds", "Position inBounds: " + getPosition().toString());

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
        if (DEBUG == 1)
            Log.d("Projectile/CTOR", "Before update: " + getPosition().toString());

        calcDisplacement(init_direction);

        if (DEBUG == 1)
            Log.d("Prj/update", " === New position: " + getPosition().toString());
    }

}
