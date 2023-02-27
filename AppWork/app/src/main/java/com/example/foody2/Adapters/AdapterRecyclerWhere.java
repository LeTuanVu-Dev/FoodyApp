package com.example.foody2.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foody2.Model.BinhLuanModel;
import com.example.foody2.Model.ChiNhanhQuanAnModel;
import com.example.foody2.Model.RestaurantModel;
import com.example.foody2.R;
import com.example.foody2.View.ChiTietQuanAnActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterRecyclerWhere extends RecyclerView.Adapter<AdapterRecyclerWhere.ViewHolder> {

    List<RestaurantModel> restaurantModelList;
    int resource;
    Context context;
    StorageReference storageHinhUser;
    public AdapterRecyclerWhere(Context context,List<RestaurantModel> restaurantModelList, int resource) {
        this.restaurantModelList = restaurantModelList;
        this.resource = resource;
        this.context = context;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenQuanAnOdau,txtTieudebinhluan,txtTieudebinhluan2,txtNoidungbinhluan,txtNoidungbinhluan2,txtChamdiem2
                ,txtChamdiem,txtTongHinhAnh,txtTongBinhLuan,txtTongdiemquanan,txtDiaChiQuanAnOdau,txtKhoangCachQuanAnOdau;
        ImageView imageHinhQuanAnOdau;
        ImageButton btnDatMonOdau;
        CircleImageView circleImageUser,circleImageUser2;
        LinearLayout containerBinhLuan,containerBinhLuan2;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenQuanAnOdau = itemView.findViewById(R.id.txtTenQuanAnOdau);
            txtTieudebinhluan = itemView.findViewById(R.id.txtTieudebinhluan);
            txtTieudebinhluan2 = itemView.findViewById(R.id.txtTieudebinhluan2);
            txtNoidungbinhluan = itemView.findViewById(R.id.txtNoidungbinhluan);
            txtNoidungbinhluan2 = itemView.findViewById(R.id.txtNoidungbinhluan2);
            imageHinhQuanAnOdau = itemView.findViewById(R.id.imageHinhQuanAnOdau);
            btnDatMonOdau = itemView.findViewById(R.id.btnDatMonOdau);
            circleImageUser = itemView.findViewById(R.id.circleImageUser);
            circleImageUser2 = itemView.findViewById(R.id.circleImageUser2);
            containerBinhLuan = itemView.findViewById(R.id.containerBinhLuan);
            containerBinhLuan2 = itemView.findViewById(R.id.containerBinhLuan2);
            txtChamdiem = itemView.findViewById(R.id.txtChamdiem);
            txtChamdiem2 = itemView.findViewById(R.id.txtChamdiem2);
            txtTongBinhLuan = itemView.findViewById(R.id.txtTongBinhLuan);
            txtTongHinhAnh = itemView.findViewById(R.id.txtTongHinhAnh);
            txtTongdiemquanan = itemView.findViewById(R.id.txtTongdiemquanan);
            txtDiaChiQuanAnOdau = itemView.findViewById(R.id.txtDiaChiQuanAnOdau);
            txtKhoangCachQuanAnOdau = itemView.findViewById(R.id.txtKhoangCachQuanAnOdau);
            cardView = itemView.findViewById(R.id.cardViewOdau);
        }
    }

    @NonNull
    @Override
    public AdapterRecyclerWhere.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        RestaurantModel restaurantModel = restaurantModelList.get(position);
        holder.txtTenQuanAnOdau.setText(restaurantModel.getTenquanan());
       /* Log.d("link",restaurantModel.getTenquanan()+"");
        Log.d("kiemtra",restaurantModel.getHinhanhquanan().size()+"");*/
        if (restaurantModel.isGiaohang()){
            holder.btnDatMonOdau.setVisibility(View.VISIBLE);
        }
        if (restaurantModel.getHinhanhquanan().size() > 0){
            StorageReference storageHinhanh = FirebaseStorage.getInstance().getReference().child("hinhanh/"+restaurantModel.getHinhanhquanan().get(0));
            long ONE_MEGABYTE = 1024 * 1024;
            storageHinhanh.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                    holder.imageHinhQuanAnOdau.setImageBitmap(bitmap);
                }
            });
        }

        // lấy danh sách bình luận
        if (restaurantModel.getBinhLuanModelList().size() > 0){
            BinhLuanModel binhLuanModel = restaurantModel.getBinhLuanModelList().get(0);
            holder.txtTieudebinhluan.setText(binhLuanModel.getTieude());
            holder.txtNoidungbinhluan.setText(binhLuanModel.getNoidung());
            holder.txtChamdiem.setText(binhLuanModel.getChamdiem());
            setHinhAnhBinhLuan(holder.circleImageUser, String.valueOf(storageHinhUser));
          //  holder.circleImageUser.setImageResource(R.drawable.icon_user);
            if (restaurantModel.getBinhLuanModelList().size() > 2){
                BinhLuanModel binhLuanModel2 = restaurantModel.getBinhLuanModelList().get(1);
                holder.txtTieudebinhluan2.setText(binhLuanModel2.getTieude());
                holder.txtNoidungbinhluan2.setText(binhLuanModel2.getNoidung());
                holder.txtChamdiem2.setText(binhLuanModel2.getChamdiem());
                setHinhAnhBinhLuan(holder.circleImageUser2,String.valueOf(storageHinhUser));
                //holder.circleImageUser2.setImageResource(R.drawable.icon_user);
            }
            // tính tổng điểm trung bình của bình luận
            double tongdiem = 0,diemtrungbinh = 0;
            for (BinhLuanModel binhLuanModel1:restaurantModel.getBinhLuanModelList()){
                tongdiem += Double.parseDouble(binhLuanModel1.getChamdiem());
            }
            diemtrungbinh = tongdiem/restaurantModel.getBinhLuanModelList().size();
            holder.txtTongdiemquanan.setText(String.format("%.2f",diemtrungbinh));
            holder.txtTongBinhLuan.setText(restaurantModel.getBinhLuanModelList().size()+"");
            holder.txtTongHinhAnh.setText(restaurantModel.getHinhanhquanan().size()+"");
        }
        else {
            holder.containerBinhLuan.setVisibility(View.GONE);
            holder.containerBinhLuan2.setVisibility(View.GONE);
            holder.txtTongBinhLuan.setText("0");
            holder.txtTongHinhAnh.setText("0");
        }

        // lấy chi nhánh quán ăn và hiển thị địa chỉ + Km
        if (restaurantModel.getChiNhanhQuanAnModelList().size()>0){
            ChiNhanhQuanAnModel khoangcachtam = restaurantModel.getChiNhanhQuanAnModelList().get(0);
            for (ChiNhanhQuanAnModel chiNhanhQuanAnModel : restaurantModel.getChiNhanhQuanAnModelList()){
                if(khoangcachtam.getKhoangcach() > chiNhanhQuanAnModel.getKhoangcach()){
                    khoangcachtam = chiNhanhQuanAnModel;
                }
            }
            holder.txtDiaChiQuanAnOdau.setText(khoangcachtam.getDiachi());
            holder.txtKhoangCachQuanAnOdau.setText(String.format("%.1f",khoangcachtam.getKhoangcach())+" km");
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iChiTietQuanAn = new Intent(context, ChiTietQuanAnActivity.class);
                iChiTietQuanAn.putExtra("quanan",restaurantModel);
                context.startActivity(iChiTietQuanAn);
            }
        });
    }
    private  void setHinhAnhBinhLuan(CircleImageView circleImageView ,String linkhinh){
         storageHinhUser = FirebaseStorage.getInstance().getReference().child("thanhvien/"+linkhinh);
        long ONE_MEGABYTE = 1024 * 1024;
        storageHinhUser.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                circleImageView.setImageBitmap(bitmap);
            }
        });
    }

    @Override
    public int getItemCount() {
        return restaurantModelList.size();
    }
}
