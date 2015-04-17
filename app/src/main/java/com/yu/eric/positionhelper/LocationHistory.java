package com.yu.eric.positionhelper;

import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lliyu on 4/17/2015.
 */
public class LocationHistory implements LocationWatcher {

    private static String TAG = "LocationHistory";

    private List<Location> locations;
    private int maxSize = 10;

    public List<Location> getLocations() {
        return locations;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public void add(Location location) {
        if (locations == null) {
            Log.i(TAG, "locations is null , init List");
            locations = new ArrayList<>();
        }
        if (locations.size() >= maxSize) {
            locations.remove(0);
        }
        locations.add(location);
    }

    public void clear() {
        if (locations != null) {
            Log.i(TAG, "clear : locations is not null");
            locations.clear();
        }
        Log.i(TAG, "location history has been cleared");
    }

    @Override
    public void update() {
        locations.add(new Location(LocationSensor.getInstance()));
        Toast.makeText(ContextProvider.getContext(), "history: " + locations.size(), Toast.LENGTH_SHORT).show();
    }
}
