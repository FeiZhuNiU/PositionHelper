package com.yu.eric.positionhelper;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;


public class MainActivity extends Activity implements LocationWatcher{

    private TextView latitudeText;
    private TextView longitudeText;
    private TextView updateTime;

    private static Location location = null;

    private static final String TAG ="MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        latitudeText = (TextView) findViewById(R.id.latitudeText);
        longitudeText = (TextView) findViewById(R.id.longitudeText);
        updateTime = (TextView) findViewById(R.id.time_updated);


        location = Location.getInstance();
        location.addWatcher(this);
        updateView();

//        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
//        LocationListener locationListener = new LocationListener() {
//            @Override
//            public void onLocationChanged(Location location) {
//
//                Log.i(TAG, "???" + location.getTime());
//                Log.i(TAG, "???"+location.getLongitude());
//                Log.i(TAG, "???"+location.getLatitude());
//                Log.i(TAG, "???"+location.getAltitude());
//
//                Toast.makeText(getApplicationContext(),"locationChanged", Toast.LENGTH_SHORT).show();
//                double latitude = location.getLatitude();
//                double longitude = location.getLongitude();
//
//                latitudeText.setText("latitude: " + String.valueOf(latitude));
//                longitudeText.setText("longitude: " + String.valueOf(longitude));
//            }
//
//            @Override
//            public void onStatusChanged(String provider, int status, Bundle extras) {
//                switch (status) {
//                    //GPS??????
//                    case LocationProvider.AVAILABLE:
//                        Log.i(TAG, "??GPS???????");
//                        break;
//                    //GPS????????
//                    case LocationProvider.OUT_OF_SERVICE:
//                        Log.i(TAG, "??GPS?????????");
//                        break;
//                    //GPS????????
//                    case LocationProvider.TEMPORARILY_UNAVAILABLE:
//                        Log.i(TAG, "??GPS?????????");
//                        break;
//                }
//                Toast.makeText(getApplicationContext(),"gps status changed", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onProviderEnabled(String provider) {
//                Toast.makeText(getApplicationContext(),"gps opened", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onProviderDisabled(String provider) {
//                Toast.makeText(getApplicationContext(),"gps Closed", Toast.LENGTH_SHORT).show();
//            }
//        };
//        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,locationListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void updateView(){
        latitudeText.setText("latitude: " + String.valueOf(location.getLatitude()));
        longitudeText.setText("longitude: " + String.valueOf(location.getLongitude()));
        updateTime.setText("Last Update: " + location.getDate());
    }

    @Override
    public void update() {
        updateView();
    }
}
