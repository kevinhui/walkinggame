package com.mygdx.game.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public abstract class Button {
    protected Texture active;
    protected Texture click;
    protected Texture inactive;
    protected Texture current;
    private boolean enable;
    private String message;
    public float x;
    public float y;
    public Button(float x,float y,String activeimg,String onclickimg,String inactiveimg,String message){
        this.x = x;
        this.y = y;
        active = new Texture(Gdx.files.internal(activeimg));
        click = new Texture(Gdx.files.internal(onclickimg));
        inactive = new Texture(Gdx.files.internal(inactiveimg));
        current = active;
        enable = true;
        this.message = message;
    }
    public Texture getImage(){
        return current;
    }

    public void onClick() {
        if (enable)
            current = click;
    }

    public String onRelease() {
        if (enable)
            return message;
        return null;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
        current = enable ? active : inactive;
    }

    public void setActive(Texture active){
        this.active = active;
    }

    public abstract boolean isClicked(float x,float y);

}
