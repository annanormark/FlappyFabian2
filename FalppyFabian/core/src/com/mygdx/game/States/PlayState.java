package com.mygdx.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

    private Fabian fabian;
    private Texture bg;

    private Array<Rainbow> tubes;

    public PlayState(GameStateManager gam) {
        super(gam);
        fabian = new Fabian(50,200);
        cam.setToOrtho(false, FlappyFabian.WIDTH/2, FlappyFabian.HEIGHT/2);
        bg = new Texture("Bakgrund.png");

        tubes = new Array<Rainbow>();

        for (int i = 0; i < TUBE_COUNT; i++){
            tubes.add(new Rainbow(i * (TUBE_SPACING + Rainbow.TUBE_WIDTH)));
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
        fabian.update(dt);
        cam.position.x = fabian.getPosition().x;

        for(int i = 0; i < tubes.size; i++){
            Rainbow rainbow = tubes.get(i);
            if(cam.position.x - (cam.viewportWidth / 2) > rainbow.getPosTopTube().x + rainbow.getTopTube().getWidth()){
                rainbow.reposition(rainbow.getPosTopTube().x + ((Rainbow.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT));
            }

            if(rainbow.collides(fabian.getBounds()))
                gam.set(new MenuState(gam));
        }

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
            sb.draw(rainbow.getTopTube(), rainbow.getPosTopTube().x, rainbow.getPosTopTube().y);
            sb.draw(rainbow.getBottomTube(), rainbow.getPosBottomTube().x, rainbow.getPosBottomTube().y);
        }

        sb.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        fabian.dispose();
        for( Rainbow rainbow : tubes)
            rainbow.dispose();
        System.out.println("Play state disposed");
    }
}
