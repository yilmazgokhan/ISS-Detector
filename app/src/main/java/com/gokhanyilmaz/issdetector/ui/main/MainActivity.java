package com.gokhanyilmaz.issdetector.ui.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.gokhanyilmaz.issdetector.R;
import com.gokhanyilmaz.issdetector.databinding.ActivityMainBinding;
import com.gokhanyilmaz.issdetector.util.Constant;
import com.huawei.hms.maps.CameraUpdateFactory;
import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.MapsInitializer;
import com.huawei.hms.maps.OnMapReadyCallback;
import com.huawei.hms.maps.model.BitmapDescriptorFactory;
import com.huawei.hms.maps.model.LatLng;
import com.huawei.hms.maps.model.Marker;
import com.huawei.hms.maps.model.MarkerOptions;
import com.huawei.hms.maps.model.Polyline;
import com.huawei.hms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainContract.View, OnMapReadyCallback {

    private ActivityMainBinding binding;
    private MainPresenter presenter;
    private HuaweiMap huaweiMap;

    private Marker marker;
    private Polyline polyline;
    private List<LatLng> polylineList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        presenter = new MainPresenter(this);

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(Constant.MAP_BUNDLE);
        }
        initHuaweiMap(mapViewBundle);
    }

    @Override
    public void onMapReady(HuaweiMap huaweiMap) {
        Log.w(Constant.TAG, "onMapReady : ");
        this.huaweiMap = huaweiMap;
        presenter.mapReady();
    }

    @Override
    public void initHuaweiMap(Bundle bundle) {
        Log.w(Constant.TAG, "initHuaweiMap : ");
        MapsInitializer.setApiKey(Constant.MAP_KEY);
        binding.mapView.onCreate(bundle);
        binding.mapView.getMapAsync(this);
    }

    @Override
    public void initMarkerTrackListener() {
        binding.checkBoxTrack.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Log.w(Constant.TAG, "onCheckedChanged : " + isChecked);
            presenter.trackMarkerChanged(isChecked);
        });
    }

    @Override
    public void setMarker(LatLng latLng) {
        Log.w(Constant.TAG, "setMarker ");
        if (marker != null)
            marker.remove();
        MarkerOptions options = new MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_iss));
        marker = huaweiMap.addMarker(options);
    }

    @Override
    public void drawRoute(LatLng latLng) {
        if (huaweiMap == null) return;
        if (polyline == null) {
            polyline = huaweiMap.addPolyline(new PolylineOptions()
                    .add(latLng)
                    .color(getColor(R.color.colorAccent))
                    .width(3));
            polylineList = new ArrayList<>();
            polylineList.add(latLng);
        } else {
            polylineList.add(latLng);
            polyline.setPoints(polylineList);
        }
    }

    @Override
    public void moveCamera(LatLng latLng) {
        Log.w(Constant.TAG, "moveCamera ");
        huaweiMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, huaweiMap.getCameraPosition().zoom));
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        binding.mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        binding.mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        binding.mapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding.mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        binding.mapView.onLowMemory();
    }
}