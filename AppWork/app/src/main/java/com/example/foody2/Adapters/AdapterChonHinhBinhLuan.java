package com.example.foody2.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foody2.Model.ChonHinhBinhLuanModel;
import com.example.foody2.R;

import java.util.List;

public class AdapterChonHinhBinhLuan extends RecyclerView.Adapter<AdapterChonHinhBinhLuan.ViewHolderChonHinh> {

    Context context;
    List<ChonHinhBinhLuanModel > listDuongDan;
    int resource;
    public AdapterChonHinhBinhLuan(Context context, List<ChonHinhBinhLuanModel> listduongdan, int resource) {
        this.context = context;
        this.listDuongDan = listduongdan;
        this.resource = resource;
    }

    @NonNull
    @Override
    public AdapterChonHinhBinhLuan.ViewHolderChonHinh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderChonHinh(
                LayoutInflater.from(context).inflate(R.layout.custom_layout_chonhinhbinhluan, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterChonHinhBinhLuan.ViewHolderChonHinh holder, @SuppressLint("RecyclerView") int position) {
        ChonHinhBinhLuanModel chonHinhBinhLuanModel = listDuongDan.get(position);

        Uri uri = Uri.parse(chonHinhBinhLuanModel.getDuondan());
        holder.imageView.setImageURI(uri);

        holder.checkBox.setChecked(chonHinhBinhLuanModel.isCheck());

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox checkBox = (CheckBox) view;
                listDuongDan.get(position).setCheck(checkBox.isChecked());
            }
        });
    }

    @Override
    public int getItemCount() {
        return listDuongDan.size();
    }

    public static class ViewHolderChonHinh extends RecyclerView.ViewHolder {

        ImageView imageView;
        CheckBox checkBox;
        public ViewHolderChonHinh(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgChonHinhBinhLuan);
            checkBox = itemView.findViewById(R.id.cbChonHinhBinhLuan);
        }
    }


}
