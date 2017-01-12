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
    
    
    private final float GRAVITY = -12;
    private final float MOVEMENT = 100;
    
    public KoopaBoi(int x, int y){
        position = new Vector3(x,y,0);
    }
    
}
