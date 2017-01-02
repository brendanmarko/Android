package treadstone.game.GameEngine;

import android.content.Context;

public abstract class MovableEntity extends Entity
{
    private RectangleHitBox hitbox;

    public abstract void update();
    public abstract void boundsCheck(float x, float y);

    public MovableEntity(Context c, Position s, Position m, char t)
    {
        super(c, s, m, t);
        setMovable();
        setHitBox();
    }

    public void setHitBox()
    {
        hitbox = new RectangleHitBox((int) getWidth(), (int) getHeight(), getImage());
    }


    public RectangleHitBox getHitBox()
    {
        return hitbox;
    }

}