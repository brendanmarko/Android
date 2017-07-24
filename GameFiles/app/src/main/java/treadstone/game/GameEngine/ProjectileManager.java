package treadstone.game.GameEngine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import java.util.ArrayList;
import java.util.Iterator;

public class ProjectileManager extends Manager
{
    // Debug info
    private String                  DEBUG_TAG = "PrjMgr/";
    private int                     DEBUG = 0;

    private ViewPort                viewport;
    private int                     index;

    private RefractionHandler       refraction_handler;

    public ProjectileManager(ViewPort v)
    {
        super();
        viewport = v;
        refraction_handler = new RefractionHandler();

        if (DEBUG == 1)
            Log.d(DEBUG_TAG + "CTOR", "ProjectileManager initialized!");
    }

    public void addBuffer(Context c, ArrayList<Projectile> buffer)
    {
        if (DEBUG == 1)
            Log.d(DEBUG_TAG + "addBuffer" , "Adding buffer to projectiles!");

        for (Projectile p : buffer)
            checkBitmap(c, p, p.getObjInfo().getType());

        getList().addAll(buffer);

        if (DEBUG == 1)
            Log.d(DEBUG_TAG + "addBuffer" , "New Size of projectiles: " + getList().size());
    }

    public void update(float x, float y)
    {
    	for (Iterator<Projectile> iterator = getList().iterator(); iterator.hasNext();)
        {
            Projectile e = iterator.next();
            if (e.isActive())
            {
                // Checks if Projectile is needed within the ViewPort
                if (viewport.clipObject(e.getPosition()))
                {
                    e.setInvisible();
                    if (DEBUG == 1)
                        Log.d(DEBUG_TAG + "update", "Projectile set as Invisible");
                }

                else
                {
                    e.setVisible();
                    if (DEBUG == 1)
                        Log.d(DEBUG_TAG + "update", "Projectile set as Visible");
                }

                int temp = e.inBounds();

                if (temp != 0)
                {
                    if (DEBUG == 1)
                        Log.d(DEBUG_TAG + "!OB", "Prj found to need refracting.");
                    refraction_handler.refractionChange(e, temp);
                    e.buildTravelVector(e.getMovementAngle());
                }

                e.update();
                e.updateHitbox(x, y);
            }
    	}
    }

    public void checkBitmap(Context c, Projectile p, char d)
    {
        if (getBitmaps()[getIndex(d)] != null)
            return;

        else
            getBitmaps()[getIndex(d)] = p.createBitmap(c, p.getObjInfo().getImageName());
    }

    public int getIndex(char c)
    {
        switch (c)
        {
            case 'b':
                index = 1;
                break;

            case 'm':
                index = 2;
                break;

            case 's':
                index = 3;
                break;

            default:
                index = 0;
        }

        return index;
    }

    public Bitmap getBitmap(char c)
    {
        switch (c)
        {
            case 'b':
                index = 1;
                break;

            case 'm':
                index = 2;
                break;

            case 's':
                index = 3;
                break;

            default:
                index = 0;
        }

        return getBitmaps()[index];
    }

    public void draw(Canvas canvas, Paint paint)
    {
        Rect r = new Rect();

        if  (DEBUG == 1)
            Log.d(DEBUG_TAG + "DrawPrj", "Drawing Projectiles with size: " + getList().size());

        for (int layer = 0; layer < 3; layer++)
        {
            for (Iterator<Projectile> iterator = getList().iterator(); iterator.hasNext();)
            {
                Projectile p = iterator.next();
                if (p.isVisible() && p.getLayer() == layer)
                {
                    r.set(viewport.worldToScreen(p.getPosition(), p.getObjInfo().getDimensions()));
                    canvas.drawBitmap(getBitmap(p.getObjInfo().getType()), r.left + p.getOwner().getWidth(), r.top + p.getOwner().getHeight()/3, paint);
                }
            }
        }
    }
}