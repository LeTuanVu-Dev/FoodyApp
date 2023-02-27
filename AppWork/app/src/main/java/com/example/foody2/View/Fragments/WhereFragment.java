package com.example.foody2.View.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foody2.Controller.OdauController;
import com.example.foody2.R;

public class WhereFragment extends Fragment {
    OdauController odauController;
    RecyclerView recyclerWhere;
    ProgressBar progressBarOdau;
    NestedScrollView nestedScrollView;
    SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_where,container,false);
        recyclerWhere = view.findViewById(R.id.recyclerWhere);
        progressBarOdau = view.findViewById(R.id.progressBarOdau);
        nestedScrollView = view.findViewById(R.id.nestedScrollViewOdau);

        sharedPreferences = getContext().getSharedPreferences("toado", Context.MODE_PRIVATE);
        Location vitrihientai = new Location("");
        vitrihientai.setLatitude(sharedPreferences.getFloat("latitude",0));
        vitrihientai.setLongitude(sharedPreferences.getFloat("longitude",0));

        odauController = new OdauController(getContext());
        odauController.getDanhSachQuanAnController(getContext(),nestedScrollView,recyclerWhere,progressBarOdau,vitrihientai);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

       }
}
