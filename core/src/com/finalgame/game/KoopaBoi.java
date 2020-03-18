/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finalgame.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
    private Texture left;
    private Texture fallRight;
    private Texture death;
    private Texture start;
    private Rectangle hitBox;
    private final float GRAVITY = -16;
    private final float MOVEMENT = 0;

    public KoopaBoi(int x, int y) {
        position = new Vector3(x, y, 0);
        velocity = new Vector3(MOVEMENT, 0, 0);
        start = new Texture("start.png");
        jump = new Texture("jump.png");
        death = new Texture("death.png");
        left = new Texture("left.png");

        hitBox = new Rectangle(position.x, position.y, start.getWidth(), start.getHeight());
    }

    public float getYVelocity() {
        return velocity.y;
    }

    public void jump() {
        velocity.y = 530;
    }
    
    public void superJump(){
        velocity.y = 800;
    }

    public void moveLeft() {
        velocity.x = -220;
    }

    public void moveRight() {
        velocity.x = 220;
    }

    public void standStill() {
        velocity.x = 0;
    }

    public void update(float deltaTime) {
        // Add gravity
        if (position.y >= 0) {
            velocity.y += GRAVITY;
        } else {
            velocity.y = 0;
            velocity.x = 0;
        }

        // scaling velocity by time
        velocity.scl(deltaTime);
        // adding velocity to position
        position.add(velocity);
        // unscale velocity
        velocity.scl(1 / deltaTime);

        if (position.x >= 470) {
            position.x = 5;
        }
        if (position.x <= 0) {
            position.x = 460;
        }

        System.out.println(position.y);

        // set the new bounds
        hitBox.setPosition(position.x, position.y);
    }

    //There's a fun feature on the info page where the koopa can go left and right as a demonstration. InfoStateUpdate makes that possible.
    public void InfoStateUpdate(float deltaTime, float KoopaStart) {
        // scaling velocity by time
        velocity.scl(deltaTime);
        // adding velocity to position
        position.add(velocity);
        // unscale velocity
        velocity.scl(1 / deltaTime);
        //Keep the koopa contained to a n X value of 110 in either direction.
        if (position.x > KoopaStart + 110) {
            position.x = KoopaStart + 110;
        }

        if (position.x < KoopaStart - 100) {
            position.x = KoopaStart - 100;
        }
        hitBox.setPosition(position.x, position.y);
    }
    
    public void endRender (SpriteBatch batch){
        // if the koopa is not on the ground render the start image
        if (position.y >95 ){
            batch.draw(start, position.x, position.y);
        }
        if (position.y <= 95){
            
            
            batch.draw(death, position.x, position.y);
        }
    }
    public void endUpdate (float deltaTime){
        if (position.y > 95){
            position.y += GRAVITY;
        }
        // scaling velocity by time
        velocity.scl(deltaTime);
        // adding velocity to position
        position.add(velocity);
        // unscale velocity
        velocity.scl(1 / deltaTime);
    }

    public void render(SpriteBatch batch) {
        // if the koopa is moving right (+ velocity) or not moving
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) == true && velocity.y < 400 && Gdx.input.isKeyPressed(Input.Keys.UP) == false && Gdx.input.isKeyPressed(Input.Keys.LEFT) == false) {
            batch.draw(start, position.x, position.y);
        }
        // if all buttons are pressed
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) == true && (Gdx.input.isKeyPressed(Input.Keys.LEFT)) == true || Gdx.input.isKeyJustPressed(Input.Keys.UP) == true && (Gdx.input.isKeyPressed(Input.Keys.LEFT))){
            batch.draw(left, position.x, position.y);
        }
        //start position (not pressing any buttons)
        if (Gdx.input.isKeyPressed(Input.Keys.UP) == true && velocity.y < 400 && Gdx.input.isKeyPressed(Input.Keys.LEFT) == false|| Gdx.input.isKeyPressed(Input.Keys.LEFT) == false && Gdx.input.isKeyPressed(Input.Keys.RIGHT) == false && velocity.y < 400){
            batch.draw(start, position.x, position.y);
        }
        if (position.y <= 0) {
            //start.dispose();
            batch.draw(death, position.x, position.y);
            //          start.dispose();
        }
        // if the koopa is moving left (- velocity)
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) == true && velocity.y < 400) {
            batch.draw(left, position.x, position.y);
        }
        //render jumping for super jump
        if ((Gdx.input.isKeyPressed(Input.Keys.UP) == true) && (400 <= velocity.y) || (400 <= velocity.y)){
            //start.dispose();
            batch.draw(jump, position.x - 30, position.y);
            //start.dispose();
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
