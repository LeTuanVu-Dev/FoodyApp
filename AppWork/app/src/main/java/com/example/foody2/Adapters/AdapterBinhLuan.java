package com.example.foody2.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foody2.Model.BinhLuanModel;
import com.example.foody2.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterBinhLuan extends RecyclerView.Adapter<AdapterBinhLuan.ViewHolder> {

    Context context;
    int layout;
    List<BinhLuanModel> binhLuanModelList;
    List<Bitmap> bitmapList;

    public AdapterBinhLuan(Context context, int layout, List<BinhLuanModel> binhLuanModelList) {
        this.context = context;
        this.layout = layout;
        this.binhLuanModelList = binhLuanModelList;
        bitmapList = new ArrayList<>();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView circleImageView;
        TextView txtTieudebinhluan,txtNoidungbinhluan,txtChamdiem;
        RecyclerView recyclerHinhBinhLuan;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            circleImageView = itemView.findViewById(R.id.circleImageUser);
            txtTieudebinhluan = itemView.findViewById(R.id.txtTieudebinhluan);
            txtNoidungbinhluan = itemView.findViewById(R.id.txtNoidungbinhluan);
            txtChamdiem = itemView.findViewById(R.id.txtChamdiem);
            recyclerHinhBinhLuan = itemView.findViewById(R.id.recyclerHinhBinhLuan);

        }
    }

    @NonNull
    @Override
    public AdapterBinhLuan.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull AdapterBinhLuan.ViewHolder holder, int position) {
        BinhLuanModel binhLuanModel = binhLuanModelList.get(position);
        holder.txtTieudebinhluan.setText(binhLuanModel.getTieude());
        holder.txtNoidungbinhluan.setText(binhLuanModel.getNoidung());
        holder.txtChamdiem.setText(binhLuanModel.getChamdiem());
       // setHinhAnhBinhLuan(holder.circleImageView,binhLuanModel.getThanhVienModel().getHinhanh()+"");
        holder.circleImageView.setImageResource(R.drawable.icon_user);


        for (String linkhinh : binhLuanModel.getHinhanhBinhLuanList()){
            StorageReference storageHinhUser = FirebaseStorage.getInstance().getReference().child("hinhanh/"+linkhinh);
            long ONE_MEGABYTE = 1024 * 1024;
            storageHinhUser.getBytes(ONE_MEGABYTE).addOnSuccessListener(bytes -> {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                bitmapList.add(bitmap);

                if (bitmapList.size() == binhLuanModel.getHinhanhBinhLuanList().size()){
                    AdapterRecyclerHinhBinhLuan adapterRecyclerHinhBinhLuan = new
                            AdapterRecyclerHinhBinhLuan(context,R.layout.custom_layout_hinhbinhluan,bitmapList,binhLuanModel,false);
                    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context,2);
                    holder.recyclerHinhBinhLuan.setLayoutManager(layoutManager);
                    holder.recyclerHinhBinhLuan.setAdapter(adapterRecyclerHinhBinhLuan);
                    adapterRecyclerHinhBinhLuan.notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        int sobinhluan = binhLuanModelList.size();
        if (sobinhluan > 5){
            return 5;
        }
        else {
            return binhLuanModelList.size();
        }
    }

    private  void setHinhAnhBinhLuan(CircleImageView circleImageView ,String linkhinh){
        StorageReference storageHinhUser = FirebaseStorage.getInstance().getReference().child("thanhvien/"+linkhinh);
        long ONE_MEGABYTE = 1024 * 1024;
        storageHinhUser.getBytes(ONE_MEGABYTE).addOnSuccessListener(bytes -> {
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
            circleImageView.setImageBitmap(bitmap);
        });
    }

}
