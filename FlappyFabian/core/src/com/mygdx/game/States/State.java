package com.mygdx.game.States;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.FlappyFabian;

/**
 * Created by Anna on 2017-05-02.
 * Superclass of states the game is in, e.i. playstate
 */

public abstract class State {
    //each state have these
    protected OrthographicCamera cam;
    protected Vector3 mouse;
    protected GameStateManager gam;

    //initiate a new state
    protected State(GameStateManager gam){
        this.gam = gam;
        cam = new OrthographicCamera(FlappyFabian.WIDTH, FlappyFabian.HEIGHT);
        mouse = new Vector3();
    }

    //functions each state should have
    protected abstract void handleInput();
    public abstract void update(float dt);
    public abstract void render(SpriteBatch sb);
    public abstract void dispose();


}
