package treadstone.game.GameEngine;

import android.util.Log;

public class Missile extends Projectile
{
    // Debug info
    private String  DEBUG_TAG = "Missile/";
    private int     DEBUG = 0;

    public Missile(ArmedEntity o, Position pos, Position p, Position max, char t)
    {
        super(o, pos, p, max, t);
    }

    public void update()
    {
        setPosition(getX() + getTravelVector().getX(), getY() + getTravelVector().getY());
    }
}
