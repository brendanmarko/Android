package treadstone.game.GameEngine;

public class Position
{

    private float position[] = {0.0f, 0.0f};

    Position(float new_x, float new_y)
    {
        position[0] = new_x;
        position[1] = new_y;
    }

    public float getX()
    {
        return position[0];
    }

    public float getY()
    {
        return position[1];
    }

    public String toString()
    {
    return position[0] + " , " + position[1];
    }

}