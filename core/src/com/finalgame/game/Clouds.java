/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finalgame.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 *
 * @author coulh9904
 */
public class Clouds {
    //Need to revise
    private final float PIPE_GAP = 100;
    private final float MIN_HEIGHT = 50;
    private final float MAX_HEIGHT = 350;
    public static final float WIDTH = 52;
    
    private boolean passed;
    private Vector2 position;
    private Texture Cloud;
    private Rectangle CloudBounds;

    
    public Clouds(float y){
        float x = (int)(Math.random()*(1) + 75);
        position = new Vector2(x,y);
        Cloud = new Texture("Cloud.png");
        
        
        CloudBounds = new Rectangle(position.x, position.y, Cloud.getWidth(), Cloud.getHeight());
        
        passed = false;
    }
    
    public void render(SpriteBatch batch){
        batch.draw(Cloud, position.x, position.y);        
    }
    
    public float getX(){
        return position.x;
    }
    
    public void setX(float x){
        passed = false;
        position.x = x;
        float y = (int)(Math.random()*(325-75+1) + 75);
        position.y = y;
        CloudBounds.setPosition(position.x, position.y + PIPE_GAP/2);
    }
    

    
    public void dispose(){
        Cloud.dispose();
    }
    
    public boolean hasPassed(){
        return passed;
    }
    
    public void pass(){
        passed = true;
    }
}

