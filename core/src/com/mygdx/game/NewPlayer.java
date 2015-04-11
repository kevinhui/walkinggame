package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Net;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mygdx.game.screen.MainScreen;

public class NewPlayer implements Input.TextInputListener, Net.HttpResponseListener{
    private final String macAddress;
    private final WalkingGame game;
    private int statusCode;
    private String result;

    public NewPlayer(WalkingGame game) {
        macAddress = game.nativeFunctions.getMacAddress();
        this.game = game;
    }

    @Override
    public void input(String text) {
        User user = new User(macAddress,text);
        if (!text.equals("")){
            Parse parse = new Parse(this);
            parse.postRequest("User",new Gson().toJson(user));
            game.user = user;
        } else{
            NewPlayer player = new NewPlayer(game);
            Gdx.input.getTextInput(player,"Register","","Invalid Username");
        }
    }

    @Override
    public void canceled() {
        Gdx.app.exit();
    }

    @Override
    public void handleHttpResponse(Net.HttpResponse httpResponse) {
        statusCode = httpResponse.getStatus().getStatusCode();
        result = httpResponse.getResultAsString();
        game.user.setObjectId(new Gson().fromJson(result, JsonObject.class).get("objectId").getAsString());
    }

    @Override
    public void failed(Throwable t) {
        Gdx.app.log("RESTConnection", t.getMessage());
    }

    @Override
    public void cancelled() {

    }
}
