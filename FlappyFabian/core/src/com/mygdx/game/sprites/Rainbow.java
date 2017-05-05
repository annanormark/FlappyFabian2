package com.mygdx.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * The class that handles the rainbow obstacles
 */

public class Rainbow {
    private static final int FLUCTUATION = 130;
    private static final int OBSTACLE_GAP = 100;
    private static final int LOWEST_OPENING = 120;
    public static final int OBSTACLE_WIDTH = 52;

    private Sound pling;
    private Texture topRainbow, bottomRainbow;
    private Vector2 posBottomRainbow, posTopRainbow;
    private Rectangle boundsTop, boundsBot, passingLine;
    private Random rand;
    private boolean point;

    // creates the obstacles and what should happen when passing
    public Rainbow(float x){
        topRainbow = new Texture("hinder.png");
        bottomRainbow = new Texture("hinder.png");
        rand = new Random();

        pling = Gdx.audio.newSound(Gdx.files.internal("point.ogg"));

        posTopRainbow = new Vector2(x, rand.nextInt(FLUCTUATION) + OBSTACLE_GAP + LOWEST_OPENING);
        posBottomRainbow = new Vector2(x, posTopRainbow.y - OBSTACLE_GAP - bottomRainbow.getHeight());

        boundsTop = new Rectangle(posTopRainbow.x + 5, posTopRainbow.y + 5, topRainbow.getWidth() - 10, topRainbow.getHeight() - 10);
        boundsBot = new Rectangle(posBottomRainbow.x - 5, posBottomRainbow.y - 5, bottomRainbow.getWidth() - 10, bottomRainbow.getHeight() - 10);
        passingLine = new Rectangle(x + topRainbow.getWidth() / 2, 0, 1, bottomRainbow.getHeight() + topRainbow.getHeight());

        point = false;

    }
    //Gets the obstacles
    public Texture getBottomRainbow() { return bottomRainbow; }
    public Texture getTopRainbow() { return topRainbow; }
    public Vector2 getPosBottomRainbow() {
        return posBottomRainbow;
    }
    public Vector2 getPosTopRainbow() {
        return posTopRainbow;
    }

    // moves the obstacle that has been passing to the start again
    public void reposition(float x){
        point = false;
        posTopRainbow.set(x, rand.nextInt(FLUCTUATION) + OBSTACLE_GAP + LOWEST_OPENING);
        posBottomRainbow.set(x, posTopRainbow.y - OBSTACLE_GAP - bottomRainbow.getHeight());
        boundsTop.setPosition(posTopRainbow.x, posTopRainbow.y);
        boundsBot.setPosition(posBottomRainbow.x, posBottomRainbow.y);
        passingLine.setPosition(x + topRainbow.getWidth() / 2, 0);

    }

    // When the player hits a rainbow
    public boolean collides(Rectangle player){
        return player.overlaps(boundsTop) || player.overlaps(boundsBot);
    }

    // When the player goes through a passing without hitting
    public boolean passing(Rectangle player){
        if(!point && player.overlaps(passingLine)) {
            pling.play(0.5f);
            point = true;
            return true;
        }
        return false;
    }

    // Called in the end inorder to not create memory leaks
    public void dispose(){
        bottomRainbow.dispose();
        topRainbow.dispose();
        pling.dispose();
    }
}
