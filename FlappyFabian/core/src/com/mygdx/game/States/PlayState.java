package com.mygdx.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.FlappyFabian;
import com.mygdx.game.sprites.Fabian;
import com.mygdx.game.sprites.Rainbow;

/**
 * Created by Anna on 2017-05-02.
 */

public class PlayState extends State {
    private static final int TUBE_SPACING = 125;
    private static final int TUBE_COUNT = 4;
    private static final int GROUND_Y_OFFSET = -30;

    private Fabian fabian;
    private Texture bg, ground;
    private Vector2 groundPos1, groundPos2;
    private int SCORE;
    private BitmapFont font;

    private Array<Rainbow> tubes;

    public PlayState(GameStateManager gam) {
        super(gam);
        cam.setToOrtho(false, FlappyFabian.WIDTH/2, FlappyFabian.HEIGHT/2);
        SCORE = 0;

        bg = new Texture("Bakgrund.png");
        ground = new Texture("moln.png");
        font = new BitmapFont();

        fabian = new Fabian(50,200);
        groundPos1 = new Vector2(cam.position.x - cam.viewportWidth /2, GROUND_Y_OFFSET);
        groundPos2 = new Vector2((cam.position.x - cam.viewportWidth / 2) + ground.getWidth(), GROUND_Y_OFFSET);

        tubes = new Array<Rainbow>();
        for (int i = 0; i < TUBE_COUNT; i++){
            tubes.add(new Rainbow(i * (TUBE_SPACING + Rainbow.OBSTACLE_WIDTH)));
        }
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            fabian.jump();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        updateGround();
        fabian.update(dt);
        cam.position.x = fabian.getPosition().x;

        for(int i = 0; i < tubes.size; i++){
            Rainbow rainbow = tubes.get(i);
            if(cam.position.x - (cam.viewportWidth / 2) > rainbow.getPosTopRainbow().x + rainbow.getTopRainbow().getWidth()){
                rainbow.reposition(rainbow.getPosTopRainbow().x + ((Rainbow.OBSTACLE_WIDTH + TUBE_SPACING) * TUBE_COUNT));

            }

            if(rainbow.collides(fabian.getBounds()))
                gam.set(new MenuState(gam));


            else if(rainbow.passing(fabian.getBounds())){
                SCORE++;
                System.out.println(SCORE);
            }
        }

        if(fabian.getPosition().y <= ground.getHeight() + GROUND_Y_OFFSET)
            gam.set(new MenuState(gam));

        cam.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();

        //create bkg
        sb.draw(bg, cam.position.x - (cam.viewportWidth / 2), 0, cam.viewportWidth, cam.viewportHeight);

        //create the "Fabian" or unicorn
        Vector3 pos = fabian.getPosition();
        sb.draw(fabian.getTexture(), pos.x, pos.y);

        //create tubes or obstacles
        for(Rainbow rainbow : tubes) {
            sb.draw(rainbow.getTopRainbow(), rainbow.getPosTopRainbow().x, rainbow.getPosTopRainbow().y);
            sb.draw(rainbow.getBottomRainbow(), rainbow.getPosBottomRainbow().x, rainbow.getPosBottomRainbow().y);
        }

        sb.draw(ground, groundPos1.x, groundPos1.y);
        sb.draw(ground, groundPos2.x, groundPos2.y);
        sb.end();

        sb.begin();
        font.setUseIntegerPositions(true);
        font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        font.draw(sb, SCORE + "", cam.position.x, cam.position.y + ( cam.viewportHeight / 3));
        sb.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        fabian.dispose();
        ground.dispose();
        for( Rainbow rainbow : tubes)
            rainbow.dispose();
        System.out.println("Play state disposed");
    }


    private void updateGround(){
        if(cam.position.x - (cam.viewportWidth / 2) > groundPos1.x + ground.getWidth())
            groundPos1.add(ground.getHeight() * 2, 0);
        if(cam.position.x - (cam.viewportWidth / 2) > groundPos2.x + ground.getWidth())
            groundPos2.add(ground.getHeight() * 2, 0);
    }


}
