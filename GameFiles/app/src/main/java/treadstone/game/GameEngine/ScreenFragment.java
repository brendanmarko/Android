package treadstone.game.GameEngine;

import android.util.Log;
import android.view.View;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ViewGroup;
import android.view.LayoutInflater;

public class ScreenFragment extends Fragment
{
    private int             x_max, y_max;
    private GameView        curr_screen;

    public ScreenFragment()
    {
        // empty
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle)
    {
        x_max = getArguments().getInt("X_BOUND");
        y_max = getArguments().getInt("Y_BOUND");
        Log.d("MAX_BOUNDS_CHECK +++" , "BOUNDS CHECK: " + x_max + ", " + y_max);
        curr_screen = new GameView(this.getContext(), new Position(x_max, y_max));
        return curr_screen;
    }

    @Override
    public void onViewCreated(View curr_view, Bundle savedInstanceState)
    {
        Log.d("ScreenFragment", "ScreenFragment created!");
    }

    @Override
    public void onResume()
    {
        super.onResume();
        curr_screen.resume();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        curr_screen.pause();
    }

    @Override
    public void onStart()
    {
        super.onStart();
    }

    public void handleButtonPress(ControllerFragment.ProjectileType p)
    {
        //curr_screen.addProjectileToPlayer(p);
    }

}