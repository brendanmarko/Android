package treadstone.game.GameEngine;

import android.os.Bundle;
import android.app.Activity;

public class GameActivity extends Activity
{

    private GameView game_view;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        game_view = new GameView(this);
        setContentView(game_view);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        game_view.pause();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        game_view.resume();
    }

}