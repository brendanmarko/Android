package treadstone.game.GameEngine;

public class ArmedEntity extends MovableEntity
{
    // Debug info
    private int         DEBUG = 1;
    private String      DEBUG_TAG = "ArmedEntity";

    private double      aim_angle;

    public ArmedEntity(Position p, Position m, Position ppm, char t)
    {
        super(p, m, ppm, t);
    }

    @Override
    public void update()
    {

    }

    public double getAimAngle()
    {
        return aim_angle;
    }

    public void setAimAngle(double d)
    {
        aim_angle = d;
    }
}
