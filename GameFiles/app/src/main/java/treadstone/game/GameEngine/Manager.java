package treadstone.game.GameEngine;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;

public abstract class Manager<T>
{
    // Debug info
    private int             DEBUG = 0;
    private String          DEBUG_TAG = "Mgr";

    private Bitmap[]        bitmaps;
    private ArrayList<T>    storage;
    private int             index;

    Manager()
    {
        bitmaps = new Bitmap[20];
        storage = new ArrayList<T>();
    }

    public ArrayList<T> getList()
    {
        return storage;
    }

    public Bitmap getBitmap(char c)
    {
        switch (c)
        {
            case 'p':
                index = 1;
                break;

            case 'e':
                index = 2;
                break;

            case 'd':
                index = 3;
                break;

            default:
                index = 0;
        }

        return bitmaps[index];
    }

    public int getIndex(char c)
    {
        switch (c)
        {
            case 'p':
                index = 1;
                break;

            case 'e':
                index = 2;
                break;

            case 'd':
                index = 3;
                break;

            default:
                index = 0;
        }

        return index;
    }

    // checkBitmap()
    // This functions checks if the specified Bitmap has already been prepared for use and prepares
    // the specified Bitmap if it requires it (builds up Bitmap array)
    public void checkBitmap(Context c, Entity e, char d)
    {
        if (bitmaps[getIndex(d)] != null)
            return;

        else
            bitmaps[getIndex(d)] = e.createBitmap(c, e.getImageName());
    }

    public Bitmap[] getBitmaps()
    {
        return bitmaps;
    }

    public Bitmap bitmapAt(int i)
    {
        return bitmaps[i];
    }

    public double matrixRotationConversion(double a)
    {
        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "Entered matrixRotationConversion with: " + a);

        if (a == 0.0d || a == 360.0d)
            return 90;

        else
        {
            if (DEBUG == 1)
                Log.d(DEBUG_TAG, "Value other than 0/360 entered.");

            double result = 90 - (45 * (a/45));

            if (result < 0.0d)
                result += 360.0d;

            if (DEBUG == 1)
                Log.d(DEBUG_TAG, "Value before adjustAngle: " + result);

            result = adjustAngle(result);

            if (DEBUG == 1)
                Log.d(DEBUG_TAG, "Value after adjustAngle: " + result);

            return result;
        }

    }

    // adjustAngle(double)
    // This function takes an angle as a parameter and rounds it to the closest value of the 8 cardinal
    // directions to move the Player.
    public double adjustAngle(double f)
    {
        // Between [22.5, 67.5]
        if (22.5d < f  && f <= 67.5d)
        {
            return 45.0d;
        }

        // Between [67.5, 112.5]
        else if (67.5d < f && f <= 112.5d)
        {
            return 90.0d;
        }

        // Between [112.5, 157.5]
        else if (112.5d < f && f <= 157.5d)
        {
            return 135.0d;
        }

        // Between [157.5, 202.5]
        else if (157.5d < f && f <= 202.5d)
        {
            return 180.0d;
        }

        // Between [202.5, 247.5]
        else if (202.5d < f && f <= 247.5d)
        {
            return 225.0d;
        }

        // Between [247.5, 292.5]
        else if (247.5d < f && f <= 292.5d)
        {
            return 270.0d;
        }

        // Between [292.5, 337.5]
        else if (292.5d < f && f <= 337.5d)
        {
            return 315.0d;
        }

        // Between [337.5, 22.5]
        else
        {
            return 0.0d;
        }

    }

    public float wrapAroundValue(float x)
    {
        if (x < 0)
            return 360.0f + x;

        else if (x > 360)
            return x - 360.0f;

        else
            return x;
    }

}