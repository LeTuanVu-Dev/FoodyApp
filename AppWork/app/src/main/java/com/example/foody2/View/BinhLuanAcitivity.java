package com.example.foody2.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foody2.Adapters.AdapterHienThiHinhBinhLuanDuocChon;
import com.example.foody2.Controller.BinhLuanController;
import com.example.foody2.Model.BinhLuanModel;
import com.example.foody2.R;

import java.util.ArrayList;
import java.util.List;

public class BinhLuanAcitivity extends AppCompatActivity implements View.OnClickListener {
    TextView txtTenQuanAn,txtDiaChiQuanAn,txtDangBinhLuan;
    Toolbar toolbar;
    EditText edtTieuDeBinhLuan,edtNoiDungBinhLuan;
    ImageButton btnChonHinh;
    RecyclerView recyclerChonHinhBinhLuan;
    AdapterHienThiHinhBinhLuanDuocChon adapterHienThiHinhBinhLuanDuocChon;
    String maquanan;
    SharedPreferences sharedPreferences;
    BinhLuanController binhLuanController;
    List<String> listHinhDuocChon;

    public final int REQUEST_CHONHINHBINHLUAN = 11;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_binhluan);

         maquanan = getIntent().getStringExtra("maquanan");
        String tenquanan = getIntent().getStringExtra("tenquanan");
        String diachi = getIntent().getStringExtra("diachi");

        sharedPreferences = getSharedPreferences("luudangnhap",MODE_PRIVATE);

        txtDiaChiQuanAn = findViewById(R.id.txtDiaChiQuanAn);
        txtTenQuanAn = findViewById(R.id.txtTenQuanAn);
        txtDangBinhLuan = findViewById(R.id.txtDangBinhLuan);

        edtTieuDeBinhLuan = findViewById(R.id.edtTieuDeBinhLuan);
        edtNoiDungBinhLuan = findViewById(R.id.edtNoiDungBinhLuan);
        toolbar = findViewById(R.id.toolbar);
        btnChonHinh = findViewById(R.id.btnChonHinh);
        recyclerChonHinhBinhLuan = findViewById(R.id.recyclerChonHinhBinhLuan);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
        recyclerChonHinhBinhLuan.setLayoutManager(layoutManager);

        binhLuanController = new BinhLuanController();
        listHinhDuocChon = new ArrayList<>();
        txtDiaChiQuanAn.setText(diachi);
        txtTenQuanAn.setText(tenquanan);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        btnChonHinh.setOnClickListener(this);
        txtDangBinhLuan.setOnClickListener(this);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.btnChonHinh:
                Intent intent = new Intent(getApplicationContext(),ChonHinhBinhLuanActivity.class);
                startActivityForResult(intent,REQUEST_CHONHINHBINHLUAN);
                break;
            case R.id.txtDangBinhLuan:
                BinhLuanModel binhLuanModel = new BinhLuanModel();
                String tieude = edtTieuDeBinhLuan.getText().toString();
                String noidung = edtNoiDungBinhLuan.getText().toString();
                String mauser = sharedPreferences.getString("mauser","");

                binhLuanModel.setTieude(tieude);
                binhLuanModel.setNoidung(noidung);
                binhLuanModel.setChamdiem("0");
                binhLuanModel.setLuotthich("0");
                binhLuanModel.setMauser(mauser);

                binhLuanController.ThemBinhLuan(maquanan,binhLuanModel,listHinhDuocChon);
              //  startActivity(new Intent(this,ChiTietQuanAnActivity.class));
                break;
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CHONHINHBINHLUAN){
            if (resultCode==RESULT_OK){

                listHinhDuocChon = data.getStringArrayListExtra("listHinhDuocChon");
                adapterHienThiHinhBinhLuanDuocChon = new AdapterHienThiHinhBinhLuanDuocChon(this,
                        R.layout.custom_layout_hienthihinhbinhluanduocchon,listHinhDuocChon);
                recyclerChonHinhBinhLuan.setAdapter(adapterHienThiHinhBinhLuanDuocChon);
                adapterHienThiHinhBinhLuanDuocChon.notifyDataSetChanged();
            }
        }
    }

}
