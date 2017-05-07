package treadstone.game.GameEngine;

import android.content.Context;
import android.util.Log;

public class Projectile
{
    private int             damage;
    private int             DEBUG = 1;
    private Entity          owner;
    private Position        position;
    private GameObject      info;
    private boolean         active, visible;

    public Projectile(Context c, Entity o, Position pos, Position ppm, char t)
    {
        Log.d("Projectile/CTOR", "Projectile created!");
        owner = o;
        position = pos;
        active = true;
        info = new GameObject(t);
        peak();
    }

    public void update()
    {
        if (DEBUG == 1)
            Log.d("Projectile/CTOR", "Inside Projectile update()");


    }

    public Entity getOwner()
    {
        return owner;
    }

    public void peak()
    {
        Log.d("Projectile/peak", "Projectile info: " + "Owner: " + owner.toString() + ", Position: " + position.toString());
    }

    public Position getPosition()
    {
        return position;
    }

    public boolean isActive()
    {
        return active;
    }

    public void setInvisible()
    {
        visible = false;
    }

    public void setVisible()
    {
        visible = true;
    }

    public boolean isVisible()
    {
        return visible;
    }

    public GameObject getObjInfo()
    {
        return info;
    }

}