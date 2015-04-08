package com.mygdx.game;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.skill.Attack;
import com.mygdx.game.skill.Blast;
import com.mygdx.game.skill.Defend;
import com.mygdx.game.skill.Skill;

public class Entity {
    private String name;
    private final int basehealth;
    private int currenthealth;
    private int strength;
    private int toughness;
    private int concentration;
    private boolean defending;
    private int energy;
    private Array<Skill> skills;
//    private Array<Item> items;

    public Entity(String name, int basehealth, int strength, int toughness, int concentration) {
        this.name = name;
        this.basehealth = basehealth;
        this.currenthealth = this.basehealth;
        this.strength = strength;
        this.toughness = toughness;
        this.concentration = concentration;
        this.defending = false;
        energy = 0;
        skills = new Array<>();
        skills.add(new Attack(this));
        skills.add(new Defend(this));
        skills.add(new Blast(this));
    }

    public String getName() {
        return name;
    }

    public int getStrength() {
        return strength;
    }

    public int getToughness() {
        return toughness;
    }

    public int getConcentration() {
        return concentration;
    }

    public int getEnergy() {
        return energy;
    }

    public Array<Skill> getSkills() {
        return skills;
    }

    public void dealDamage(int strength) {
        if (defending)
            currenthealth = currenthealth - (strength - 4*toughness/5);
        else
            currenthealth = currenthealth - (strength - toughness/3);
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

    public boolean consume(int en){
        if (energy>=en){
            energy -= en;
            return true;
        }
        return false;
    }

    public void addEnergy(int en){
        energy += en;
    }

    public boolean isDead(){
        return (currenthealth<=0);
    }

    public float getHealthPercentage(){
        return (float) currenthealth/basehealth;
    }

}
