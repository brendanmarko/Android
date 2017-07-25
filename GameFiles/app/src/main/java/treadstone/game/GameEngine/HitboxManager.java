package treadstone.game.GameEngine;

import android.util.Log;
import java.util.ArrayList;
import android.graphics.Rect;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Canvas;

public class HitboxManager extends Manager
{
    // Debug info
    private int         DEBUG = 2;
    private String      DEBUG_TAG = "HitboxMgr";

    HitboxManager()
    {
        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "HitboxManager created.");
    }

    public void draw(ArrayList<Entity> ent, ArrayList<Projectile> prj, Canvas c, Paint p)
    {
        Rect box;

        p.setColor(Color.argb(255, 100, 100, 200));

        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "Drawing Entity boxes");

        // Draw Hitboxes for Entities
        for (Entity e : ent)
        {
            if (DEBUG == 1)
                Log.d(DEBUG_TAG, "Drawing entity: " + e.toString());

            if (e.isVisible())
            {
                box = new Rect(e.getHitbox());
                if (e.getRotationAngle() == 0.0d)
                    c.drawRect(box.left, box.top, box.right, box.bottom, p);

                else
                {
                    if (DEBUG == 2)
                        Log.d(DEBUG_TAG, "Rotated Entity found, rotating Hitbox with angle: " + e.getRotationAngle());
                    c.save();
                    //c.rotate((int) e.getRotationAngle(), e.getPosition().getX() + e.getWidth()/2, e.getPosition().getY() + e.getHeight()/2);
                    //c.drawRect(box.left, box.top, box.right, box.bottom, p);
                    //c.drawRect(e.getPosition().getX(), e.getPosition().getY(), e.getPosition().getX() + e.getWidth(),e.getPosition().getY() + e.getHeight(), p);
                    c.restore();
                }
            }

            else
                box = null;

            if (DEBUG == 1 && box != null)
                Log.d(DEBUG_TAG, "Box value: " + box.toString());

        }

        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "Drawing Projectile boxes");

        p.setColor(Color.argb(255, 255, 255, 0));

        // Draw Hitboxes for Projectiles
        for (Projectile projectile : prj)
        {
            if (projectile.isVisible())
            {
                box = new Rect(projectile.getHitbox());
                c.drawRect(box.left, box.top, box.right, box.bottom, p);
            }

            else
                box = null;

            if (DEBUG == 1 && box != null)
                Log.d(DEBUG_TAG, "Prj value: " + box.toString());
        }
    }

}
