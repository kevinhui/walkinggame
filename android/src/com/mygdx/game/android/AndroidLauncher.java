package com.mygdx.game.android;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.ContextThemeWrapper;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.mygdx.game.NativeFunctions;
import com.mygdx.game.WalkingGame;

public class AndroidLauncher extends AndroidApplication implements NativeFunctions{
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new WalkingGame(this), config);
	}


    @Override
    public String getMacAddress() {
        WifiManager manager = (WifiManager) getSystemService(ContextThemeWrapper.WIFI_SERVICE);
        WifiInfo info = manager.getConnectionInfo();
        return info.getMacAddress();
    }
}
