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
    public MenuState(GameStateManager gam) {
        super(gam);
        backgroud = new Texture("Bakgrund.png");
        playBtn = new Texture("play.png");
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
        sb.begin();
        sb.draw(backgroud, 0, 0, FlappyFabian.WIDTH, FlappyFabian.HEIGHT);
        sb.draw(playBtn, (FlappyFabian.WIDTH / 2) - (playBtn.getWidth() / 2), (FlappyFabian.HEIGHT / 2) - (playBtn.getHeight()/2));
        sb.end();

    }

    @Override
    public void dispose() {
        backgroud.dispose();
        playBtn.dispose();
    }
}
