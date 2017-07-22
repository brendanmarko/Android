package treadstone.game.GameEngine;

import android.util.Log;

public class Missile extends Projectile
{
    // Debug toggle
    private int     DEBUG = 0;

    public Missile(ArmedEntity o, Position pos, Position p, Position max, char t)
    {
        super(o, pos, p, max, t);
    }

    public void update()
    {
        if (DEBUG == 1)
            Log.d("Projectile/CTOR", "Before update: " + getPosition().toString());

        setPosition(getPosition().getX() + getObjInfo().getSpeed(), getPosition().getY());

        if (DEBUG == 1)
            Log.d("Prj/update", " === New position: " + getPosition().toString());
    }
}
