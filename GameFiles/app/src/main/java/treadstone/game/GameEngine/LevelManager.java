package treadstone.game.GameEngine;

import android.content.Context;
import android.graphics.Bitmap;

import android.util.Log;
import java.util.ArrayList;

public class LevelManager
{
    private int                     curr_index, player_index;
    private Player                  player;
    private String                  level;
    private Position                level_max;
    private LevelData               level_data;
    private ArrayList<Entity>       game_objects;
    private Bitmap[]                bitmaps;
    private Position                pixelSize;
    private boolean                 playing;
    private float                   map_h, map_w;

    public LevelManager(Context c, String level_name, Position m, Position ppm, Position player_start)
    {
        // Gather parameters
        level = level_name;
        level_max = m;
        pixelSize = ppm;

        // Initialize level
        switch (level_name)
        {
            case "TestLevel":
                level_data = new TestLevel();
        }

        // Initialize Bitmap/Game object storage
        game_objects = new ArrayList<Entity>();
        bitmaps = new Bitmap[20];

        // Load the map
        loadMapData(c, ppm, player_start);
        playing = true;
    }

    public Bitmap getBitmap(char c)
    {
        int index;
        switch (c)
        {
            case 'p':
                index = 1;
                break;

            case 'e':
                index = 2;
                break;

            case 'd':
                index = 3;
                break;

            default:
                index = 0;
        }

        return bitmaps[index];
    }

    public int getIndex(char c)
    {
        int index;
        switch (c)
        {
            case 'p':
                index = 1;
                break;

            case 'e':
                index = 2;
                break;

            case 'd':
                index = 3;
                break;

            default:
                index = 0;
        }

        return index;
    }

    private void loadMapData(Context c, Position ppm, Position player_start)
    {
        curr_index = -1;
        map_h = level_data.getTiles().size();
        map_w = level_data.getTiles().get(0).length();

        // Reads in the Level layout file
        for (int i = 0; i < map_h; i++)
        {

            Log.d("enter_row", "Entering ROW #" + i);

            for (int j = 0; j < level_data.getTiles().get(i).length(); j++)
            {
                Log.d("enter_col", "Entering COL #" + j);
                objectCreate(c, i, j, level_data.getTiles().get(i).charAt(j));
            }
        }

        Log.d("success_loadmapdata", "Success +++ loaded map data!");
    }

    // checkBitmap()
    // This functions checks if the specified Bitmap has already been prepared for use and prepares
    // the specified Bitmap if it requires it (builds up Bitmap array)
    public void checkBitmap(Context c, Entity e, char d)
    {
        if (bitmaps[getIndex(d)] != null)
        {
            return;
        }

        else
        {
            bitmaps[getIndex(d)] = e.createBitmap(c, e.getImageName());
        }

    }

    // objectCreate(char)
    // This function takes a char as input and creates an object based upon the type letter passed
    public void objectCreate(Context c, int i, int j, char d)
    {
        if (d == '.')
        {
            return;
        }

        else
        {
            Log.d("curr_index_pre++", "curr_index_pre++ = " + curr_index);
            curr_index++;
            Log.d("curr_index_post++", "curr_index_post++ = " + curr_index);

            Entity temp;

            switch (d)
            {
                case 'p':
                    Log.d("player_test_object+++", "Creating player...");
                    temp = new Player(c, new Position(i, j), level_max, d);
                    Log.d("player_test_object+++", "Player created!");
                    player = (Player) temp;
                    Log.d("player_test_object+++", player.toString());
                    player_index = curr_index;
                    game_objects.add(player);
                    checkBitmap(c, temp, d);
                    break;

                case 'e':
                    temp = new TestEnemy(c, new Position(i, j), level_max, d);
                    game_objects.add(temp);
                    checkBitmap(c, temp, d);
                    break;

                case 'd':
                    temp = new Debris(c, new Position(i, j), level_max, d);
                    game_objects.add(temp);
                    checkBitmap(c, temp, d);
                    break;
            }

        }

    }

    /*
    // scaleLocation(Position)
    // Given a Position this function will scale the Layout file co-ordinates into Game locations
    public Position scaleLocation(Position p)
    {
        Log.d("scaleLocation return", p.getX() * pixelSize.getX() + ", " + p.getY() * pixelSize.getY());
        return new Position(p.getX() * pixelSize.getX(), p.getY() * pixelSize.getY());
    }
    */

    public String getLevelName()
    {
        return level;
    }

    public Bitmap[] getBitmaps()
    {
        return bitmaps;
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

    public ArrayList<Entity> getGameObjects()
    {
        return game_objects;
    }

    public Entity objectAt(int i)
    {
        return game_objects.get(i);
    }

    public Bitmap bitmapAt(int i)
    {
        return bitmaps[i];
    }

}