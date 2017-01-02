package treadstone.game.GameEngine;

import android.util.Log;

import java.util.ArrayList;
import java.util.ListIterator;

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
    Player                          curr_player;
    TestEnemy                       test_enemy1, test_enemy2, test_enemy3;
    private Position                max_bounds;

    private Paint                   paint;
    private Canvas                  canvas;
    private SurfaceHolder           curr_holder;

    ArrayList<TestEnemy>            enemy_list = new ArrayList<>();
    ArrayList<Projectile>           temp_buffer = new ArrayList<>();
    ArrayList<BackgroundEffect>     background_visuals = new ArrayList<>();

    BackgroundEffect                b1, b2, b3, b4;
    CollisionChecker                collision_check;

    public GameView(Context curr_context, Position max)
    {
        super(curr_context);
        max_bounds  = max;
        init();
    }

    public void init()
    {
        // Initialize Surface Requirements
        paint = new Paint();
        curr_holder = getHolder();

        // Player added to Game
        curr_player = new Player(getContext(), new Position(0, 1000), max_bounds, 'p');

        // Test Enemy added to Map
        test_enemy1 = new TestEnemy(getContext(), new Position(2000, 50), max_bounds, 'e');
        test_enemy2 = new TestEnemy(getContext(), new Position(1000, 50), max_bounds, 'e');
        test_enemy3 = new TestEnemy(getContext(), new Position(750, 100), max_bounds, 'e');

        enemy_list.add(test_enemy1);
        enemy_list.add(test_enemy2);
        enemy_list.add(test_enemy3);

        // Background Effects
        b1 = new BackgroundEffect(getContext(), new Position(0, 0), max_bounds, 'S');
        b2 = new BackgroundEffect(getContext(), new Position(0, 0), max_bounds, 's');
        b3 = new BackgroundEffect(getContext(), new Position(0, 0), max_bounds, 'S');
        b4 = new BackgroundEffect(getContext(), new Position(0, 0), max_bounds, 's');

        background_visuals.add(b1);
        background_visuals.add(b2);
        background_visuals.add(b3);
        background_visuals.add(b4);
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
        updateProjectiles();
        updateEnemies();
        updateBackgroundEffects();
    }

    public void draw()
    {
        if (curr_holder.getSurface().isValid())
        {
            // Lock Canvas
            canvas = curr_holder.lockCanvas();
            canvas.drawColor(Color.argb(255, 0, 0, 0));

            // Paint Colour
            paint.setColor(Color.argb(255, 255, 0, 0));

            // Draw Entities
            drawTarget(curr_player);
            drawProjectiles();
            drawEnemies();
            drawBackgroundEffects();
            drawHitBoxes();
            checkCollisions();

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
            curr_player.processMovement(curr_motion.getRawX(), curr_motion.getRawY());
        }

        return true;
    }

    public void drawTarget(MovableEntity curr_target)
    {
        canvas.drawBitmap(curr_target.getImage(), curr_target.getX(), curr_target.getY(), paint);
    }

    public void drawBackgroundEffects()
    {
        for (BackgroundEffect curr_item : background_visuals)
        {
            drawTarget(curr_item);
        }
    }

    public void drawEnemies()
    {
        for (TestEnemy e : enemy_list)
        {
            canvas.drawBitmap(e.getImage(), e.getX(), e.getY(), paint);
        }

    }

    public void updateBackgroundEffects()
    {
        BackgroundEffect curr_item;

        for (ListIterator<BackgroundEffect> iterator = background_visuals.listIterator(); iterator.hasNext();)
        {
            curr_item = iterator.next();
            curr_item.update();
        }

    }

    public void drawProjectiles()
    {
        for (Projectile p : curr_player.getProjectiles())
        {
            drawProjectile(p);
        }

    }

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

    public void updateEnemies()
    {
        for (TestEnemy e : enemy_list)
        {
            e.update();
        }

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

}