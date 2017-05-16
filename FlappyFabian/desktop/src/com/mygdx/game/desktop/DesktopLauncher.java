package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.FlappyFabian;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = FlappyFabian.WIDTH;
		config.height = FlappyFabian.HEIGHT;
		config.title = FlappyFabian.TITLE;
		new LwjglApplication(new FlappyFabian(), config);
	}
}
