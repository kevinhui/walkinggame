package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("objectId")
    private String objectId;
    @SerializedName("userID")
    private String userID;
    @SerializedName("Steps")
    private int Steps;
    @SerializedName("basehealth")
    private int basehealth;
    @SerializedName("strength")
    private int strength;
    @SerializedName("toughness")
    private int toughness;
    @SerializedName("concentration")
    private int concentration;
    @SerializedName("Stage")
    private Stage stage;
    @SerializedName("MacAddress")
    private String MacAddress;
    @SerializedName("spriteSheet")
    private String spriteSheet;

    public User(String macAddress,String userID){
        this.MacAddress = macAddress;
        this.userID = userID;
        Steps = 0;
        basehealth = 100;
        strength = 25;
        toughness = 10;
        concentration = 5;
        stage = new Stage("ATEI6xiisE");
        spriteSheet = "chricky spritnew.png";
    }
    public User(){}
    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setBasehealth(int basehealth) {
        this.basehealth = basehealth;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setToughness(int toughness) {
        this.toughness = toughness;
    }

    public void setConcentration(int concentration) {
        this.concentration = concentration;
    }

    public String getMacAddress() {
        return MacAddress;
    }

    public void setMacAddress(String macAddress) {
        MacAddress = macAddress;
    }

    public void use(Consumable consumable) {
        basehealth += consumable.getBasehealth();
        strength += consumable.getStrength();
        toughness += consumable.getToughness();
        concentration += consumable.getConcentration();
    }

    public String getSpriteSheet() {
        return spriteSheet;
    }

    public void setSpriteSheet(String spriteSheet) {
        this.spriteSheet = spriteSheet;
    }

    public static class Stage{
        public Stage(String objectId){
            this.__type ="Pointer";
            this.className = "Stage";
            this.objectId = objectId;
        }
        @SerializedName("__type")
        private String __type;
        @SerializedName("className")
        private String className;
        @SerializedName("objectId")
        private String objectId;

        public String get__type() {
            return __type;
        }

        public void set__type(String __type) {
            this.__type = __type;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public String getObjectId() {
            return objectId;
        }

        public void setObjectId(String objectId) {
            this.objectId = objectId;
        }
    }

    public int getSteps() {
        return Steps;
    }

    public void setSteps(int steps) {
        Steps = steps;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public Entity createEntity(){
        return new Entity(userID,basehealth,strength,toughness,concentration,new Texture(spriteSheet),false);
    }

}
