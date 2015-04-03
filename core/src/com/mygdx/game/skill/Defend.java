package com.mygdx.game.skill;

import com.mygdx.game.Entity;

public class Defend extends Skill{
    protected Defend(String name, int id, Entity target, Entity caster) {
        super(name, id, target, caster);
    }

    @Override
    public void execute() {
        caster.defend(3);
    }
}
