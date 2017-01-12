/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.finalgame.game.KoopaBoi;

/**
 *
 * @author sevcm7279
 */
public class PlayState extends State {

    private KoopaBoi koopa;
    private Texture bg;

    public PlayState(StateManager stm) {
        super(stm);
        koopa = new KoopaBoi(50, 0);
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
    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {
            koopa.jump();
        }
    }

    @Override
    public void dispose() {
        koopa.dispose();
    }
}
