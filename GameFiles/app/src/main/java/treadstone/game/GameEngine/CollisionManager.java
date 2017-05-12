package treadstone.game.GameEngine;

import android.util.Log;

import java.util.ArrayList;

public class CollisionManager
{
    // Debug toggle
    private int DEBUG = 1;

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
            for (int j = 1; j < e.size(); j++)
            {
                if (DEBUG == 1)
                    Log.d("CollMgr/entityColl", e.get(i).toString() + ", " + e.get(j).toString());

                if (e.get(i).getHitbox().intersect(e.get(j).getHitbox()))
                {
                    if (DEBUG == 1)
                        Log.d("CollMgr/entityColl", "Collision between entities found!");
                }
            }
        }
    }

    // void projectileCollisions()
    // This function checks if there are collisions between entities and projectiles that
    // are NOT owned by them
    public void projectileCollisions(ArrayList<Entity> e, ArrayList<Projectile> p)
    {
        if (DEBUG == 1)
            Log.d("CollMgr/pCollide", "Checking entities & projectiles for collisions...");
    }
}