package com.example.foody2.Model;


import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ParserPolyline {

    public static List<LatLng> LayDanhSachToaDo(String dataJson){
        List<LatLng> latLngs = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(dataJson);
            JSONArray routes = jsonObject.getJSONArray("routes");

            for (int i=0;i< routes.length();i++){
                JSONObject jsonObject1 = routes.getJSONObject(i);
                JSONObject overViewPolyline = jsonObject1.getJSONObject("overview_polyline");
                String point = overViewPolyline.getString("points");
                latLngs =  PolyUtil.decode(point);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return latLngs;
    }
}
