/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package States;

import com.badlogic.gdx.graphics.g2d.BitmapFont;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
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

        clouds = new Clouds[6];
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

        for (int i = 0; i < clouds.length; i++) {
            clouds[i].render(batch);
        }
        batch.end();
    }

    @Override
    public void update(float deltaTime) {
        koopa.update(deltaTime);
        for (int i = 0; i < clouds.length; i++) {
            clouds[i].update();
        }
        if (koopa.getY() <= -12) {
            // end the game
            StateManager gsm = getStateManager();
            // pop off the game screen to go to menu

        }


        //did the bird hit the pipe?
        for (int i = 0; i < clouds.length; i++) {
            if (clouds[i].collides(koopa)) {
                //end the game
                StateManager GSM = getStateManager();
                //pop off 
                GSM.pop();
                //Get current highscore
                Preferences Pref = Gdx.app.getPreferences("HighScore");
                int HighScore = Pref.getInteger("HighScore", 0);
                //did they beat it?
                if (Score > HighScore) {
                    Pref.putInteger("HighScore", Score);
                    //save it
                    Pref.flush();
                }
            } else if (!clouds[i].hasPassed()
                    && koopa.getX() > clouds[i].getY() + Clouds.WIDTH) {
                Score++;
                clouds[i].pass();
            }
        }
        for (int i = 0; i < clouds.length; i++) {
            if(clouds[i].getY() <= -30) {
                float y = (int) (Math.random() * (1800 - 900 + 1) + 900);
                float x = (int) (Math.random() * (900 - 100 + 1) + 30);
                clouds[i].setY(x, y);
            }

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
        for (int i = 0; i < clouds.length; i++) {
            clouds[i].dispose();
        }
    }
}
