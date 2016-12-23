package treadstone.game.GameEngine;

import android.util.Log;
import android.view.View;
import android.os.Bundle;
import android.graphics.Point;
import android.support.v4.app.Fragment;
import android.view.ViewGroup;
import android.view.LayoutInflater;

public class ScreenFragment extends Fragment
{
    private int             xMax, yMax;
    private GameView        curr_screen;

    public ScreenFragment()
    {
        // empty
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle)
    {
        xMax = getArguments().getInt("X_BOUND");
        yMax = getArguments().getInt("Y_BOUND");
        curr_screen = new GameView(this.getContext(), new Point(xMax, yMax));
        return curr_screen;
    }

    @Override
    public void onViewCreated(View curr_view, Bundle savedInstanceState)
    {
        Log.d("ScreenFragment", "ScreenFragment created!");
    }

    public void run()
    {
        curr_screen.run();
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

}
