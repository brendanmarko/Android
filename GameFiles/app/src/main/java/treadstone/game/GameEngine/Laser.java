package treadstone.game.GameEngine;

import android.util.Log;

public class Laser extends Projectile
{
    // Debug toggle
    private int     DEBUG = 0;

    Laser(ArmedEntity o, Position p, Position m, Position ppm, char t)
    {
        super(o, p, m, ppm, t);
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