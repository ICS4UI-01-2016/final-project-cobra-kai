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
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.finalgame.game.Clouds;
import com.finalgame.game.KoopaBoi;

/**
 *
 * @author lamon
 */

public class PlayState extends State {

    private KoopaBoi Koopa;
    private Clouds[] Cloud;
    private Texture bg;
    private int Score;
    private BitmapFont font;
    private final float CAM_X_OFFSET = 30;
    private final float PIPE_GAP_AMOUNT = 4;

    public PlayState(StateManager sm) {
        super(sm);
        //Replace Flappybird w/ FinalGame - to match our states
        setCameraView(FinalGame.WIDTH / 2, FinalGame.LENGTH / 2);
        //setCameraPosition(FlappyBird.WIDTH/2, FlappyBird.HEIGHT/2);
        Koopa = new KoopaBoi(50, 200);
        bg = new Texture("bg.png");
        moveCameraX(Koopa.getX() + CAM_X_OFFSET);

        Cloud = new Clouds[3];
        for (int i = 0; i < Cloud.length; i++) {
            Cloud[i] = new Clouds(200 + PIPE_GAP_AMOUNT * Clouds.WIDTH * i);
        }
        Score = 0;
        font = new BitmapFont();

    }

    @Override
    public void render(SpriteBatch batch) {
        // draw the screen
        // link spritebatch to the camera
        batch.setProjectionMatrix(getCombinedCamera());
        // beginning of stuff to draw
        batch.begin();
        // draw the background
        batch.draw(bg, getCameraX() - getViewWidth() / 2, getCameraY() - getViewHeight() / 2);
        font.draw(batch, " " + Score, getCameraX(), getCameraY() + 190);
        // draw the bird
        Koopa.render(batch);
        //draw pipes
        for (int i = 0; i < Cloud.length; i++) {
            Cloud[i].render(batch);
        }
        // end the stuff to draw
        batch.end();
    }

    @Override
    public void update(float deltaTime) {
        // update any game models
        Koopa.update(deltaTime);

        moveCameraX(Koopa.getX() + CAM_X_OFFSET);



        //did the bird hit the pipe?
        for (int i = 0; i < Cloud.length; i++) {
            if (Cloud[i].collides(Koopa)) {
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
            } else if (!Cloud[i].hasPassed()
                    && Koopa.getX() > Cloud[i].getX() + Clouds.WIDTH) {
                Score++;
                Cloud[i].pass();
            }

        }
        //adjust the pipes
        for (int i = 0; i < Cloud.length; i++) {
            //HAS THE BIRD PASSED THE PIPE?
            if (getCameraX() - FinalGame.WIDTH / 4 > Cloud[i].getX() + Clouds.WIDTH) {
                float x = Cloud[i].getX() + PIPE_GAP_AMOUNT * Clouds.WIDTH * Cloud.length;
                Cloud[i].setX(x);
            }
        }
    }

    @Override
    public void handleInput() {
        // handle any player input changes

        if (Gdx.input.justTouched()) {
            Koopa.jump();
        }
    }

    @Override
    public void dispose() {
        bg.dispose();
        Koopa.dispose();
        for (int i = 0; i < Cloud.length; i++) {
            Cloud[i].dispose();
        }
    }
}


