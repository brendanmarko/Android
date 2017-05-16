package treadstone.game.GameEngine;

import android.util.Log;

import java.util.Random;

public class GameObject
{
    private char            type;
    private int frame_count, layer;
    private float           speed, effective_range;
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
                dimensions = new Position(2.0f, 3.0f);
                speed = 10.0f;
                movementType = "dynamic";
                break;
            }

            // Bullet
            case ('b'):
            {
                layer = 2;
                image_name = "bullet";
                frame_count = 1;
                dimensions = new Position(1.0f, 1.0f);
                speed = 20.0f;
                movementType = "dynamic";
                effective_range = 2000.0f;
                break;
            }

            // Debris
            case ('d'):
            {
                layer = 1;
                image_name = "debris";
                frame_count = 1;
                dimensions = new Position(1.0f, 1.0f);
                movementType = "static";
                break;
            }

            // Enemy
            case ('e'):
            {
                layer = 2;
                image_name = "bob_evil";
                frame_count = 1;
                dimensions = new Position(2.0f, 3.0f);
                speed = 10.0f;
                movementType = "dynamic";
                break;
            }

            //  Missile
            case ('m'):
            {
                layer = 2;
                image_name = "missile";
                frame_count = 1;
                dimensions = new Position(2.0f, 1.0f);
                speed = 14.0f;
                movementType = "dynamic";
                effective_range = 3000.0f;
                break;
            }

            // Little Star
            case ('s'):
            {
                layer = 0;
                image_name = "star_small";
                frame_count = 1;
                dimensions = new Position(1f, 1f);
                Random r = new Random();
                speed = 18.0f * r.nextFloat();
                movementType = "dynamic";
                break;
            }

            // Big Star
            case ('S'):
            {
                layer = 0;
                image_name = "star_yellow";
                frame_count = 1;
                dimensions = new Position(1.0f, 1.0f);
                Random r = new Random();
                speed = 12.0f * r.nextFloat();
                movementType = "dynamic";
                break;
            }

            default:
                layer = 0;
                frame_count = 1;
                image_name = "default";
                speed = 0.0f;
                dimensions = new Position(0.0f, 0.0f);
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

    public float getSpeed()
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

    public float getEffectiveRange()
    {
        return effective_range;
    }
}