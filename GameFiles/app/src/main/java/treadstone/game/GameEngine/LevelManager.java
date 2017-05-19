package treadstone.game.GameEngine;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;

public class LevelManager extends Manager
{
    // Debug toggle
    private int                     DEBUG = 1;

    private int                     curr_index, player_index;
    private Player                  player;
    private String                  level;
    private Position                level_max;
    private LevelData               level_data;

    private Position                pixels_per_metre, end_point, start_point;
    private boolean                 playing;

    public LevelManager(Context c, String level_name, Position ppm)
    {
        super();
        level = level_name;
        pixels_per_metre = ppm;

        switch (level_name)
        {
            case "TestLevel":
                level_data = new TestLevel();
        }

        // Show LevelData contents and Dimensions
        level_data.printTiles();
        level_max = level_data.getMapDimens();

        // Load the map
        loadMapData(c);
        playing = true;
    }

    private void loadMapData(Context c)
    {
        curr_index = -1;

        // Reads in the Level layout file
        for (int y = 0; y < level_data.getTiles().size(); y++)
        {
            for (int x = 0; x < level_data.getTiles().get(y).length(); x++)
                objectCreate(c, new Position(x * pixels_per_metre.getX(), y * pixels_per_metre.getY()), level_data.getTiles().get(y).charAt(x));
        }

    }

    // objectCreate(char)
    // This function takes a char as input and creates an object based upon the type letter passed
    public void objectCreate(Context c, Position p, char d)
    {
        if (d == '.')
            return;

        else if (d == '@')
        {
            if (DEBUG == 1)
                Log.d("LevelMgr/objCreate", "Setting starting point ref");

            start_point = new Position(p.getX() * pixels_per_metre.getX(), p.getY() * pixels_per_metre.getY());
        }

        else if (d == '#')
        {
            if (DEBUG == 1)
                Log.d("LevelMgr/objCreate", "Setting endpoint ref");

            end_point = new Position(p.getX() * pixels_per_metre.getX(), p.getY() * pixels_per_metre.getY());
        }

        else
        {
            curr_index++;
            Entity temp;

            switch (d)
            {
                case 'p':
                    temp = new Player(p, level_max, pixels_per_metre, d);
                    player = (Player) temp;
                    player_index = curr_index;
                    getList().add(player);
                    checkBitmap(c, temp, d);

                    if (DEBUG == 1)
                        Log.d("player_test_object+++", "Player created: " + player.toString());

                    break;

                case 'e':
                    temp = new TestEnemy(p, level_max, pixels_per_metre, d);
                    getList().add(temp);
                    checkBitmap(c, temp, d);
                    break;

                case 'd':
                    temp = new Debris(p, level_max, pixels_per_metre, d);
                    getList().add(temp);
                    checkBitmap(c, temp, d);
                    break;
            }
        }
    }

    public String getLevelName()
    {
        return level;
    }

    public LevelData getLevelData()
    {
        return level_data;
    }

    public boolean isPlaying()
    {
        return playing;
    }

    public int getPlayerIndex()
    {
        return player_index;
    }


    public Player getPlayer()
    {
        return player;
    }

    public Position getMapDimens()
    {
        return level_max;
    }

    public Position getEndpoint()
    {
        return end_point;
    }

    public Position getStartPoint()
    {
        return start_point;
    }

}