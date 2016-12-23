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
    private int xMax, yMax;

    public ScreenFragment()
    {
        // empty
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle)
    {
        xMax = getArguments().getInt("X_BOUND");
        yMax = getArguments().getInt("Y_BOUND");
        return new GameView(this.getContext(), new Point(xMax, yMax));
    }

    @Override
    public void onViewCreated(View curr_view, Bundle savedInstanceState)
    {
        Log.d("ScreenFragment", "ScreenFragment created!");
    }

}
