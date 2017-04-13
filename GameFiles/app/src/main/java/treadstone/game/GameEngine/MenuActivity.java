package treadstone.game.GameEngine;

import android.content.Intent;
import android.util.Log;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.support.v4.app.FragmentActivity;

public class MenuActivity extends FragmentActivity
{
    private Button      button_start;
    private Button      button_settings;
    private Button      button_help;

    @Override
    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.activity_menu);

        button_start = (Button) findViewById(R.id.start_button);
        button_settings = (Button) findViewById(R.id.settings_button);
        button_help = (Button) findViewById(R.id.help_button);

        button_start.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Log.d("button_start_click", "Start Button clicked!");
                Intent i = new Intent(getApplicationContext(), GameActivity.class);
                startActivity(i);
                finish();
            }
        });

        button_settings.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Log.d("button_settings_click", "Settings Button clicked!");
            }
        });

        button_help.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Log.d("button_help_click", "Start Help clicked!");
            }
        });

    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.d("menu_destroy", "Menu Activity destroyed!");
    }

}