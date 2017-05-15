package com.mygdx.game.States;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

/**
 * Handels the different states of the game (which is a stack)
 */

public class GameStateManager {
    // stack of states
    private Stack<State> states;

    //initiate
    public GameStateManager(){
        states = new Stack<State>();
    }

    // push a new state to the stack (start a new state on top o the old one)
    public void push(State state){
        states.push(state);
    }

    // pop the top state from the stack (end the current state)
    public void pop(){
        states.pop().dispose();
    }

    // change the current state to a new one
    public void set(State state){
        states.pop().dispose();
        states.push(state);
    }

    // update the current state
    public void update(float dt){
        states.peek().update(dt);
    }

    //render the current state
    public void render(SpriteBatch sb){
        states.peek().render(sb);
    }
}
