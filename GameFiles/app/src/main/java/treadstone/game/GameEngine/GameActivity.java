package treadstone.game.GameEngine;

import treadstone.game.R;

import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.Display;
import android.graphics.Point;
import android.support.v4.app.FragmentActivity;

public class GameActivity extends FragmentActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Find Display Size
        Display curr_display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        curr_display.getSize(size);

        // Fragment Management
        FragmentManager manager = getSupportFragmentManager();

        // Create Bundle
        Bundle bundle = new Bundle();
        bundle.putInt("X_BOUND", size.x);
        bundle.putInt("Y_BOUND", size.y);

        // Add Fragment [Controller]
        FragmentTransaction transaction = manager.beginTransaction();
        ControllerFragment controller = new ControllerFragment();
        transaction.replace(R.id.controller_screen, controller, "controller_screen");

        // Add Fragment [GameView]
        ScreenFragment screen = new ScreenFragment();
        screen.setArguments(bundle);
        transaction.replace(R.id.game_screen, screen, "game_screen");
        transaction.commit();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

}