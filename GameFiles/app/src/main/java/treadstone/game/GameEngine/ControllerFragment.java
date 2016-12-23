package treadstone.game.GameEngine;

import treadstone.game.R;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.view.LayoutInflater;

public class ControllerFragment extends Fragment
{
    private ImageButton button_a, button_b, button_c, button_s;

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
        button_a.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Log.d("button_a_click", "button_a clicked within ControllerFragment");
            }
        });

        button_b.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Log.d("button_b_click", "button_b clicked within ControllerFragment");
            }
        });

        button_c.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Log.d("button_c_click", "button_c clicked within ControllerFragment");
            }
        });

        button_s.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Log.d("button_s_click", "button_s clicked within ControllerFragment");
            }
        });
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

}