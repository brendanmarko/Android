package treadstone.game.GameEngine;

import android.util.Log;
import java.util.Iterator;
import java.util.ArrayList;
import android.graphics.Color;
import android.graphics.Paint;
import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.support.v4.view.GestureDetectorCompat;

public class GameView extends SurfaceView implements Runnable
{
    // Debug info
    private int                             DEBUG = 0;
    private String                          DEBUG_TAG = "GameView";

    // Thread
    Thread                                  game_thread = null;
    volatile boolean                        view_active;

    // Viewport/Level/Player info
    private Position                        pixels_per_metre;
    private Player                          curr_player;
    private ViewPort                        viewport;
    private LevelManager                    level_manager;
    private double                          displacementX, displacementY;

    // Canvas
    private Paint                           paint;
    private Canvas                          canvas;
    private SurfaceHolder                   curr_holder;

    // Managers
    private EntityManager                   entity_manager;
    private HitboxManager                   hitbox_manager;
    private ProjectileManager               projectile_manager;
    private CollisionManager                collision_manager;

    // Touch handlers
    private TouchManager                    touch_manager;
    private MultiTouchManager               multi_touch_manager;

    // Gestures
    private GestureDetectorCompat           single_touch_detector;

    // Temp buffers
    private ArrayList<Projectile>           projectile_buffer;
    private ArrayList<Entity>               entity_buffer;

    public GameView(Context c, Position m)
    {
        super(c);
        paint = new Paint();
        curr_holder = getHolder();

        viewport = new ViewPort(new Position(m.getX() * 0.9f, m.getY()));
        pixels_per_metre = viewport.getPixelsPerMetre();

        initLevel(c);
        initPlayer();

        // Pass ViewPort max pixel dimensions
        viewport.setMapDimens(level_manager.getMapDimens());

        // Touch handlers
        touch_manager = new TouchManager(curr_player, viewport);
        multi_touch_manager = new MultiTouchManager(curr_player, viewport);

        // Mgr init
        hitbox_manager = new HitboxManager();
        collision_manager = new CollisionManager();
        projectile_manager = new ProjectileManager(viewport);
        entity_manager = new EntityManager(c, viewport, level_manager.getStartPoint(), level_manager.getEndpoint(), level_manager.getList());

        // Assign Touch handlers
        single_touch_detector = new GestureDetectorCompat(c, touch_manager);

        // Temp buffers
        entity_buffer = new ArrayList<>();
        projectile_buffer = new ArrayList<>();
    }

    public void loadLevel(Context c, String n)
    {
        level_manager = null;
        level_manager = new LevelManager(c, n, pixels_per_metre);
    }

    // onTouchEvent
    // This function handles the transfer of on-screen events
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if (event.getPointerCount() > 1)
        {
            return multi_touch_manager.handleEvent(event);
        }

        else
        {
            return single_touch_detector.onTouchEvent(event);
        }
    }

    @Override
    public void run()
    {
        while (view_active)
        {
            preUpdate();
            update();
            draw();
            control();
        }

    }

    // update()
    // This function is called once every iteration of the game loop and handles the changes in
    // all the game world objects
    public void update()
    {
        showObjects();
        updateEntities();
        updateProjectiles();
        collisionCheck();
    }

    public void showObjects()
    {
        for (Iterator<Entity> iterator = entity_manager.getList().iterator(); iterator.hasNext();)
        {
            Entity e = iterator.next();
            e.toString();
        }
    }

    public void updateProjectiles()
    {
        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "Updating Projectiles @ GameView");

        projectile_manager.addBuffer(this.getContext(), projectile_buffer);
        projectile_manager.update(displacementX, displacementY);
        projectile_buffer.removeAll(projectile_buffer);
    }

    public void draw()
    {
        if (curr_holder.getSurface().isValid())
        {
            // Lock Canvas
            canvas = curr_holder.lockCanvas();
            canvas.drawColor(Color.argb(255, 0, 0, 0));

            // Paint Colour
            paint.setColor(Color.argb(255, 0, 0, 0));

            // Draw to Screen
            entity_manager.draw(canvas, paint);
            projectile_manager.draw(canvas, paint);
            hitbox_manager.draw(entity_manager.getList(), projectile_manager.getList(), canvas, paint);

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
            Log.d(DEBUG_TAG, "Interrupt from control()");
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

    public void addProjectileToPlayer(ControllerFragment.ProjectileType p)
    {
        char        type;
        Projectile  x;

        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "AddProjectile2Player called with p = " + p.toString());

        switch (p)
        {
            case BULLET:
                type = 'b';
                x = new Bullet(curr_player, curr_player.getFiringPosition(), viewport.getMaxBounds(), pixels_per_metre, type);
                projectile_buffer.add(x);
                break;

            case MISSILE:
                type = 'm';
                x = new Missile(curr_player, curr_player.getFiringPosition(), viewport.getMaxBounds(), pixels_per_metre, type);
                projectile_buffer.add(x);
                break;

            case SHIELD:
                type = 's';
                x = new Bullet(curr_player, curr_player.getFiringPosition(), viewport.getMaxBounds(), pixels_per_metre, type);
                projectile_buffer.add(x);
                break;
        }

        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "Projectile added to Buffer");
    }

    public void updateEntities()
    {
        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "Updating Entities @ GameView");

        entity_manager.addBuffer(this.getContext(), entity_buffer);
        entity_manager.update(displacementX, displacementY);
        entity_buffer.removeAll(entity_buffer);
    }

    // void calcDisplacement
    // This function finds the amount of displacement that the ViewPort is from where it started.
    // These values are used for updating screen touches with the Game World.
    public void calcDisplacement()
    {
        displacementX = viewport.getViewPortCentre().getX() - viewport.getCentre().getX();
        displacementY = viewport.getViewPortCentre().getY() - viewport.getCentre().getY();

        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "Displacements: " + displacementX + ", " + displacementY);
    }

    // void preUpdate()
    // This function serves the purpose of doing any calculations/work before the update function is called wrt the GameView
    public void preUpdate()
    {
        calcDisplacement();
        touch_manager.updateTouchDisplacement(displacementX, displacementY);
        multi_touch_manager.updateTouchDisplacement(displacementX, displacementY);
    }

    public void initLevel(Context c)
    {
        loadLevel(c, "TestLevel");
    }

    public void initPlayer()
    {
        curr_player = level_manager.getPlayer();
        curr_player.initCenter(viewport.getCentre(), pixels_per_metre);
    }

    public void collisionCheck()
    {
        if (DEBUG == 1)
        {
            Log.d(DEBUG_TAG, "Checking for collisions wrt Entities");
            showObjects();
        }

        collision_manager.entityCollisions(level_manager.getList());

        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "Checking for collisions wrt Entities/Projectiles");

        collision_manager.projectileCollisions(level_manager.getList(), projectile_manager.getList());

        if (DEBUG == 1)
            Log.d(DEBUG_TAG, "Collision check completed.");
    }

}