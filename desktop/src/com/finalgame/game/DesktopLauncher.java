package com.finalgame.game;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
                config.width = FinalGame.WIDTH;
                 config.height = FinalGame.LENGTH;
		new LwjglApplication(new FinalGame(), config);
	}
}
