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
import com.badlogic.gdx.math.Vector3;
import com.finalgame.game.FinalGame;
import com.finalgame.game.KoopaBoi;

/**
 *
 * @author mike
 */
public class EndState extends State {

    private Texture GBG;
    private Texture Cloud;
    
    private int HighScore;
    private float Score;
    private BitmapFont Font;
    private KoopaBoi koopa;
    private Texture ground;
    private Texture Back;

    public EndState(StateManager gsm, float Score, float koopaX) {        
        super(gsm);
        this.Score = Score;
        GBG = new Texture("Background.jpg");
        ground = new Texture("ground.jpg");
        Back = new Texture ("back.jpg");
        koopa = new KoopaBoi((int) koopaX, FinalGame.LENGTH / 2);
        //Score = PlayState.getScore();
        setCameraView(FinalGame.WIDTH /2 , FinalGame.LENGTH /2 );
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
        batch.draw(ground, 0, 0, getViewWidth(), 100);
        batch.draw(Back, 250 - (Back.getWidth())/2, 30);
        // draw the koopa
        koopa.endRender(batch);
        Font.draw(batch, "Score: " + (int) Score, 12, koopa.getY() + 200);
        
        batch.end();
    }

    @Override
    public void update(float deltaTime) {
        koopa.endUpdate(deltaTime);
    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {
            //get mouse click/touch position
            Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            //Convert that point to game coords
            unproject(touch);
            //check if button is pressed
            float BackButtonX = Back.getWidth();
            float BackButtonY = Back.getHeight();
            if (touch.x > BackButtonX && touch.x < BackButtonX + Back.getWidth()
                    && touch.y > BackButtonY && touch.y < BackButtonY + Back.getHeight()) {
                StateManager GSM = getStateManager();
                GSM.push(new MenuState(GSM));
            }
        }
    }

    @Override
    public void dispose() {
        Cloud.dispose();
        Back.dispose();
    }

}
