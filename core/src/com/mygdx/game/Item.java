package com.mygdx.game;

public class Item extends Entity{
    private boolean isEquipped;
    public Item(String name,  int strength, int toughness, int dexterity, int concentration) {
        super(name, 0, strength, toughness, dexterity, concentration);
        isEquipped = false;
    }

    public void setEquipped(boolean isEquipped) {
        this.isEquipped = isEquipped;
    }
}
