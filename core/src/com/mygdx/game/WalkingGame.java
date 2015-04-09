package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.screen.LoadingScreen;
import com.mygdx.game.screen.TestScreen;

public class WalkingGame extends Game {
    public SpriteBatch batch;
    public BitmapFont font;
    public NativeFunctions nativeFunctions;

    public WalkingGame(NativeFunctions nativeFunctions){
        super();
        this.nativeFunctions = nativeFunctions;
    }

    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        this.setScreen(new LoadingScreen(this));
    }

    public void render() {
        super.render();
    }

    public void dispose(){
        batch.dispose();
        font.dispose();
    }
}
