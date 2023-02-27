package com.example.foody2.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foody2.Adapters.AdapterBinhLuan;
import com.example.foody2.Controller.ChiTietQuanAnController;
import com.example.foody2.Controller.ThucDonController;
import com.example.foody2.Model.RestaurantModel;
import com.example.foody2.Model.TienIchModel;
import com.example.foody2.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class ChiTietQuanAnActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {

    TextView tongSoLuuLai,txtTenQuanAn,txtDiaChiQuanAn,txtThoiGianHoatDong,
            txtTrangThaiHoatDong,txtTongSoHinhAnh,txtTongSoCheckIn,txtTongSoBinhLuan,txtTieuDeToolBar,txtGioiHanGia,txtTenWifi,txtMatKhauWifi,txtNgayDangWifi;
    ImageView imageHinhQuanAn,imgPlayTrailer;
    RestaurantModel restaurantModel;
    Toolbar toolbar;
    RecyclerView recyclerBinhLuan,recyclerThucDon;
    AdapterBinhLuan adapterBinhLuan;
    GoogleMap googleMap;
    LinearLayout khungTienIch;
    LinearLayout khungWifi;
    ChiTietQuanAnController chiTietQuanAnController;
    View khungTinhNang;
    Button btnBinhLuan;
    VideoView videoView;
    ThucDonController thucDonController;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main_chitietquanan);


        restaurantModel =getIntent().getParcelableExtra("quanan");
        tongSoLuuLai =findViewById(R.id.tongSoLuuLai);
        txtTenQuanAn= findViewById(R.id.txtTenQuanAn);
        txtDiaChiQuanAn= findViewById(R.id.txtDiaChiQuanAn);
        txtThoiGianHoatDong= findViewById(R.id.txtThoiGianHoatDong);
        txtTrangThaiHoatDong= findViewById(R.id.txtTrangThaiHoatDong);
        txtTongSoHinhAnh= findViewById(R.id.txtTongSoHinhAnh);
        txtTongSoCheckIn= findViewById(R.id.txtTongSoCheckIn);
        txtTongSoBinhLuan= findViewById(R.id.txtTongSoBinhLuan);
        imageHinhQuanAn = findViewById(R.id.imageHinhQuanAn);
        txtTieuDeToolBar = findViewById(R.id.txtTieuDeToolBar);
        recyclerBinhLuan = findViewById(R.id.recyclerBinhLuanChiTietQuanAn);
        txtGioiHanGia = findViewById(R.id.txtGioiHanGia);
        toolbar = findViewById(R.id.toolbar);
        khungTienIch = findViewById(R.id.khungTienIch);
        txtMatKhauWifi = findViewById(R.id.txtMatKhauWifi);
        txtTenWifi = findViewById(R.id.txtTenWifi);
        khungWifi = findViewById(R.id.khungWifi);
        txtNgayDangWifi= findViewById(R.id.txtNgayDangWifi);
        khungTinhNang = findViewById(R.id.khungTinhNang);
        btnBinhLuan = findViewById(R.id.btnBinhLuan);
        videoView = findViewById(R.id.videoTrailer);
        imgPlayTrailer = findViewById(R.id.imgPlayTrailer);
        recyclerThucDon = findViewById(R.id.recyclerThucDon);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        Objects.requireNonNull(mapFragment).getMapAsync(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");


        chiTietQuanAnController = new ChiTietQuanAnController();
        thucDonController = new ThucDonController();
        khungTinhNang.setOnClickListener(this);
        btnBinhLuan.setOnClickListener(this);
        HienThiChiTietQuanAn();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @SuppressLint({"SimpleDateFormat", "NotifyDataSetChanged"})
    private void HienThiChiTietQuanAn(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

        String giohientai = dateFormat.format(calendar.getTime());
        String giomocua = restaurantModel.getGiomocua();
        String giodongcua = restaurantModel.getGiodongcua();

        try {
            Date dateHientai = dateFormat.parse(giohientai);
            Date dateMocua = dateFormat.parse(giomocua);
            Date dateDongcua = dateFormat.parse(giodongcua);

            if (dateHientai.after(dateMocua) && dateHientai.before(dateDongcua)){
                txtTrangThaiHoatDong.setText(R.string.dangmocua);

            }else {
                txtTrangThaiHoatDong.setText(R.string.dadongcua);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        txtTieuDeToolBar.setText(restaurantModel.getTenquanan());
        txtTenQuanAn.setText(restaurantModel.getTenquanan());
        txtDiaChiQuanAn.setText(restaurantModel.getChiNhanhQuanAnModelList().get(0).getDiachi());
        txtThoiGianHoatDong.setText(restaurantModel.getGiomocua()+" - "+restaurantModel.getGiodongcua());
        txtTongSoBinhLuan.setText(restaurantModel.getBinhLuanModelList().size()+"");
        txtTongSoHinhAnh.setText(String.format("%.0f",restaurantModel.getBinhLuanModelList().size() * 1.5)+"");

        StorageReference storageHinhanh = FirebaseStorage.getInstance().getReference().child("hinhanh/"+restaurantModel.getHinhanhquanan().get(0));
        long ONE_MEGABYTE = 1024 * 1024;
        storageHinhanh.getBytes(ONE_MEGABYTE).addOnSuccessListener(bytes -> {
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
            imageHinhQuanAn.setImageBitmap(bitmap);
        });

        DownloadHinhTienich();

        if (restaurantModel.getGiatoithieu()!=0 && restaurantModel.getGiatoida()!=0){
            NumberFormat numberFormat = new DecimalFormat("###,###");
            String giatoida = numberFormat.format(restaurantModel.getGiatoida())+ " đ";
            String giatoithieu = numberFormat.format(restaurantModel.getGiatoithieu())+ " đ";
            txtGioiHanGia.setText(giatoithieu+" - "+giatoida +"");
        }
        else {
            txtGioiHanGia.setVisibility(View.INVISIBLE);
        }

        imageHinhQuanAn.setImageResource(R.drawable.hinhtest);

        // kiểm tra quán ăn có video giới thiệu không

        if (restaurantModel.getVideogioithieu() !=null){
            videoView.setVisibility(View.VISIBLE);
            imgPlayTrailer.setVisibility(View.VISIBLE);
            imageHinhQuanAn.setVisibility(View.GONE);

            StorageReference storageVideo = FirebaseStorage.getInstance().getReference().child("video").child(restaurantModel.getVideogioithieu());
            storageVideo.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    videoView.setVideoURI(uri);
                    videoView.seekTo(1);
                }
            });
            imgPlayTrailer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    videoView.start();
                    MediaController mediaController = new MediaController(getApplicationContext());
                    videoView.setMediaController(mediaController);
                    imgPlayTrailer.setVisibility(View.GONE);
                }
            });
        }else {
            videoView.setVisibility(View.GONE);
            imgPlayTrailer.setVisibility(View.GONE);
            imageHinhQuanAn.setVisibility(View.VISIBLE);
        }

        // load ds bình luận
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerBinhLuan.setLayoutManager(layoutManager);
        adapterBinhLuan = new AdapterBinhLuan(this,R.layout.custom_layout_binhluan,restaurantModel.getBinhLuanModelList());
        recyclerBinhLuan.setAdapter(adapterBinhLuan);
        adapterBinhLuan.notifyDataSetChanged();

        NestedScrollView nestedScrollViewChiTiet = findViewById(R.id.nestedScrollViewChiTiet);
        nestedScrollViewChiTiet.smoothScrollTo(0,0);

        chiTietQuanAnController.HienThiDSWifiQuanAn(restaurantModel.getMaquanan(),txtTenWifi,txtMatKhauWifi,txtNgayDangWifi);
        khungWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iDanhSachWifi=  new Intent(getApplicationContext(),CapNhatDanhSachWifiActivity.class);
                iDanhSachWifi.putExtra("maquanan",restaurantModel.getMaquanan());
                startActivity(iDanhSachWifi);
            }
        });
        thucDonController.getDanhSachThucDonQuanAnTheoMa(this,restaurantModel.getMaquanan(),recyclerThucDon);
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale", "SimpleDateFormat", "NotifyDataSetChanged"})
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;

        double latitude =restaurantModel.getChiNhanhQuanAnModelList().get(0).getLatitude() ;
        double longitude = restaurantModel.getChiNhanhQuanAnModelList().get(0).getLongitude();

        LatLng sydney = new LatLng(latitude,longitude);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(sydney);
        markerOptions.title(restaurantModel.getTenquanan());
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(sydney,14);
        googleMap.moveCamera(cameraUpdate);


    }

    private void DownloadHinhTienich(){
        for (String matienich : restaurantModel.getTienich()){
            DatabaseReference nodeTienich = FirebaseDatabase.getInstance().getReference().child("quanlytienichs").child(matienich);
            nodeTienich.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    TienIchModel tienIchModel = snapshot.getValue(TienIchModel.class);

                    StorageReference storageHinhTienich = FirebaseStorage.getInstance().getReference().child("hinhtienich").child(tienIchModel.getHinhtienich());
                    long OME_MEGABYTE = 1024 * 1024;
                    storageHinhTienich.getBytes(OME_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Bitmap  bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                            ImageView imageHinhTienich = new ImageView(ChiTietQuanAnActivity.this);
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(50,50);
                            layoutParams.setMargins(10,10,10,10);
                            imageHinhTienich.setLayoutParams(layoutParams);
                            imageHinhTienich.setScaleType(ImageView.ScaleType.FIT_XY);
                            imageHinhTienich.setPadding(5,5,5,5);

                            imageHinhTienich.setImageBitmap(bitmap);
                            khungTienIch.addView(imageHinhTienich);
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.khungTinhNang:
                Intent intent = new Intent(getApplicationContext(), DanDuongQuanAnActivity.class);
                intent.putExtra("latitude", restaurantModel.getChiNhanhQuanAnModelList().get(0).getLatitude());
                intent.putExtra("longitude", restaurantModel.getChiNhanhQuanAnModelList().get(0).getLongitude());
                startActivity(intent);
                break;

            case R.id.btnBinhLuan:
                    Intent i = new Intent(getApplicationContext(),BinhLuanAcitivity.class);
                    i.putExtra("maquanan",restaurantModel.getMaquanan());
                    i.putExtra("tenquanan",restaurantModel.getTenquanan());
                    i.putExtra("diachi",restaurantModel.getChiNhanhQuanAnModelList().get(0).getDiachi());
                    startActivity(i);
                break;
        }



    }
}
