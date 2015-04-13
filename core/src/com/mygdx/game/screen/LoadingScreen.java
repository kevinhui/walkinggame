package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mygdx.game.NewPlayer;
import com.mygdx.game.Parse;
import com.mygdx.game.User;
import com.mygdx.game.WalkingGame;

public class LoadingScreen extends ScreenAdapter implements Net.HttpResponseListener{
    private WalkingGame game;
    private OrthographicCamera camera;
    private int statusCode;
    private String result;
    private String message;

    public LoadingScreen(WalkingGame game){
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false,480,800);
        statusCode = 0;
        result = null;
        message = "Loading";
    }

    @Override
    public void show(){
        String macAddress = game.nativeFunctions.getMacAddress();
        if (!game.nativeFunctions.isNetworkEnabled()){
            message = "Error: No Network Connectivity";
        } else if (macAddress != null){
            Parse parse = new Parse(this);
            parse.getRequest("User", "where={\"MacAddress\":\"" + macAddress + "\"}");
        }
    }

    @Override
    public void render(float delta){
        if (result!=null){
            JsonObject jobj = new Gson().fromJson(result, JsonObject.class);
            result = null;
            JsonArray results = jobj.getAsJsonArray("results");
//            Gdx.app.log("RESTTEST", String.valueOf(jobj.getAsJsonArray("results").toString()));
            if (results.size()==0){
                NewPlayer newPlayer = new NewPlayer(game,this);
                Gdx.input.getTextInput(newPlayer, "Register", "", "Username");
            } else{
                game.user = new Gson().fromJson(results.get(0),User.class);
                game.setScreen(new MainScreen(game));
                dispose();
//            Gdx.app.log("RESTDATA", game.user.getObjectId() + " " + game.user.getUserID() + " " + game.user.getSteps()+" "+game.user.getStageID());
            }
        }
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(game.background,0,0);
        game.font.draw(game.batch, message, (camera.viewportWidth- game.font.getBounds(message).width)/2,(camera.viewportHeight-game.font.getBounds(message).height)/2);
        game.batch.end();
    }

    @Override
    public void handleHttpResponse(Net.HttpResponse httpResponse) {
        statusCode = httpResponse.getStatus().getStatusCode();
        result = httpResponse.getResultAsString();
    }

    @Override
    public void failed(Throwable t) {
        Gdx.app.log("RESTConnection", t.getMessage());
    }

    @Override
    public void cancelled() {

    }
}
