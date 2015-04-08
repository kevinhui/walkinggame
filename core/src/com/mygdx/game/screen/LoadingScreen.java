package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.game.NativeFunctions;
import com.mygdx.game.WalkingGame;

public class LoadingScreen extends ScreenAdapter implements Net.HttpResponseListener{
    private WalkingGame game;
    private OrthographicCamera camera;
    private NativeFunctions nativeFunctions;
    public LoadingScreen(WalkingGame game,NativeFunctions nativeFunctions){
        this.game = game;
        this.nativeFunctions = nativeFunctions;
        camera = new OrthographicCamera();
        camera.setToOrtho(false,480,800);

    }

    @Override
    public void show(){

    }

    @Override
    public void handleHttpResponse(Net.HttpResponse httpResponse) {
        final int statusCode = httpResponse.getStatus().getStatusCode();
        String status = statusCode + " " + httpResponse.getResultAsString();
        Gdx.app.log("RESTConnection",status);
    }

    @Override
    public void failed(Throwable t) {
        Gdx.app.log("RESTConnection", t.getMessage());
    }

    @Override
    public void cancelled() {

    }
}
