package treadstone.game.GameEngine;

import java.util.ArrayList;

public class LevelData
{
    private ArrayList<String>           tiles;

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

}
