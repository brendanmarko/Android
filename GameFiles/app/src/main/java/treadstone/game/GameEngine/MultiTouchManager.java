package treadstone.game.GameEngine;

import android.util.Log;
import android.view.View;
import android.view.MotionEvent;
import android.view.View.OnTouchListener;

public class MultiTouchManager implements OnTouchListener
{
    // Debug info
    private int         DEBUG = 1;
    private String      DEBUG_TAG = "MultiTouchMgr";

    private Player      curr_player;
    private ViewPort    viewport;
    private float       displacementX, displacementY;

    MultiTouchManager(Player p, ViewPort v)
    {
        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "MultiTouchMgr created!");
        curr_player = p;
        viewport = v;
        displacementX = 0.0f;
        displacementY = 0.0f;
    }

    public boolean handleEvent(MotionEvent event)
    {
        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "MTM onTouchEvent triggered with: " + event.getAction());

        int action = event.getAction();

        if (action == MotionEvent.ACTION_DOWN)
        {
            if (DEBUG == 1)
                Log.d(DEBUG_TAG, "ACTION_DOWN triggered.");
        }

        else if (action == MotionEvent.ACTION_MOVE)
        {
            if (DEBUG == 1)
                Log.d(DEBUG_TAG, "ACTION_MOVE triggered.");
        }

        if (action == MotionEvent.ACTION_UP)
        {
            if (DEBUG == 1)
                Log.d(DEBUG_TAG, "ACTION_UP triggered.");
        }

        if (action == MotionEvent.ACTION_CANCEL)
        {
            if (DEBUG == 1)
                Log.d(DEBUG_TAG, "ACTION_CANCEL triggered.");
        }

        return true;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "MTM onTouchEvent triggered");

        // get pointer index from the event object
        int pointerIndex = event.getActionIndex();

        // get pointer ID
        int pointerId = event.getPointerId(pointerIndex);

        // get masked (not specific to a pointer) action
        int maskedAction = event.getActionMasked();

        switch (maskedAction) {

            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
            {
                break;
            }

            case MotionEvent.ACTION_MOVE:
            {
                break;
            }

            case MotionEvent.ACTION_UP:

            case MotionEvent.ACTION_POINTER_UP:

            case MotionEvent.ACTION_CANCEL:
            {
                break;
            }
        }

        return true;
    }



}
