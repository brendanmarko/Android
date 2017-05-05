package treadstone.game.GameEngine;

import android.content.Context;
import android.util.Log;

public abstract class MovableEntity extends Entity
{
    private RectangleHitBox         hitbox;
    private int                     DEBUG = 1;

    public abstract void boundsCheck(float x, float y);

    public MovableEntity(Context c, Position s, Position m, Position ppm, char t)
    {
        super(c, s, m, ppm, t);
        setMovable();
        //setHitBox();
    }

    public void setHitBox()
    {
        if (DEBUG == 1)
            Log.d("player_test_object+++", "Entering setHitBox");

        hitbox = new RectangleHitBox((int) getGameObject().getDimensions().getX(), (int) getGameObject().getDimensions().getY(), getImage());
    }


    public RectangleHitBox getHitBox()
    {
        return hitbox;
    }

}