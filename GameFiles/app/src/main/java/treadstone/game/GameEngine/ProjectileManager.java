package treadstone.game.GameEngine;

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
    private ArrayList<Projectile>   projectiles;

    public ProjectileManager(LevelManager l, ViewPort v)
    {
        viewport = v;
        levelMgr = l;
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

    public void addBuffer(ArrayList<Projectile> buffer)
    {
        if (DEBUG == 1)
            Log.d("PrjMgr/addBuffer" , "Adding buffer to projectiles!");

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
                    Log.d("PrjMgr/update", "Projectile set as Invisible");
                }

                else
                {
                    e.setVisible();
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

}