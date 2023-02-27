package com.example.foody2.Controller;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import com.example.foody2.Adapters.AdapterDanhSachWifi;
import com.example.foody2.Controller.Interfaces.ChiTietQuanAnInterface;
import com.example.foody2.Model.WifiQuanAnModel;
import com.example.foody2.R;
import com.example.foody2.View.CapNhatDanhSachWifiActivity;

import java.util.ArrayList;
import java.util.List;

public class CapNhatWifiController {
    WifiQuanAnModel wifiQuanAnModel;
    Context context;
    List<WifiQuanAnModel> wifiQuanAnModelList;

    public CapNhatWifiController(Context context) {
       wifiQuanAnModel = new WifiQuanAnModel();
       this.context = context;
    }

    public void HienThiDanhSachWifi(String maquanan, RecyclerView recyclerView){
        wifiQuanAnModelList = new ArrayList<>();

        ChiTietQuanAnInterface chiTietQuanAnInterface = new ChiTietQuanAnInterface() {
            @Override
            public void HienThiDSWifi(WifiQuanAnModel wifiQuanAnModel) {

                wifiQuanAnModelList.add(wifiQuanAnModel);

                AdapterDanhSachWifi adapterDanhSachWifi = new AdapterDanhSachWifi(context, R.layout.layout_wifi_chitietquanan,wifiQuanAnModelList);
                recyclerView.setAdapter(adapterDanhSachWifi);
                adapterDanhSachWifi.notifyDataSetChanged();
            }
        };
        wifiQuanAnModel.LayDanhSachWifiQuanAn(maquanan,chiTietQuanAnInterface);
    }

    public void ThemWifi(Context context, WifiQuanAnModel wifiQuanAnModel,String maquanan){
        wifiQuanAnModel.ThemWifiQuanAn(context,wifiQuanAnModel,maquanan);
    }
}
