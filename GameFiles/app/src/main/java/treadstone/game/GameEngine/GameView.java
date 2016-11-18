package treadstone.game.GameEngine;

import android.graphics.Color;
import android.graphics.Paint;
import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import treadstone.game.R;

public class GameView extends SurfaceView implements Runnable
{

    volatile boolean        view_active;
    Thread                  game_thread = null;
    Player                  curr_player;

    private Paint           paint;
    private Canvas          canvas;
    private SurfaceHolder   curr_holder;

    public GameView(Context curr_context)
    {
        super(curr_context);
        curr_holder = getHolder();
        paint = new Paint();
        curr_player = new Player(curr_context, "Mini-Meep", 50, 50, 1);
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
    }

    public void draw()
    {

        if (curr_holder.getSurface().isValid())
        {

            // Lock Canvas
            canvas = curr_holder.lockCanvas();
            canvas.drawColor(Color.argb(255, 0, 0, 0));

            // Draw Player
            canvas.drawBitmap(curr_player.getImage(), curr_player.getX(), curr_player.getY(), paint);

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

}