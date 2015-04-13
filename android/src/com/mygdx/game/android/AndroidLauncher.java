package com.mygdx.game.android;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.ContextThemeWrapper;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;
import com.mygdx.game.NativeFunctions;
import com.mygdx.game.WalkingGame;

public class AndroidLauncher extends AndroidApplication implements NativeFunctions, ConnectionCallbacks, OnConnectionFailedListener, SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mAccelerometerSensor;
    private float mLimit = 10;
    private float mLastValues[] = new float[3 * 2];
    private float mScale[] = new float[2];
    private float mYOffset;
    private float mLastDirections[] = new float[3 * 2];
    private float mLastExtremes[][] = {new float[3 * 2], new float[3 * 2]};
    private float mLastDiff[] = new float[3 * 2];
    private int mLastMatch = -1;
    private int stepCount = 0;
    private boolean stepCounterEnable = false;

    private String QRreaderResult;
    private Location location;
    private GoogleApiClient mGoogleApiClient;
    private double lot;
    private double lat;

    @Override
    public void openScanReader() {
        QRreaderResult = null;
        final IntentIntegrator integrator = new IntentIntegrator(AndroidLauncher.this);
        handler.post(new Runnable() {
            @Override
            public void run() {
                integrator.initiateScan();
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null) {
            QRreaderResult = scanResult.getContents();
        }
    }

    @Override
    public String getQRreaderResult() {
        return QRreaderResult;
    }

    @Override
    public void resetQRreaderResult() {
        QRreaderResult = null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int h = 480;
        mYOffset = h * 0.5f;
        mScale[0] = -(h * 0.5f * (1.0f / (SensorManager.STANDARD_GRAVITY * 2)));
        mScale[1] = -(h * 0.5f * (1.0f / (SensorManager.MAGNETIC_FIELD_EARTH_MAX)));
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        initialize(new WalkingGame(this), config);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Step Counter Method
        if (stepCounterEnable) {
            boolean a = mSensorManager.registerListener(this, mAccelerometerSensor, SensorManager.SENSOR_DELAY_FASTEST);
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        //Step Counter Method
        if (stepCounterEnable) {
            mSensorManager.unregisterListener(this, mAccelerometerSensor);
        }
    }

    @Override
    public String getMacAddress() {
        WifiManager manager = (WifiManager) getSystemService(ContextThemeWrapper.WIFI_SERVICE);
        WifiInfo info = manager.getConnectionInfo();
        return info.getMacAddress();
    }

    @Override
    public boolean isNetworkEnabled() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
        if (networkInfo != null)
            for (int i = 0; i < networkInfo.length; i++)
                if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
        return false;
    }

    @Override
    public void onConnected(Bundle bundle) {
        location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location != null) {
            lat = location.getLatitude();
            lot = location.getLongitude();
        }
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
    public double[] getGeolocation() {
        if (mGoogleApiClient == null) {
            buildGoogleApiClient();
        }
        mGoogleApiClient.connect();
        return new double[]{lat, lot};
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;
        float[] values = event.values;
        int value = -1;
        if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            // For test only. Only allowed value is 1.0 i.e. for step taken
            //  textView.setText("Step Detector Detected : " + value);

            Sensor sensorAcc = event.sensor;
            synchronized (this) {

                int j = (sensorAcc.getType() == Sensor.TYPE_ACCELEROMETER) ? 1 : 0;
                if (j == 1) {
                    float vSum = 0;
                    for (int i = 0; i < 3; i++) {
                        final float v = mYOffset + event.values[i] * mScale[j];
                        vSum += v;
                    }
                    int k = 0;
                    float v = vSum / 3;
                    float direction = (v > mLastValues[k] ? 1 : (v < mLastValues[k] ? -1 : 0));
                    if (direction == -mLastDirections[k]) {
// Direction changed
                        int extType = (direction > 0 ? 0 : 1); // minumum or maximum?
                        mLastExtremes[extType][k] = mLastValues[k];
                        float diff = Math.abs(mLastExtremes[extType][k] - mLastExtremes[1 - extType][k]);
                        if (diff > mLimit) {
                            boolean isAlmostAsLargeAsPrevious = diff > (mLastDiff[k] * 2 / 3);
                            boolean isPreviousLargeEnough = mLastDiff[k] > (diff / 3);
                            boolean isNotContra = (mLastMatch != 1 - extType);
                            if (isAlmostAsLargeAsPrevious && isPreviousLargeEnough && isNotContra) {
                                stepCount++;
                                mLastMatch = extType;
                            } else {
                                mLastMatch = -1;
                            }
                        }
                        mLastDiff[k] = diff;
                    }
                    mLastDirections[k] = direction;
                    mLastValues[k] = v;
                }
            }

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public int getStepCount() {
        return stepCount;
    }

    @Override
    public void resetStepCount() {
        stepCount = 0;
    }

    @Override
    public void enableStepCounter() {
        stepCounterEnable = true;
        boolean a = mSensorManager.registerListener(this, mAccelerometerSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    public void disableStepCounter() {
        stepCounterEnable = false;
        mSensorManager.unregisterListener(this, mAccelerometerSensor);
    }
}

