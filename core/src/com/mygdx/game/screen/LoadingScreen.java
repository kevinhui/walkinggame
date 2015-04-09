package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mygdx.game.Parse;
import com.mygdx.game.WalkingGame;

public class LoadingScreen extends ScreenAdapter implements Net.HttpResponseListener{
    private WalkingGame game;
    private OrthographicCamera camera;
    private Parse parse;
    private int statusCode;
    private String result;
    public LoadingScreen(WalkingGame game){
        this.game = game;
        this.parse = new Parse(this);
        camera = new OrthographicCamera();
        camera.setToOrtho(false,480,800);
        statusCode = 0;
        result = "";
    }

    @Override
    public void show(){
        String macAddress = game.nativeFunctions.getMacAddress();
        if (macAddress!=null){
            parse.getRequest("User", "where={\"MacAddress\":\"" + "asdsa" + "\"}");
//            JsonObject json = new Gson().fromJson(result,JsonObject.class);
//            Gdx.app.log("REST1",json.get("result").toString());
//            Gdx.app.log("REST1", String.valueOf(json.get("result").toString().isEmpty()));
        }
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.font.draw(game.batch, "Loading", 240,400);
        game.batch.end();
    }

    @Override
    public void handleHttpResponse(Net.HttpResponse httpResponse) {
        statusCode = httpResponse.getStatus().getStatusCode();
        result = httpResponse.getResultAsString();
        Gdx.app.log("RESTConnection",result);
//        JsonObject json = new Gson().fromJson(result,JsonObject.class);
//        Gdx.app.log("REST1",json.get("result").toString());
//        Gdx.app.log("REST1", String.valueOf(json.get("result").toString().isEmpty()));
        String myJSONString = result;
JsonObject jobj = new Gson().fromJson(result, JsonObject.class);

int resul = jobj.getAsJsonArray("results").size();

        Gdx.app.log("RESTTEST", String.valueOf(resul));
    }

    @Override
    public void failed(Throwable t) {
        Gdx.app.log("RESTConnection", t.getMessage());
    }

    @Override
    public void cancelled() {

    }

    public void showResult(){
        Gdx.app.log("REST1",result);
    }
}
