package com.example.foody2.Model;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.foody2.Controller.Interfaces.ChiTietQuanAnInterface;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WifiQuanAnModel {
    String ten , matkhau , ngaydang;

    public WifiQuanAnModel() {
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public String getNgaydang() {
        return ngaydang;
    }

    public void setNgaydang(String ngaydang) {
        this.ngaydang = ngaydang;
    }

    private DatabaseReference nodeWifiQuanAn;

    public void LayDanhSachWifiQuanAn(String maquanan, ChiTietQuanAnInterface chiTietQuanAnInterface){
        nodeWifiQuanAn = FirebaseDatabase.getInstance().getReference().child("wifiquanans").child(maquanan);
        nodeWifiQuanAn.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot valueWifi : snapshot.getChildren()){
                    WifiQuanAnModel wifiQuanAnModel = valueWifi.getValue(WifiQuanAnModel.class);
                    chiTietQuanAnInterface.HienThiDSWifi(wifiQuanAnModel);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void ThemWifiQuanAn(Context context,WifiQuanAnModel wifiQuanAnModel,String maquanan){
        DatabaseReference dataNodeWifiQuanAn = FirebaseDatabase.getInstance().getReference().child("wifiquanans").child(maquanan);
        dataNodeWifiQuanAn.push().setValue(wifiQuanAnModel, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(context, "Thêm Wifi thành công !!", Toast.LENGTH_SHORT).show();
            }
        });




    }
}
