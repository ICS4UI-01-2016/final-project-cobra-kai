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
    private Vector3 position;
    private Vector3 velocity;
    private Texture Cloud;
    private Rectangle CloudBounds;
    private final float GRAVITY = -2;
    private final float MOVEMENT1 = 0;

    public Clouds(float y) {
        
        y = -20;
        float x = (int)(Math.random()*(600-30+1) + 30);
        
        position = new Vector3(x, y, 0);
        Cloud = new Texture("CloudBox.png");
        velocity = new Vector3(MOVEMENT1, 0, 0);

        CloudBounds = new Rectangle(position.x, position.y, Cloud.getWidth(), Cloud.getHeight());

        passed = false;
    }



    
    public void render(SpriteBatch batch) {
        batch.draw(Cloud, position.x, position.y);
    }

    public float getY() {
        return position.y;
    }
    
        public float getX() {
        return position.x;
    }
    
    public void setY(float x, float y){
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

    public void update() {
        // add gravity
        position.y = position.y + GRAVITY;
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
