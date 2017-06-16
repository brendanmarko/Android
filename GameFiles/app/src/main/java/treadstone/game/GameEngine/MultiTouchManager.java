package treadstone.game.GameEngine;

import android.util.Log;
import android.view.MotionEvent;

public class MultiTouchManager
{
    // Debug info
    private int         DEBUG = 1;
    private String      DEBUG_TAG = "MultiTouchMgr";

    private Player      curr_player;
    private ViewPort    viewport;
    private float       displacementX, displacementY;

    MultiTouchManager(Player p, ViewPort v)
    {
        curr_player = p;
        viewport = v;
        displacementX = 0.0f;
        displacementY = 0.0f;
    }

    public boolean handleEvent(MotionEvent event)
    {
        Position pointerPos[] = new Position[2];
        int action = event.getActionMasked();

        switch(action)
        {
            case MotionEvent.ACTION_MOVE:
                int count = event.getPointerCount();
                for (int i = 0; i < count; i++)
                {
                    pointerPos[i] = new Position(event.getX(i) + displacementX, event.getY(i) + displacementY);
                    if (DEBUG == 1)
                        Log.d(DEBUG_TAG, "Co-ordinates: " + pointerPos[i].toString());
                }
        }

        return true;
        
    }

}
