package treadstone.game.GameEngine;

import android.util.Log;

import java.util.Random;

public class GameObject
{
    private char            type;
    private int             frame_count, layer, boost_factor;
    private double          speed, effective_range;
    private String          movementType;
    private String          image_name;
    private Position        dimensions; // assigned as [Width, Height]

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
                frame_count = 1;
                dimensions = new Position(2.0d, 3.0d);
                speed = 3.5d;
                movementType = "dynamic";
                boost_factor = 3;
                break;
            }

            // Bullet
            case ('b'):
            {
                layer = 2;
                image_name = "bullet";
                frame_count = 1;
                dimensions = new Position(1.0d, 1.0d);
                speed = 12.0d;
                movementType = "dynamic";
                effective_range = 2000.0d;
                break;
            }

            // Debris
            case ('d'):
            {
                layer = 1;
                image_name = "debris";
                frame_count = 1;
                dimensions = new Position(1.0d, 1.0d);
                movementType = "static";
                break;
            }

            // Enemy
            case ('e'):
            {
                layer = 2;
                image_name = "bob_evil";
                frame_count = 1;
                dimensions = new Position(2.0d, 3.0d);
                speed = 10.0d;
                movementType = "dynamic";
                break;
            }

            //  Missile
            case ('m'):
            {
                layer = 2;
                image_name = "missile";
                frame_count = 1;
                dimensions = new Position(2.0d, 1.0d);
                speed = 14.0d;
                movementType = "dynamic";
                effective_range = 3000.0d;
                break;
            }

            // Little Star
            case ('s'):
            {
                layer = 0;
                image_name = "star_small";
                frame_count = 1;
                dimensions = new Position(1.0d, 1.0d);
                Random r = new Random();
                speed = 18.0d * r.nextDouble();
                movementType = "dynamic";
                break;
            }

            // Big Star
            case ('S'):
            {
                layer = 0;
                image_name = "star_yellow";
                frame_count = 1;
                dimensions = new Position(1.0d, 1.0d);
                Random r = new Random();
                speed = 12.0d * r.nextDouble();
                movementType = "dynamic";
                break;
            }

            // Planet
            case ('P'):
            {
                layer = 1;
                image_name = "planet_small";
                speed = 0.0d;
                movementType = "static";
                dimensions = new Position(4.0d, 4.0d);
                frame_count = 1;
            }

            default:
                layer = 0;
                frame_count = 1;
                image_name = "default";
                speed = 0.0d;
                dimensions = new Position(0.0d, 0.0d);
                movementType = "static";
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

    public int getFrameCount()
    {
        return frame_count;
    }

    public Position getDimensions()
    {
        return dimensions;
    }

    public double getSpeed()
    {
        return speed;
    }

    public char getType()
    {
        return type;
    }

    public String toString()
    {
        Log.d("GameObject", image_name + " dimens: " + dimensions + " in layer " + layer);
        return image_name + " dimens: " + dimensions + " in layer " + layer;
    }

    public String getMovementType()
    {
        return movementType;
    }

    public double getEffectiveRange()
    {
        return effective_range;
    }

    public int getBoostFactor()
    {
        return boost_factor;
    }
}