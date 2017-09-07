package treadstone.game.GameEngine;

public class Position
{
    private double position[] = {0.0d, 0.0d};

    Position()
    {
        position[0] = 0.0d;
        position[1] = 0.0d;
    }

    Position(double x, double y)
    {
        position[0] = x;
        position[1] = y;
    }

    Position(Position p)
    {
        position[0] = p.getX();
        position[1] = p.getY();
    }

    public void updatePosition(double x, double y)
    {
        position[0] += x;
        position[1] += y;
    }

    // equalTo(Pos)
    // This function checks whether 2 positions are equal to each other
    public boolean equalTo(Position p)
    {
        if (position[0] == p.getX() && position[1] == p.getY())
            return true;
        return false;
    }

    public void changeX(double new_x)
    {
        position[0] = new_x;
    }

    public void changeY(double new_y)
    {
        position[1] = new_y;
    }

    public double getX()
    {
        return position[0];
    }

    public double getY()
    {
        return position[1];
    }

    public String toString()
    {
    return position[0] + " , " + position[1];
    }

}