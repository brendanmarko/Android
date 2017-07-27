package treadstone.game.GameEngine;

public class Position
{
    private float position[] = {0.0f, 0.0f};

    Position()
    {
        position[0] = 0.0f;
        position[1] = 0.0f;
    }

    Position(float x, float y)
    {
        position[0] = x;
        position[1] = y;
    }

    Position(Position p)
    {
        position[0] = p.getX();
        position[1] = p.getY();
    }

    public void changeX(float new_x)
    {
        position[0] = new_x;
    }

    public void changeY(float new_y)
    {
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