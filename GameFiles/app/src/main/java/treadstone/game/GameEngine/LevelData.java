package treadstone.game.GameEngine;

import android.util.Log;

import java.util.ArrayList;

public class LevelData
{
    private ArrayList<String>           tiles;

    public Position getMapDimens()
    {
        return new Position(tiles.get(0).length(), tiles.size());
    }

    public LevelData()
    {
        tiles = new ArrayList<String>();
    }

    public ArrayList<String> getTiles()
    {
        return tiles;
    }

    public void setTiles(ArrayList<String> a)
    {
        tiles = a;
    }

    public String toString()
    {
        Log.d("size_of_tiles+++", "Size of getTiles() currently: " + getTiles().size());
        return "getTilesSize() = " + getTiles().size();
    }

    public void printTiles()
    {
        String temp = "";

        for (int i = 0; i < tiles.size(); i++)
        {
            for (int j = 0; j < tiles.get(i).length(); j++)
            {
                    temp += tiles.get(i).charAt(j);
            } // end : inner for

            Log.d("ROW_PRINT +++", temp);
            temp = "";
        }

    }

}