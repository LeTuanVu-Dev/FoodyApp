package com.example.foody2.Controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.view.View;
import android.widget.ProgressBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foody2.Adapters.AdapterRecyclerWhere;
import com.example.foody2.Controller.Interfaces.OdauInterface;
import com.example.foody2.Model.RestaurantModel;
import com.example.foody2.R;

import java.util.ArrayList;
import java.util.List;


public class OdauController {
    Context context;
    RestaurantModel restaurantModel;
    AdapterRecyclerWhere adapterRecyclerWhere;
    int itemDaco = 3;

    public OdauController(Context context) {
        this.context = context;
        restaurantModel = new RestaurantModel();
    }

    public void getDanhSachQuanAnController(Context context,androidx.core.widget.NestedScrollView nestedScrollView, RecyclerView recyclerOdau, ProgressBar progressBarOdau, Location vitriHienTai){

        List<RestaurantModel> restaurantModelList = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        recyclerOdau.setLayoutManager(layoutManager);

        OdauInterface odauInterface = new OdauInterface() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void getDanhSachQuanAnModel(RestaurantModel restaurantModel) {
                restaurantModelList.add(restaurantModel);
                 adapterRecyclerWhere = new AdapterRecyclerWhere(context,restaurantModelList, R.layout.custom_layout_recyclerview_where);
                recyclerOdau.setAdapter(adapterRecyclerWhere);
                adapterRecyclerWhere.notifyDataSetChanged();
                progressBarOdau.setVisibility(View.GONE);
            }
        };

        nestedScrollView.setOnScrollChangeListener(new androidx.core.widget.NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(androidx.core.widget.NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (v.getChildAt(v.getChildCount() - 1)!=null){
                    if (scrollY>=(v.getChildAt(v.getChildCount()-1)).getMeasuredHeight()-v.getMeasuredHeight()){
                        itemDaco +=3;
                        restaurantModel.getDanhSachQuanAn(odauInterface,vitriHienTai,itemDaco,itemDaco-3);
                    }
                }
            }
        });
        restaurantModel.getDanhSachQuanAn(odauInterface,vitriHienTai,itemDaco,0);
    }

}
