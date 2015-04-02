package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.control.Button;
import com.mygdx.game.control.EllipseButton;
import com.mygdx.game.control.RectButton;

public class BattleScreen extends ScreenAdapter implements InputProcessor {
    final WalkingGame game;
    OrthographicCamera camera;
    private final char[] pattern;
    private char[] sequence;
    private int pointer;
    Array<Button> buttons;
    Vector3 touch;

    Entity opponent;
    Entity self;
    Texture smallUp;
    Texture smallDown;
    Texture smallLeft;
    Texture smallRight;
    float firstPos;
    float speed;

    public BattleScreen(final WalkingGame game) {
        this.game = game;
        pattern = new char[] {'h','j','k','l'};
        sequence = new char[9];
        touch = new Vector3();
        for (int i=0;i<sequence.length;i++)
            sequence[i] = pattern[MathUtils.random(3)];
        buttons = new Array<Button>();
        buttons.add(new RectButton(0,0,"Left.png","Left.png","Left.png","h"));
        buttons.add(new RectButton(120,0,"Down.png","Down.png","Down.png","j"));
        buttons.add(new RectButton(240,0,"Up.png","Up.png","Up.png","k"));
        buttons.add(new RectButton(360,0,"Right.png","Right.png","Right.png","l"));


        camera = new OrthographicCamera();
        camera.setToOrtho(false,480,800);
        Gdx.input.setInputProcessor(this);

        smallDown = new Texture(Gdx.files.internal("smallDown.png"));
        smallLeft = new Texture(Gdx.files.internal("smallLeft.png"));
        smallRight = new Texture(Gdx.files.internal("smallRight.png"));
        smallUp = new Texture(Gdx.files.internal("smallUp.png"));
        pointer = 0;
        firstPos = camera.viewportWidth;
        speed = 100;
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

       for (Button button:buttons) {
           game.batch.draw(button.getImage(), button.x, button.y);
       }
       firstPos -= speed*Gdx.graphics.getDeltaTime();
        if (firstPos<0)
            forward();
       float accumulator = firstPos;
         for (int i=pointer;i<pointer+sequence.length;i++){
           switch (sequence[i%sequence.length]){
                  case 'h':
                      game.batch.draw(smallLeft,accumulator,500);
                      break;
                  case 'j':
                      game.batch.draw(smallDown,accumulator,500);
                      break;
                  case 'k':
                      game.batch.draw(smallUp,accumulator,500);
                      break;
                  case 'l':
                      game.batch.draw(smallRight,accumulator,500);
                      break;
                  default:
                      game.batch.draw(smallLeft,accumulator,500);
                      break;
              }
              accumulator += smallDown.getWidth();
          }
         game.batch.end();

//        if (Gdx.input.isTouched()){
//            game.setScreen(new BattleScreen(game));
//            dispose();
//            forward();
//        }

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        camera.unproject(touch.set(screenX,screenY,0));
        for (Button select:buttons) {
            if (select.isClicked(touch.x, touch.y))
                if (select.onRelease().charAt(0) == sequence[this.pointer]) {
                    forward();
                }
        }
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public void forward(){
        sequence[pointer] = pattern[MathUtils.random(3)];
        pointer = (pointer+1)%sequence.length;
        firstPos += smallDown.getWidth();

    }

}
