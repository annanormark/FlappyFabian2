package com.mygdx.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.FlappyFabian;
import com.mygdx.game.sprites.Fabian;
import com.mygdx.game.sprites.Rainbow;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;


import java.awt.Canvas;
import java.lang.Object;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

/**
 * handles the entire state where the user plays
 */

public class TutorialState extends State {
    // Set variables for the play
    private static final int OBSTACLE_SPACING = 140;
    private static final int OBSTACLE_COUNT = 4;
    private static final int GROUND_Y_OFFSET = -30;

    private Fabian fabian;
    private Texture bg, ground, clickPic, goThrough, crash;
    private Vector2 groundPos1, groundPos2;
    private int score, tutPic;
    private BitmapFont font;
    public boolean paused = true;
    public boolean dead = false;
    private Array<Rainbow> tubes;
    private ShapeRenderer shapeRenderer;

    // initiate the tutorialstate
    public TutorialState(GameStateManager gam) {
        super(gam);
        cam.setToOrtho(false, FlappyFabian.WIDTH / 2, FlappyFabian.HEIGHT / 2);
        score = 0;

        bg = new Texture("Bakgrund.png");
        ground = new Texture("moln.png");
        clickPic = new Texture("click.png");
        goThrough = new Texture("goThrough.png");
        crash = new Texture("dead.png");
        font = new BitmapFont();
        shapeRenderer = new ShapeRenderer();

        fabian = new Fabian(50, 200);
        groundPos1 = new Vector2(cam.position.x - cam.viewportWidth / 2, GROUND_Y_OFFSET);
        groundPos2 = new Vector2((cam.position.x - cam.viewportWidth / 2) + ground.getWidth(), GROUND_Y_OFFSET);

        tubes = new Array<Rainbow>();

        for (int i = 0; i < OBSTACLE_COUNT; i++) {
            tubes.add(new Rainbow(i * (OBSTACLE_SPACING + Rainbow.OBSTACLE_WIDTH)));
        }
    }

    // handles input, e.g. if a user touch screen, fabian jumps
    @Override
    protected void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_LEFT)){
            paused = !paused;
        }
        if (Gdx.input.justTouched()) {
            if (!paused) {
                fabian.jump();
            }
            else if (paused && !dead){
                if (tutPic<3) {
                    tutPic++;
                }
                if (tutPic>=3){
                    paused=false;
                }

            }

            else if (paused && dead){
                gam.set(new MenuState(gam));
            }
        }
    }

    // update is called 60 times per seconds and update the entire game
    @Override
    public void update(float dt) {
        // update different parts of the game + makes sure input is handled directly
        handleInput();
        updateGround();

        // update only if the game is running
        if (!paused) {

            fabian.update(dt);

            // center fabian on the screen (moves "camera" to fabians position)
            cam.position.x = fabian.getPosition().x + 60;

            //for each tube check if fabian collides, passes or if the tube should be moved to front
            for (int i = 0; i < tubes.size; i++) {
                Rainbow rainbow = tubes.get(i);
                if (cam.position.x - (cam.viewportWidth / 2) > rainbow.getPosTopRainbow().x + rainbow.getTopRainbow().getWidth()) {
                    rainbow.reposition(rainbow.getPosTopRainbow().x + ((Rainbow.OBSTACLE_WIDTH + OBSTACLE_SPACING) * OBSTACLE_COUNT));
                }
                // Pause the game if you collide
                if (rainbow.collides(fabian.getBounds())) {
                    paused = true;
                    dead = true;



                } else if (rainbow.passing(fabian.getBounds())) {
                    score++;
                    System.out.println(score);
                }
            }

            // check if fabian collides with ground, pause the game if so
            if (fabian.getPosition().y <= ground.getHeight() + GROUND_Y_OFFSET) {
                paused = true;
                dead = true;

            }

            cam.update();
        }
    }

    // render draws all items on the screen
    @Override
    public void render(SpriteBatch sb) {

        sb.setProjectionMatrix(cam.combined);
        sb.begin();

        //create background
        sb.draw(bg, cam.position.x - (cam.viewportWidth / 2), 0, cam.viewportWidth, cam.viewportHeight);




        //create the "Fabian" or unicorn
        Vector3 pos = fabian.getPosition();
        sb.draw(fabian.getTexture(), pos.x, pos.y);

        //create tubes or obstacles
        for (Rainbow rainbow : tubes) {
            sb.draw(rainbow.getTopRainbow(), rainbow.getPosTopRainbow().x, rainbow.getPosTopRainbow().y);
            sb.draw(rainbow.getBottomRainbow(), rainbow.getPosBottomRainbow().x, rainbow.getPosBottomRainbow().y);
        }

        //draws the moving ground
        sb.draw(ground, groundPos1.x, groundPos1.y);
        sb.draw(ground, groundPos2.x, groundPos2.y);
        sb.end();

        // draw the opaque background for highlighted info
        if (tutPic<3) {
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.begin(ShapeType.Filled);
            shapeRenderer.setColor(new Color(1, 1, 1, 0.5f));
            shapeRenderer.rect(0, 0, 1920, 1080);
            shapeRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);
        }


        //prints the score on the top of the screen
        sb.begin();
        font.setUseIntegerPositions(true);
        font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        font.draw(sb, score + "", cam.position.x, cam.position.y + (cam.viewportHeight / 3));




        // draw the different steps in the tutorial
        if (tutPic==0){
            sb.draw(clickPic, cam.position.x - (clickPic.getWidth() / 2), cam.position.y - (clickPic.getHeight()/2));

        }

        if (tutPic==1){
            sb.draw(goThrough, cam.position.x - (goThrough.getWidth() / 2), cam.position.y - (goThrough.getHeight()/2));

        }

        if (tutPic==2){
            sb.draw(crash, cam.position.x - (crash.getWidth() / 2), cam.position.y - (crash.getHeight()/2));

        }

        sb.end();


        Gdx.graphics.requestRendering();

        }


    //dispose is called in the end to make sure we don't create memory leaks
    @Override
    public void dispose() {
        bg.dispose();
        fabian.dispose();
        ground.dispose();

        for (Rainbow rainbow : tubes)
            rainbow.dispose();

        System.out.println("Tutorial state disposed");
    }

    // moves the ground
    private void updateGround() {
        if (cam.position.x - (cam.viewportWidth / 2) > groundPos1.x + ground.getWidth())
            groundPos1.add(ground.getHeight() * 2, 0);
        if (cam.position.x - (cam.viewportWidth / 2) > groundPos2.x + ground.getWidth())
            groundPos2.add(ground.getHeight() * 2, 0);
    }


}
