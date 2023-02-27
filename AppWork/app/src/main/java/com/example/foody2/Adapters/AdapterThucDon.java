package com.example.foody2.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foody2.Model.ThucDonModel;
import com.example.foody2.R;

import java.util.List;

public class AdapterThucDon extends RecyclerView.Adapter<AdapterThucDon.HolderThucDon> {

    Context context;
    List<ThucDonModel> thucDonModels;

    public AdapterThucDon(Context context, List<ThucDonModel> thucDonModels) {
        this.context = context;
        this.thucDonModels = thucDonModels;
    }

    @NonNull
    @Override
    public AdapterThucDon.HolderThucDon onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_layout_thucdon,parent,false);
        return new HolderThucDon(view);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull AdapterThucDon.HolderThucDon holder, int position) {
        ThucDonModel thucDonModel = thucDonModels.get(position);
        holder.txtThucDon.setText(thucDonModel.getTenthucdon());
        holder.recyclerMonAn.setLayoutManager(new LinearLayoutManager(context));
        AdapterMonAn adapterMonAn = new AdapterMonAn(context,thucDonModel.getMonAnModelList());
        holder.recyclerMonAn.setAdapter(adapterMonAn);
        adapterMonAn.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return thucDonModels.size();
    }

    public class HolderThucDon extends RecyclerView.ViewHolder {
    TextView txtThucDon;
    RecyclerView recyclerMonAn;
        public HolderThucDon(@NonNull View itemView) {
            super(itemView);
            txtThucDon = itemView.findViewById(R.id.txtTenThucDon);
            recyclerMonAn = itemView.findViewById(R.id.recyclerMonAn);
        }
    }
}
