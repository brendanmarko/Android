package treadstone.game.GameEngine;

import android.util.Log;
import java.util.ArrayList;

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
    // Debug toggle
    private int                             DEBUG = 0;

    // Thread
    Thread                                  game_thread = null;
    volatile boolean                        view_active;

    // Viewport/Level/Player info
    private Position                        pixels_per_metre;
    private Player                          curr_player;
    private Position                        screen_size;
    private ViewPort                        viewport;
    private LevelManager                    level_manager;
    private float                           displacementX, displacementY;

    // Canvas
    private Paint                           paint;
    private Canvas                          canvas;
    private SurfaceHolder                   curr_holder;

    // Managers
    private ProjectileManager               projectileMgr;
    private CollisionManager                collisionMgr;

    // Temp buffer for Projectiles
    private ArrayList<Projectile>           temp_buffer;

    public GameView(Context c, Position m)
    {
        super(c);
        screen_size = m;
        init();

        initViewPort(m);
        initLevel(c);
        initPlayer();

        // Pass ViewPort max pixel dimensions
        viewport.setMapDimens(level_manager.getMapDimens());

        // Mgr init
        collisionMgr = new CollisionManager();
        projectileMgr = new ProjectileManager(viewport);

        // Projectile buffer
        temp_buffer = new ArrayList<Projectile>();
    }

    public void init()
    {
        paint = new Paint();
        curr_holder = getHolder();
    }

    public void loadLevel(Context c, String n, Position s)
    {
        level_manager = null;
        level_manager = new LevelManager(c, n, viewport.getPixelsPerMetre());
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
    // the game world objects within LevelManager.getGameOjbects()
    public void update()
    {
        showObjects();
        updateEntities();
        updateProjectiles();
        collisionCheck();
    }

    public void showObjects()
    {
        for (Entity e : level_manager.getGameObjects())
        {
            e.toString();
        }
    }

    public void updateProjectiles()
    {
        if (DEBUG == 1)
            Log.d("GameView/UpdateP", "Updating Projectiles @ GameView");

            if (DEBUG == 1)
                Log.d("GameView/UpdateP", "=== Inside Else");

            projectileMgr.addBuffer(this.getContext(), temp_buffer);
            projectileMgr.update(displacementX, displacementY);

            if (DEBUG == 1)
                Log.d("GameView/UpdateP", "=== Complete update and removing from buffer with size: " + projectileMgr.numProjectiles());

            temp_buffer.removeAll(temp_buffer);
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
            drawEntities();
            drawProjectiles();
            drawHitboxes();

            // Unlock and draw
            curr_holder.unlockCanvasAndPost(canvas);
        }

    }

    public void drawProjectiles()
    {
        Rect r = new Rect();

        if  (DEBUG == 1)
            Log.d("GameView/DrawPrj", "Drawing Projectiles within GameView with size: " + projectileMgr.getProjectiles().size());

        for (int layer = -1; layer < 3; layer++)
        {
            for (Projectile p : projectileMgr.getProjectiles())
            {
                if (p.isVisible() && p.getLayer() == layer)
                {
                    r.set(viewport.worldToScreen(p.getPosition(), p.getObjInfo().getDimensions()));
                    canvas.drawBitmap(projectileMgr.getBitmap(p.getObjInfo().getType()), r.left + p.getOwner().getWidth(), r.top + p.getOwner().getHeight()/3, paint);
                }
            }
        }
    }

    public void drawEntities()
    {
        Rect new_target = new Rect();

        for (int layer = -1; layer < 3; layer++)
        {
            for (Entity e : level_manager.getGameObjects())
            {
                if (e.isVisible() && e.getLayer() == layer)
                {
                    new_target.set(viewport.worldToScreen(e.getPosition(), e.getObjInfo().getDimensions()));
                    canvas.drawBitmap(level_manager.getBitmap(e.getObjInfo().getType()), new_target.left, new_target.top, paint);
                }
            }
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
                Log.d("GV.onTouch", "Touch ended!");
        }

        else if (curr_motion.getAction() == MotionEvent.ACTION_DOWN)
        {
            if (DEBUG == 1)
                Log.d("entering_action_move", "ProcessMovement to be called with Displacement values calculated");

            curr_player.processMovement(curr_motion.getX() + displacementX, curr_motion.getY() + displacementY);
            viewport.setViewPortCentre(curr_player.getPosition());

            if (DEBUG == 1)
                Log.d("GameView.onTouch", "Current Centre: " + viewport.getViewPortCentre().toString());

        }

        else if (curr_motion.getAction() == MotionEvent.ACTION_MOVE)
        {
            // To do
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
                x = new Bullet(curr_player, curr_player.getPosition(), viewport.getPixelsPerMetre(), viewport.getMaxBounds(), type);
                temp_buffer.add(x);
                break;

            case MISSILE:
                type = 'm';
                x = new Missile(curr_player, curr_player.getPosition(), viewport.getPixelsPerMetre(), viewport.getMaxBounds(), type);
                temp_buffer.add(x);
                break;

            case SHIELD:
                type = 's';
                x = new Bullet(curr_player, curr_player.getPosition(), viewport.getPixelsPerMetre(), viewport.getMaxBounds(), type);
                temp_buffer.add(x);
                break;

            default:
                type = '.';
        }

        if (DEBUG == 1)
            Log.d("GameView.addP2P", "Projectile added to Buffer");
    }

    public void updateEntities()
    {
        for (Entity e : level_manager.getGameObjects())
        {
            if (e.isActive())
            {

                if (DEBUG == 1)
                    Log.d("GameView/UpEnt", "Examining: " + e.toString());

                if (viewport.clipObject(e.getPosition()))
                {
                    Log.d("GameView/UpdateE", "ENTITY SHOULD BE INVISIBLE");
                    e.setInvisible();
                }

                else
                {
                    Log.d("GameView/UpdateE", "ENTITY SHOULD BE VISIBLE");
                    e.setVisible();

                    if (e.getSpeed() > 0.0f)
                    {
                        MovableEntity m = (MovableEntity) e;
                        m.update();

                        if (m.getObjInfo().getType() == 'p')
                            viewport.setViewPortCentre(curr_player.getPosition());
                    }
                }

                if (DEBUG == 1)
                    Log.d("GameView/UpEnt", "Post-Examining: " + e.toString());

                e.updateHitbox(displacementX, displacementY);

            }
        }
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

    // void initViewPort()
    // This function handles the ViewPort's creation wrt the size and ppm
    public void initViewPort(Position m)
    {
        viewport = new ViewPort(new Position(m.getX() * 0.9f, m.getY()));
        pixels_per_metre = viewport.getPixelsPerMetre();
    }

    public void initLevel(Context c)
    {
        loadLevel(c, "TestLevel", new Position(0.0f, 0.0f));
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

        collisionMgr.entityCollisions(level_manager.getGameObjects());

        if (DEBUG == 1)
            Log.d("GameView/CollChk", "Checking for collisions wrt Entities/Projectiles");

        collisionMgr.projectileCollisions(level_manager.getGameObjects(), projectileMgr.getProjectiles());

        if (DEBUG == 1)
            Log.d("GameView/CollChk", "Collision check completed.");
    }

    public void drawHitboxes()
    {
        Rect box;

        paint.setColor(Color.argb(255, 100, 100, 200));

        if (DEBUG == 1)
            Log.d("GameView/drawHB", "Drawing Entity boxes");

        for (Entity e : level_manager.getGameObjects())
        {
            if (DEBUG == 1)
                Log.d("GameView/drawHB", "Drawing entity: " + e.toString());

            if (e.isVisible())
            {
                box = new Rect(e.getHitbox());
                canvas.drawRect(box.left, box.top, box.right, box.bottom, paint);
            }

            else
                box = null;

            if (DEBUG == 1 && box != null)
                Log.d("GameView/drawHB", "Box value: " + box.toString());

        }

        if (DEBUG == 1)
            Log.d("GameView/drawHB", "Drawing Projectile boxes");

        paint.setColor(Color.argb(255, 255, 255, 0));

        for (Projectile p : projectileMgr.getProjectiles())
        {
            if (p.isVisible())
            {
                box = new Rect(p.getHitbox());
                canvas.drawRect(box.left, box.top, box.right, box.bottom, paint);
            }

            else
                box = null;

            if (DEBUG == 1 && box != null)
                Log.d("GameView/drawHBP", "Prj value: " + box.toString());
        }

    }

} // end : GameView Class