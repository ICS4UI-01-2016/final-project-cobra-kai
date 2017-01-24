/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.finalgame.game.FinalGame;

/**
 *
 * @author coulh9904
 */
public class MenuState extends State {
    
    private Texture GBG;
    private Texture BG;
    private Texture Button;
    private Texture Logo;
    private int HighScore;
    private BitmapFont Font;

    public MenuState(StateManager gsm) {
        super(gsm);
        GBG = new Texture("Background.jpg");
        Button = new Texture("TheStartButton.png");
        Logo = new Texture("KoopaBOIlogo.png");
        setCameraView(FinalGame.WIDTH, FinalGame.LENGTH);
        setCameraPosition(getViewWidth() / 2, getViewHeight() / 2);
        
        Preferences Pref = Gdx.app.getPreferences("HighScore");
        HighScore = Pref.getInteger("HighScore", 0);
        Font = new BitmapFont(); //default Arial 15pt font
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.setProjectionMatrix(getCombinedCamera());
        batch.begin();
        batch.draw(GBG, 0, 0, getViewWidth(), getViewHeight());
        Font.draw(batch, "HighScore: " + HighScore, getViewWidth()/12 - 50, getViewHeight() - 800);
        batch.draw(Button, getViewWidth() / 2 - Button.getWidth() / 2, getViewHeight() / 2 - (Button.getHeight())/2);
        batch.draw(Logo, getViewWidth() / 2 - Logo.getWidth() / 2, getViewHeight() -100 - (Logo.getHeight())/2);
        batch.end();

    }
    public void updateScore(){
        Preferences Pref = Gdx.app.getPreferences("HighScore");
        HighScore = Pref.getInteger("HighScore", 0);
    }

    @Override
    public void update(float deltaTime) {
    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {
            //get mouse click/touch position
            Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            //Convert that point to game coords
            unproject(touch);
            //check if button is pressed
            float buttonX = getViewWidth() / 2 - Button.getWidth() / 2;
            float buttonY = getViewHeight() / 2 - (Button.getHeight())/2;
            if(touch.x > buttonX && touch.x < buttonX + Button.getWidth()
                    && touch.y > buttonY && touch.y < buttonY + Button.getHeight()){
                StateManager GSM = getStateManager();
                GSM.push(new PlayState(GSM));
            }
        }
    }

    @Override
    public void dispose() {
        BG.dispose();
        Button.dispose();
    }
}

