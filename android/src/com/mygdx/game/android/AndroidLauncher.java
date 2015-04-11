package com.mygdx.game.android;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.ContextThemeWrapper;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.mygdx.game.NativeFunctions;
import com.mygdx.game.WalkingGame;

public class AndroidLauncher extends AndroidApplication implements NativeFunctions, ConnectionCallbacks, OnConnectionFailedListener{
    private Handler uiThread;
    private String QRreaderResult;
    private LocationManager mLocationMgr;
    private String provider;
    private Location location;
    private GoogleApiClient mGoogleApiClient;
    private double lot;
    private double lat;


//    @Override
//    public void openScanReader(){
//        final IntentIntegrator integrator = new IntentIntegrator(new Activity(){
//            public void onActivityResult(int requestCode, int resultCode, Intent intent)  {
//                IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
//                CharSequence text;
//                if (scanResult != null) {
//
//                    text = scanResult.getContents();
//                    if (text == null || text.length()==0){text= "Sorry!! Try Again!!";}
//                    else {
//                        int seed=0;
//                        for(int i=0;i<text.length();i++) {
//                            seed += (int)text.charAt(i);
//                        }
//                        text = text + " " +Integer.toString(seed);
//                        QRreaderResult = text;
//                    }
//                } else {
//                    text = "Sorry!! Try Again!!";
//                }
//            }
//
//        });
//        integrator.initiateScan();
//    }
    @Override
    public void openScanReader(){
        QRreaderResult = null;
        final IntentIntegrator integrator = new IntentIntegrator(AndroidLauncher.this);
        handler.post(new Runnable() {
            @Override
            public void run() {
                integrator.initiateScan();
            }
        });
    }
    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,intent);
        if (scanResult != null){
            QRreaderResult = scanResult.getContents();
        }
    }

    @Override
    public String getQRreaderResult(){return QRreaderResult;}

    @Override
    public void resetQRreaderResult() {
        QRreaderResult = null;
    }

    @Override
    public void setUpGeoService(){
        mLocationMgr = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        boolean enabled = mLocationMgr.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (!enabled) {
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        }
        Criteria criteria = new Criteria();
        provider = mLocationMgr.getBestProvider(criteria,false);
        location = mLocationMgr.getLastKnownLocation(provider);
    }

    @Override
    public double getLatitude(){
        if (mLocationMgr == null){setUpGeoService();}
        return location.getLatitude();
    }
    @Override
    public double getLongitude(){
        if (mLocationMgr == null){setUpGeoService();}
        if (location == null) return 0;
        return location.getLongitude();
    }




	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new WalkingGame(this), config);
        uiThread = new Handler();
	}


    @Override
    public String getMacAddress() {
        WifiManager manager = (WifiManager) getSystemService(ContextThemeWrapper.WIFI_SERVICE);
        WifiInfo info = manager.getConnectionInfo();
        return info.getMacAddress();
    }

    @Override
    public boolean isWifiEnabled() {
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (networkInfo!= null && networkInfo.isConnected()){
            return true;
        } else{
            return false;
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        lat = location.getLatitude();
        lot = location.getLongitude();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }
    @Override
    public double[] getGeolocation(){
       if(mGoogleApiClient==null){buildGoogleApiClient();}
        mGoogleApiClient.connect();
        return new double[]{lat,lot};
    }
}
