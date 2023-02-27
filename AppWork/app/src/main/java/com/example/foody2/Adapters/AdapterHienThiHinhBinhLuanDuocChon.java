package com.example.foody2.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foody2.R;

import java.util.List;

public class AdapterHienThiHinhBinhLuanDuocChon extends RecyclerView.Adapter<AdapterHienThiHinhBinhLuanDuocChon.ViewHolderHinhBinhLuan> {

    Context context ;
    int resource;
    List<String >list;

    public AdapterHienThiHinhBinhLuanDuocChon(Context context, int resource, List<String> list) {
        this.context = context;
        this.resource = resource;
        this.list = list;
    }

    @NonNull
    @Override
    public AdapterHienThiHinhBinhLuanDuocChon.ViewHolderHinhBinhLuan onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
        ViewHolderHinhBinhLuan viewHolderHinhBinhLuan = new ViewHolderHinhBinhLuan(view);
        return viewHolderHinhBinhLuan;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHienThiHinhBinhLuanDuocChon.ViewHolderHinhBinhLuan holder, int position) {
        Uri uri = Uri.parse(list.get(position));
        holder.imageView.setImageURI(uri);

        holder.imgXoa.setTag(position);
        holder.imgXoa.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {
                int vitri = (int) view.getTag();
                Log.d("kiemtra",vitri+"");
                list.remove(vitri);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolderHinhBinhLuan extends RecyclerView.ViewHolder {
        ImageView imageView,imgXoa;

        public ViewHolderHinhBinhLuan(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imgChonHinhBinhLuan);
            imgXoa = itemView.findViewById(R.id.imgDelete);
        }
    }
}
