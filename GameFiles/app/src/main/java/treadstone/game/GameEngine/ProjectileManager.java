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
    private int                     DEBUG = 1;

    private ViewPort                viewport;
    private LevelManager            levelMgr;

    // Storage of Projectiles
    private Bitmap[]                bitmaps;
    private ArrayList<Projectile>   projectiles;

    public ProjectileManager(LevelManager l, ViewPort v)
    {
        viewport = v;
        levelMgr = l;
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

    public void update()
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
            }
    	}
    }


    public void add(Projectile p)
    {
        if (DEBUG == 1)
            Log.d("PMgr/add", "=== Adding to PMGR: " + p.toString());

        projectiles.add(p);

        if (DEBUG == 1)
            Log.d("PMgr/add", "=== add() successful");
    }

    public void drawProjectiles(Canvas canvas, Paint paint)
    {
        if (DEBUG == 1)
        {
            if (projectiles.size() > 0)
            {
                Log.d("GameView/DrawP", "Drawing Projectiles!");
                Log.d("GameView/DrawP", "=== Starting draw from PrjMgr with size: " + projectiles.size());
            }
        }

        for (Projectile p : projectiles)
        {
            if (p.isVisible())
                drawP(canvas, paint, p);
        }

        if (DEBUG == 1)
            if (projectiles.size() > 0)
                Log.d("GameView/DrawP", "=== Drawing Projectiles complete!");
    }

    public void drawP(Canvas canvas, Paint paint, Projectile p)
    {
        if (DEBUG == 1)
            Log.d("GameView/DrawP", "Drawing Projectile p: " + p.toString());

        Rect new_target = new Rect();
        new_target.set(viewport.worldToScreen(p.getPosition(), p.getObjInfo().getDimensions()));
        canvas.drawBitmap(levelMgr.getBitmap(p.getObjInfo().getType()), new_target.left, new_target.top, paint);
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