package treadstone.game.GameEngine;

import android.util.Log;

import java.util.ArrayList;

public class CollisionManager
{
    // Debug toggle
    private int DEBUG = 1;

    public CollisionManager()
    {
        Log.d("CollMgr/CTOR", "CollisionMgr created");
    }

    // void entityCollisions()
    // This functions checks if there are any collisions between entities managed by GameView
    public void entityCollisions(ArrayList<Entity> e)
    {
        Log.d("CollMgr/eCollide", "Checking entities for collisions...");
    }

    // void projectileCollisions()
    // This function checks if there are collisions between entities and projectiles that
    // are NOT owned by them
    public void projectileCollisions(ArrayList<Entity> e, ArrayList<Projectile> p)
    {
        Log.d("CollMgr/pCollide", "Checking entities & projectiles for collisions...");
    }
}