/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
    package States;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.finalgame.game.FinalGame;
    
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.finalgame.game.FinalGame;
import com.finalgame.game.KoopaBoi;

/**
 *
 * @author lamon
 */
public class PlayState extends State {
    
    

    private KoopaBoi koopa;
    private Texture bg;
    
    private boolean right = false;
    private boolean left = false;
    
    public static final int DPAD_RIGHT = Input.Keys.RIGHT;
    public static final int DPAD_LEFT = Input.Keys.LEFT;
    public static final int DPAD_UP = Input.Keys.UP;
    public PlayState(StateManager stm) {
        super(stm);
        setCameraView(FinalGame.WIDTH / 2, FinalGame.LENGTH / 2);
        koopa = new KoopaBoi(FinalGame.WIDTH / 4, FinalGame.LENGTH / 4);
        moveCameraY(koopa.getY());
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
        
        if(koopa.getX() >= 1000){
            koopa.setX(0);
        }
    }

    @Override
    public void handleInput() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP) == true) {
            koopa.jump();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) == true){
           
            koopa.moveRight();    
        }


        if(Gdx.input.isKeyPressed(DPAD_LEFT) == true){
          
            koopa.moveLeft();
        
          
        }
    }

    @Override
    public void dispose() {
        koopa.dispose();
    }

    
}


