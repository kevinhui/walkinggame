package com.mygdx.game.skill;

import com.mygdx.game.Entity;

public class Attack extends Skill{
    public Attack(Entity caster) {
        super("Attack", 1,  caster, 60, 3);
    }

    @Override
    public void execute() {
        if (ready && caster.consume(energy)) {
            target.dealDamage(caster.getStrength());
            startCooldown();
        }
    }
}
