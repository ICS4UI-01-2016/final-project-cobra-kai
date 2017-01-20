/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finalgame.game;

import States.PlayState;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 *
 * @author moore3607
 */
public class KoopaBoi {

    private Vector3 position;
    private Vector3 velocity;
    private Texture jumpRight;
    private Texture jump;
    private Texture fallLeft;
    private Texture fallRight;
    private Texture death;
    private Texture start;
    private Rectangle hitBox;
    private final float GRAVITY = -12;
    private final float MOVEMENT = 0;

    public KoopaBoi(int x, int y) {
        position = new Vector3(x, y, 0);
        velocity = new Vector3(MOVEMENT, 0, 0);
        start = new Texture("start.png");
        jump = new Texture("jump.png");
        death = new Texture("death.png");

        hitBox = new Rectangle(position.x, position.y, start.getWidth(), start.getHeight());
    }

    public void jump() {
        velocity.y = 350;
    }

    public void moveLeft() {
        velocity.x = -110;
    }
    
    public void moveRight() {
        velocity.x = 110;
    }

    public void update(float deltaTime) {
        // add gravity
        if (position.y >= 0) {
            velocity.y += GRAVITY;
        } else {
            velocity.y = 0;
        }
        // scaling velocity by time
        velocity.scl(deltaTime);
        // adding velocity to position
        position.add(velocity);
        // unscale velocity
        velocity.scl(1 / deltaTime);

        if (position.x >= 900) {
            position.x = 0;
        }

        // set the new bounds
        hitBox.setPosition(position.x, position.y);
    }

    public void render(SpriteBatch batch) {

        if (position.y > 0) {
            batch.draw(start, position.x, position.y);
        }
        if (position.y <= 0) {
            start.dispose();
            batch.draw(death, position.x, position.y);
        }


    }

    public float setX(float NewX) {
        position.x = NewX;
        return position.x;
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public void dispose() {
        start.dispose();
        death.dispose();
    }
}
