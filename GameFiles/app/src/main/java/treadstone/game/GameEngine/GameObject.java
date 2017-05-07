package treadstone.game.GameEngine;

import java.util.Random;

public class GameObject
{
    private char            type;
    private int             layer;
    private String          image_name;
    private int             animateFrameCount;
    private Position        dimensions; // assigned as [Width, Height]
    private float           speed;

    GameObject(char object_type)
    {
        type = object_type;
        init();
    }

    public void init()
    {
        switch (type)
        {
            // Player
            case ('p'):
            {
                layer = 2;
                image_name = "bob";
                animateFrameCount = 1;
                dimensions = new Position(2.0f, 3.0f);
                speed = 10.0f;
                break;
            }

            // Bullet
            case ('b'):
            {
                layer = 2;
                image_name = "bullet";
                animateFrameCount = 1;
                dimensions = new Position(1.0f, 1.0f);
                speed = 20.0f;
                break;
            }

            // Debris
            case ('d'):
            {
                layer = 1;
                image_name = "debris";
                animateFrameCount = 1;
                dimensions = new Position(1.0f, 1.0f);
                speed = 0.0f;
                break;
            }

            // Enemy
            case ('e'):
            {
                layer = 2;
                image_name = "bob_evil";
                animateFrameCount = 1;
                dimensions = new Position(2.0f, 3.0f);
                speed = 10.0f;
                break;
            }

            //  Missile
            case ('m'):
            {
                layer = 2;
                image_name = "missile";
                animateFrameCount = 1;
                dimensions = new Position(2.0f, 1.0f);
                speed = 14.0f;
                break;
            }

            // Little Star
            case ('s'):
            {
                layer = 0;
                image_name = "star_small";
                animateFrameCount = 1;
                dimensions = new Position(1f, 1f);
                Random r = new Random();
                speed = 18.0f * r.nextFloat();
                break;
            }

            // Big Star
            case ('S'):
            {
                layer = 0;
                image_name = "star_yellow";
                animateFrameCount = 1;
                dimensions = new Position(1.0f, 1.0f);
                Random r = new Random();
                speed = 12.0f * r.nextFloat();
                break;
            }

            default:
                layer = 0;
                animateFrameCount = 1;
                image_name = "default";
                speed = 0.0f;
                dimensions = new Position(0.0f, 0.0f);
        }
    }

    public int getLayer()
    {
        return layer;
    }

    public String getImageName()
    {
        return image_name;
    }

    public int getAnimateFrameCount()
    {
        return animateFrameCount;
    }

    public Position getDimensions()
    {
        return dimensions;
    }

    public float getSpeed()
    {
        return speed;
    }

    public char getType()
    {
        return type;
    }

}