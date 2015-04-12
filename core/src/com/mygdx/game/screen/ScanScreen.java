package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.google.gson.Gson;
import com.mygdx.game.Consumable;
import com.mygdx.game.Parse;
import com.mygdx.game.WalkingGame;
import com.mygdx.game.control.Button;

public class ScanScreen extends MenuScreen implements Net.HttpResponseListener {
    private int statusCode;
    private String result;
    private Consumable consumable;
    private Texture consumableIcon;
    private String message;

    public ScanScreen(final WalkingGame game) {
        super(game);
        message = "Starting Scanner...";
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                game.nativeFunctions.openScanReader();
            }
        });
    }
    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(game.background,0,0);
        for (Button button:buttons) {
            game.batch.draw(button.getImage(), button.x, button.y);
        }
        if (game.nativeFunctions.getQRreaderResult()!=null){
            if (game.nativeFunctions.getQRreaderResult().equals(""))
                message = "Invalid QR Code";
            else
                new Parse(this).getRequestById("Consumable", game.nativeFunctions.getQRreaderResult());
            game.nativeFunctions.resetQRreaderResult();
        }
        if (consumable!=null){
            consumableIcon = new Texture(consumable.getImagePath());
            game.batch.draw(consumableIcon,(camera.viewportWidth-consumableIcon.getWidth())/2,300);
        } else {
            game.font.draw(game.batch, message, (camera.viewportWidth- game.font.getBounds(message).width)/2,(camera.viewportHeight-game.font.getBounds(message).height)/2);
        }
        game.batch.end();
    }

    @Override
    public void handleHttpResponse(Net.HttpResponse httpResponse) {
        statusCode = httpResponse.getStatus().getStatusCode();
        result = httpResponse.getResultAsString();
        if (statusCode==200) {
            consumable = new Gson().fromJson(result, Consumable.class);
            game.user.use(consumable);
            game.update();
//            new Parse().deleteRequest("Consumable",consumable.getObjectId());
        } else {
            message = "Invalid QR Code";
        }
    }

    @Override
    public void failed(Throwable t) {

    }

    @Override
    public void cancelled() {

    }
}
