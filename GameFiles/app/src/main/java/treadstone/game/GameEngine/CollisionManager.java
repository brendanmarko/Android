package treadstone.game.GameEngine;

import android.graphics.Rect;
import android.util.Log;

import java.util.ArrayList;

public class CollisionManager
{
    // Debug toggle
    private int DEBUG = 0;

    public CollisionManager()
    {
        if (DEBUG == 1)
            Log.d("CollMgr/CTOR", "CollisionMgr created");
    }

    // void entityCollisions()
    // This functions checks if there are any collisions between entities managed by GameView
    public void entityCollisions(ArrayList<Entity> e)
    {
        if (DEBUG == 1)
            Log.d("CollMgr/eCollide", "Checking entities for collisions...");

        for (int i = 0; i < e.size(); i++)
        {
            if (e.get(i).isVisible())
            {
                for (int j = i + 1; j < e.size(); j++)
                {
                    if (e.get(j).isVisible())
                    {
                        if (DEBUG == 1)
                            Log.d("CollMgr/entityColl", e.get(i).toString() + ", " + e.get(j).toString());

                        if (Rect.intersects(e.get(j).getHitbox(), e.get(i).getHitbox()))
                        {
                            if (DEBUG == 1)
                                Log.d("CollMgr/entityColl", "Collision between entities found!");
                        }
                    }
                }
            }

            if (DEBUG == 1)
                Log.d("CollMgr/entityColl", "=======================================");
        }
    }

    // void projectileCollisions()
    // This function checks if there are collisions between entities and projectiles that
    // are NOT owned by them
    public void projectileCollisions(ArrayList<Entity> e, ArrayList<Projectile> p)
    {
        if (DEBUG == 1)
            Log.d("CollMgr/pCollide", "Checking entities & projectiles for collisions...");

        for (Projectile prj : p)
        {
            for (Entity obj : e)
            {
                if (obj.isVisible())
                {
                    if (obj.getHitbox().intersect(prj.getHitbox()) && prj.getOwner() != obj)
                    {
                        if (DEBUG == 1)
                            Log.d("CollMgr/prjColl", "Collision between entity and projectile found!");

                    }
                }
            }
        }
    }
}