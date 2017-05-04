package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.States.GameStateManager;
import com.mygdx.game.States.MenuState;
import java.lang.String;

public class FlappyFabian extends ApplicationAdapter {
	public static final int WIDTH = 480;
	public static final int HEIGHT = 800;
	public static final String TITLE = "FlappyFabian";

	private GameStateManager gam;
	private SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		gam = new GameStateManager();
		Gdx.gl.glClearColor(1, 0, 0, 1);
		gam.push(new MenuState(gam));

	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gam.update(Gdx.graphics.getDeltaTime());
		gam.render(batch);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
