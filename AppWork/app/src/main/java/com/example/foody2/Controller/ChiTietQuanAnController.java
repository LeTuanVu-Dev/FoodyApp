package com.example.foody2.Controller;

import android.util.Log;
import android.widget.TextView;

import com.example.foody2.Controller.Interfaces.ChiTietQuanAnInterface;
import com.example.foody2.Model.WifiQuanAnModel;

import java.util.ArrayList;
import java.util.List;

public class ChiTietQuanAnController {

    WifiQuanAnModel wifiQuanAnModel;
    List<WifiQuanAnModel> wifiQuanAnModelList;
    public ChiTietQuanAnController(){
        wifiQuanAnModel = new WifiQuanAnModel();
        wifiQuanAnModelList = new ArrayList<>();
    }

    public void HienThiDSWifiQuanAn(String maquanan, TextView txtTenWifi,TextView txtMatKhauWifi,TextView txtNgayDangWifi){
        ChiTietQuanAnInterface chiTietQuanAnInterface = new ChiTietQuanAnInterface() {
            @Override
            public void HienThiDSWifi(WifiQuanAnModel wifiQuanAnModel) {
                wifiQuanAnModelList.add(wifiQuanAnModel);
                txtTenWifi.setText(wifiQuanAnModel.getTen());
                txtMatKhauWifi.setText(wifiQuanAnModel.getMatkhau());
                txtNgayDangWifi.setText(wifiQuanAnModel.getNgaydang());
            }
        };
        wifiQuanAnModel.LayDanhSachWifiQuanAn(maquanan,chiTietQuanAnInterface);


    }
}
