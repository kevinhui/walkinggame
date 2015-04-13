package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.Entity;
import com.mygdx.game.WalkingGame;
import com.mygdx.game.control.Button;

import java.awt.TextArea;

public class MainScreen extends MenuScreen {
    private Animation normalAnimation;
    private float stateTime;
    private Entity me;
    private TextureRegion currentFrame;
    public MainScreen(WalkingGame game) {
        super(game);
        me = game.user.createEntity();
        normalAnimation = new Animation(0.5f,me.getWalkFrames());
        normalAnimation.setPlayMode(Animation.PlayMode.LOOP);
        stateTime = 0;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(game.background, 0, 0);
        stateTime += delta;
        currentFrame = normalAnimation.getKeyFrame(stateTime,true);
        game.batch.draw(normalAnimation.getKeyFrame(stateTime),(camera.viewportWidth-currentFrame.getRegionWidth())/2,600);
        game.font.draw(game.batch,"Health",30,600);
        game.font.draw(game.batch,"Strength",250,600);
        game.font.draw(game.batch,"Defence",30,300);
        game.font.draw(game.batch,"Spirit",250,300);
        game.font.draw(game.batch,String.valueOf(me.getBasehealth()),30,500);
        game.font.draw(game.batch,String.valueOf(me.getStrength()),250,500);
        game.font.draw(game.batch,String.valueOf(me.getToughness()),30,200);
        game.font.draw(game.batch,String.valueOf(me.getConcentration()),250,200);
        game.font.draw(game.batch,me.getName(),5,750);
        for (Button button : buttons) {
            game.batch.draw(button.getImage(), button.x, button.y);
        }
        game.batch.end();
    }
}

