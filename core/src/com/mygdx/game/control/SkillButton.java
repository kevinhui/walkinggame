package com.mygdx.game.control;

import com.mygdx.game.skill.Skill;


public class SkillButton extends RectButton {

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

    public int getCooldowntime(){
        return skill.getCooldowntime();
    }

}
