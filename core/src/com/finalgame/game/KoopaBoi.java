/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finalgame.game;

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
    private Texture birdPic;
    private Rectangle bounds;
    
    private final float GRAVITY = -15;
    private final float MOVEMENT = 30;
    
    public KoopaBoi(int x, int y){
        position = new Vector3(x,y,0);
        velocity = new Vector3(MOVEMENT,0,0);
        birdPic = new Texture("bird.png");
        bounds  = new Rectangle(position.x, position.y, birdPic.getWidth(), birdPic.getHeight());
    }
    
    public void jump(){
        velocity.y = 250;
    }
    
    public void update(float deltaTime){
        // add gravity
        velocity.y += GRAVITY;
        // scaling velocity by time
        velocity.scl(deltaTime);
        // adding velocity to position
        position.add(velocity);
        // unscale velocity
        velocity.scl(1/deltaTime);
        
        bounds.setPosition(position.x, position.y);
    }
    
    public void render(SpriteBatch batch){
        batch.draw(birdPic, position.x, position.y);
    }
    
    public float getX(){
        return position.x;
    }
    
    public Rectangle getBounds(){
        return bounds;
    }
    
    public void dispose(){
        birdPic.dispose();
    }
}