package treadstone.game.GameEngine;

import android.graphics.Rect;

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

    public boolean hitCheck()
    {

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

}
