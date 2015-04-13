package com.mygdx.game.skill;

import com.mygdx.game.Entity;

public class Blast extends Skill {
    public Blast(Entity caster) {
        super("Blast", 3,  caster, 50, 5);
    }

    @Override
    public void execute() {
        if (caster.consume(energy)) {
            target.dealDamage(caster.getStrength() + 30);
            startCooldown();
        }
    }
}
