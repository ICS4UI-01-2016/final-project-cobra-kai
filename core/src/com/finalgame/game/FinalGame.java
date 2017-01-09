package com.finalgame.game;

import States.StateManager;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FinalGame extends ApplicationAdapter {
        public static final int WIDTH = 480;
        public static final int LENGTH = 800;
        
        private SpriteBatch batch;
        private StateManager stateManager;
        
	// Initial setup
	@Override
	public void create () {
		batch = new SpriteBatch();
	}

        // Game loop
	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.end();
	}
	
        // End section
	@Override
	public void dispose () {
		batch.dispose();
	}
}
