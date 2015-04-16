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

    private TextView latitudeText;
    private TextView longitudeText;
    private TextView updateTime;
    private MapView mapView;

    private static Location location = null;

    private static final String TAG ="MainActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);


        latitudeText    = (TextView) findViewById(R.id.latitudeText);
        longitudeText   = (TextView) findViewById(R.id.longitudeText);
        updateTime      = (TextView) findViewById(R.id.time_updated);
        mapView         = (MapView)  findViewById(R.id.bmapView);


        location = Location.getInstance();
        location.addWatcher(this);
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
        latitudeText.setText("latitude: " + String.valueOf(location.getLatitude()));
        longitudeText.setText("longitude: " + String.valueOf(location.getLongitude()));
        updateTime.setText("Last Update: " + location.getDate());

        updateMapView();

    }

    private void updateMapView() {
        BaiduMap map = mapView.getMap();
        LatLng point = new LatLng(location.getLatitude(),location.getLongitude());

        Bitmap bitmapOrg = BitmapFactory.decodeResource(getResources(), R.drawable.star);
        Matrix matrix = new Matrix();
        matrix.postScale((float)20.0/bitmapOrg.getWidth(),(float)20.0/bitmapOrg.getHeight());
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0,
                bitmapOrg.getWidth(), bitmapOrg.getHeight(), matrix, true);

        //BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.star);

        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(resizedBitmap);

        OverlayOptions option = new MarkerOptions().position(point).icon(bitmapDescriptor);
        map.addOverlay(option);

        MapStatus mapStatus = new MapStatus.Builder().target(point).zoom(12).build();
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mapStatus);
        map.setMapStatus(mapStatusUpdate);
    }

    @Override
    public void update() {
        updateView();
    }


}
