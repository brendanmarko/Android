package treadstone.game.GameEngine;

import android.graphics.Rect;

import java.util.ArrayList;

public class CollisionChecker
{

    private Rect                        curr_player;
    private ArrayList<TestEnemy>        target_list;

    CollisionChecker(Rect player, ArrayList<TestEnemy> enemy_list)
    {
        curr_player = player;
        target_list = enemy_list;
    }

    private TestEnemy collisionFound()
    {
        return null;
    }

}
