package com.mygdx.game;

public interface NativeFunctions {
    public String getMacAddress();
    public boolean isNetworkEnabled();
    public void openScanReader();
    public String getQRreaderResult();
    public void resetQRreaderResult();
    public double[] getGeolocation();

    public int getStepCount();

    public void resetStepCount();

    public void enableStepCounter();

    public void disableStepCounter();
}
