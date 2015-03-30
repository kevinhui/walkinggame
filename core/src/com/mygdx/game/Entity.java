package com.mygdx.game;

public class Entity {
    private String name;
    private int health;
    private int strength;
    private int toughness;
    private int dexterity;
    private int concentration;

    public Entity(String name, int health, int strength, int toughness, int dexterity, int concentration) {
        this.name = name;
        this.health = health;
        this.strength = strength;
        this.toughness = toughness;
        this.dexterity = dexterity;
        this.concentration = concentration;
    }
}
