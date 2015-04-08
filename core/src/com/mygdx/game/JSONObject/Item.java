package com.mygdx.game.JSONObject;

import com.google.gson.annotations.SerializedName;

public class Item {
    @SerializedName("name")
    private String name;
    @SerializedName("Type")
    private int Type;
    @SerializedName("basehealth")
    private int basehealth;
    @SerializedName("concentration")
    private int concentration;
    @SerializedName("strength")
    private int strength;
    @SerializedName("toughness")
    private int toughness;
    @SerializedName("id")
    private String id;

    public int getBasehealth() {
        return basehealth;
    }

    public int getConcentration() {
        return concentration;
    }

    public int getStrength() {
        return strength;
    }

    public int getToughness() {
        return toughness;
    }

    public int getType() {
        return Type;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setBasehealth(int basehealth) {
        this.basehealth = basehealth;
    }

    public void setConcentration(int concentration) {
        this.concentration = concentration;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setToughness(int toughness) {
        this.toughness = toughness;
    }

    public void setType(int type) {
        Type = type;
    }
}
