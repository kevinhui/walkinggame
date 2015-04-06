package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Parse implements Net.HttpResponseListener{
    private String app_id;
    private String app_key;

    public Parse(){
        app_id = "bv8fPwj8xrh9uWDu1ZoCPNnZxIFuxaeZcyDT99ek";
        app_key = "BiWGypziJuCPd0UOJReMWzFdAusXm6Q4c4BXzEFJ";
    }


    public void addUserID(){
        Net.HttpRequest httpPost = new Net.HttpRequest(Net.HttpMethods.POST);
        httpPost.setUrl("https://api.parse.com/1/classes/User/");
        httpPost.setHeader("Content-Type","application/json");
        httpPost.setHeader("X-Parse-Application-Id", app_id);
        httpPost.setHeader("X-Parse-REST-API-Key", app_key);
        httpPost.setContent("{\"userID\":\"TESTTEST\"}");
        Gdx.net.sendHttpRequest(httpPost,Parse.this);
    }

    public void getUserID(){
        Net.HttpRequest httpGET = new Net.HttpRequest(Net.HttpMethods.GET);
        httpGET.setUrl("https://api.parse.com/1/classes/User/");
        httpGET.setHeader("Content-Type","application/json");
        httpGET.setHeader("X-Parse-Application-Id", app_id);
        httpGET.setHeader("X-Parse-REST-API-Key", app_key);
        try {
            httpGET.setContent(URLEncoder.encode("where={\"userID\":\"asjkf\"}","UTF-8"));
        } catch (UnsupportedEncodingException e) {
            Gdx.app.log("RESTConnection",e.getMessage());
        }
        Gdx.net.sendHttpRequest(httpGET, Parse.this);
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
