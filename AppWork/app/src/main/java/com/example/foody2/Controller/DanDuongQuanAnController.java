package com.example.foody2.Controller;

import com.example.foody2.Model.DownloadPolyline;
import com.example.foody2.Model.ParserPolyline;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class DanDuongQuanAnController {

    ParserPolyline parserPolyline;
    DownloadPolyline downloadPolyline;

    public DanDuongQuanAnController() {

    }

    public void HienThiDanDuongQuanAn(GoogleMap googleMap,String duongdan){
        parserPolyline = new ParserPolyline();
        downloadPolyline = new DownloadPolyline();
        downloadPolyline.execute(duongdan);

        try {
            String dataJson = downloadPolyline.get();
           List<LatLng> latLngList = parserPolyline.LayDanhSachToaDo(dataJson);
            PolylineOptions polylineOptions =new PolylineOptions();
            for (LatLng toado : latLngList){
                polylineOptions.add(toado);
            }

            Polyline polyline= googleMap.addPolyline(polylineOptions);

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
