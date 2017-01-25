/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package States;

import static States.PlayState.DPAD_LEFT;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.finalgame.game.FinalGame;
import com.finalgame.game.KoopaBoi;

/**
 *
 * @author coulh9904
 */
public class InfoState extends State {

    private Texture GBG;
    private Texture GeneralInfo;
    private Texture SuperJumpInfo;
    private Texture UnstableCIns;
    private Texture BrokenCloud;
    private Texture StableCIns;
    private Texture Cloud;
    private Texture CharacterInfo;
    private Texture Koopa;
    private Texture Back;
    private Texture Start;
    private KoopaBoi koopa;
    private float KoopaStart;

    public InfoState(StateManager ifs) {
        super(ifs);
        GBG = new Texture("Background.jpg");
        GeneralInfo = new Texture("GeneralInfo.jpg");
        SuperJumpInfo = new Texture("SuperJumpInfo.jpg");
        UnstableCIns = new Texture("UnstableClouds.jpg");
        BrokenCloud = new Texture("BrokenCloudBox.png");
        StableCIns = new Texture("StableClouds.jpg");
        Cloud = new Texture("CloudBox.png");
        CharacterInfo = new Texture("CharacterInfo.jpg");
        Koopa = new Texture("start.png");
        Back = new Texture("Back.jpg");
        Start = new Texture("StartGame.jpg");

        setCameraView(FinalGame.WIDTH, FinalGame.LENGTH);
        setCameraPosition(getViewWidth() / 2, getViewHeight() / 2);
        koopa = new KoopaBoi(715, 365);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.setProjectionMatrix(getCombinedCamera());
        batch.begin();
        batch.draw(GBG, 0, 0, getViewWidth(), getViewHeight());
        batch.draw(StableCIns, getViewWidth() / 4 - StableCIns.getWidth() / 2, getViewHeight() - 200);
        batch.draw(Cloud, getViewWidth() / 2 - Cloud.getWidth() + StableCIns.getWidth() / 2 + 100, (getViewHeight() - 200) + ((StableCIns.getHeight() - Cloud.getHeight()) / 2));
        batch.draw(UnstableCIns, getViewWidth() / 4 - UnstableCIns.getWidth() / 2, getViewHeight() - 350);
        batch.draw(BrokenCloud, getViewWidth() / 2 - BrokenCloud.getWidth() + UnstableCIns.getWidth() / 2 + 100, (getViewHeight() - 350) + ((UnstableCIns.getHeight() - BrokenCloud.getHeight()) / 2));
        batch.draw(CharacterInfo, getViewWidth() / 4 - CharacterInfo.getWidth() / 2, getViewHeight() - 500);
        koopa.render(batch);
        batch.draw(SuperJumpInfo, getViewWidth() / 2 - (SuperJumpInfo.getWidth() / 2), getViewHeight() - 650);
        batch.draw(GeneralInfo, getViewWidth() / 2 - (GeneralInfo.getWidth() / 2), getViewHeight() - 800);
        batch.draw(Back, getViewWidth() / 12 - 30, getViewHeight() - 800);
        batch.draw(Start, getViewWidth() - (getViewWidth() / 12) - (Start.getWidth() / 2) - 50, getViewHeight() - 800);
        batch.end();
    }

    @Override
    public void update(float deltaTime) {
        KoopaStart = 710;
        koopa.InfoStateUpdate(deltaTime, KoopaStart);

    }

    @Override
    public void handleInput() {

        if (Gdx.input.justTouched()) {
            //get mouse click/touch position
            Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            //Convert that point to game coords
            unproject(touch);
            //check if button is pressed
            float BackButtonX = getViewWidth() / 12 - 50;
            float BackButtonY = getViewHeight() - 800;
            if (touch.x > BackButtonX && touch.x < BackButtonX + Back.getWidth()
                    && touch.y > BackButtonY && touch.y < BackButtonY + Back.getHeight()) {
                StateManager GSM = getStateManager();
                GSM.pop();
            }       
            float StartButtonX = getViewWidth() - (getViewWidth() / 12) - (Start.getWidth() / 2) - 50;
            float StartButtonY = getViewHeight() - 800;
            if (touch.x > StartButtonX && touch.x < StartButtonX + Back.getWidth()
                    && touch.y > StartButtonY && touch.y < StartButtonY + Back.getHeight()) {
                StateManager GSM = getStateManager();
                GSM.push(new PlayState(GSM));
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
    }

    @Override
    public void dispose() {
        UnstableCIns.dispose();
        BrokenCloud.dispose();
        StableCIns.dispose();
        Cloud.dispose();
        Back.dispose();
    }
}
