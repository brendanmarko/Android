package treadstone.game.GameEngine;

public class Position
{

    private int position[] = {0, 0};

    Position(int new_x, int new_y)
    {
        position[0] = new_x;
        position[1] = new_y;
    }

    public int getX()
    {
        return position[0];
    }

    public int getY()
    {
        return position[1];
    }

    public void setPosition(int new_x, int new_y)
    {
        position[0] = new_x;
        position[1] = new_y;
    }

}