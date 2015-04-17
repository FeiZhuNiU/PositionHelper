package com.yu.eric.positionhelper;

import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by lliyu on 4/15/2015.
 */
public class Location {

    private List<LocationWatcher> watchers;

    private String TAG = "Location";

    private static final Location location = new Location(ContextProvider.getContext());


    private double latitude = 0;
    private double longitude = 0;
    private String time;

    private BDLocation baidLocation = null;

    public String getTime() {
        return time;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public BDLocation getBaidLocation() {
        return baidLocation;
    }

    private Location(final Context context){

        watchers = new ArrayList<>();

//        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
//        LocationListener locationListener = new LocationListener() {
//            @Override
//            public void onLocationChanged(android.location.Location location) {
//
//                Log.i(TAG, "locationChanged:");
//                Log.i(TAG, "???" + location.getTime());
//                Log.i(TAG, "???" + location.getLongitude());
//                Log.i(TAG, "???" + location.getLatitude());
//                Log.i(TAG, "???" + location.getAltitude());
//
//
//                Toast.makeText(context, "locationChanged", Toast.LENGTH_SHORT).show();
//                latitude = location.getLatitude();
//                longitude = location.getLongitude();
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTimeInMillis(location.getTime());
//                date = calendar.getTime();
//                notifyWatchers();
//            }
//
//            @Override
//            public void onStatusChanged(String provider, int status, Bundle extras) {
//
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
//                Toast.makeText(context," status changed", Toast.LENGTH_SHORT).show();
//
//            }
//
//            @Override
//            public void onProviderEnabled(String provider) {
//                Toast.makeText(context,"provider enabled", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onProviderDisabled(String provider) {
//                Toast.makeText(context,"provider disabled", Toast.LENGTH_SHORT).show();
//            }
//        };
//        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);


        BDLocationListener bdLocationListener = new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                baidLocation = bdLocation;
                latitude = baidLocation.getLatitude();
                longitude = baidLocation.getLongitude();
                time = baidLocation.getTime();
                Toast.makeText(context, "baidulocationChanged", Toast.LENGTH_SHORT).show();
                notifyWatchers();
            }
        };
        LocationClient locationClient = new LocationClient(context);
        locationClient.registerLocationListener(bdLocationListener);
        locationClient.start();
    }


    /*
    * ???????????????
    * ???????
    * ???   http://blog.csdn.net/jason0539/article/details/23297037
    *
    * */
    public static Location getInstance(){
        return location;
    }

    public void addWatcher(LocationWatcher watcher){
        watchers.add(watcher);
    }


    private void notifyWatchers(){
        for(LocationWatcher locationWatcher : watchers){
            locationWatcher.update();
        }
    }



}
