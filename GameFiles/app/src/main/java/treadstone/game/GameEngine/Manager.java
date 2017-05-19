package treadstone.game.GameEngine;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.ArrayList;

public abstract class Manager<T>
{
    // Debug toggle
    private int             DEBUG = 0;

    private Bitmap[]        bitmaps;
    private ArrayList<T>    storage;

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
        int index;
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
        int index;
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

}