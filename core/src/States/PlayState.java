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
    private Texture BG;
    private Clouds[] clouds;
    private float y;
    private float x;
    private int Score;
    private BitmapFont Font;
    private boolean right = false;
    private boolean left = false;
    public static final int DPAD_RIGHT = Input.Keys.RIGHT;
    public static final int DPAD_LEFT = Input.Keys.LEFT;
    public static final int DPAD_UP = Input.Keys.UP;

    public PlayState(StateManager stm) {
        super(stm);
        setCameraView(FinalGame.WIDTH / 2, FinalGame.LENGTH / 2);
        koopa = new KoopaBoi(FinalGame.WIDTH / 4, FinalGame.LENGTH / 4);
        BG = new Texture ("bg.jpg");
        moveCameraY(koopa.getY());
        //If you change the size of the clouds array, you must also scale the exception where the clouds are generated in Clouds.java
        clouds = new Clouds[11];
        clouds[10] = new Clouds(10);
        for (int i = 0; i < clouds.length - 1; i++) {
            clouds[i] = new Clouds(i);
        }
        Score = 0;
        Font = new BitmapFont();

    }

    @Override
    public void render(SpriteBatch batch) {
        batch.setProjectionMatrix(getCombinedCamera());
        batch.begin();
        batch.draw(BG, 0, 0, getViewWidth(), getViewHeight());
        koopa.render(batch);
        Font.draw(batch, "Score: " + Score, 12, koopa.getY() + 120);
        for (int i = 0; i < clouds.length; i++) {
            clouds[i].render(batch);
        }
        batch.end();
    }

    @Override
    public void update(float deltaTime) {
        
        koopa.update(deltaTime);
        moveCameraY(koopa.getY());
        for (int i = 0; i < clouds.length; i++) {
            clouds[i].update();
        }
        if (koopa.getY() <= 0) {
            // end the game
            StateManager gsm = getStateManager();
            
            // pop off the game screen to go to menu
            gsm.pop();

        }
        

        //did the bird hit the pipe?
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
        }
        for (int i = 0; i < clouds.length - 1; i++) {
            if (clouds[i].getY() <= koopa.getY() - 300) {
                if (i == 0) {
                    y = (int) (Math.random() * ((clouds[9].getY() + 140) - (clouds[9].getY() + 100) + 1) + (clouds[9].getY() + 100));
                    x = -50;
                    while (10.0 > x || x > 400.0) {
                    int LR = (int) (Math.random() * (2 - 1 + 1) + 1);
                        if(LR == 1){
                        x = (int) (Math.random() * ((clouds[clouds.length - 2].getX() + 160) - (clouds[clouds.length - 2].getX() + 120) + 1) + (clouds[9].getX() + 120));
                        }
                        if(LR == 2){
                        x = (int) (Math.random() * ((clouds[clouds.length - 2].getX() - 120) - (clouds[clouds.length - 2].getX() - 160) + 1) + (clouds[9].getX() - 160));
                        }
                    }
                    System.out.println("SX " + i + " " + x + " 9: " + (clouds[clouds.length - 2]));
                    System.out.println("SY " + i  + " " + y);
                } else {

                    y = (int) (Math.random() * ((clouds[i - 1].getY() + 140) - (clouds[i - 1].getY() + 100) + 1) + (clouds[i - 1].getY() + 100));
                    System.out.println("Y " + y);
                    x = -50;
                    while (10.0 > x || x > 400.0) {
                        int LR = (int) (Math.random() * (2 - 1 + 1) + 1);
                        if(LR == 1){
                        x = (int) (Math.random() * ((clouds[i - 1].getX() + 160) - (clouds[i - 1].getX() + 120) + 1) + (clouds[i - 1].getX() + 120));
                        }
                        if(LR == 2){
                        x = (int) (Math.random() * ((clouds[i - 1].getX() - 120) - (clouds[i - 1].getX() - 160) + 1) + (clouds[i - 1].getX() - 160));
                        }
                        
                        System.out.println("X " + i + " " + x + " " + LR + " " + clouds[i - 1].getX());
                    }
                }
                
                clouds[i].setPos(x, y);
            }
        }

        for (int i = 0; i < clouds.length; i++) {
            if (clouds[i].getY() <= koopa.getY()) {
                if (clouds[i].collides(koopa)) {
                    koopa.jump();
                    //end the game
                    //StateManager GSM = getStateManager();
                    //pop off 
                    //GSM.pop();
                }
            }
        }     

    }

    @Override
    public void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) == true) {
            koopa.jump();

        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) == true) {

            koopa.moveRight();
        } else {
            koopa.standStill();
        }

        if (Gdx.input.isKeyPressed(DPAD_LEFT) == true) {

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
