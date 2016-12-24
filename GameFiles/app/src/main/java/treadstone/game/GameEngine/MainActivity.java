package treadstone.game.GameEngine;

import treadstone.game.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

public class MainActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        run();
    }

    public void run()
    {
        Intent i = new Intent(this, MenuActivity.class);
        startActivity(i);
        finish();
    }

}
