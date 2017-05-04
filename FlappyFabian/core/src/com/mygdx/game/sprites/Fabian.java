package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import java.io.Console;

/**
 * Created by Anna on 2017-05-02.
 */

public class Fabian {
    private static final int GRAVITY = -15;
    private static final int MOVEMENT = 100;
    private Vector3 position;
    private Vector3 velocity;
    private Texture bird;
    private Rectangle bounds;
    private Animation birdAni;

    /***
     * TODO: add the animation, part 13 of videos
     */

    public Fabian(int x, int y){
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);
        bird = new Texture("fabian.gif");
        bounds = new Rectangle(x, y, bird.getWidth() - 6, bird.getHeight() - 5);
    }

    public Vector3 getPosition() {
        return position;
    }

    public Texture getBird() {
        return bird;
    }

    public void update(float dt){
        if(position.y > 0)
            velocity.add(0, GRAVITY, 0);
        velocity.scl(dt);
        position.add(MOVEMENT * dt, velocity.y, 0);
        if(position.y < 0)
            position.y = 0;
        velocity.scl(1/dt);
        bounds.setPosition(position.x, position.y);
    }

    public Texture getTexture(){
        return bird;
    }

    public void jump(){
        velocity.y = 250;
    }

    public Rectangle getBounds(){
        return bounds;
    }

    public void dispose(){
        bird.dispose();
    }
}
