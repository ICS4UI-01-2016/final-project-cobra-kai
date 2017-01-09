/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package States;

import com.badlogic.gdx.graphics.Texture;

/**
 *
 * @author sevcm7279
 */
public class MenuState {

    private Texture bg;
    private Texture button;

    public MenuState(StateManager gsm) {
        super(gsm);
        bg = new Texture("bg.png");
        button = new Texture("playbtn.png");
        setCameraView(FlappyBird.WIDTH, FlappyBird.HEIGHT);
        setCameraPosition(getViewWidth() / 2, getViewHeight() / 2);
    }
}
