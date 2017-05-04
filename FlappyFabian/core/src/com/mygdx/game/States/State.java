package com.mygdx.game.States;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.FlappyFabian;

/**
 * Created by Anna on 2017-05-02.
 */

public abstract class State {

    protected OrthographicCamera cam;
    protected Vector3 mouse;
    protected GameStateManager gam;

    protected State(GameStateManager gam){
        this.gam=gam;
        cam = new OrthographicCamera(FlappyFabian.WIDTH, FlappyFabian.HEIGHT);
        mouse  = new Vector3();
    }

    protected abstract void handleInput();
    public abstract void update(float dt);
    public abstract void render(SpriteBatch sb);
    public abstract void dispose();


}
