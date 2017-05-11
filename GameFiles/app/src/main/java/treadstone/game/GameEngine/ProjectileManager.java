package treadstone.game.GameEngine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import java.util.ArrayList;
import java.util.Iterator;

public class ProjectileManager
{
    // Debug toggle
private int                     DEBUG = 0;

    private ViewPort                viewport;

    // Storage of Projectiles
    private Bitmap[]                bitmaps;
    private ArrayList<Projectile>   projectiles;

    public ProjectileManager(ViewPort v)
    {
        viewport = v;
        bitmaps = new Bitmap[20];
        projectiles = new ArrayList<Projectile>();

        if (DEBUG == 1)
            Log.d("PMgr/CTOR", "ProjectileManager initialized!");
    }

    public int numProjectiles()
    {
        return projectiles.size();
    }

    public ArrayList<Projectile> getProjectiles()
    {
        return projectiles;
    }

    public void addBuffer(Context c, ArrayList<Projectile> buffer)
    {
        if (DEBUG == 1)
            Log.d("PrjMgr/addBuffer" , "Adding buffer to projectiles!");

        for (Projectile p : buffer)
            checkBitmap(c, p, p.getObjInfo().getType());

        projectiles.addAll(buffer);

        if (DEBUG == 1)
            Log.d("PrjMgr/addBuffer" , "New Size of projectiles: " + projectiles.size());
    }

    public void update(float x, float y)
    {
    	for (Iterator<Projectile> iterator = projectiles.iterator(); iterator.hasNext();)
        {
            Projectile e = iterator.next();

            if (e.isActive())
            {
                if (viewport.clipObject(e.getPosition()))
                {
                    e.setInvisible();
                    if (DEBUG == 1)
                        Log.d("PrjMgr/update", "Projectile set as Invisible");
                }

                else
                {
                    e.setVisible();
                    if (DEBUG == 1)
                        Log.d("PrjMgr/update", "Projectile set as Visible");
                }

                e.updateHitbox(x, y);
            }
    	}
    }

    public void checkBitmap(Context c, Projectile p, char d)
    {
        if (bitmaps[getIndex(d)] != null)
            return;

        else
            bitmaps[getIndex(d)] = p.createBitmap(c, p.getObjInfo().getImageName());
    }

    public int getIndex(char c)
    {
        int index;
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
        int index;
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

        return bitmaps[index];
    }

}