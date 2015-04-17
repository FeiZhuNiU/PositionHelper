package com.yu.eric.positionhelper;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;


public class MainActivity extends Activity implements LocationWatcher{

    private TextView locationText;
    private TextView updateTimeText;
    private MapView mapView;

    private static LocationSensor locationSensor = null;
    private LocationHistory locationHistory;

    private static final String TAG ="MainActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);


        locationText    = (TextView) findViewById(R.id.locationText);
        updateTimeText  = (TextView) findViewById(R.id.updateTimeText);
        mapView         = (MapView)  findViewById(R.id.bmapView);


        locationSensor = LocationSensor.getInstance();
        locationHistory = new LocationHistory();
        locationSensor.addWatcher(locationHistory);
        locationSensor.addWatcher(this);

        updateView();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
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

        //update textView
        if(locationSensor != null ) {
            locationText.setText("location: " + locationSensor.getCity() + " " + String.format("%.3f", locationSensor.getLatitude()) + "/" + String.format("%.3f", locationSensor.getLongitude()));
            updateTimeText.setText("Last Update: " + locationSensor.getTime() + "    history:" + (locationHistory.getLocations() == null ? "" : locationHistory.getLocations().size()));
        }

        //update Map View
        updateMapView();

    }

    private void updateMapView() {

        BaiduMap map = mapView.getMap();

//        //tag star.png at system detected location
//        tagMap(map, point, R.drawable.star);
//        animateMapToPoint(map, point);

        // tag simpson.png at baidu detected location
        LatLng point = new LatLng(locationSensor.getLatitude(), locationSensor.getLongitude());
        tagMap(map, point, R.drawable.simpson);
        animateMapToPoint(map, point);

    }

    private void animateMapToPoint(BaiduMap map, LatLng point) {
        MapStatus mapStatus = new MapStatus.Builder().target(point).build();
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mapStatus);
        map.animateMapStatus(mapStatusUpdate);
    }

    private void tagMap(BaiduMap map, LatLng point, int picRes) {

        Bitmap bitmapOrg_star = BitmapFactory.decodeResource(getResources(), picRes);
        Matrix matrix = new Matrix();
        matrix.postScale((float) 30.0 / bitmapOrg_star.getWidth(), (float) 30.0 / bitmapOrg_star.getHeight());
        Bitmap resizedBitmap_star = Bitmap.createBitmap(bitmapOrg_star, 0, 0,
                bitmapOrg_star.getWidth(), bitmapOrg_star.getHeight(), matrix, true);

        //BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.star);
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(resizedBitmap_star);
        OverlayOptions option = new MarkerOptions().position(point).icon(bitmapDescriptor);
        map.addOverlay(option);
    }

    @Override
    public void update() {
        updateView();
    }


}
