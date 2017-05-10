package treadstone.game.GameEngine;

public abstract class MovableEntity extends Entity
{
    // Debug toggle
    private int                     DEBUG = 1;

    public abstract void boundsCheck(float x, float y);

    public MovableEntity(Position s, Position m, Position ppm, char t)
    {
        super(s, m, ppm, t);
        setMovable();
    }

    public void update()
    {

    }

}