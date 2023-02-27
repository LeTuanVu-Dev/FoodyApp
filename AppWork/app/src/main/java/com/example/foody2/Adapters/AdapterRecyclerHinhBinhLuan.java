package com.example.foody2.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foody2.Model.BinhLuanModel;
import com.example.foody2.R;
import com.example.foody2.View.HienThiChiTietHinhBinhLuanActivity;

import java.util.List;

public class AdapterRecyclerHinhBinhLuan extends RecyclerView.Adapter<AdapterRecyclerHinhBinhLuan.ViewHolderHinhBinhLuan> {

    Context context;
    int resource;
    List<Bitmap> listHinh;
    BinhLuanModel binhLuanModel;
    boolean isChiTietBinhLuan;

    public AdapterRecyclerHinhBinhLuan(Context context, int resource, List<Bitmap> listHinh,BinhLuanModel binhLuanModel, boolean isChiTietBinhLuan) {
        this.context = context;
        this.resource = resource;
        this.listHinh = listHinh;
        this.binhLuanModel = binhLuanModel;
        this.isChiTietBinhLuan = isChiTietBinhLuan;

    }

    public class ViewHolderHinhBinhLuan extends RecyclerView.ViewHolder {
        ImageView imgHinhBinhLuan;
        TextView txtSoHinhBinhLuan;
        FrameLayout khungSoHinhBinhLuan;

        public ViewHolderHinhBinhLuan(@NonNull View itemView) {
            super(itemView);
            imgHinhBinhLuan = itemView.findViewById(R.id.imgHinhBinhLuan);
            txtSoHinhBinhLuan = itemView.findViewById(R.id.txtSoHinhBinhLuan);
            khungSoHinhBinhLuan = itemView.findViewById(R.id.khungSoHinhBinhLuan);
        }
    }

    @NonNull
    @Override
    public AdapterRecyclerHinhBinhLuan.ViewHolderHinhBinhLuan onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
        ViewHolderHinhBinhLuan viewHolderHinhBinhLuan = new ViewHolderHinhBinhLuan(view);

        return viewHolderHinhBinhLuan;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AdapterRecyclerHinhBinhLuan.ViewHolderHinhBinhLuan holder, int position) {
        holder.imgHinhBinhLuan.setImageBitmap(listHinh.get(position));
        if (!isChiTietBinhLuan){
            if (position == 3){
                int sohinhconlai = listHinh.size()-4;
                if (sohinhconlai>0){
                    holder.khungSoHinhBinhLuan.setVisibility(View.VISIBLE);
                    holder.txtSoHinhBinhLuan.setText("+"+sohinhconlai);
                    holder.imgHinhBinhLuan.setOnClickListener(view -> {
                        Intent iChiTietBinhLuan = new Intent(context, HienThiChiTietHinhBinhLuanActivity.class);
                        iChiTietBinhLuan.putExtra("binhluanmodel",binhLuanModel);
                        context.startActivity(iChiTietBinhLuan);
                    });
                }
            }
        }


    }

    @Override
    public int getItemCount() {
        if (!isChiTietBinhLuan){
            return 4;
        }
        else {
            return listHinh.size();
        }
    }


}
