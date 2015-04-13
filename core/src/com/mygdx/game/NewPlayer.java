package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Screen;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mygdx.game.screen.MainScreen;

public class NewPlayer implements Input.TextInputListener, Net.HttpResponseListener{
    private final String macAddress;
    private final WalkingGame game;
    private int statusCode;
    private String result;
    private Screen currentScreen;

    public NewPlayer(WalkingGame game,Screen currentScreen) {
        macAddress = game.nativeFunctions.getMacAddress();
        this.game = game;
        this.currentScreen = currentScreen;
    }

    @Override
    public void input(String text) {
        User user = new User(macAddress,text);
        if (!text.equals("")){
            Parse parse = new Parse(this);
            parse.postRequest("User",new Gson().toJson(user));
            game.user = user;
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    game.setScreen(new MainScreen(game));
                    currentScreen.dispose();
                }
            });
        } else{
            NewPlayer player = new NewPlayer(game,currentScreen);
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
