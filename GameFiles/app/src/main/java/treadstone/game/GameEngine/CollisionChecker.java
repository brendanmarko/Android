package treadstone.game.GameEngine;

import android.graphics.Rect;

import java.util.ArrayList;
import java.util.ListIterator;

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
        TestEnemy curr_enemy;

        // Checks for collisions between Player and Enemies
        for (ListIterator<TestEnemy> iterator =  target_list.listIterator(); iterator.hasNext();)
        {
            curr_enemy  = iterator.next();
            if (Rect.intersects(curr_player.getHitBox().getHitBox(), curr_enemy.getHitBox().getHitBox()))
            {
                iterator.remove();
                return true;
            }
        }

        return false;
    }

    public boolean projectileCollisions()
    {
        TestEnemy   e;
        Projectile  p;
        // Checks Projectiles of Player for collisions
        for (ListIterator<TestEnemy> e_iterator = target_list.listIterator(); e_iterator.hasNext();)
        {

            e = e_iterator.next();

            for (ListIterator<Projectile> p_iterator = curr_player.getProjectiles().listIterator(); p_iterator.hasNext();)
            {
                p = p_iterator.next();

                if (Rect.intersects(p.getHitBox().getHitBox(), e.getHitBox().getHitBox()))
                {
                    p_iterator.remove();
                    e_iterator.remove();
                    return true;
                }
            }
        }

        return false;
    }

    public boolean projectileBoundary()
    {
        Projectile p;
        for (ListIterator<Projectile> iterator = curr_player.getProjectiles().listIterator(); iterator.hasNext();)
        {
            p = iterator.next();
            if (p.getX() > p.getXMax())
            {
                iterator.remove();
                return true;
            }

        }

        return false;

    }

}