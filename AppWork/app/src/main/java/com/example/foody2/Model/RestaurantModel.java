package com.example.foody2.Model;


import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.foody2.Controller.Interfaces.OdauInterface;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RestaurantModel implements Parcelable {
    boolean giaohang;
    String giodongcua , giomocua,tenquanan , videogioithieu,maquanan;
    List<String> tienich;
    List<String> hinhanhquanan;
    List<BinhLuanModel> binhLuanModelList;
    List<ChiNhanhQuanAnModel> chiNhanhQuanAnModelList;
    List<ThucDonModel> thucDonList;

    long giatoida , giatoithieu;

    public List<ThucDonModel> getThucDonList() {
        return thucDonList;
    }

    public void setThucDonList(List<ThucDonModel> thucDonList) {
        this.thucDonList = thucDonList;
    }

    public long getGiatoida() {
        return giatoida;
    }

    public void setGiatoida(long giatoida) {
        this.giatoida = giatoida;
    }

    public long getGiatoithieu() {
        return giatoithieu;
    }

    public void setGiatoithieu(long giatoithieu) {
        this.giatoithieu = giatoithieu;
    }

    protected RestaurantModel(Parcel in) {
        giaohang = in.readByte() != 0;
        giodongcua = in.readString();
        giomocua = in.readString();
        tenquanan = in.readString();
        videogioithieu = in.readString();
        maquanan = in.readString();
        tienich = in.createStringArrayList();
        hinhanhquanan = in.createStringArrayList();
        luotthich = in.readString();
        giatoida = in.readLong();
        giatoithieu = in.readLong();
        chiNhanhQuanAnModelList = new ArrayList<ChiNhanhQuanAnModel>();
        in.readTypedList(chiNhanhQuanAnModelList,ChiNhanhQuanAnModel.CREATOR);

        binhLuanModelList = new ArrayList<BinhLuanModel>();
        in.readTypedList(binhLuanModelList,BinhLuanModel.CREATOR);
    }

    public static final Creator<RestaurantModel> CREATOR = new Creator<RestaurantModel>() {
        @Override
        public RestaurantModel createFromParcel(Parcel in) {
            return new RestaurantModel(in);
        }

        @Override
        public RestaurantModel[] newArray(int size) {
            return new RestaurantModel[size];
        }
    };

    public List<ChiNhanhQuanAnModel> getChiNhanhQuanAnModelList() {
        return chiNhanhQuanAnModelList;
    }

    public void setChiNhanhQuanAnModelList(List<ChiNhanhQuanAnModel> chiNhanhQuanAnModelList) {
        this.chiNhanhQuanAnModelList = chiNhanhQuanAnModelList;
    }

    String luotthich;
    private DatabaseReference nodeRoot;

    public List<BinhLuanModel> getBinhLuanModelList() {
        return binhLuanModelList;
    }

    public void setBinhLuanModelList(List<BinhLuanModel> binhLuanModelList) {
        this.binhLuanModelList = binhLuanModelList;
    }

    public List<String> getHinhanhquanan() {
        return hinhanhquanan;
    }

    public void setHinhanhquanan(List<String> hinhanhquanan) {
        this.hinhanhquanan = hinhanhquanan;
    }

    public RestaurantModel() {
        nodeRoot = FirebaseDatabase.getInstance().getReference();
    }

    public String getLuotthich() {
        return luotthich;
    }

    public void setLuotthich(String luotthich) {
        this.luotthich = luotthich;
    }

    public boolean isGiaohang() {
        return giaohang;
    }

    public void setGiaohang(boolean giaohang) {
        this.giaohang = giaohang;
    }

    public String getGiodongcua() {
        return giodongcua;
    }

    public void setGiodongcua(String giodongcua) {
        this.giodongcua = giodongcua;
    }

    public String getGiomocua() {
        return giomocua;
    }

    public void setGiomocua(String giomocua) {
        this.giomocua = giomocua;
    }

    public String getTenquanan() {
        return tenquanan;
    }

    public void setTenquanan(String tenquanan) {
        this.tenquanan = tenquanan;
    }

    public String getVideogioithieu() {
        return videogioithieu;
    }

    public void setVideogioithieu(String videogioithieu) {
        this.videogioithieu = videogioithieu;
    }

    public String getMaquanan() {
        return maquanan;
    }

    public void setMaquanan(String maquanan) {
        this.maquanan = maquanan;
    }

    public List<String> getTienich() {
        return tienich;
    }

    public void setTienich(List<String> tienich) {
        this.tienich = tienich;
    }

    private DataSnapshot dataRoot;
    public void getDanhSachQuanAn(OdauInterface odauInterface,Location vitriHienTai,int itemTieptheo,int itemDaco){

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataRoot = snapshot;
                LayDanhSachQuanAn(snapshot,odauInterface,vitriHienTai,itemTieptheo,itemDaco);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        };
        if (dataRoot!=null){
            LayDanhSachQuanAn(dataRoot,odauInterface,vitriHienTai,itemTieptheo,itemDaco);
        }
        else {
            nodeRoot.addListenerForSingleValueEvent(valueEventListener);

        }
    }

    private void LayDanhSachQuanAn(DataSnapshot snapshot,OdauInterface odauInterface,Location vitriHienTai,int itemTieptheo,int itemDaco){
        DataSnapshot dataSnapshotQuanAn = snapshot.child("quanans");
        // lấy danh sách quán ăn

        int i =0;

        for (DataSnapshot valueQuanAn : dataSnapshotQuanAn.getChildren()) {

            if (i==itemTieptheo){
                break;
            }
            if(i==itemDaco){
                i++;
                continue;
            }
            i++;

            RestaurantModel restaurantModel = valueQuanAn.getValue(RestaurantModel.class);
            Objects.requireNonNull(restaurantModel).setMaquanan(valueQuanAn.getKey());

            // lấy danh sách hình ảnh quán ăn theo mã
            String maQuanAn = valueQuanAn.getKey();
            DataSnapshot dataSnapshotHinhQuanAn = snapshot.child("hinhanhquanans").child(maQuanAn);

            List<String> hinhanhList = new ArrayList<>();
            for (DataSnapshot valueHinhAnh : dataSnapshotHinhQuanAn.getChildren()) {
                hinhanhList.add(String.valueOf(valueHinhAnh.getValue()));
            }
            restaurantModel.setHinhanhquanan(hinhanhList);
            // Log.d("link",restaurantModel.getHinhanhquanan()+"");

            // lấy ds bình luận của quán ăn
            DataSnapshot snapshotBinhLuan = snapshot.child("binhluans").child(valueQuanAn.getKey());
            List<BinhLuanModel> binhLuanModels = new ArrayList<>();

            for (DataSnapshot valueBinhLuan : snapshotBinhLuan.getChildren()) {
                BinhLuanModel binhLuanModel = valueBinhLuan.getValue(BinhLuanModel.class);
                binhLuanModel.setManbinhluan(valueBinhLuan.getKey());
                ThanhVienModel thanhVienModel = snapshot.child("thanhviens").child(binhLuanModel.getMauser()).getValue(ThanhVienModel.class);
                binhLuanModel.setThanhVienModel(thanhVienModel);

                List<String> hinhanhBinhLuanList = new ArrayList<>();
                DataSnapshot snapshotNodeHinhAnhBL = snapshot.child("hinhanhbinhluans").child(binhLuanModel.getManbinhluan());
                for (DataSnapshot valueHinhBinhLuan : snapshotNodeHinhAnhBL.getChildren()){
                    hinhanhBinhLuanList.add(valueHinhBinhLuan.getValue(String.class));
                }
                binhLuanModel.setHinhanhBinhLuanList(hinhanhBinhLuanList);
                binhLuanModels.add(binhLuanModel);

            }
            restaurantModel.setBinhLuanModelList(binhLuanModels);

            // lấy chi nhánh quán ăn
            DataSnapshot snapshotChiNhanhQuanAn = snapshot.child("chinhanhquanans").child(restaurantModel.getMaquanan());
            List<ChiNhanhQuanAnModel> chiNhanhQuanAnModels = new ArrayList<>();

            for (DataSnapshot valueChiNhanhQuanAn : snapshotChiNhanhQuanAn.getChildren()){
                ChiNhanhQuanAnModel chiNhanhQuanAnModel = valueChiNhanhQuanAn.getValue(ChiNhanhQuanAnModel.class);
                Location vitriQuanAn = new Location("");
                vitriQuanAn.setLatitude(chiNhanhQuanAnModel.getLatitude());
                vitriQuanAn.setLongitude(chiNhanhQuanAnModel.getLongitude());

                double khoangcach =  vitriHienTai.distanceTo(vitriQuanAn)/1000;
                //Log.d("kiemtra",khoangcach+" - "+chiNhanhQuanAnModel.getDiachi());
                chiNhanhQuanAnModel.setKhoangcach(khoangcach);
                chiNhanhQuanAnModels.add(chiNhanhQuanAnModel);

            }
            restaurantModel.setChiNhanhQuanAnModelList(chiNhanhQuanAnModels);
            odauInterface.getDanhSachQuanAnModel(restaurantModel);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte((byte) (giaohang ? 1 : 0));
        parcel.writeString(giodongcua);
        parcel.writeString(giomocua);
        parcel.writeString(tenquanan);
        parcel.writeString(videogioithieu);
        parcel.writeString(maquanan);
        parcel.writeStringList(tienich);
        parcel.writeStringList(hinhanhquanan);
        parcel.writeString(luotthich);
        parcel.writeLong(giatoida);
        parcel.writeLong(giatoithieu);
        parcel.writeTypedList(chiNhanhQuanAnModelList);
        parcel.writeTypedList(binhLuanModelList);
    }
}
