package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.States.GameStateManager;
import com.mygdx.game.States.MenuState;
import java.lang.String;

/**
 * The main file of the entire program
 */

public class FlappyFabian extends ApplicationAdapter {
	public static final int WIDTH = 480;
	public static final int HEIGHT = 800;
	public static final String TITLE = "FlappyFabian";

	private GameStateManager gam;
	private SpriteBatch batch;

	private Music music;

	// on create the game
	@Override
	public void create () {
		batch = new SpriteBatch();
		gam = new GameStateManager();
		music = Gdx.audio.newMusic(Gdx.files.internal("vindljud.mp3"));
		music.setLooping(true);
		music.setVolume(0.8f);
		music.play();
		Gdx.gl.glClearColor(1, 0, 0, 1);
		gam.push(new MenuState(gam));

	}

	// render the game
	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gam.update(Gdx.graphics.getDeltaTime());
		gam.render(batch);
	}

	// dispose is called in the end to make sure we don't create any memory leaks
	@Override
	public void dispose () {
		batch.dispose();
		music.dispose();
	}
}
