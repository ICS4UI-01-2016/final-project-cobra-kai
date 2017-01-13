package com.finalgame.game;

import States.MenuState;
import States.PlayState;
import States.State;
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
    public void create() {
        batch = new SpriteBatch();
        Gdx.gl.glClearColor(0, 1, 0, 0);

        stateManager = new StateManager();
        State firstScreen = new PlayState(stateManager);
        stateManager.push(firstScreen);
    }

    // Game loop
    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // handle input
        stateManager.handleInput();
        // update the game states
        stateManager.update(Gdx.graphics.getDeltaTime());
        // draw the screen
        stateManager.render(batch);
    }

    // End section
    @Override
    public void dispose() {
        batch.dispose();
    }
}
