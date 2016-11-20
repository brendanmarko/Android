package treadstone.game.GameEngine;

public abstract class Entity
{

    private String          name;
    private Position        curr_pos;
    private int             max_x, max_y;
    private Position        start_pos;

    Entity(String new_name, int x, int y)
    {
        name = new_name;
        start_pos = new Position(x, y);
        curr_pos = new Position(x, y);
    }

    public String getName()
    {
        return name;
    }

    public int getX()
    {
        return curr_pos.getX();
    }

    public int getY()
    {
        return curr_pos.getY();
    }

    public void setPosition(int x, int y)
    {
        curr_pos.setPosition(x, y);
    }

    public void setMaxBounds(int x, int y)
    {
        max_x = x;
        max_y = y;
    }

    public int getXMax()
    {
        return max_x;
    }

    public int getYMax()
    {
        return max_y;
    }

    public void respawn()
    {
        curr_pos.setPosition(start_pos.getX(), start_pos.getY());
    }

}