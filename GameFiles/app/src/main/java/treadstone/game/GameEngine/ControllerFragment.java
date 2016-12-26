package treadstone.game.GameEngine;

import android.app.Activity;
import android.util.Log;
import treadstone.game.R;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.view.LayoutInflater;
import android.support.v4.app.Fragment;

public class ControllerFragment extends Fragment implements View.OnClickListener
{
    private ImageButton button_a;
    private ImageButton button_b;
    private ImageButton button_c;
    private ImageButton button_s;

    public enum ProjectileType {BULLET, MISSILE, SHIELD}

    OnControllerPress button_listener;

    public ControllerFragment()
    {
        // empty
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.layout_controller, container, false);
    }

    @Override
    public void onViewCreated(View curr_view, Bundle savedInstanceState)
    {
        button_a = (ImageButton) curr_view.findViewById(R.id.button_a);
        button_b = (ImageButton) curr_view.findViewById(R.id.button_b);
        button_c = (ImageButton) curr_view.findViewById(R.id.button_c);
        button_s = (ImageButton) curr_view.findViewById(R.id.button_start);

        // Establish Listeners for ImageButtons
        button_a.setOnClickListener(this);
        button_b.setOnClickListener(this);
        button_c.setOnClickListener(this);
        button_s.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.button_a:
                Log.d("button_a", "A BUTTON CLICKED IN CTRL_FRAG");
                button_listener.onButtonPress(ProjectileType.BULLET);
                break;

            case R.id.button_b:
                Log.d("button_b", "B BUTTON CLICKED IN CTRL_FRAG");
                button_listener.onButtonPress(ProjectileType.MISSILE);
                break;

            case R.id.button_c:
                Log.d("button_c", "C BUTTON CLICKED IN CTRL_FRAG");
                button_listener.onButtonPress(ProjectileType.SHIELD);
                break;

            case R.id.button_start:
                Log.d("button_start", "START BUTTON CLICKED IN CTRL_FRAG");
                break;
        }

    }

    @Override
    public void onStart()
    {
        super.onStart();
    }

    @Override
    public void onSaveInstanceState(Bundle bundle)
    {
        super.onSaveInstanceState(bundle);
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);

        try
        {
            button_listener = (OnControllerPress) activity;
        }

        catch (ClassCastException e)
        {
            throw new ClassCastException(activity.toString() + " must implement OnControllerPress");
        }

    }

}