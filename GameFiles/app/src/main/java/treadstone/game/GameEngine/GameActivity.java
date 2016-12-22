package treadstone.game.GameEngine;

import treadstone.game.R;
import android.os.Bundle;
import android.view.Display;
import android.graphics.Point;
import android.support.v4.app.FragmentActivity;

public class GameActivity extends FragmentActivity
{

    private GameView curr_screen;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Find Display Size
        Display curr_display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        curr_display.getSize(size);

            if (findViewById(R.id.game_screen) != null)
            {
                if (savedInstanceState != null)
                {
                    return;
                }

                System.out.println("Screen Fragment attempt");

                // Create Game Screen
                ScreenFragment screen = new ScreenFragment();
                Bundle args = new Bundle();
                args.putInt("X_BOUND", size.x);
                args.putInt("Y_BOUND", size.y);
                screen.setArguments(args);
                getSupportFragmentManager().beginTransaction().add(R.id.game_screen, screen).commit();

                System.out.println("Screen Fragment success");

                // Create Controller Fragment
                ControllerFragment controller = new ControllerFragment();
                controller.setArguments(getIntent().getExtras());
                //getSupportFragmentManager().beginTransaction().add(R.id.game_screen, controller).commit();
            }


    }

    @Override
    protected void onPause()
    {
        super.onPause();
        //curr_screen.pause();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
       //curr_screen.resume();
    }

}