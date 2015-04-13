package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.google.gson.Gson;
import com.mygdx.game.Entity;
import com.mygdx.game.GPSHandler;
import com.mygdx.game.Parse;
import com.mygdx.game.Stage;
import com.mygdx.game.WalkingGame;
import com.mygdx.game.control.Button;
import com.mygdx.game.control.RectButton;

public class StageScreen extends MenuScreen implements Net.HttpResponseListener{
    private Entity opponent;
    private int statusCode;
    private Stage result;
    private RectButton startButton;
    private RectButton searchButton;
    private boolean started;
    private boolean requestSent;
    private double[] position;
    private GPSHandler handler;

    public StageScreen(WalkingGame game) {
        super(game);
        new Parse(this).getRequestByIdContent("Stage",game.user.getStage().getObjectId(),"include=Entity");
        startButton = new RectButton(160,500,"button_START160x60.png","button_START160x60.png","button_START160x60.png","Start");
        searchButton = new RectButton(160,250,"button_START160x60.png","button_START160x60.png","button_START160x60.png","Search");
        buttons.add(startButton);
        buttons.add(searchButton);
        started = false;
        requestSent = false;
        position = game.nativeFunctions.getGeolocation();
    }
    @Override
    public void render(float delta){
        if (position[0]==0&&position[1]==0) {
            position = game.nativeFunctions.getGeolocation();
        } else if (opponent==null){
            opponent = GPSHandler.getEntityByGPS(position);
        }
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(game.background,0,0);
        if (result!=null){
            Stage.EntityObject temp = result.getEntity();
            int stepsRequired = result.getStepsRequired() - (game.user.getSteps() + game.nativeFunctions.getStepCount());
            if (stepsRequired<0) stepsRequired = 0;
            if (started&&(stepsRequired==0)){
                game.nativeFunctions.resetStepCount();
                game.user.setSteps(0);
                game.setScreen(new BattleScreen(game,game.user.createEntity(),new Entity(temp,true)));
                game.nativeFunctions.disableStepCounter();
                dispose();
            }
            game.font.draw(game.batch, temp.getEntityname(), (camera.viewportWidth-game.font.getBounds(temp.getEntityname()).width)/2, 640);
            game.font.draw(game.batch,"Steps:"+String.valueOf(stepsRequired),(camera.viewportWidth-game.font.getBounds("Steps"+String.valueOf(stepsRequired)).width)/2,700);
        }
        if (opponent!=null){
            game.font.draw(game.batch,opponent.getName(),(camera.viewportWidth-game.font.getBounds(opponent.getName()).width)/2,350);
        }
        for (Button button:buttons) {
            game.batch.draw(button.getImage(), button.x, button.y);
        }
//        game.font.draw(game.batch, "Stage", 240,400);
        game.batch.end();
    }
    @Override
    public void dispose(){
        game.user.setSteps(game.user.getSteps()+game.nativeFunctions.getStepCount());
        game.nativeFunctions.resetStepCount();
        game.update();
        super.dispose();
    }
    @Override
    public void pause(){
        game.update();
    }
    @Override
    public void handleHttpResponse(Net.HttpResponse httpResponse) {
        statusCode = httpResponse.getStatus().getStatusCode();
        result = new Gson().fromJson(httpResponse.getResultAsString(),Stage.class);


    }

    @Override
    public void failed(Throwable t) {

    }

    @Override
    public void cancelled() {

    }
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Vector3 touch = new Vector3();
        camera.unproject(touch.set(screenX,screenY,0));
        for (Button select:buttons) {
            if (select.isClicked(touch.x, touch.y)) {
                switch (select.onRelease()) {
                    case "Start":
                        if (!started) {
                            game.nativeFunctions.enableStepCounter();
                            started = true;
                            select.setActive(new Texture("button_STOP160x60.png"));
                        } else{
                            game.nativeFunctions.disableStepCounter();
                            started = false;
                            select.setActive(new Texture("button_START160x60.png"));
                        }
                        break;
                    case "Search":
                        if (opponent!=null){
                            Gdx.app.postRunnable(new Runnable() {
                                @Override
                                public void run() {
                                    game.setScreen(new BattleScreen(game, game.user.createEntity(), opponent));
                                    dispose();
                                }
                            });
                        }
                        break;
                    case "Main":
                        game.setScreen(new MainScreen(game));
                        dispose();
                        break;
                    case "Stage":
                        game.setScreen(new StageScreen(game));
                        dispose();
                        break;
                    case "Scan":
                        game.setScreen(new ScanScreen(game));
                        dispose();
                        break;
                }
            }
        }
        return true;
    }
}

