package treadstone.game.GameEngine;

import android.util.Log;
import android.view.MotionEvent;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector.OnDoubleTapListener;

public class TouchManager implements OnGestureListener, OnDoubleTapListener
{
    // Debug info
    private int         DEBUG = 1;
    private String      DEBUG_TAG = "TouchMgr";

    private Player      curr_player;
    private ViewPort    viewport;
    private double      displacementX, displacementY;

    TouchManager(Player p, ViewPort v)
    {
        curr_player = p;
        viewport = v;
        displacementX = 0.0f;
        displacementY = 0.0f;
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
    public boolean onScroll(MotionEvent e1, MotionEvent curr_motion, float dX, float dY)
    {
        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "onScroll: " + e1.toString() + curr_motion.toString());
        curr_player.processMovement(curr_motion.getX() + displacementX, curr_motion.getY() + displacementY, false);
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
        curr_player.processMovement(curr_motion.getX() + displacementX, curr_motion.getY() + displacementY, true);
        viewport.setViewPortCentre(curr_player.getPosition());
        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent curr_motion)
    {
        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "onSingleTapConfirmed: " + curr_motion.toString());
        curr_player.adjustAimDirection(curr_motion.getX() + displacementX, curr_motion.getY() + displacementY);
        return true;
    }

    public void updateTouchDisplacement(double dX, double dY)
    {
        displacementX = dX;
        displacementY = dY;
    }

}
