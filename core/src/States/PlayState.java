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
 * @author coulh9904
 */
public class PlayState extends State {

    private KoopaBoi koopa;
    private Texture GBG;
    private Texture BG;
    private Texture ground;
    private Clouds[] clouds;
    private float SuperJump = 1;
    private float ScoreTrack = 0;
    private float y;
    private float x;
    private int Score;
    private float Pause = 100;
    private BitmapFont Font;
    private boolean right = false;
    private boolean left = false;
    public static final int DPAD_RIGHT = Input.Keys.RIGHT;
    public static final int DPAD_LEFT = Input.Keys.LEFT;
    public static final int DPAD_UP = Input.Keys.UP;

    public PlayState(StateManager stm) {
        super(stm);
        // set the camera view
        setCameraView(FinalGame.WIDTH / 2, FinalGame.LENGTH / 2);
        // create a new koopa in the center of the screen
        koopa = new KoopaBoi(FinalGame.WIDTH / 4, FinalGame.LENGTH / 4);

        BG = new Texture("bg.jpg");
        GBG = new Texture("Background.jpg");
        // have the camera follow the koopa's y position
        moveCameraY(koopa.getY());
        //If you change the size of the clouds array, you must also scale the exception where the clouds are generated in Clouds.java
        clouds = new Clouds[11];
        clouds[10] = new Clouds(10);
        for (int i = 0; i < clouds.length - 1; i++) {
            clouds[i] = new Clouds(i);
        }
        // set the score at 0
        Score = 0;
        Font = new BitmapFont();

    }

    @Override
    public void render(SpriteBatch batch) {
        batch.setProjectionMatrix(getCombinedCamera());
        // begin drawing
        batch.begin();
        // draw the back ground so it moves with the koopa
        batch.draw(GBG, 0, koopa.getY() - 212, getViewWidth(), getViewHeight());
        // draw the koopa
        koopa.render(batch);
        //
        for (int i = 0; i < clouds.length; i++) {
            if (clouds[i].isBroken() == false) {
                clouds[i].render(batch);
            } else {
                clouds[i].brokenRender(batch);
            }
        }
        Font.draw(batch, "Score: " + Score, 12, koopa.getY() + 200);
        Font.draw(batch, "SuperJumps: " + SuperJump, 12, koopa.getY() + 180);
        Font.draw(batch, "Press P to pause.", getViewWidth() - 121, koopa.getY() + 200);
        batch.end();
    }

    @Override
    public void update(float deltaTime) {
        //Pause here will activte/deactivate as P is pressed. 
        if (Pause == 100) {
            //This little if statement gives you one SuperJump power - up every 1000 y coords.
            if (Score >= ScoreTrack + 2000) {
                ScoreTrack = ScoreTrack + 2000;
                SuperJump = SuperJump + 1;
            }
            //The start cloud should 'despawn' (it just moves it far away so the player can't land on it)
            if (koopa.getY() - 400 >= clouds[clouds.length - 1].getY()) {
                clouds[clouds.length - 1].setPos(1100, -1100);
            }
            koopa.update(deltaTime);
            moveCameraY(koopa.getY());
            for (int i = 0; i < clouds.length; i++) {
                clouds[i].update(deltaTime);

            }

            if (koopa.getY() <= Score - 1000 || koopa.getY() <= 0) {
                
                // end the game
                
                // pop off the game screen to go to menu
                StateManager GSM = getStateManager();
                GSM.push(new EndState(GSM));

            }
            // the score is the highest y pos the koopa reaches
            if (koopa.getY() >= Score) {
                Score = (int) koopa.getY();
            }

            for (int i = 0; i < clouds.length; i++) {
                if (clouds[i].collides(koopa)) {
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
                clouds[i].sideMotion(clouds[i].getX());
            }
            for (int i = 0; i < clouds.length - 1; i++) {
                if (clouds[i].getY() <= koopa.getY() - 400) {
                    clouds[i].setBroken(false);
                    float cloudWidth = clouds[0].getWidth();
                    if (i == 0) {
                        float getArrayX = clouds[clouds.length - 2].getX();
                        float getArrayY = clouds[9].getY();

                        y = (int) (Math.random() * ((getArrayY + 140) - (getArrayY + 100) + 1) + (getArrayY + 100));
                        x = -50;
                        while (10.0 > x || x > 400.0) {
                            int LR = (int) (Math.random() * (2 - 1 + 1) + 1);
                            if (LR == 1) {
                                x = (int) (Math.random() * ((getArrayX + (200 + cloudWidth)) - (getArrayX + (100 + cloudWidth)) + 1) + (getArrayX + (100 + cloudWidth)));
                            }
                            if (LR == 2) {
                                x = (int) (Math.random() * ((getArrayX - 100) - (getArrayX - 200) + 1) + (getArrayX - 200));
                            }
                        }

                    } else {
                        float getArrayX = clouds[i - 1].getX();
                        float getArrayY = clouds[i - 1].getY();
                        y = (int) (Math.random() * ((getArrayY + 140) - (getArrayY + 100) + 1) + (getArrayY + 100));

                        x = -50;

                        while (10.0 > x || x > 400.0) {
                            int LR = (int) (Math.random() * (2 - 1 + 1) + 1);
                            if (LR == 1) {
                                x = (int) (Math.random() * ((getArrayX + (200 + cloudWidth)) - (getArrayX + (100 + cloudWidth)) + 1) + (getArrayX + (100 + cloudWidth)));
                            }
                            if (LR == 2) {
                                x = (int) (Math.random() * ((getArrayX - 100) - (getArrayX - 200) + 1) + (getArrayX - 200));
                            }
                        }
                    }
                    float Motion = (int) (Math.random() * (5 - 1 + 1) + 1);
                    float RandBroken = (int) (Math.random() * (4 - 1 + 1) + 1);
                    if (RandBroken == 1) {
                        clouds[i].setBroken(true);
                    }
                    clouds[i].setMotion(Motion);
                    clouds[i].setPos(x, y);
                }
            }

            for (int i = 0; i < clouds.length; i++) {
                if (clouds[i].getY() <= koopa.getY()) {
                    if (koopa.getYVelocity() <= 0) {
                        if (clouds[i].collides(koopa)) {
                            koopa.jump();
                            if (clouds[i].isBroken() == true) {
                                clouds[i].setPos(12000, clouds[i].getY());
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) == true) {
            if (SuperJump > 0) {
                koopa.superJump();
                SuperJump = SuperJump - 1;
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) == true) {
            koopa.moveRight();
        } else {
            koopa.standStill();
        }

        if (Gdx.input.isKeyPressed(DPAD_LEFT) == true) {

            koopa.moveLeft();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.P) == true) {
            Pause = Pause * -1;
        }
    }
    public int getScore(){
        return Score;
    }

    @Override
    public void dispose() {
        koopa.dispose();
        for (int i = 0; i < clouds.length; i++) {
            clouds[i].dispose();
        }
    }

}
