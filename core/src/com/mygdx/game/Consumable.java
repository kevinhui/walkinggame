package com.mygdx.game;

import com.google.gson.annotations.SerializedName;

public class Consumable {
    @SerializedName("objectId")
    private String objectId;
    @SerializedName("name")
    private String name;
    @SerializedName("basehealth")
    private int basehealth;
    @SerializedName("strength")
    private int strength;
    @SerializedName("toughness")
    private int toughness;
    @SerializedName("concentration")
    private int concentration;
    @SerializedName("ImagePath")
    private String ImagePath;

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBasehealth() {
        return basehealth;
    }

    public void setBasehealth(int basehealth) {
        this.basehealth = basehealth;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getToughness() {
        return toughness;
    }

    public void setToughness(int toughness) {
        this.toughness = toughness;
    }

    public int getConcentration() {
        return concentration;
    }

    public void setConcentration(int concentration) {
        this.concentration = concentration;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }
}
