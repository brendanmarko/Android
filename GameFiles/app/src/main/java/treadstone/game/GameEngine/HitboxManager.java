package treadstone.game.GameEngine;

import android.util.Log;
import java.util.ArrayList;
import android.graphics.Rect;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Canvas;

public class HitboxManager extends Manager
{
    // Debug toggle
    private int         DEBUG = 0;

    HitboxManager()
    {
        if (DEBUG == 1)
            Log.d("HitboxMgr/CTOR", "HitboxManager created.");
    }

    public void draw(ArrayList<Entity> ent, ArrayList<Projectile> prj, Canvas c, Paint p)
    {
        Rect box;

        p.setColor(Color.argb(255, 100, 100, 200));

        if (DEBUG == 1)
            Log.d("GameView/drawHB", "Drawing Entity boxes");

        // Draw Hitboxes for Entities
        for (Entity e : ent)
        {
            if (DEBUG == 1)
                Log.d("GameView/drawHB", "Drawing entity: " + e.toString());

            if (e.isVisible()) {
                box = new Rect(e.getHitbox());
                c.drawRect(box.left, box.top, box.right, box.bottom, p);
            } else
                box = null;

            if (DEBUG == 1 && box != null)
                Log.d("GameView/drawHB", "Box value: " + box.toString());

        }

        if (DEBUG == 1)
            Log.d("GameView/drawHB", "Drawing Projectile boxes");

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
                Log.d("GameView/drawHBP", "Prj value: " + box.toString());
        }
    }

}
