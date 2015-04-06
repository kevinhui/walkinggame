package com.mygdx.game.control;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.skill.Skill;


public class SkillButton extends EllipseButton {

    private Skill skill;

    public SkillButton(float x, float y, String activeimg, String onclickimg, String inactiveimg, String message , Skill skill) {
        super(x, y, activeimg, onclickimg, inactiveimg, message);
        this.skill = skill;
    }

    @Override
    public String onRelease(){
        String message = super.onRelease();
        if (message != null)
            skill.execute();
        return message;
    }

    public void update(){
        if (skill.canExecute()){
            this.setEnable(true);
        } else {
            this.setEnable(false);
        }
    }

//    float accumulator;
//    public void animate(final Batch batch, final float delta){
//        accumulator = 0;
//        Timer.schedule(new Timer.Task() {
//            @Override
//            public void run() {
//                batch.begin();
//                batch.draw(inactive,x,y,0,0,inactive.getWidth(), inactive.getHeight() - (int) accumulator);
//                batch.end();
//                accumulator += current.getHeight()*delta/skill.getCooldown();
//            }
//        },0,skill.getCooldown(), (int) (skill.getCooldown()/delta));
//    }

}
