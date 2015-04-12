package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.skill.Attack;
import com.mygdx.game.skill.Blast;
import com.mygdx.game.skill.Defend;
import com.mygdx.game.skill.Skill;

public class Entity {
    private final int FRAME_COL = 8;
    public final int WALK_STATE = 0;
    public final int ATTACK_STATE = 1;
    public final int INJURE_STATE = 2;
    private boolean stateChanged;
    private int currentState;
    private String name;
    private final int basehealth;
    private int currenthealth;
    private int strength;
    private int toughness;
    private int concentration;
    private boolean defending;
    private int energy;
    private Array<Skill> skills;
    private TextureRegion[] walkFrames;
    private TextureRegion[] attackFrames;
    private TextureRegion[] injureFrames;
    private boolean isAI;

    public Entity(Stage.EntityObject entityObject,boolean isAI){
        this.name = entityObject.getEntityname();
        this.basehealth = entityObject.getBasehealth();
        this.currenthealth = this.basehealth;
        this.strength = entityObject.getStrength();
        this.toughness = entityObject.getToughness();
        this.concentration = entityObject.getConcentration();
        this.defending = false;
        energy = 0;
        skills = new Array<>();
        skills.add(new Attack(this));
        skills.add(new Defend(this));
        skills.add(new Blast(this));
        Texture spriteSheet = new Texture(entityObject.getSpriteSheet());
        TextureRegion[][] tmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth() / FRAME_COL, spriteSheet.getHeight());
        walkFrames = new TextureRegion[3];
        attackFrames = new TextureRegion[3];
        injureFrames = new TextureRegion[2];
        for (int i = 0; i < 3; i++) {
            walkFrames[i] = tmp[0][i];
            if (isAI) walkFrames[i].flip(true, false);
        }
        for (int i = 3; i < 6; i++) {
            attackFrames[i - 3] = tmp[0][i];
            if (isAI) attackFrames[i - 3].flip(true, false);
        }
        for (int i = 6; i < 8; i++) {
            injureFrames[i - 6] = tmp[0][i];
            if (isAI) injureFrames[i - 6].flip(true, false);
        }
        currentState = WALK_STATE;
        stateChanged = false;

        this.isAI = isAI;
    }
    public Entity(String name, int basehealth, int strength, int toughness, int concentration, Texture spriteSheet, boolean isAI) {
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
        TextureRegion[][] tmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth() / FRAME_COL, spriteSheet.getHeight());
        walkFrames = new TextureRegion[3];
        attackFrames = new TextureRegion[3];
        injureFrames = new TextureRegion[2];
        for (int i = 0; i < 3; i++) {
            walkFrames[i] = tmp[0][i];
            if (isAI) walkFrames[i].flip(true, false);
        }
        for (int i = 3; i < 6; i++) {
            attackFrames[i - 3] = tmp[0][i];
            if (isAI) attackFrames[i - 3].flip(true, false);
        }
        for (int i = 6; i < 8; i++) {
            injureFrames[i - 6] = tmp[0][i];
            if (isAI) injureFrames[i - 6].flip(true, false);
        }
        currentState = WALK_STATE;
        stateChanged = false;

        this.isAI = isAI;
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
        if (defending) {
            if (strength - 4 * toughness / 5 > 0)
                currenthealth -= (strength - 4 * toughness / 5);
        } else {
            if (strength - toughness / 3 > 0)
                currenthealth = currenthealth - (strength - toughness / 3);
        }
    }

    public void defend(float second) {
        defending = true;
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                defending = false;
            }
        }, second);
    }

    public boolean consume(int en) {
        if (energy >= en) {
            energy -= en;
            return true;
        }
        return false;
    }

    public void addEnergy(int en) {
        energy += en;
    }

    public boolean isDead() {
        return (currenthealth <= 0);
    }

    public float getHealthPercentage() {
        return (float) currenthealth / basehealth;
    }

    public TextureRegion[] getWalkFrames() {
        return walkFrames;
    }

    public TextureRegion[] getAttackFrames() {
        return attackFrames;
    }

    public TextureRegion[] getInjureFrames() {
        return injureFrames;
    }

    public boolean isStateChanged() {
        boolean tmp = stateChanged;
        stateChanged = false;
        return tmp;
    }

    public int getCurrentState() {
        return currentState;
    }

    public void changeState(int state) {
        currentState = state;
        stateChanged = true;
    }
}

