package com.mygdx.game.skill;

import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.Entity;

public abstract class Skill {
    private final String name;
    private final int id;
    protected Entity target;
    protected final Entity caster;
    protected final int energy;
    protected final int cooldown;
    protected boolean ready;

    protected Skill(String name, int id, Entity caster, int energy, int cooldown) {
        this.name = name;
        this.id = id;
        this.caster = caster;
        this.energy = energy;
        this.cooldown = cooldown;
        ready = true;
    }

    protected void startCooldown(){
        ready = false;
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                ready = true;
            }
        }, cooldown);
    }

    public String getName() {
        return name;
    }

    public int getCooldown() {
        return cooldown;
    }

    public abstract void execute();

    public boolean canExecute(){
        return (caster.getEnergy()>=energy);
    }

    public void setTarget(Entity target) {
        this.target = target;
    }

    public boolean isReady() {
        return ready;
    }
}
