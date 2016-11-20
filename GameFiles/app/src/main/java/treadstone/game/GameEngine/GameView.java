package treadstone.game.GameEngine;

import java.util.ArrayList;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

public class GameView extends SurfaceView implements Runnable
{

    volatile boolean        view_active;
    Thread                  game_thread = null;
    Player                  curr_player;
    TestEnemy               test_enemy1;
    private int             max_x;
    private int             max_y;

    private Paint           paint;
    private Canvas          canvas;
    private SurfaceHolder   curr_holder;

    ArrayList<BackgroundEffect> background_visuals = new ArrayList<>();

    BackgroundEffect b1, b2, b3, b4;

    public GameView(Context curr_context, Point dimensions)
    {
        super(curr_context);
        curr_holder = getHolder();
        paint = new Paint();
        max_x = dimensions.x;
        max_y = dimensions.y;

        // Player added to Game
        curr_player = new Player(curr_context, "Mini-Meep", 50, 50);
        curr_player.setMaxBounds(dimensions.x - curr_player.getImageHeight(), dimensions.y);

        // Test Enemy added to Map
        test_enemy1 = new TestEnemy(curr_context, "bob_evil", 2000, 50);
        test_enemy1.setMaxBounds(dimensions.x - test_enemy1.getImageHeight(), dimensions.y);

        // Background Effects
        b1 = new BackgroundEffect(curr_context, "star_yellow", 0, 0, 4);
        b2 = new BackgroundEffect(curr_context, "star_small", 0, 0, 10);
        b3 = new BackgroundEffect(curr_context, "star_small", 0, 0, 8);
        b4 = new BackgroundEffect(curr_context, "star_small", 0, 0, 6);

        background_visuals.add(b1);
        background_visuals.add(b2);
        background_visuals.add(b3);
        background_visuals.add(b4);

        setBackgroundLimits(background_visuals);

    }

    @Override
    public void run()
    {

        while (view_active)
        {
            update();
            draw();
            control();
        }

    }

    public void update()
    {
        curr_player.update();
        test_enemy1.update();
        updateListContents(background_visuals);
    }

    public void draw()
    {

        if (curr_holder.getSurface().isValid())
        {

            // Lock Canvas
            canvas = curr_holder.lockCanvas();
            canvas.drawColor(Color.argb(255, 0, 0, 0));

            // Draw Entities
            drawTarget(curr_player);
            drawTarget(test_enemy1);
            drawListContents(background_visuals);

            // Unlock and draw
            curr_holder.unlockCanvasAndPost(canvas);

        }

    }

    public void control()
    {

        try
        {
            game_thread.sleep(20);
        }

        catch (InterruptedException e)
        {
            System.out.println("Interrupt caught within control() [View]");
        }

    }

    public void pause()
    {

        view_active = false;

        try
        {
            game_thread.join();
        }

        catch (InterruptedException e)
        {
            System.out.println("Exception within pause() [View]");
        }

    }

    public void resume()
    {
        view_active = true;
        game_thread = new Thread(this);
        game_thread.start();
    }

    public boolean onTouchEvent(MotionEvent curr_motion)
    {

        switch (curr_motion.getAction() & MotionEvent.ACTION_MASK)
        {

            case MotionEvent.ACTION_UP:
                System.out.println("Finger lifted");
                curr_player.changePace();
                break;

            case MotionEvent.ACTION_DOWN:
                System.out.println("Finger down!");
                curr_player.changePace();
                break;

        }

        return true;

    }

    public void drawTarget(MovableImage curr_target)
    {
        canvas.drawBitmap(curr_target.getImage(), curr_target.getX(), curr_target.getY(), paint);
    }

    public void setBackgroundLimits(ArrayList<BackgroundEffect> curr_list)
    {

        for (BackgroundEffect curr_item : curr_list)
        {
            curr_item.setMaxBounds(max_x - curr_item.getImageHeight(), max_y);
        }

    }

    public void drawListContents(ArrayList<BackgroundEffect> curr_list)
    {

        for (MovableImage curr_item : curr_list)
        {
            drawTarget(curr_item);
        }
    }

    public void updateListContents(ArrayList<BackgroundEffect> curr_list)
    {

        for (BackgroundEffect curr_item : curr_list)
        {
            curr_item.update();
        }
    }

}