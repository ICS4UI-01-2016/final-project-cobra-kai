/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
    package States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.finalgame.game.KoopaBoi;
import com.finalgame.game.FinalGame;
import com.finalgame.game.Clouds;
import java.awt.Font;

/**
 *
 * @author lamon
 */
public class PlayState extends State {

    private KoopaBoi Koopa;
    private Clouds[] Clouds;
    private Texture bg;
    private int Score;
    private BitmapFont font;
    private final float CAM_X_OFFSET = 30;
    private final float PIPE_GAP_AMOUNT = 4;

    public PlayState(StateManager sm) {
        super(sm);
        //Replace Flappybird w/ FinalGame - to match our states
        setCameraView(FinalGame.WIDTH / 2, FinalGame.HEIGHT / 2);
        //setCameraPosition(FlappyBird.WIDTH/2, FlappyBird.HEIGHT/2);
        bird = new Bird(50, 200);
        bg = new Texture("bg.png");
        moveCameraX(bird.getX() + CAM_X_OFFSET);

        pipes = new Pipe[3];
        for (int i = 0; i < pipes.length; i++) {
            pipes[i] = new Pipe(200 + PIPE_GAP_AMOUNT * Pipe.WIDTH * i);
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
        bird.render(batch);
        //draw pipes
        for (int i = 0; i < pipes.length; i++) {
            pipes[i].render(batch);
        }
        // end the stuff to draw
        batch.end();
    }

    @Override
    public void update(float deltaTime) {
        // update any game models
        bird.update(deltaTime);

        moveCameraX(bird.getX() + CAM_X_OFFSET);



        //did the bird hit the pipe?
        for (int i = 0; i < pipes.length; i++) {
            if (pipes[i].collides(bird)) {
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
            } else if (!pipes[i].hasPassed()
                    && bird.getX() > pipes[i].getX() + Pipe.WIDTH) {
                Score++;
                pipes[i].pass();
            }

        }
        //adjust the pipes
        for (int i = 0; i < pipes.length; i++) {
            //HAS THE BIRD PASSED THE PIPE?
            if (getCameraX() - FlappyBird.WIDTH / 4 > pipes[i].getX() + Pipe.WIDTH) {
                float x = pipes[i].getX() + PIPE_GAP_AMOUNT * Pipe.WIDTH * pipes.length;
                pipes[i].setX(x);
            }
        }
    }

    @Override
    public void handleInput() {
        // handle any player input changes

        if (Gdx.input.justTouched()) {
            bird.jump();
        }
    }

    @Override
    public void dispose() {
        bg.dispose();
        bird.dispose();
        for (int i = 0; i < pipes.length; i++) {
            pipes[i].dispose();
        }
    }
}
