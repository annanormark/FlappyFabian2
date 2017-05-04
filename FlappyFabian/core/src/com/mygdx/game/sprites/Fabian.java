package com.mygdx.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
    private Animation birdAnimation;
    private Texture texture;
    private Sound flap;

    /***
     * TODO: add the animation, part 13 of videos
     */

    public Fabian(int x, int y){
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);
        bird = new Texture("fabian.gif");
        texture = new Texture("flappar.png");
        birdAnimation = new Animation(new TextureRegion(texture), 2, 0.5f);
        bounds = new Rectangle(x, y, texture.getWidth() / 2 - 6, texture.getHeight() - 5);
        flap = Gdx.audio.newSound(Gdx.files.internal("horse.ogg"));
    }

    public Vector3 getPosition() {
        return position;
    }

    public Texture getBird() {
        return bird;
    }

    public void update(float dt){
        birdAnimation.update(dt);
        if(position.y > 0)
            velocity.add(0, GRAVITY, 0);
        velocity.scl(dt);
        position.add(MOVEMENT * dt, velocity.y, 0);
        if(position.y < 0)
            position.y = 0;
        velocity.scl(1/dt);
        bounds.setPosition(position.x, position.y);
    }

    public TextureRegion getTexture(){
        return birdAnimation.getFrame();
    }

    public void jump(){
        velocity.y = 250;
        flap.play(0.5f);
    }

    public Rectangle getBounds(){
        return bounds;
    }

    public void dispose(){
        texture.dispose();
        flap.dispose();
    }
}
