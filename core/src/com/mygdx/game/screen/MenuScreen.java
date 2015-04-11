package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.WalkingGame;
import com.mygdx.game.control.Button;
import com.mygdx.game.control.RectButton;

public class MenuScreen extends ScreenAdapter implements InputProcessor{
    protected WalkingGame game;
    protected OrthographicCamera camera;
    Array<Button> buttons;
    public MenuScreen(WalkingGame game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 480, 800);
        buttons = new Array<>();
        buttons.add(new RectButton(0, 0, "home_button160x160.png", "home_button160x160.png", "home_button160x160.png", "Main"));
        buttons.add(new RectButton(160, 0,"battle_button_transparent160x160.png" , "battle_button_transparent160x160.png", "battle_button_transparent160x160.png", "Stage"));
        buttons.add(new RectButton(320, 0, "scan_button-02-160x160.png", "scan_button-02-160x160.png", "scan_button-02-160x160.png", "Scan"));
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void dispose(){
        for (int i=0;i<buttons.size;i++)
            buttons.removeIndex(i);
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
        Vector3 touch = new Vector3();
        camera.unproject(touch.set(screenX,screenY,0));
        for (Button select:buttons) {
            if (select.isClicked(touch.x, touch.y)) {
                switch (select.onRelease()) {
                    case  "Main":
                        game.setScreen(new MainScreen(game));
                        dispose();
                        break;
                    case "Stage":
                        game.setScreen(new StageScreen(game));
                        dispose();
                        break;
                    case "Scan":
                        game.setScreen(new ScanScreen(game));
                        dispose();
                        break;
                }
            }
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

