package treadstone.game.GameEngine;

import android.graphics.Rect;
import android.util.Log;

import java.util.ArrayList;

public class CollisionChecker
{

    private Player                      curr_player;
    private ArrayList<TestEnemy>        target_list;

    CollisionChecker(Player player, ArrayList<TestEnemy> enemy_list)
    {
        curr_player = player;
        target_list = enemy_list;
    }

    public boolean shipCollisions()
    {
        // Checks for collisions between Player and Enemies
        for (TestEnemy curr_enemy : target_list)
        {
            if (Rect.intersects(curr_player.getHitRect().getHitbox(), curr_enemy.getHitRect().getHitbox()))
            {
                target_list.remove(curr_enemy);
                return true;
            }
        }

        return false;
    }

    public boolean projectileCollisions()
    {
        // Checks Projectiles of Player for collisions
        for (TestEnemy e : target_list)
        {
            for (Projectile p : curr_player.getProjectiles())
            {
                if (Rect.intersects(p.getHitRect().getHitbox(), e.getHitRect().getHitbox()))
                {
                    curr_player.getProjectiles().remove(p);
                    target_list.remove(e);
                    return true;
                }
            }
        }

        return false;
    }

    /*
    public boolean projectileBoundary()
    {
        for (Projectile p : curr_player.getProjectiles())
        {
            if (p.outOfBoundsCheck(p.getX(), p.getY()))
            {
                curr_player.getProjectiles().remove(p);
                Log.d("OB_proj", "Removing OB projectile from player.");
                return true;
            }
        }

        return false;

    }
    */

}
