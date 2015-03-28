package com.mygdx.game.control;

import com.badlogic.gdx.math.Ellipse;

public class EllipseButton extends Button{
    Ellipse object;

    public EllipseButton(float x, float y, String activeimg, String onclickimg, String inactiveimg,String message) {
        super(x,y,activeimg, onclickimg, inactiveimg, message);
        object = new Ellipse(x+current.getWidth()/2,y+current.getHeight()/2,current.getWidth(),current.getHeight());
    }

    @Override
    public boolean isClicked(float x, float y) {
        return object.contains(x,y);
    }

}
