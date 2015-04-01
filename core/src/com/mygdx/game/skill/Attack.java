package com.mygdx.game.skill;

import com.mygdx.game.Entity;

public class Attack extends Skill{
    protected Attack(String name, int id, Entity target, Entity caster) {
        super(name, id, target, caster);
    }

    @Override
    public void execute() {
        target.dealDamage(caster.getStrength());
    }
}
