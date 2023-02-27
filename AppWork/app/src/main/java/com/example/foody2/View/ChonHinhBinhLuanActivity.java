package com.example.foody2.View;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foody2.Adapters.AdapterChonHinhBinhLuan;
import com.example.foody2.Model.ChonHinhBinhLuanModel;
import com.example.foody2.R;

import java.util.ArrayList;
import java.util.List;

public class ChonHinhBinhLuanActivity extends AppCompatActivity implements View.OnClickListener {

    List<ChonHinhBinhLuanModel >listDuongdan;
    List<String  >listHinhDuocChon;
    RecyclerView recyclerChonHinhBinhLuan;
    AdapterChonHinhBinhLuan adapterChonHinhBinhLuan;
    TextView txtXong;

    @SuppressLint("MissingInflatedId")
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_chonhinh_binhluan);

        listDuongdan = new ArrayList<>();
        listHinhDuocChon = new ArrayList<>();
        recyclerChonHinhBinhLuan = findViewById(R.id.recyclerChonHinhBinhLuan);

        recyclerChonHinhBinhLuan.setHasFixedSize(true);
        adapterChonHinhBinhLuan = new AdapterChonHinhBinhLuan(getApplicationContext(), listDuongdan, R.layout.custom_layout_chonhinhbinhluan);
        recyclerChonHinhBinhLuan.setLayoutManager(new GridLayoutManager(this,3));
        recyclerChonHinhBinhLuan.setAdapter(adapterChonHinhBinhLuan);
        txtXong = findViewById(R.id.txtXong);

        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }else {
            getHinhAnh();
        }

        txtXong.setOnClickListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==1){
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                getHinhAnh();
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void getHinhAnh(){
        String []projection = {MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

        Uri uri= MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        Cursor cursor = getContentResolver().query(uri,projection,null,null,null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            @SuppressLint("Range")
            String duongdan = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            ChonHinhBinhLuanModel chonHinhBinhLuanModel = new ChonHinhBinhLuanModel(duongdan,false);
            listDuongdan.add(chonHinhBinhLuanModel);
            adapterChonHinhBinhLuan.notifyDataSetChanged();
            cursor.moveToNext();
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.txtXong:
                for (ChonHinhBinhLuanModel value : listDuongdan){
                    if (value.isCheck()){
                        listHinhDuocChon.add(value.getDuondan());
                    }
                }
                Intent data = getIntent();
                data.putStringArrayListExtra("listHinhDuocChon", (ArrayList<String>) listHinhDuocChon);
                setResult(RESULT_OK,data);
                finish();
                break;
        }
    }
}
