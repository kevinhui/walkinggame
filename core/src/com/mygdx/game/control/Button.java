package com.mygdx.game.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public abstract class Button {
    protected Texture active;
    protected Texture click;
    protected Texture inactive;
    protected Texture current;
    boolean enable;
    public Button(String activeimg,String onclickimg,String inactiveimg){
        active = new Texture(Gdx.files.internal(activeimg));
        click = new Texture(Gdx.files.internal(onclickimg));
        inactive = new Texture(Gdx.files.internal(inactiveimg));
        current = active;
        enable = true;
    }
    public Texture getImage(){
        return current;
    }

    public boolean onClick() {
        if (enable)
            current = click;
        return enable;
    }

    public boolean onRelease() {
        if (enable)
            current = active;
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
        current = enable ? active : inactive;
    }

    public abstract boolean isClicked(float x,float y);
}
