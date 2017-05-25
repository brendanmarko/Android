package treadstone.game.GameEngine;

import android.util.Log;
import android.view.MotionEvent;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector.OnDoubleTapListener;

public class TouchManager implements OnGestureListener, OnDoubleTapListener
{
    // Debug toggle
    private int         DEBUG = 1;
    private String      DEBUG_TAG = "TouchMgr";
    private Player      curr_player;
    private ViewPort    viewport;

    TouchManager(Player p, ViewPort v)
    {
        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "TouchMgr created!");
        curr_player = p;
        viewport = v;
    }

    @Override
    public boolean onDoubleTap(MotionEvent event)
    {
        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "onDoubleTap: " + event.toString());
        curr_player.stopMovement();
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent event)
    {
        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "onDoubleTapEvent: " + event.toString());
        curr_player.stopMovement();
        return true;
    }

    @Override
    public void onLongPress(MotionEvent event)
    {
        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "onLongPress: " + event.toString());
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent curr_motion, float displacementX, float displacementY)
    {
        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "onScroll: " + e1.toString() + curr_motion.toString());
        curr_player.processMovement(curr_motion.getX() + displacementX, curr_motion.getY() + displacementY);
        viewport.setViewPortCentre(curr_player.getPosition());
        return true;
    }

    @Override
    public void onShowPress(MotionEvent event)
    {
        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "onShowPress: " + event.toString());
    }

    @Override
    public boolean onSingleTapUp(MotionEvent event)
    {
        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "onSingleTapUp: " + event.toString());
        return true;
    }

    @Override
    public boolean onDown(MotionEvent event)
    {
        if (DEBUG == 1)
            Log.d(DEBUG_TAG,"onDown: " + event.toString());
        return true;
    }

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent curr_motion, float velocityX, float velocityY)
    {
        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "onFling: " + event1.toString() + curr_motion.toString());
        curr_player.processMovement(curr_motion.getX() + (velocityX * viewport.getPixelsPerMetre().getX()), curr_motion.getY() + (velocityY * viewport.getPixelsPerMetre().getY()));
        viewport.setViewPortCentre(curr_player.getPosition());
        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent curr_motion)
    {
        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "onSingleTapConfirmed: " + curr_motion.toString());
        curr_player.adjustDirection(curr_motion.getX(), curr_motion.getY());
        return true;
    }

}
