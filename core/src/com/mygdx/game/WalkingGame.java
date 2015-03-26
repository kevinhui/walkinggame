package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class WalkingGame extends ApplicationAdapter implements InputProcessor {
	SpriteBatch batch;
    Texture leftImg;
    Texture upImg;
    Texture downImg;
    Texture rightImg;
    OrthographicCamera camera;

    Vector3 touch;

	@Override
	public void create () {

        touch = new Vector3();

		batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false,480,800);
        leftImg = new Texture("Left.png");
        upImg = new Texture("Up.png");
        downImg = new Texture("Down.png");
        rightImg = new Texture("Right.png");

        Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render () {

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
		batch.begin();
        batch.draw(leftImg,0,0,0,0);
//        batch.draw(upImg,leftImg.getWidth(),0);
//        batch.draw(downImg,leftImg.getWidth()+upImg.getWidth(),0);
//        batch.draw(rightImg,leftImg.getWidth()+upImg.getWidth()+downImg.getWidth(),0);
//
        batch.draw(upImg,120,0);
        batch.draw(downImg,240,0);
        batch.draw(rightImg,360,0);
		batch.end();
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
        camera.unproject(touch.set(screenX,screenY,0));
        if (touch.y<120)
            if (touch.x<=120)
                Gdx.app.log("Position","Button1 is clicked!");
            else if ((touch.x>120)&&(touch.x<=240))
                Gdx.app.log("Position","Button2 is clicked!");
            else if ((touch.x>240)&&(touch.x<=360))
                Gdx.app.log("Position","Button3 is clicked!");
            else if (touch.x>360)
                Gdx.app.log("Position","Button4 is clicked!");
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
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
