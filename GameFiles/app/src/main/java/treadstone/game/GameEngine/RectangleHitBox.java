package treadstone.game.GameEngine;

import android.graphics.Rect;
import android.graphics.Bitmap;

public class RectangleHitBox
{
    private Rect hitbox;

    public RectangleHitBox(int x, int y, Position p)
    {
        hitbox = new Rect(x, y, (int) p.getX(), (int) p.getY());
    }

    public RectangleHitBox(Position p, Bitmap curr_bitmap)
    {
        hitbox = new Rect((int) p.getX(), (int) p.getY(), curr_bitmap.getWidth(), curr_bitmap.getHeight());
    }

    public Rect getHitBox()
    {
        return hitbox;
    }

    public void update(int x, int y, Bitmap curr_bitmap)
    {
        hitbox.set(x, y, x + curr_bitmap.getWidth(), y + curr_bitmap.getHeight());
    }

}