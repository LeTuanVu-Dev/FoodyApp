package com.example.foody2.Controller;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foody2.Adapters.AdapterThucDon;
import com.example.foody2.Controller.Interfaces.ThucDonInterface;
import com.example.foody2.Model.ThucDonModel;

import java.util.List;

public class ThucDonController {
    ThucDonModel thucDonModel;

    public ThucDonController() {
        thucDonModel = new ThucDonModel();
    }

    public void getDanhSachThucDonQuanAnTheoMa(Context context, String maquanan, RecyclerView recyclerView){
        recyclerView.setLayoutManager(new LinearLayoutManager(context));


        ThucDonInterface thucDonInterface = new ThucDonInterface() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void getThucDonThanhCong(List<ThucDonModel> thucDonModelList) {
                AdapterThucDon adapterThucDon  = new AdapterThucDon(context,thucDonModelList);
                recyclerView.setAdapter(adapterThucDon);
                adapterThucDon.notifyDataSetChanged();
            }
        };
        thucDonModel.getDanhSachThucDonQuanAnTheoMa(maquanan,thucDonInterface);
    }

}
