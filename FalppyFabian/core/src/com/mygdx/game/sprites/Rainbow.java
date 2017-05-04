package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Created by Anna on 2017-05-02.
 */

public class Rainbow {
    private static final int FLUCTUATION = 130;
    private static final int TUBE_GAP = 100;
    private static final int LOWEST_OPENING = 120;
    public static final int TUBE_WIDTH = 52;

    private Texture topTube, bottomTube;
    private Vector2 posBottomTube, posTopTube;
    private Rectangle boundsTop, boundsBot;
    private Random rand;

    public Rainbow(float x){
        topTube = new Texture("hinder.png");
        bottomTube = new Texture("hinder.png");
        rand = new Random();

        posTopTube = new Vector2(x, rand.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING);
        posBottomTube = new Vector2(x, posTopTube.y - TUBE_GAP - bottomTube.getHeight());

        boundsTop = new Rectangle(posTopTube.x + 5, posTopTube.y + 5, topTube.getWidth() - 10, topTube.getHeight() - 10);
        boundsBot = new Rectangle(posBottomTube.x - 5, posBottomTube.y - 5, bottomTube.getWidth() - 10, bottomTube.getHeight());

    }

    public Texture getBottomTube() {
        return bottomTube;
    }

    public Texture getTopTube() {
        return topTube;
    }

    public Vector2 getPosBottomTube() {
        return posBottomTube;
    }

    public Vector2 getPosTopTube() {
        return posTopTube;
    }

    public void reposition(float x){
        posTopTube.set(x, rand.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING);
        posBottomTube.set(x, posTopTube.y - TUBE_GAP - bottomTube.getHeight());
        boundsTop.setPosition(posTopTube.x, posTopTube.y);
        boundsBot.setPosition(posBottomTube.x, posBottomTube.y);
    }

    public boolean collides(Rectangle player){
        return player.overlaps(boundsTop) || player.overlaps(boundsBot);
    }

    public void dispose(){
        bottomTube.dispose();
        topTube.dispose();
    }
}
