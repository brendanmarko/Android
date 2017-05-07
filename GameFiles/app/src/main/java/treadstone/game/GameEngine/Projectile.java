package treadstone.game.GameEngine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class Projectile
{
    private int             damage;
    private Bitmap          image;
    private int             DEBUG = 1;
    private Entity          owner;
    private Position        position;
    private GameObject      info;
    private boolean         active, visible;

    public Projectile(Context c, Entity o, Position pos, Position ppm, char t)
    {
        owner = o;
        position = pos;
        active = true;
        info = new GameObject(t);

        if (DEBUG == 1)
            Log.d("Projectile/CTOR", "Projectile created!");
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

    public String toString()
    {
        Log.d("Projectile/peak", "Projectile info: " + "Owner: " + owner.toString() + ", Position: " + position.toString());
        return "Projectile info: " + "Owner: " + owner.toString() + ", Position: " + position.toString();
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

    public int getLayer()
    {
        return info.getLayer();
    }

    public Bitmap createBitmap(Context c, String s)
    {
        int id = c.getResources().getIdentifier(s, "drawable", c.getPackageName());
        image = BitmapFactory.decodeResource(c.getResources(), id);
        image = Bitmap.createScaledBitmap(image, (int) (info.getDimensions().getX() * info.getAnimateFrameCount()), (int) (info.getDimensions().getX() * info.getAnimateFrameCount()), false);
        return image;
    }

}