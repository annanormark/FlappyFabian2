package com.mygdx.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.FlappyFabian;

/**
 * Created by Anna on 2017-05-02.
 */

public class MenuState extends State {
    private Texture backgroud;
    private Texture playBtn;
    private SpriteBatch batch;
    public MenuState(GameStateManager gam) {
        super(gam);
        backgroud = new Texture("Bakgrund.png");
        playBtn = new Texture("start.png");
    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()){
            gam.set(new PlayState(gam));
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();

        float x = cam.position.x - (cam.viewportWidth / 2);
        float y = cam.position.y - (cam.viewportHeight/2);
        sb.draw(backgroud, x, y, cam.viewportWidth, cam.viewportHeight);
        sb.draw(playBtn, cam.position.x - (playBtn.getWidth() / 2), cam.position.y - (playBtn.getHeight()/2));
        sb.end();
        batch = sb;

    }

    @Override
    public void dispose() {
        backgroud.dispose();
        playBtn.dispose();
        System.out.println("Menu state disposed");

    }
}
