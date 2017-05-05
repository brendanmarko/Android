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
    private Position                pixels_per_metre;
    private boolean                 playing;
    private int                     DEBUG = 0;

    public LevelManager(Context c, String level_name, Position ppm, Position player_start)
    {
        // Gather parameters
        level = level_name;
        pixels_per_metre = ppm;

        // Initialize level
        switch (level_name)
        {
            case "TestLevel":
                level_data = new TestLevel();
        }

        // Show LevelData contents and Dimensions
        level_data.printTiles();
        level_max = level_data.getMapDimens();

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

        // Reads in the Level layout file
        for (int y = 0; y < level_data.getTiles().size(); y++)
        {
            for (int x = 0; x < level_data.getTiles().get(y).length(); x++)
            {
                objectCreate(c, new Position(x, y), level_data.getTiles().get(y).charAt(x));
            }
        }

    }

    // checkBitmap()
    // This functions checks if the specified Bitmap has already been prepared for use and prepares
    // the specified Bitmap if it requires it (builds up Bitmap array)
    public void checkBitmap(Context c, Entity e, char d)
    {
        if (bitmaps[getIndex(d)] != null)
            return;

        else
            bitmaps[getIndex(d)] = e.createBitmap(c, e.getImageName());
    }

    // objectCreate(char)
    // This function takes a char as input and creates an object based upon the type letter passed
    public void objectCreate(Context c, Position p, char d)
    {
        if (d == '.')
            return;

        else
        {
            curr_index++;

            Entity temp;

            switch (d)
            {
                case 'p':

                    if (DEBUG == 1)
                        Log.d("player_test_object+++", "Creating player...");

                    temp = new Player(c, p, level_max, pixels_per_metre, d);
                    if (DEBUG == 1)
                        Log.d("player_test_object+++", "Player created!");

                    player = (Player) temp;

                    if (DEBUG == 1)
                        Log.d("player_test_object+++", player.toString());

                    player_index = curr_index;
                    game_objects.add(player);
                    checkBitmap(c, temp, d);
                    break;

                case 'e':
                    temp = new TestEnemy(c, p, level_max, pixels_per_metre, d);
                    game_objects.add(temp);
                    checkBitmap(c, temp, d);
                    break;

                case 'd':
                    temp = new Debris(c, p, level_max, pixels_per_metre, d);
                    game_objects.add(temp);
                    checkBitmap(c, temp, d);
                    break;
            }

        }

    }

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

    public Player getPlayer()
    {
        return player;
    }

    public Position getMapDimens()
    {
        return level_max;
    }

}