package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.game.Parse;
import com.mygdx.game.WalkingGame;


public class NewPlayerScreen extends ScreenAdapter implements Net.HttpResponseListener {
    private WalkingGame game;
    private OrthographicCamera camera;
    private int statusCode;
    private String result;
    private String message;
    private boolean ready;
    private boolean duplicated;

    public  NewPlayerScreen(WalkingGame game,boolean duplicated){
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false,480,800);
        statusCode = 0;
        result = "";
        ready = false;
        this.duplicated = duplicated;
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.font.draw(game.batch, message, 240,400);
        game.batch.end();
    }

    @Override
    public void handleHttpResponse(Net.HttpResponse httpResponse) {
        statusCode = httpResponse.getStatus().getStatusCode();
        message = httpResponse.getResultAsString();
    }

    @Override
    public void failed(Throwable t) {

    }

    @Override
    public void cancelled() {

    }
}
