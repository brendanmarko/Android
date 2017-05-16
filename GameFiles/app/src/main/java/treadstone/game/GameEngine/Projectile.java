package treadstone.game.GameEngine;

import android.util.Log;

public abstract class Projectile extends MovableEntity
{
    // Debug toggle
    private int                 DEBUG = 0;

    private Entity              owner;
    private float               range;

    // Abstract functions
    public abstract boolean     inBounds();
    public abstract void        update();

    public Projectile(Entity o, Position pos, Position max, Position p, char t)
    {
        super(pos, max, p, t);
        owner = o;
        range = getObjInfo().getEffectiveRange();

        if (DEBUG == 1)
            Log.d("Projectile/CTOR", "Projectile created!");
    }

    public Entity getOwner()
    {
        return owner;
    }

    @Override
    public String toString()
    {
        return "Projectile info: " + "Owner: " + owner.toString() + ", Position: " + getPosition().toString();
    }

    @Override
    public void updateHitbox(float x, float y)
    {
        if (DEBUG == 1)
        {
            Log.d("Prj/updateHB", "Values within updateHB: " + "POS: " + getPosition().toString());
            Log.d("Prj/updateHB", "Values within updateHB: " + "PPM: " + getPPM().toString());
            Log.d("Prj/updateHB", "Values within updateHB: " + "DIMENS: " + getObjInfo().getDimensions().toString());
        }

        Position temp = new Position(getPosition().getX() - x + owner.getWidth(), getPosition().getY() - y + owner.getHeight()/3);
        setHitbox(new RectangleHitbox(temp, getPPM(), getObjInfo().getDimensions()));
    }

    public float getRange()
    {
        return range;
    }

}