package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Timer;


public class TestScreen implements Screen {
    private final Music battleMusic;
    private int tesxt;
    final WalkingGame game;
    OrthographicCamera camera;
    BitmapFont font;

    Parse parse;

    public  TestScreen(final WalkingGame game){
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false,480,800);
        tesxt = 0;
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                addFive();
            }
        },0,1);
        battleMusic = Gdx.audio.newMusic(Gdx.files.internal("Ectoplasm.mp3"));
        battleMusic.setLooping(true);
        battleMusic.play();

        parse = new Parse();
        parse.getUserID();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("LeagueGothic-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20;
        font = generator.generateFont(parameter);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        font.draw(game.batch, String.valueOf(tesxt), 100, 150);

        game.batch.end();

        if (Gdx.input.isTouched()){
            game.setScreen(new BattleScreen(game,new Entity("123",100,10,10,10,10),new Entity("456",10,10,10,10,10)));
            battleMusic.dispose();
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public void addFive() {
        tesxt += 1;
    }
}
