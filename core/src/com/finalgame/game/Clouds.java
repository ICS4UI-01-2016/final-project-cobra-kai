/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finalgame.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 *
 * @author coulh9904
 */
public class Clouds {
    //Need to revise

    public static final float WIDTH = 128;
    private boolean passed;
    private boolean hitR = false;
    private boolean hitL = false;
    private boolean isBroken = false;
    private float x;
    private float y;
    private Vector3 position;
    private Vector3 velocity;
    private Texture Cloud;
    private Texture BrokenCloud;
    private Rectangle CloudBounds;
    private float Motion;
    private final float MOVEMENT1 = 0;

    public Clouds(float i) {
        //This if exception generates a still cloud directly below the cloud's start position, it occupies the last position in the array.
        if (i == 10) {
            y = FinalGame.LENGTH / 4 - 30;
            x = FinalGame.WIDTH / 4;
        } else {
            y = -1000;
            x = (int) (Math.random() * (400 - 60 + 1) + 60);
        }
        position = new Vector3(x, y, 0);
        Cloud = new Texture("CloudBox.png");
        //Broken cloud is a darker cloud that signifies if a cloud will break after one jump. 
        BrokenCloud = new Texture("BrokenCloudBox.png");
        velocity = new Vector3(MOVEMENT1, 0, 0);

        CloudBounds = new Rectangle(position.x, position.y, Cloud.getWidth(), Cloud.getHeight());

        passed = false;
    }

    public int getWidth() {
        return Cloud.getWidth();
    }

    public void render(SpriteBatch batch) {
        batch.draw(Cloud, position.x, position.y);
    }

    public void brokenRender(SpriteBatch batch) {
        batch.draw(BrokenCloud, position.x, position.y);
    }
    public void setBroken(boolean TF){
        isBroken = TF;
    }
    public boolean isBroken(){
        return isBroken;
    }

    public float getY() {
        return position.y;
    }

    public float getX() {
        return position.x;
    }

    public void setPos(float x, float y) {
        passed = false;
        position.x = x;
        position.y = y;
        CloudBounds.setPosition(position.x, position.y);
    }

    public boolean collides(KoopaBoi k) {
        if (CloudBounds.overlaps(k.getHitBox())) {
            return true;
        }

        return false;
    }

    public void update(float deltaTime) {
        // scaling velocity by time
        velocity.scl(deltaTime);
        // adding velocity to position
        position.add(velocity);
        // unscale velocity
        velocity.scl(1 / deltaTime);

        if (position.x == 470) {
            hitR = true;
            if (hitR = true) {
                velocity.x = -100;
                if (position.x <= 0) {
                    hitR = false;
                    hitL = true;
                }
            }
            if (hitL = true) {
                velocity.x = 100;
            }
        }

        if (position.x == 0) {
            velocity.x = -100;
        }
    }

    public void setMotion(float m) {
        Motion = m;
    }

    public void sideMotion(float x) {

        // add gravity
        if (x < 10) {
            Motion = Motion * -1;
        }
        if (x > 400) {
            Motion = Motion * -1;
        }
        position.x = position.x + Motion;
        // set the new bounds
        CloudBounds.setPosition(position.x, position.y);
    }

    public void dispose() {
        Cloud.dispose();
    }

    public boolean hasPassed() {
        return passed;
    }

    public void pass() {
        passed = true;
    }

}
