package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class GPSHandler{
    public static Entity getEntityByGPS(double[] position){
        if ((Math.abs(position[0]-22.33675)<=0.002&&(Math.abs(position[1]-114.172827)<=0.002))) {
            return new Entity("Prof. J", 10000, 80, 50, 3, new Texture("professor_J copy_transparent.png"), true);
        }
        return null;
    }
}

