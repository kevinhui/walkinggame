package com.mygdx.game;

public interface NativeFunctions {
    public String getMacAddress();
    public boolean isWifiEnabled();
    public void openScanReader();
    public String getQRreaderResult();
    public void resetQRreaderResult();
    public double getLongitude();

    void setUpGeoService();

    public double getLatitude();

    double[] getGeolocation();
}
