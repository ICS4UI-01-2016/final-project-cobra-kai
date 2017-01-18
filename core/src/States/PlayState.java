/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
    package States;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
    
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.finalgame.game.Clouds;
import com.finalgame.game.FinalGame;
import com.finalgame.game.KoopaBoi;

/**
 *
 * @author lamon
 */
public class PlayState extends State {

    private KoopaBoi koopa;
    private Texture bg;
    private Clouds[] clouds;
    private int Score;
    private BitmapFont font;
    

    public PlayState(StateManager stm) {
        super(stm);
        setCameraView(FinalGame.WIDTH / 2, FinalGame.LENGTH / 2);
        koopa = new KoopaBoi(FinalGame.WIDTH / 4, FinalGame.LENGTH / 4);
        moveCameraY(koopa.getY());
        
        clouds = new Clouds[3];
        for (int i = 0; i < clouds.length; i++) {
            clouds[i] = new Clouds(200 + (1 + 1 + 1) * Clouds.WIDTH * i);
        }
        Score = 0;
        font = new BitmapFont();

    }

    @Override
    public void render(SpriteBatch batch) {
        batch.setProjectionMatrix(getCombinedCamera());
        batch.begin();
        koopa.render(batch);
        batch.end();
    }

    @Override
    public void update(float deltaTime) {
        koopa.update(deltaTime);
        if (koopa.getY() <= 0) {
            // end the game
            StateManager gsm = getStateManager();
            // pop off the game screen to go to menu
            
        }
    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {
            koopa.jump();
        }
    }

    @Override
    public void dispose() {
        koopa.dispose();
    }
}


