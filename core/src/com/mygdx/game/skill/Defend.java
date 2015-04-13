package com.mygdx.game.skill;

import com.mygdx.game.Entity;

public class Defend extends Skill{
    public Defend(Entity caster) {
        super("Defend", 2,  caster, 25, 2);
    }

    @Override
    public void execute() {
        if (caster.consume(energy)) {
            caster.defend(3);
            startCooldown();
        }
    }
}
