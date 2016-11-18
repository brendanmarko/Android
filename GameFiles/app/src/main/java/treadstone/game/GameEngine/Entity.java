package treadstone.game.GameEngine;

public abstract class Entity
{

    private String          name;
    private int             x, y;
    private Position        curr_pos;

    Entity(String new_name, int x, int y)
    {
        name = new_name;
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

}