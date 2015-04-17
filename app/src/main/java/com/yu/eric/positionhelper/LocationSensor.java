package com.yu.eric.positionhelper;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lliyu on 4/15/2015.
 */
public class LocationSensor {

    private List<LocationWatcher> watchers;

    private String TAG = "Location";

    private static final LocationSensor LOCATION_SENSOR = new LocationSensor(ContextProvider.getContext());


    private double latitude = 0;
    private double longitude = 0;
    private String city;
    private String time;

    private BDLocation baidLocation = null;
    private LocationHistory locationHistory;

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

    public String getCity() {
        return city;
    }

    private LocationSensor(final Context context){

        watchers = new ArrayList<>();
        locationHistory = new LocationHistory();

        BDLocationListener bdLocationListener = new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                baidLocation = bdLocation;
                Log.i(TAG, "location changed");
                Log.i(TAG, "latitude: " + baidLocation.getLatitude());
                Log.i(TAG, "longitude: " + baidLocation.getLongitude());
                latitude = baidLocation.getLatitude() + 0.006;
                longitude = baidLocation.getLongitude() + 0.0065;
                time = baidLocation.getTime();
                city = baidLocation.getCity();



                Toast.makeText(context, "location Changed", Toast.LENGTH_SHORT).show();

                notifyWatchers();
            }

        };
        LocationClient locationClient = new LocationClient(context);
        locationClient.registerLocationListener(bdLocationListener);
        locationClient.start();
    }


    /*
    *   Singleton pattern
    *
    *    http://blog.csdn.net/jason0539/article/details/23297037
    *
    * */
    public static LocationSensor getInstance(){
        return LOCATION_SENSOR;
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
