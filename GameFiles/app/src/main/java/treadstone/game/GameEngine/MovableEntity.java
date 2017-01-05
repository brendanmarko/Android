package treadstone.game.GameEngine;

import android.content.Context;
import android.util.Log;

public abstract class MovableEntity extends Entity
{
    private RectangleHitBox hitbox;

    public abstract void update();
    public abstract void boundsCheck(float x, float y);

    public MovableEntity(Context c, Position s, Position m, char t)
    {
        super(c, s, m, t);
        setMovable();
        Log.d("player_test_object+++", "Setting hitbox");
        // setHitBox();
    }

    public void setHitBox()
    {
        Log.d("player_test_object+++", "Entering setHitBox");
        hitbox = new RectangleHitBox((int) getType().getDimensions().getX(), (int) getType().getDimensions().getY(), getImage());
    }


    public RectangleHitBox getHitBox()
    {
        return hitbox;
    }

}