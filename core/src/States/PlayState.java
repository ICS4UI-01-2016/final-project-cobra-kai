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
    private Clouds[] clouds;
    private float SuperJump = 1;
    private float ScoreTrack = 0;
    private float y;
    private float x;
    private float getArrayX;
    private float getArrayY;
    private int Score;
    //Pause is 100. Pressing P multiplies 100 by -1, which triggers the Pause if statement that encoumpasses update. Prevents update from running until P is pressed again, essentially pausing the game.
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
            if (Score >= ScoreTrack + 4000) {
                ScoreTrack = ScoreTrack + 4000;
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
                GSM.push(new EndState(GSM, Score, koopa.getX()));

            }
            // the score is the highest y pos the koopa reaches
            if (koopa.getY() >= Score) {
                Score = (int) koopa.getY();
            }

            for (int i = 0; i < clouds.length; i++) {
                if (clouds[i].collides(koopa)) {
                    //Find current highscore
                    Preferences pref = Gdx.app.getPreferences("HighScore");
                    int HighScore = pref.getInteger("HighScore", 0);
                    //Is the current score higher?
                    if (Score > HighScore) {
                        pref.putInteger("HighScore", Score);
                        //Save the new highscore.
                        pref.flush();
                        
                    }
                } else if (!clouds[i].hasPassed()
                        && koopa.getX() > clouds[i].getY() + Clouds.WIDTH) {
                    clouds[i].pass();
                }
                //This little line implements the side to side motion of the clouds.
                clouds[i].sideMotion(clouds[i].getX());
            }
            //This is a massive loop that resets the X and Y values of clouds once they are at a y value of 400 below the player(koopa).
            for (int i = 0; i < clouds.length - 1; i++) {
                if (clouds[i].getY() <= koopa.getY() - 400) {
                    //All clouds are the same width, so this just gathers and saves that width value.
                    float cloudWidth = clouds[0].getWidth();
                    //Resets all clouds to normal (not broken).
                    clouds[i].setBroken(false);
                    //Becuase there is no i - 1 value for the first spot in the array, it has an exception here that replaces i - 1 with the second last place in the array 
                    //RECALL: The last spot in the array is the cloud that always generates under the koopa at the start.
                    if (i == 0) {
                        getArrayX = clouds[clouds.length - 2].getX();
                        getArrayY = clouds[clouds.length - 2].getY();
                    } else {
                        getArrayX = clouds[i - 1].getX();
                        getArrayY = clouds[i - 1].getY();
                    }
                    //Randomly generates a Y value between 100 and 140 above the cloud below it.
                    y = (int) (Math.random() * ((getArrayY + 140) - (getArrayY + 100) + 1) + (getArrayY + 100));
                    //In short, this makes is so that if two clouds spawn with the same speed, they won't ever be directly on top of each other.
                    //x = -50 makes the X value outside the while loop's criteria, ensuring it runs.
                    x = -50;
                    //The clouds must continue to generate on the screen, so contitions of this while loop keep it running until an X value that matches all perameters is on the screen.
                    while (10.0 > x || x > 400.0) {
                        //Randomly generates a 1 or a 2, which baseically decides whether the cloud spawns to the left or right.
                        int LR = (int) (Math.random() * (2 - 1 + 1) + 1);
                        if (LR == 1) {
                            //It generates X values between 100 and 200 to EITHER the left or right (depending on if LR is 1 or 2) of the cloud below it, so that no clouds spawn directly above another.
                            x = (int) (Math.random() * ((getArrayX + (200 + cloudWidth)) - (getArrayX + (100 + cloudWidth)) + 1) + (getArrayX + (100 + cloudWidth)));
                        }
                        if (LR == 2) {
                            x = (int) (Math.random() * ((getArrayX - 100) - (getArrayX - 200) + 1) + (getArrayX - 200));
                        }
                    }

                    //Motion is a random value between 1 and 5, which represents the side to side speed of each cloud.
                    float Motion = (int) (Math.random() * (5 - 1 + 1) + 1);
                    //RandBroken is the probability of a 'broken' cloud spawning amoung the others.
                    float RandBroken = (int) (Math.random() * (4 - 1 + 1) + 1);
                    //If rand it 1, a broken cloud is generated. This makes it a 1 in 4 chance.
                    if (RandBroken == 1) {
                        clouds[i].setBroken(true);
                    }
                    //Save the randomly generated motion.
                    clouds[i].setMotion(Motion);
                    //Set the new X and Y position of the clouds.
                    clouds[i].setPos(x, y);
                }
            }
            //This for loop detects when the koopa is FALLING down onto a cloud, and if it is it will automatically propell it's jump.

            for (int i = 0; i < clouds.length; i++) {
                if (clouds[i].getY() <= koopa.getY()) {
                    //This specific line makes it so that the koopa isn't simply propelled when it touches a cloud, it has to fall back onto the cloud.
                    if (koopa.getYVelocity() <= 0) {
                        if (clouds[i].collides(koopa)) {
                            koopa.jump();
                            //If the cloud is a 'broken' cloud, this removes it from the screen so it can't be jumped on again, but doens't change the Y value so it's still in sequence with other clouds.
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
        //Pressing the P key is what tirggers the if statement directly at the beginning of update (it encompasses all of update w/ the condition of Pause = -100, pPause is 100 be default), which will stop update from running until P is pressed again.
        if (Gdx.input.isKeyJustPressed(Input.Keys.P) == true) {
            Pause = Pause * -1;
        }
    }

    public int getScore() {
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
