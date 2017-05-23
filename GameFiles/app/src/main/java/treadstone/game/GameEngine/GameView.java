package treadstone.game.GameEngine;

import android.util.Log;
import java.util.ArrayList;
import java.util.Iterator;
import android.graphics.Color;
import android.graphics.Paint;
import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

public class GameView extends SurfaceView implements Runnable
{
    // Debug toggle
    private int                             DEBUG = 1;

    // Thread
    Thread                                  game_thread = null;
    volatile boolean                        view_active;

    // Viewport/Level/Player info
    private Position                        pixels_per_metre;
    private Player                          curr_player;
    private ViewPort                        viewport;
    private LevelManager                    level_manager;
    private float                           displacementX, displacementY;

    // Canvas
    private Paint                           paint;
    private Canvas                          canvas;
    private SurfaceHolder                   curr_holder;

    // Managers
    private EntityManager                   entity_manager;
    private HitboxManager                   hitbox_manager;
    private ProjectileManager               projectile_manager;
    private CollisionManager                collision_manager;

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

        // Mgr init
        hitbox_manager = new HitboxManager();
        collision_manager = new CollisionManager();
        projectile_manager = new ProjectileManager(viewport);
        entity_manager = new EntityManager(c, viewport, level_manager.getStartPoint(), level_manager.getEndpoint(), level_manager.getList());

        // Temp buffers
        entity_buffer = new ArrayList<>();
        projectile_buffer = new ArrayList<>();

    }

    public void loadLevel(Context c, String n)
    {
        level_manager = null;
        level_manager = new LevelManager(c, n, pixels_per_metre);
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
            Log.d("GameView/UpdateP", "Updating Projectiles @ GameView");

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
            Log.d("GameView/Control", "Interrupt from control()");
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
        if (DEBUG == 1)
            Log.d("on_touch_event+++", "GameView onTouch called!");

        if (curr_motion.getAction() == MotionEvent.ACTION_UP)
        {
            if (DEBUG == 1)
                Log.d("entering_action_up", "ProcessMovement to be called with Displacement values calculated");

            curr_player.processMovement(curr_motion.getX() + displacementX, curr_motion.getY() + displacementY);
            viewport.setViewPortCentre(curr_player.getPosition());

            if (DEBUG == 1)
                Log.d("GameView.onTouch", "Current Centre: " + viewport.getViewPortCentre().toString());
        }

        else if (curr_motion.getAction() == MotionEvent.ACTION_DOWN)
        {
            if (DEBUG == 1)
                Log.d("entering_action_down", "Screen touched");
        }

        else if (curr_motion.getAction() == MotionEvent.ACTION_MOVE)
        {
            if (DEBUG == 1)
                Log.d("GameView/onTouch", "ACTION_MOVE triggered!");
            curr_player.adjustDirection(curr_motion.getX(), curr_motion.getY());
        }

        return true;
    }

    public void addProjectileToPlayer(ControllerFragment.ProjectileType p)
    {
        char        type;
        Projectile  x;

        if (DEBUG == 1)
            Log.d("GameView.addP2P", "AddProjectile2Player called with p = " + p.toString());

        switch (p)
        {
            case BULLET:
                type = 'b';
                x = new Bullet(curr_player, curr_player.getPosition(), viewport.getMaxBounds(), pixels_per_metre, type);
                projectile_buffer.add(x);
                break;

            case MISSILE:
                type = 'm';
                x = new Missile(curr_player, curr_player.getPosition(), viewport.getMaxBounds(), pixels_per_metre, type);
                projectile_buffer.add(x);
                break;

            case SHIELD:
                type = 's';
                x = new Bullet(curr_player, curr_player.getPosition(), viewport.getMaxBounds(), pixels_per_metre, type);
                projectile_buffer.add(x);
                break;
        }

        if (DEBUG == 1)
            Log.d("GameView.addP2P", "Projectile added to Buffer");
    }

    public void updateEntities()
    {
        if (DEBUG == 1)
            Log.d("GameView/UpdateP", "Updating Entities @ GameView");

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
            Log.d("GameView/calcD", "Displacements: " + displacementX + ", " + displacementY);
    }

    // void preUpdate()
    // This function serves the purpose of doing any calculations/work before the update function is called wrt the GameView
    public void preUpdate()
    {
        calcDisplacement();
    }

    public void initLevel(Context c)
    {
        loadLevel(c, "TestLevel");
    }

    public void initPlayer()
    {
        curr_player = level_manager.getPlayer();
        curr_player.initCenter(viewport.getCentre());
    }

    public void collisionCheck()
    {
        if (DEBUG == 1)
        {
            Log.d("GameView/CollChk", "Checking for collisions wrt Entities");
            showObjects();
        }

        collision_manager.entityCollisions(level_manager.getList());

        if (DEBUG == 1)
            Log.d("GameView/CollChk", "Checking for collisions wrt Entities/Projectiles");

        collision_manager.projectileCollisions(level_manager.getList(), projectile_manager.getList());

        if (DEBUG == 1)
            Log.d("GameView/CollChk", "Collision check completed.");
    }

}