package com.example.foody2.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foody2.Model.DatMonModel;
import com.example.foody2.Model.MonAnModel;
import com.example.foody2.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterMonAn extends RecyclerView.Adapter<AdapterMonAn.HolderMonAn> {

    Context context;
    List<MonAnModel> monAnModelList;
    public static List<DatMonModel> datMonModelList = new ArrayList<>();

    public AdapterMonAn(Context context, List<MonAnModel> monAnModelList) {
        this.context = context;
        this.monAnModelList = monAnModelList;
    }

    @NonNull
    @Override
    public AdapterMonAn.HolderMonAn onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_layout_monan,parent,false);
        return new HolderMonAn(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMonAn.HolderMonAn holder, int position) {
        MonAnModel monAnModel = monAnModelList.get(position);
        holder.txtTenMonAn.setText(monAnModel.getTenmon());

        holder.txtSoLuong.setTag(0);
        holder.imgTangSoLuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int dem = Integer.parseInt(holder.txtSoLuong.getTag().toString());
                dem++;
                holder.txtSoLuong.setText(dem+"");
                holder.txtSoLuong.setTag(dem);

                DatMonModel monModel = (DatMonModel) holder.imgGiamSoLuong.getTag();
                if (monModel!=null){
                    AdapterMonAn.datMonModelList.remove(monModel);
                }

                DatMonModel datMonModel = new DatMonModel();
                datMonModel.setSoluong(dem);
                datMonModel.setTenmonan(monAnModel.getTenmon());

                holder.imgGiamSoLuong.setTag(datMonModel);
                AdapterMonAn.datMonModelList.add(datMonModel);
            }
        });

        holder.imgGiamSoLuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int dem = Integer.parseInt(holder.txtSoLuong.getTag().toString());
                if (dem !=0){
                    dem--;
                    if (dem==0){
                        DatMonModel datMonModel = (DatMonModel) view.getTag();
                        AdapterMonAn.datMonModelList.remove(datMonModel);
                    }
                }
                holder.txtSoLuong.setText(dem+"");
                holder.txtSoLuong.setTag(dem);

            }
        });
    }

    @Override
    public int getItemCount() {
        return monAnModelList.size();
    }

    public class HolderMonAn extends RecyclerView.ViewHolder {
        TextView txtTenMonAn,txtSoLuong;
        ImageView imgTangSoLuong,imgGiamSoLuong;
        public HolderMonAn(@NonNull View itemView) {
            super(itemView);
            txtTenMonAn = itemView.findViewById(R.id.txtTenMonAn);
            txtSoLuong = itemView.findViewById(R.id.txtSoLuong);
            imgTangSoLuong = itemView.findViewById(R.id.imgTangSoLuong);
            imgGiamSoLuong = itemView.findViewById(R.id.imgGiamSoLuong);
        }
    }
}
