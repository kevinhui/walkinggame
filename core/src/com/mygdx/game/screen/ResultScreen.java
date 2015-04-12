package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.WalkingGame;
import com.mygdx.game.control.Button;
import com.mygdx.game.control.RectButton;

public class ResultScreen extends ScreenAdapter implements InputProcessor {
    private WalkingGame game;
    private OrthographicCamera camera;
    private String message;
    private Button confirmButton;
    Vector3 touch;
    public ResultScreen(WalkingGame game, boolean victory){
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false,480,800);
        touch = new Vector3();
        Gdx.input.setInputProcessor(this);
        if (victory)
            message = "Victory!";
        else
            message = "Defeat...";
        confirmButton = new RectButton((camera.viewportWidth-160)/2,(camera.viewportHeight-60)/2-100,"button-160x60.png","button-160x60.png","button-160x60.png","OK");
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(game.background,0,0);
        game.batch.draw(confirmButton.getImage(),confirmButton.x,confirmButton.y);
        game.font.draw(game.batch, message, (camera.viewportWidth- game.font.getBounds(message).width)/2,(camera.viewportHeight-game.font.getBounds(message).height)/2);
        game.batch.end();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        camera.unproject(touch.set(screenX,screenY,0));
        if (confirmButton.isClicked(touch.x,touch.y)){
            game.setScreen(new MainScreen(game));
            dispose();
        }
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
