package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.google.gson.Gson;
import com.mygdx.game.screen.LoadingScreen;
import com.mygdx.game.screen.ResultScreen;
import com.mygdx.game.screen.ScanScreen;
import com.mygdx.game.screen.TestScreen;

public class WalkingGame extends Game {
    public SpriteBatch batch;
    public BitmapFont font;
    public NativeFunctions nativeFunctions;
    public User user;
    public Texture background;

    public WalkingGame(NativeFunctions nativeFunctions){
        super();
        this.nativeFunctions = nativeFunctions;
    }

    public void create() {
        background = new Texture("background.jpg");
        batch = new SpriteBatch();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("hurry up.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 40;
        parameter.borderWidth = 3;
        font = generator.generateFont(parameter);
//        font = new BitmapFont();
        this.setScreen(new LoadingScreen(this));
//        this.setScreen(new TestScreen(this));
    }

    public void render() {
        super.render();
    }

    public void dispose(){
        if (user!=null)
            update();
        batch.dispose();
        font.dispose();
        background.dispose();
    }

    public void update(){
        Parse parse = new Parse();
        parse.putRequest("User",user.getObjectId(),new Gson().toJson(user));
    }
}
