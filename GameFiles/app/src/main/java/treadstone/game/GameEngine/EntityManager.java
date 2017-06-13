package treadstone.game.GameEngine;

import android.util.Log;
import java.util.Iterator;
import java.util.ArrayList;
import android.graphics.Rect;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.content.Context;

public class EntityManager extends Manager
{
    // Debug toggle
    private int                 DEBUG = 0;

    private ViewPort            viewport;
    private Position            start_point, end_point;

    EntityManager(Context c, ViewPort v, Position start, Position end, ArrayList<Entity> list)
    {
        super();
        viewport = v;
        start_point = start;
        end_point = end;
        addBuffer(c, list);
        initDirections();
    }

    public void update(float x, float y)
    {
        for (Iterator<Entity> iterator = getList().iterator(); iterator.hasNext();)
        {
            Entity e = iterator.next();
            if (e.isActive())
            {
                if (DEBUG == 1)
                    Log.d("GameView/UpEnt", "Examining: " + e.toString());

                if (viewport.clipObject(e.getPosition()))
                {
                    if (DEBUG == 1)
                        Log.d("GameView/UpdateE", "ENTITY SHOULD BE INVISIBLE");
                    e.setInvisible();
                }

                else
                {
                    if (DEBUG == 1)
                        Log.d("GameView/UpdateE", "ENTITY SHOULD BE VISIBLE");
                    e.setVisible();

                    if (e.movementType().equals("dynamic"))
                    {
                        MovableEntity m = (MovableEntity) e;
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

        getList().addAll(buffer);

        if (DEBUG == 1)
            Log.d("PrjMgr/addBuffer" , "New Size of projectiles: " + getList().size());
    }

    public void draw(Canvas canvas, Paint paint)
    {
        Rect r = new Rect();

        if  (DEBUG == 1)
            Log.d("GameView/DrawPrj", "Drawing Entities with size: " + getList().size());

        for (int layer = 0; layer < 3; layer++)
        {
            for (Iterator<Entity> iterator = getList().iterator(); iterator.hasNext();)
            {
                Entity e = iterator.next();
                if (e.isVisible() && e.getLayer() == layer)
                {
                    r.set(viewport.worldToScreen(e.getPosition(), e.getObjInfo().getDimensions()));
                    canvas.drawBitmap(getBitmap(e.getObjInfo().getType()), r.left, r.top, paint);
                }
            }
        }
    }

    // initDirections()
    // This function initializes the directions of all the entities at run-time
    public void initDirections()
    {
        if  (DEBUG == 1)
            Log.d("GameView/initDir", "Initializing directions for entities");

        for (Iterator<Entity> iterator = getList().iterator(); iterator.hasNext();)
        {
            Entity e = iterator.next();
            if (e.movementType().equals("dynamic"))
            {
                MovableEntity m = (MovableEntity) e;

                if (m.getObjInfo().getType() == 'p')
                {
                    ArmedEntity a = (ArmedEntity) m;
                    a.setDirection("E");
                    a.setAimDirection(a.getDirection());
                    a.updateAimBounds(a.getDirection());

                    if (DEBUG == 1)
                        Log.d("EntityMgr/initDir", "Initiated Player direction to: " + m.getDirection());
                }

                else
                {
                    m.setDirection("W");
                    if (DEBUG == 1)
                        Log.d("EntityMgr/initDir", "Initiated Entity direction to: " + m.getDirection());
                }
            }
        }
    }

}