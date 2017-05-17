package treadstone.game.GameEngine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;

public class EntityManager
{
    // Debug toggle
    private int                 DEBUG = 0;

    private Bitmap[]            bitmaps;
    private ViewPort            viewport;
    private Position            start_point, end_point;
    private ArrayList<Entity>   entity_list;

    EntityManager(Context c, ViewPort v, Position start, Position end, ArrayList<Entity> list)
    {
        viewport = v;
        bitmaps = new Bitmap[20];
        start_point = start;
        end_point = end;
        entity_list = new ArrayList<>();
        addBuffer(c, list);
    }

    public ArrayList<Entity> getEntities()
    {
        return entity_list;
    }

    public void update(float x, float y)
    {
        for (Entity e : entity_list)
        {
            if (e.isActive())
            {
                if (DEBUG == 1)
                    Log.d("GameView/UpEnt", "Examining: " + e.toString());

                if (viewport.clipObject(e.getPosition()))
                {
                    Log.d("GameView/UpdateE", "ENTITY SHOULD BE INVISIBLE");
                    e.setInvisible();
                }

                else
                {
                    Log.d("GameView/UpdateE", "ENTITY SHOULD BE VISIBLE");
                    e.setVisible();

                    if (e.movementType().equals("dynamic"))
                    {
                        MovableEntity m = (MovableEntity) e;
                        // m.setDirection(dir_finder.findDirection(e.getObjInfo().getType()));
                        m.update();

                        if (m.getObjInfo().getType() == 'p')
                            viewport.setViewPortCentre(m.getPosition());
                    }
                }

                if (DEBUG == 1)
                    Log.d("GameView/UpEnt", "Post-Examining: " + e.toString());

                e.updateHitbox(x, y);

            }
        }
    }

    public void addBuffer(Context c, ArrayList<Entity> buffer)
    {
        if (DEBUG == 1)
            Log.d("PrjMgr/addBuffer" , "Adding buffer to projectiles!");

        for (Entity e : buffer)
            checkBitmap(c, e, e.getObjInfo().getType());

        entity_list.addAll(buffer);

        if (DEBUG == 1)
            Log.d("PrjMgr/addBuffer" , "New Size of projectiles: " + entity_list.size());
    }

    public void checkBitmap(Context c, Entity e, char d)
    {
        if (bitmaps[getIndex(d)] != null)
            return;

        else
            bitmaps[getIndex(d)] = e.createBitmap(c, e.getObjInfo().getImageName());
    }

    public int getIndex(char c)
    {
        int index;
        switch (c)
        {
            case 'b':
                index = 1;
                break;

            case 'm':
                index = 2;
                break;

            case 's':
                index = 3;
                break;

            default:
                index = 0;
        }

        return index;
    }

    public Bitmap getBitmap(char c)
    {
        int index;
        switch (c)
        {
            case 'b':
                index = 1;
                break;

            case 'm':
                index = 2;
                break;

            case 's':
                index = 3;
                break;

            default:
                index = 0;
        }

        return bitmaps[index];
    }

    public void draw(Canvas canvas, Paint paint)
    {
        Rect r = new Rect();

        if  (DEBUG == 1)
            Log.d("GameView/DrawPrj", "Drawing Entities with size: " + entity_list.size());

        for (int layer = 0; layer < 3; layer++)
        {
            for (Entity e : entity_list)
            {
                if (e.isVisible() && e.getLayer() == layer)
                {
                    r.set(viewport.worldToScreen(e.getPosition(), e.getObjInfo().getDimensions()));
                    canvas.drawBitmap(getBitmap(e.getObjInfo().getType()), r.left, r.top, paint);
                }
            }
        }
    }

}
