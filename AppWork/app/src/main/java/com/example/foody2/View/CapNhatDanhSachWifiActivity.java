package com.example.foody2.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foody2.Controller.CapNhatWifiController;
import com.example.foody2.R;

public class CapNhatDanhSachWifiActivity extends AppCompatActivity {

    RecyclerView recyclerDanhSachWifi;
    Button btnCapNhatWifi;
    CapNhatWifiController capNhatWifiController;
    String maquanan;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_capnhatdanhsachwifi);

        recyclerDanhSachWifi = findViewById(R.id.recyclerDanhSachWifi);
        btnCapNhatWifi = findViewById(R.id.btnCapNhatWifi);

         maquanan = getIntent().getStringExtra("maquanan");

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,true);
        recyclerDanhSachWifi.setLayoutManager(layoutManager);

        capNhatWifiController = new CapNhatWifiController(this);
        capNhatWifiController.HienThiDanhSachWifi(maquanan,recyclerDanhSachWifi);

        btnCapNhatWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),PopupCapNhatWifiActivity.class);
                intent.putExtra("maquanan",maquanan);
                startActivity(intent);
            }
        });

    }
}
