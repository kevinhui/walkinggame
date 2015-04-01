package com.mygdx.game.skill;

import com.mygdx.game.Entity;

public abstract class Skill {
    private final String name;
    private final int id;
    protected final Entity target;
    protected final Entity caster;

    protected Skill(String name, int id, Entity target, Entity caster) {
        this.name = name;
        this.id = id;
        this.target = target;
        this.caster = caster;
    }

    public abstract void execute();
}
