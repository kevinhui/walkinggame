package com.mygdx.game;

import com.badlogic.gdx.utils.Timer;

public class Entity {
    private String name;
    private int health;
    private int strength;
    private int toughness;
    private int dexterity;
    private int concentration;
    private boolean defending;

    public Entity(String name, int health, int strength, int toughness, int dexterity, int concentration) {
        this.name = name;
        this.health = health;
        this.strength = strength;
        this.toughness = toughness;
        this.dexterity = dexterity;
        this.concentration = concentration;
        this.defending = false;
    }

    public int getStrength() {
        return strength;
    }

    public void dealDamage(int strength) {
        if (defending)
            health = health - (strength - 4*toughness/5);
        else
            health = health - (strength - toughness/3);
    }

    public void defend(float second){
        defending = true;
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                defending = false;
            }
        },second);
    }
}
