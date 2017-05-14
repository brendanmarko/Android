package treadstone.game.GameEngine;

import android.util.Log;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class GameActivity extends FragmentActivity implements OnControllerPress
{
    private ScreenFragment          screen;
    private ControllerFragment      controller;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Find Display Size
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        // Fragment Management
        FragmentManager manager = getSupportFragmentManager();

        // Create Bundle
        Bundle bundle = new Bundle();
        bundle.putInt("X_BOUND", metrics.widthPixels);
        bundle.putInt("Y_BOUND", metrics.heightPixels);

        // Add Fragment [Controller]
        FragmentTransaction transaction = manager.beginTransaction();
        controller = new ControllerFragment();
        transaction.replace(R.id.controller_screen, controller, "controller_screen");

        // Add Fragment [GameView]
        screen = new ScreenFragment();
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

    public void onButtonPress(ControllerFragment.ProjectileType s)
    {
        Log.d("int_test", "Testing OnControllerPress interface with " + s);
        screen.handleButtonPress(s);
    }

}