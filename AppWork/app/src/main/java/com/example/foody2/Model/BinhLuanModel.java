package com.example.foody2.Model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.List;

public class    BinhLuanModel implements Parcelable {
    String  chamdiem ,luotthich;
    ThanhVienModel thanhVienModel;
    String noidung,tieude,mauser;
    String manbinhluan;
    List<String> hinhanhBinhLuanList;

    public BinhLuanModel() {
    }

    protected BinhLuanModel(Parcel in) {
        chamdiem = in.readString();
        luotthich = in.readString();
        noidung = in.readString();
        tieude = in.readString();
        mauser = in.readString();
        manbinhluan = in.readString();
        hinhanhBinhLuanList = in.createStringArrayList();
        thanhVienModel = in.readParcelable(ThanhVienModel.class.getClassLoader());
    }

    public static final Creator<BinhLuanModel> CREATOR = new Creator<BinhLuanModel>() {
        @Override
        public BinhLuanModel createFromParcel(Parcel in) {
            return new BinhLuanModel(in);
        }

        @Override
        public BinhLuanModel[] newArray(int size) {
            return new BinhLuanModel[size];
        }
    };

    public ThanhVienModel getThanhVienModel() {
        return thanhVienModel;
    }

    public void setThanhVienModel(ThanhVienModel thanhVienModel) {
        this.thanhVienModel = thanhVienModel;
    }

    public String getManbinhluan() {
        return manbinhluan;
    }

    public void setManbinhluan(String manbinhluan) {
        this.manbinhluan = manbinhluan;
    }



    public List<String> getHinhanhBinhLuanList() {
        return hinhanhBinhLuanList;
    }

    public void setHinhanhBinhLuanList(List<String> hinhanhList) {
        this.hinhanhBinhLuanList = hinhanhList;
    }



    public String getMauser() {
        return mauser;
    }

    public void setMauser(String mauser) {
        this.mauser = mauser;
    }

    public String getChamdiem() {
        return chamdiem;
    }

    public void setChamdiem(String chamdiem) {
        this.chamdiem = chamdiem;
    }

    public String getLuotthich() {
        return luotthich;
    }

    public void setLuotthich(String luotthich) {
        this.luotthich = luotthich;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public String getTieude() {
        return tieude;
    }

    public void setTieude(String tieude) {
        this.tieude = tieude;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(chamdiem);
        parcel.writeString(luotthich);
        parcel.writeString(noidung);
        parcel.writeString(tieude);
        parcel.writeString(mauser);
        parcel.writeString(manbinhluan);
        parcel.writeStringList(hinhanhBinhLuanList);
        parcel.writeParcelable(thanhVienModel,i);
    }

    public void ThemBinhLuan(String maquanan,BinhLuanModel binhLuanModel, List<String >listHinh){
        DatabaseReference nodeBinhLuan = FirebaseDatabase.getInstance().getReference().child("binhluans");
        String mabinhluan =  nodeBinhLuan.child(maquanan).push().getKey();

        nodeBinhLuan.child(maquanan).child(mabinhluan).setValue(binhLuanModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){

                    if (listHinh.size() >0){
                        for (String valueHinh : listHinh){
                            Uri uri = Uri.fromFile(new File(valueHinh));
                            StorageReference reference = FirebaseStorage.getInstance().getReference().child("hinhanh/"+uri.getLastPathSegment());
                            reference.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                                }
                            });
                        }
                    }
                }
            }
        });

        if (listHinh.size() >0){
            for (String valueHinh : listHinh){
                Uri uri = Uri.fromFile(new File(valueHinh));
                FirebaseDatabase.getInstance().getReference().child("hinhanhbinhluans").child(mabinhluan).push().setValue(uri.getLastPathSegment());

            }
        }

    }
}
