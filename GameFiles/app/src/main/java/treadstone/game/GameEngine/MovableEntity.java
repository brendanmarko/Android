package treadstone.game.GameEngine;

import android.util.Log;

public abstract class MovableEntity extends Entity
{
    // Debug toggle
    private int                     DEBUG = 1;

    public MovableEntity()
    {
        if (DEBUG == 1)
            Log.d("MovableE/CTOR", "Empty MovableE created.");
    }

    public MovableEntity(Position p, Position m, Position ppm, char t)
    {
        super(p, m, ppm, t);
    }

}