package com.mygdx.game.control;

import com.badlogic.gdx.math.Rectangle;

public class RectButton extends Button{
    Rectangle object;

    public RectButton(float x, float y,String activeimg, String onclickimg, String inactiveimg,String message) {
        super(x,y,activeimg, onclickimg, inactiveimg, message);
        object = new Rectangle(x,y,active.getWidth(),active.getHeight());
    }

    @Override
    public boolean isClicked(float x, float y) {
        return object.contains(x,y);
    }
}
