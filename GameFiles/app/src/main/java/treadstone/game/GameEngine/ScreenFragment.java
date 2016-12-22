package treadstone.game.GameEngine;

import treadstone.game.R;

import android.util.Log;
import android.view.View;
import android.os.Bundle;
import android.graphics.Point;
import android.support.v4.app.Fragment;
import android.view.ViewGroup;
import android.view.LayoutInflater;

public class ScreenFragment extends Fragment
{
    private static int          xMax, yMax;

    public ScreenFragment()
    {
        // empty
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle)
    {
        xMax = bundle.getInt("X_BOUND");
        yMax = bundle.getInt("Y_BOUND");
        return new GameView(this.getContext(), new Point(xMax, yMax));
    }

    @Override
    public void onViewCreated(View curr_view, Bundle savedInstanceState)
    {
        Log.d("ScreenFragment", "ScreenFragment created!");
    }



}
