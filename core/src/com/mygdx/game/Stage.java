package com.mygdx.game;

import com.google.gson.annotations.SerializedName;

public class Stage {
    @SerializedName("objectId")
    private String objectId;
    @SerializedName("StepsRequired")
    private int StepsRequired;
    @SerializedName("NextStage")
    private StagePointer NextStage;
    @SerializedName("Entity")
    private EntityObject Entity;

    public StagePointer getNextStage() {
        return NextStage;
    }

    public void setNextStage(StagePointer nextStage) {
        NextStage = nextStage;
    }

    public EntityObject getEntity() {
        return Entity;
    }

    public void setEntity(EntityObject entity) {
        Entity = entity;
    }


    public static class StagePointer{
//        public StagePointer(String objectId){
//            this.__type ="Pointer";
//            this.className = "Stage";
//            this.objectId = objectId;
//        }
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
    public static class EntityObject{
        @SerializedName("basehealth")
        private int basehealth;
        @SerializedName("concentration")
        private int concentration;
        @SerializedName("entityname")
        private String entityname;
        @SerializedName("spriteSheet")
        private String spriteSheet;
        @SerializedName("strength")
        private int strength;
        @SerializedName("toughness")
        private int toughness;

        public int getToughness() {
            return toughness;
        }

        public void setToughness(int toughness) {
            this.toughness = toughness;
        }

        public int getStrength() {
            return strength;
        }

        public void setStrength(int strength) {
            this.strength = strength;
        }

        public String getSpriteSheet() {
            return spriteSheet;
        }

        public void setSpriteSheet(String spriteSheet) {
            this.spriteSheet = spriteSheet;
        }

        public String getEntityname() {
            return entityname;
        }

        public void setEntityname(String entityname) {
            this.entityname = entityname;
        }

        public int getConcentration() {
            return concentration;
        }

        public void setConcentration(int concentration) {
            this.concentration = concentration;
        }

        public int getBasehealth() {
            return basehealth;
        }

        public void setBasehealth(int basehealth) {
            this.basehealth = basehealth;
        }
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public int getStepsRequired() {
        return StepsRequired;
    }

    public void setStepsRequired(int stepsRequired) {
        StepsRequired = stepsRequired;
    }
}
