package treadstone.game.GameEngine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import treadstone.game.R;

public class Player extends Entity
{

    private int         speed;
    private Bitmap      image;

    Player(Context context, String name, int x, int y, int curr_speed)
    {
        super(name, x, y);
        speed = curr_speed;
        image = BitmapFactory.decodeResource(context.getResources(), R.drawable.bob);
    }

    public int getSpeed()
    {
        return speed;
    }

    private void changeSpeed(int new_speed)
    {
        speed = new_speed;
    }

    public void update()
    {
        setPosition(getX()+10, getY());
    }

    public Bitmap getImage()
    {
        return image;
    }

}