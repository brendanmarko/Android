package treadstone.game.GameEngine;

import android.util.Log;

import android.graphics.Rect;
import android.graphics.Color;
import android.graphics.Paint;
import android.content.Context;
import android.graphics.Canvas;

import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

public class GameView extends SurfaceView implements Runnable
{
    volatile boolean                view_active;
    Thread                          game_thread = null;
    private Player                  curr_player;
    private Position                max_bounds;
    private ViewPort                viewport;
    private LevelManager            level_manager;

    private Paint                   paint;
    private Canvas                  canvas;
    private SurfaceHolder           curr_holder;

    CollisionChecker                collision_check;

    public GameView(Context c, Position m)
    {
        super(c);
        max_bounds = m;
        init();

        viewport = new ViewPort(max_bounds);
        loadLevel(c, "TestLevel", new Position(0.0f, 0.0f));

        curr_player = level_manager.getPlayer();
        curr_player.setPosition(viewport.getCentre().getX(), viewport.getCentre().getY());
        Log.d("GameView/CTOR", "Testing centre init value: " + viewport.getViewPortCentre().toString());
        Log.d("GameView/CTOR", "Testing centre init value: " + curr_player.getX() + ", " + curr_player.getY());
    }

    public void init()
    {
        paint = new Paint();
        curr_holder = getHolder();
    }

    public void loadLevel(Context c, String n, Position s)
    {
        level_manager = null;
        level_manager = new LevelManager(c, n, max_bounds, viewport.getPixelsPerMetre(), s);
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

    // update()
    // This function is called once every iteration of the game loop and handles the changes in
    // the game world objects within LevelManager.getGameOjbects()
    public void update()
    {
        for (Entity e : level_manager.getGameObjects())
        {
            if (e.isActive())
            {
                if (viewport.clipObject(e.getPosition(), e.getType().getDimensions())) {
                    e.setInvisible();
                }

                else {
                    e.setVisible();
                }

            }

        }

    }

    public void draw()
    {
        Rect new_target = new Rect();

        if (curr_holder.getSurface().isValid())
        {
            // Lock Canvas
            canvas = curr_holder.lockCanvas();
            canvas.drawColor(Color.argb(255, 0, 0, 0));

            // Paint Colour
            paint.setColor(Color.argb(255, 0, 0, 0));

            // Draw Entities
            for (int layer = -1; layer < 2; layer++)
            {
                for (Entity e : level_manager.getGameObjects())
                {
                    if (e.isVisible() && e.getLayer() == layer) {
                        // Log.d("GameView/draw", "Object is visible and being drawn!");
                        new_target.set(viewport.worldToScreen(e.getPosition(), e.getType().getDimensions()));
                        canvas.drawBitmap(level_manager.getBitmap(e.getType().getType()), new_target.left, new_target.top, paint);
                    }
                }
            }

            // Unlock and draw
            curr_holder.unlockCanvasAndPost(canvas);
        }

    }

    public void control()
    {
        try
        {
            game_thread.sleep(17);
        }

        catch (InterruptedException e)
        {
            Log.d("CONTROL_CRASH", "Interrupt caught within control() [View]");
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
        Log.d("on_touch_event+++", "GameView onTouch called!");

        if (curr_motion.getAction() == MotionEvent.ACTION_UP)
        {
            curr_player.setUnmovable();
        }

        else if (curr_motion.getAction() == MotionEvent.ACTION_DOWN)
        {
            curr_player.setMovable();
        }

        else if (curr_motion.getAction() == MotionEvent.ACTION_MOVE)
        {
            Log.d("entering_action_move", "ProcessMovement to be called...");
            curr_player.processMovement(curr_motion.getX(), curr_motion.getY());
            viewport.setViewPortCentre(curr_player.getPosition());
        }

        return true;
    }

    /*
    public void updateProjectiles()
    {
        // Add temp_buffer to Projectile List
        curr_player.getProjectiles().addAll(temp_buffer);

        for (Projectile p : curr_player.getProjectiles())
        {
            p.update();
        }

        temp_buffer.removeAll(temp_buffer);

    }

    public void drawProjectile(Projectile curr_target)
    {
        canvas.drawBitmap(curr_target.getImage(), curr_target.getX(), curr_target.getY(), paint);
    }

    public void checkCollisions()
    {
        collision_check = new CollisionChecker(curr_player, enemy_list);

        if (collision_check.shipCollisions())
        {
            Log.d("collision_log", "Collision [Ship] just occurred");
        }

        if (collision_check.projectileCollisions())
        {
            Log.d("collision_log", "Collision [Projectiles] just occurred!");
        }

        if (collision_check.projectileBoundary())
        {
            Log.d("collision_log", "Projectile out of bounds and being deleted!");
        }

    }


    public void drawHitBoxes()
    {
        Rect curr_box;
        curr_box = curr_player.getHitBox().getHitBox();
        canvas.drawRect(curr_box.left, curr_box.top, curr_box.right, curr_box.bottom, paint);

        for (Projectile p : curr_player.getProjectiles())
        {
            curr_box = p.getHitBox().getHitBox();
            canvas.drawRect(curr_box.left, curr_box.top, curr_box.right, curr_box.bottom, paint);
        }

        for (TestEnemy e : enemy_list)
        {
            curr_box = e.getHitBox().getHitBox();
            canvas.drawRect(curr_box.left, curr_box.top, curr_box.right, curr_box.bottom, paint);
        }

    }


    public void addProjectileToPlayer(ControllerFragment.ProjectileType p)
    {
        char type;

        switch (p)
        {
            case BULLET:
                type = 'b';
                break;

            case MISSILE:
                type = 'm';
                break;

            case SHIELD:
                type = 's';
                break;

            default:
                type = '.';
        }

        // Buffer for adding to Projectiles
        temp_buffer.add(new Projectile(getContext(), new Position(curr_player.getX(), curr_player.getY()), max_bounds, type));
    }

     */


}