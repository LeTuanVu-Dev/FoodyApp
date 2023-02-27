package com.example.foody2.Model;

import androidx.annotation.NonNull;

import com.example.foody2.Controller.Interfaces.ThucDonInterface;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ThucDonModel {
    String mathucdon,tenthucdon;
    List<MonAnModel> monAnModelList;

    public List<MonAnModel> getMonAnModelList() {
        return monAnModelList;
    }

    public void setMonAnModelList(List<MonAnModel> monAnModelList) {
        this.monAnModelList = monAnModelList;
    }

    public String getMathucdon() {
        return mathucdon;
    }

    public void setMathucdon(String mathucdon) {
        this.mathucdon = mathucdon;
    }

    public String getTenthucdon() {
        return tenthucdon;
    }

    public void setTenthucdon(String tenthucdon) {
        this.tenthucdon = tenthucdon;
    }

    public void getDanhSachThucDonQuanAnTheoMa(String maquanan, ThucDonInterface thucDonInterface){
        DatabaseReference nodeThucDonQuanAn = FirebaseDatabase.getInstance().getReference().child("thucdonquanans").child(maquanan);
        nodeThucDonQuanAn.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                List<ThucDonModel> thucDonModels = new ArrayList<>();

                for (DataSnapshot valueThucDon : snapshot.getChildren()){
                    ThucDonModel thucDonModel = new ThucDonModel();

                    DatabaseReference nodeThucDon =FirebaseDatabase.getInstance().getReference().child("thucdons").child(valueThucDon.getKey());
                    nodeThucDon.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshotThucDon) {
                            String mathucdon = snapshotThucDon.getKey();
                            thucDonModel.setMathucdon(mathucdon);
                            thucDonModel.setTenthucdon(snapshotThucDon.getValue(String.class));
                            List<MonAnModel> monAnModels = new ArrayList<>();

                            for (DataSnapshot valueMonAn : snapshot.child(mathucdon).getChildren()){
                                MonAnModel monAnModel = valueMonAn.getValue(MonAnModel.class);
                                monAnModel.setMamon(valueMonAn.getKey());
                                monAnModels.add(monAnModel);
                            }
                            thucDonModel.setMonAnModelList(monAnModels);
                            thucDonModels.add(thucDonModel);
                            thucDonInterface.getThucDonThanhCong(thucDonModels);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
