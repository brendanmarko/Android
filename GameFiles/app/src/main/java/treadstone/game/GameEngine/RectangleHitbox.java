package treadstone.game.GameEngine;

import android.graphics.Rect;
import android.graphics.Bitmap;

public class RectangleHitbox
{

    private Rect hitbox;

    public RectangleHitbox(int x, int y, Bitmap curr_bitmap)
    {
        hitbox = new Rect(x, y, curr_bitmap.getWidth(), curr_bitmap.getHeight());
    }

    public Rect getHitbox()
    {
        return hitbox;
    }

    public void updateHitbox(int x, int y, Bitmap curr_bitmap)
    {
        hitbox.set(x, y, x + curr_bitmap.getWidth(), y + curr_bitmap.getHeight());
    }

}