package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Parse{
    private String app_id;
    private String app_key;
    private Net.HttpResponseListener listener;

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
    }

}
