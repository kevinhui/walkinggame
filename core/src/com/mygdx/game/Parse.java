package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Parse implements Net.HttpResponseListener{
    private final String app_id;
    private final String app_key;
    private Net.HttpResponseListener listener;

    public Parse(){
        app_id = "bv8fPwj8xrh9uWDu1ZoCPNnZxIFuxaeZcyDT99ek";
        app_key = "BiWGypziJuCPd0UOJReMWzFdAusXm6Q4c4BXzEFJ";
        listener = this;
    }
    public Parse(Net.HttpResponseListener listener){
        app_id = "bv8fPwj8xrh9uWDu1ZoCPNnZxIFuxaeZcyDT99ek";
        app_key = "BiWGypziJuCPd0UOJReMWzFdAusXm6Q4c4BXzEFJ";
        this.listener = listener;
    }


    public void postRequest(String table,String content){
        Net.HttpRequest httpPost = new Net.HttpRequest(Net.HttpMethods.POST);
        httpPost.setUrl("https://api.parse.com/1/classes/" + table + "/");
        httpPost.setHeader("Content-Type","application/json");
        httpPost.setHeader("X-Parse-Application-Id", app_id);
        httpPost.setHeader("X-Parse-REST-API-Key", app_key);
        httpPost.setContent(content);
        Gdx.net.sendHttpRequest(httpPost,listener);
    }

    public void getRequest(String table,String content){
        Net.HttpRequest httpGET = new Net.HttpRequest(Net.HttpMethods.GET);
        httpGET.setUrl("https://api.parse.com/1/classes/"+ table +"/");
        httpGET.setHeader("Content-Type","application/json");
        httpGET.setHeader("X-Parse-Application-Id", app_id);
        httpGET.setHeader("X-Parse-REST-API-Key", app_key);
        try {
            httpGET.setContent(URLEncoder.encode(content,"UTF-8"));
            httpGET.setContent(content);
        } catch (UnsupportedEncodingException e) {
            Gdx.app.log("RESTConnection",e.getMessage());
        }
        Gdx.net.sendHttpRequest(httpGET, listener);
    }

    public void getRequestById(String table,String ID){
        Net.HttpRequest httpGET = new Net.HttpRequest(Net.HttpMethods.GET);
        httpGET.setUrl("https://api.parse.com/1/classes/"+ table +"/"+ID);
        httpGET.setHeader("X-Parse-Application-Id", app_id);
        httpGET.setHeader("X-Parse-REST-API-Key", app_key);
        Gdx.net.sendHttpRequest(httpGET, listener);
    }

    public void putRequest(String table, String ID, String content){
        Net.HttpRequest httpPUT = new Net.HttpRequest(Net.HttpMethods.PUT);
        httpPUT.setUrl("https://api.parse.com/1/classes/"+ table +"/"+ID);
        httpPUT.setHeader("X-Parse-Application-Id", app_id);
        httpPUT.setHeader("X-Parse-REST-API-Key", app_key);
        httpPUT.setContent(content);
        Gdx.net.sendHttpRequest(httpPUT,listener);
    }

    public void deleteRequest(String table,String ID){
        Net.HttpRequest httpDELETE = new Net.HttpRequest(Net.HttpMethods.DELETE);
        httpDELETE.setUrl("https://api.parse.com/1/classes/" + table + "/" + ID);
        httpDELETE.setHeader("X-Parse-Application-Id", app_id);
        httpDELETE.setHeader("X-Parse-REST-API-Key", app_key);
        Gdx.net.sendHttpRequest(httpDELETE, listener);
    }

    @Override
    public void handleHttpResponse(Net.HttpResponse httpResponse) {

    }

    @Override
    public void failed(Throwable t) {

    }

    @Override
    public void cancelled() {

    }
}
