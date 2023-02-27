package com.example.foody2.View;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foody2.Adapters.AdapterRecyclerHinhBinhLuan;
import com.example.foody2.Model.BinhLuanModel;
import com.example.foody2.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HienThiChiTietHinhBinhLuanActivity extends AppCompatActivity {

    CircleImageView circleImageView;
    TextView txtTieudebinhluan,txtNoidungbinhluan,txtChamdiem;
    RecyclerView recyclerHinhBinhLuan;
    List<Bitmap> bitmapList;
    BinhLuanModel binhLuanModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_layout_binhluan);

        circleImageView = findViewById(R.id.circleImageUser);
        txtTieudebinhluan = findViewById(R.id.txtTieudebinhluan);
        txtNoidungbinhluan = findViewById(R.id.txtNoidungbinhluan);
        txtChamdiem = findViewById(R.id.txtChamdiem);
        recyclerHinhBinhLuan = findViewById(R.id.recyclerHinhBinhLuan);

         binhLuanModel = getIntent().getParcelableExtra("binhluanmodel");

        txtTieudebinhluan.setText(binhLuanModel.getTieude());
        txtNoidungbinhluan.setText(binhLuanModel.getNoidung());
        txtChamdiem.setText(binhLuanModel.getChamdiem());
        circleImageView.setImageResource(R.drawable.icon_user);

        bitmapList = new ArrayList<>();

        for (String linkhinh : binhLuanModel.getHinhanhBinhLuanList()){
            StorageReference storageHinhUser = FirebaseStorage.getInstance().getReference().child("hinhanh/"+linkhinh);
            long ONE_MEGABYTE = 1024 * 1024;
            storageHinhUser.getBytes(ONE_MEGABYTE).addOnSuccessListener(bytes -> {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                bitmapList.add(bitmap);

                if (bitmapList.size() == binhLuanModel.getHinhanhBinhLuanList().size()){
                    AdapterRecyclerHinhBinhLuan adapterRecyclerHinhBinhLuan = new
                            AdapterRecyclerHinhBinhLuan(HienThiChiTietHinhBinhLuanActivity.this,R.layout.custom_layout_hinhbinhluan,bitmapList,binhLuanModel,true);
                    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(HienThiChiTietHinhBinhLuanActivity.this,2);
                    recyclerHinhBinhLuan.setLayoutManager(layoutManager);
                    recyclerHinhBinhLuan.setAdapter(adapterRecyclerHinhBinhLuan);
                    adapterRecyclerHinhBinhLuan.notifyDataSetChanged();
                }
            });
        }
    }

    private  void setHinhAnhBinhLuan(CircleImageView circleImageView ,String linkhinh){
        StorageReference storageHinhUser = FirebaseStorage.getInstance().getReference().child("thanhvien/"+linkhinh);
        long ONE_MEGABYTE = 1024 * 1024;
        storageHinhUser.getBytes(ONE_MEGABYTE).addOnSuccessListener(bytes -> {
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
            circleImageView.setImageBitmap(bitmap);
        });
    }
}
