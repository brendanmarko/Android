package treadstone.game.GameEngine;

import android.os.Bundle;
import android.app.Activity;
import android.view.Display;
import android.graphics.Point;

public class GameActivity extends Activity
{

    private GameView game_view;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Display curr_display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        curr_display.getSize(size);

        game_view = new GameView(this, size);
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