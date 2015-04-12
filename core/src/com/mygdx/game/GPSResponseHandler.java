package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class GPSResponseHandler implements Net.HttpResponseListener{
    private Stage.EntityObject entityObject;
    private boolean gotObject;

    public GPSResponseHandler(){
        gotObject = false;
    }
    @Override
    public void handleHttpResponse(Net.HttpResponse httpResponse) {
        JsonObject result = new Gson().fromJson(httpResponse.getResultAsString(),JsonObject.class).getAsJsonObject("results");
        JsonObject entity = result.getAsJsonObject("Entity");
        Gdx.app.log("JSONJSON",entity.getAsString());
        entityObject = new Gson().fromJson(entity,Stage.EntityObject.class);
        gotObject = true;
    }

    @Override
    public void failed(Throwable t) {

    }

    @Override
    public void cancelled() {

    }

    public Stage.EntityObject getEntityObject() {
        return entityObject;
    }

    public boolean isGotObject() {
        return gotObject;
    }
}
