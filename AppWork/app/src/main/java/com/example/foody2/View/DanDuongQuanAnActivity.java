package com.example.foody2.View;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foody2.Controller.DanDuongQuanAnController;
import com.example.foody2.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class DanDuongQuanAnActivity extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap googleMap;
    double latitude = 0,longitude = 0;
    Location vitrihientai;
    SharedPreferences sharedPreferences;
    DanDuongQuanAnController danDuongQuanAnController;
    String duongdan = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_danduong);

        danDuongQuanAnController = new DanDuongQuanAnController();

         latitude = getIntent().getDoubleExtra("latitude",0);
         longitude = getIntent().getDoubleExtra("longitude",0);

        sharedPreferences = getSharedPreferences("toado", Context.MODE_PRIVATE);
         vitrihientai = new Location("");
        vitrihientai.setLatitude(sharedPreferences.getFloat("latitude",0));
        vitrihientai.setLongitude(sharedPreferences.getFloat("longitude",0));

         duongdan = "https://maps.googleapis.com/maps/api/directions/json?origin=" + vitrihientai.getLatitude() + "," + vitrihientai.getLongitude() + "&destination="+latitude+","+longitude+"&key=AIzaSyAk0MCJXI4AmSbWxi61VwTr9UTjiHu9gew";
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        //LatLng sydney = new LatLng(vitrihientai.getLatitude(), vitrihientai.getLongitude());
        LatLng sydney = new LatLng(latitude, longitude);
        googleMap.addMarker(new MarkerOptions()
                .position(sydney));

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,14));

        danDuongQuanAnController.HienThiDanDuongQuanAn(googleMap,duongdan);
    }
}
