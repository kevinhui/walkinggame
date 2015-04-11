package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.game.WalkingGame;
import com.mygdx.game.control.Button;

public class StageScreen extends MenuScreen{
    public StageScreen(WalkingGame game) {
        super(game);
    }
    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(game.background,0,0);
        for (Button button:buttons) {
            game.batch.draw(button.getImage(), button.x, button.y);
        }
        game.font.draw(game.batch, "Stage", 240,400);
        game.batch.end();
    }
}

