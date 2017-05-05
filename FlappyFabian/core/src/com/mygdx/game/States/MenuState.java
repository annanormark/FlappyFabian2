package com.mygdx.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Created by Anna on 2017-05-02.
 */

public class MenuState extends State {
    private Texture backgroud, playBtnPic, titlepic;
    private SpriteBatch batch;
    private Image playBtn, tutBtn;

    public MenuState(GameStateManager gam) {
        super(gam);
        backgroud = new Texture("Bakgrund.png");
        titlepic = new Texture("title.png");
        playBtnPic = new Texture("start.png");
        playBtn = new Image(playBtnPic);


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
        sb.draw(titlepic, cam.position.x - (titlepic.getWidth() / 2), cam.position.x - (titlepic.getWidth()) + 470);
        sb.draw(playBtnPic, cam.position.x - (playBtnPic.getWidth() / 2), cam.position.y - (playBtnPic.getHeight()/2));
        sb.end();
    }

    @Override
    public void dispose() {
        backgroud.dispose();
        playBtnPic.dispose();
        System.out.println("Menu state disposed");

    }
}
