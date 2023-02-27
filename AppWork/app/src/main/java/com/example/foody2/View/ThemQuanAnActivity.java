package com.example.foody2.View;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foody2.Model.ChiNhanhQuanAnModel;
import com.example.foody2.Model.MonAnModel;
import com.example.foody2.Model.RestaurantModel;
import com.example.foody2.Model.ThemThucDonModel;
import com.example.foody2.Model.ThucDonModel;
import com.example.foody2.Model.TienIchModel;
import com.example.foody2.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ThemQuanAnActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    final int RESULT_IMG1 = 111;
    final int RESULT_IMG2 = 112;
    final int RESULT_IMG3 = 113;
    final int RESULT_IMG4 = 114;
    final int RESULT_IMG5 = 115;
    final int RESULT_IMG6 = 116;
    final int RESULT_IMGTHUCDON = 117;
    final int RESULT_IMGVIDEO = 118;

    Button btnGioMoCua,btnGioDongCua,btnThemQuanAn;
    Spinner spinnerKhuVuc;
    String giomocua,giodongcua,khuvuc,maquanan;
    LinearLayout khungTienIch,khungChiNhanh,khungChuaChiNhanh,khungChuaThucDon;
    RadioGroup rdTrangThai;
    EditText edTenQuan,edGiaToiDa,edGiaToiThieu;

    List<ThucDonModel> thucDonModelList;
    List<String > selectedTienIchList;
    List<String > khuVucList,thucDonList,chiNhanhList;
    List<ThemThucDonModel> themThucDonModelList;
    List<Uri> hinhQuanAn;
    List<Bitmap> hinhDaChup;
    ImageView imgTam,imgHinhQuan1,imgHinhQuan2,imgHinhQuan3,imgHinhQuan4,imgHinhQuan5,imgHinhQuan6,imgVideo;
    VideoView videoView;
    ArrayAdapter<String >adapterKhuVuc;
    Uri videoSelected;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_themquanan);

        btnGioDongCua = findViewById(R.id.btnGioDongCua);
        btnGioMoCua = findViewById(R.id.btnGioMoCua);
        spinnerKhuVuc = findViewById(R.id.spinnerKhuVuc);
        khungTienIch = findViewById(R.id.khungTienIch);
        khungChiNhanh = findViewById(R.id.khungChiNhanh);
        khungChuaChiNhanh = findViewById(R.id.khungChuaChiNhanh);
        khungChuaThucDon = findViewById(R.id.khungChuaThucDon);
        imgHinhQuan1 = findViewById(R.id.imgHinhQuan1);
        imgHinhQuan2 = findViewById(R.id.imgHinhQuan2);
        imgHinhQuan3 = findViewById(R.id.imgHinhQuan3);
        imgHinhQuan4 = findViewById(R.id.imgHinhQuan4);
        imgHinhQuan5 = findViewById(R.id.imgHinhQuan5);
        imgHinhQuan6 = findViewById(R.id.imgHinhQuan6);
        imgVideo = findViewById(R.id.imgVideo);
        videoView = findViewById(R.id.videoView);
        btnThemQuanAn = findViewById(R.id.btnThemQuanAn);
        rdTrangThai = findViewById(R.id.rdTrangThai);
        edTenQuan = findViewById(R.id.edTenQuan);
        edGiaToiDa = findViewById(R.id.edGiaToiDa);
        edGiaToiThieu = findViewById(R.id.edGiaToiThieu);

        hinhQuanAn = new ArrayList<>();
        themThucDonModelList = new ArrayList<>();
        thucDonModelList = new ArrayList<>();
        selectedTienIchList = new ArrayList<>();
        khuVucList = new ArrayList<>();
        thucDonList = new ArrayList<>();
        chiNhanhList = new ArrayList<>();
        hinhDaChup = new ArrayList<>();
        adapterKhuVuc = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,khuVucList);
        spinnerKhuVuc.setAdapter(adapterKhuVuc);
        adapterKhuVuc.notifyDataSetChanged();

        CloneChiNhanh();
        CloneThucDon();

        LayDanhSachKhuVuc();
        LayDanhSachTienIch();

        btnGioMoCua.setOnClickListener(this);
        btnGioDongCua.setOnClickListener(this);
        spinnerKhuVuc.setOnItemSelectedListener(this);
        imgHinhQuan1.setOnClickListener(this);
        imgHinhQuan2.setOnClickListener(this);
        imgHinhQuan3.setOnClickListener(this);
        imgHinhQuan4.setOnClickListener(this);
        imgHinhQuan5.setOnClickListener(this);
        imgHinhQuan6.setOnClickListener(this);
        imgVideo.setOnClickListener(this);
        btnThemQuanAn.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case RESULT_IMG1:
                if (RESULT_OK==resultCode){
                    Uri uri = data.getData();
                    imgHinhQuan1.setImageURI(uri);
                    hinhQuanAn.add(uri);
                }
                break;
            case RESULT_IMG2:
                if (RESULT_OK==resultCode){
                    Uri uri = data.getData();
                    imgHinhQuan2.setImageURI(uri);
                    hinhQuanAn.add(uri);
                }
                break;
            case RESULT_IMG3:
                if (RESULT_OK==resultCode){
                    Uri uri = data.getData();
                    imgHinhQuan3.setImageURI(uri);
                    hinhQuanAn.add(uri);
                }
                break;
            case RESULT_IMG4:
                if (RESULT_OK==resultCode){
                    Uri uri = data.getData();
                    imgHinhQuan4.setImageURI(uri);
                    hinhQuanAn.add(uri);
                }
                break;
            case RESULT_IMG5:
                if (RESULT_OK==resultCode){
                    Uri uri = data.getData();
                    imgHinhQuan5.setImageURI(uri);
                    hinhQuanAn.add(uri);
                }
                break;
            case RESULT_IMG6:
                if (RESULT_OK==resultCode){
                    Uri uri = data.getData();
                    imgHinhQuan6.setImageURI(uri);
                    hinhQuanAn.add(uri);
                }
                break;
            case RESULT_IMGTHUCDON:
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                imgTam.setImageBitmap(bitmap);
                hinhDaChup.add(bitmap);
                break;
            case RESULT_IMGVIDEO:
                if (RESULT_OK==resultCode){
                    imgVideo.setVisibility(View.GONE);
                    videoView.setVisibility(View.VISIBLE);
                    Uri uri = data.getData();
                    videoSelected = uri;
                    videoView.setVideoURI(uri);
                    videoView.start();
                }
                break;
        }
    }

    private void CloneThucDon(){
        View view = LayoutInflater.from(ThemQuanAnActivity.this).inflate(R.layout.layout_clone_thucdon,null);
        Spinner spinnerThucDon = view.findViewById(R.id.spinnerThucDon);
        Button btnThemThucDon = view.findViewById(R.id.btnThemThucDon);
        EditText edTenMon = view.findViewById(R.id.edTenMon);
        EditText edGiaTien = view.findViewById(R.id.edGiaTien);
        ImageView imgChupHinh = view.findViewById(R.id.imgChupHinh);
        imgTam = imgChupHinh;
        ArrayAdapter<String > adapterThucDon = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,thucDonList);
        spinnerThucDon.setAdapter(adapterThucDon);
        adapterThucDon.notifyDataSetChanged();

        if (thucDonModelList.size()==0){
            LayDanhSachThucDon(adapterThucDon);
        }

        imgChupHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,RESULT_IMGTHUCDON);
            }
        });
        btnThemThucDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setVisibility(View.GONE);
                String tenhinh = Calendar.getInstance().getTimeInMillis()+".jpg";
                int position = spinnerThucDon.getSelectedItemPosition();
                String mathucdon = thucDonModelList.get(position).getMathucdon();

                MonAnModel monAnModel = new MonAnModel();
                monAnModel.setTenmon(edTenMon.getText().toString());
                monAnModel.setGiatien(Long.parseLong(edGiaTien.getText().toString()));
                monAnModel.setHinhanh(tenhinh);

                ThemThucDonModel thucDonModel = new ThemThucDonModel();
                thucDonModel.setMathucdon(mathucdon);
                thucDonModel.setMonAnModel(monAnModel);

                themThucDonModelList.add(thucDonModel);
                CloneThucDon();
            }
        });
        khungChuaThucDon.addView(view);
    }

    private void CloneChiNhanh(){
        View view1 = LayoutInflater.from(ThemQuanAnActivity.this).inflate(R.layout.layout_clone_chinhanh,null);
        ImageButton btnThemChiNhanh = view1.findViewById(R.id.btnThemChiNhanh);
        ImageButton btnXoaChiNhanh = view1.findViewById(R.id.btnXoaChiNhanh);

        btnThemChiNhanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText edTenChiNhanh = view1.findViewById(R.id.edTenChiNhanh);
                String tenChiNhanh = edTenChiNhanh.getText().toString();

                view.setVisibility(View.GONE);
                btnXoaChiNhanh.setVisibility(View.VISIBLE);
                btnXoaChiNhanh.setTag(tenChiNhanh);

                chiNhanhList.add(tenChiNhanh);
                CloneChiNhanh();
            }
        });

        btnXoaChiNhanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenChiNhanh = view.getTag().toString();
                chiNhanhList.remove(tenChiNhanh);
                khungChuaChiNhanh.removeView(view1);
            }
        });
        khungChuaChiNhanh.addView(view1);
    }

    private void LayDanhSachThucDon(ArrayAdapter<String > adapterThucDon){
        FirebaseDatabase.getInstance().getReference().child("thucdons").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    ThucDonModel thucDonModel = new ThucDonModel();
                    String key = dataSnapshot.getKey();
                    String value = dataSnapshot.getValue(String.class);

                    thucDonModel.setTenthucdon(value);
                    thucDonModel.setMathucdon(key);
                    thucDonModelList.add(thucDonModel);
                    thucDonList.add(value);

                }
                adapterThucDon.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void LayDanhSachKhuVuc(){
        FirebaseDatabase.getInstance().getReference().child("khuvucs").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String tenKhuVuc = dataSnapshot.getKey();
                    khuVucList.add(tenKhuVuc);
                }
                adapterKhuVuc.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void LayDanhSachTienIch(){
        FirebaseDatabase.getInstance().getReference().child("quanlytienichs").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String matienich = dataSnapshot.getKey();
                    TienIchModel tienIchModel = dataSnapshot.getValue(TienIchModel.class);
                    tienIchModel.setMatienich(matienich);

                    CheckBox checkBox = new CheckBox(ThemQuanAnActivity.this);
                    checkBox.setBackgroundColor(Color.GRAY);
                    checkBox.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    checkBox.setText(tienIchModel.getTentienich());
                    checkBox.setTag(matienich);
                    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                            String matienich = compoundButton.getTag().toString();
                            if (isChecked){
                                selectedTienIchList.add(matienich);
                            }
                            else {
                                selectedTienIchList.remove(matienich);
                            }
                           // Log.d("kiemtra",selectedTienIchList.size()+"");
                        }
                    });
                    khungTienIch.addView(checkBox);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        Calendar calendar = Calendar.getInstance();
        int gio = calendar.get(Calendar.HOUR_OF_DAY);
        int phut = calendar.get(Calendar.MINUTE);

        int id = view.getId();
        switch (id){
            case R.id.btnGioDongCua:
                TimePickerDialog timePickerDialog = new TimePickerDialog(ThemQuanAnActivity.this,
                        new TimePickerDialog.OnTimeSetListener(){
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hourofday, int minute) {
                                giodongcua = hourofday+":"+minute;
                                ((Button)view).setText(giodongcua);
                            }
                        },gio,phut,true);
                timePickerDialog.show();
                break;
            case R.id.btnGioMoCua:
                TimePickerDialog moCuatimePickerDialog = new TimePickerDialog(ThemQuanAnActivity.this,
                        (timePicker, hourofday, minute) -> {
                            giomocua = hourofday+":"+minute;
                            ((Button)view).setText(giomocua);
                        },gio,phut,true);
                moCuatimePickerDialog.show();
                break;

            case R.id.imgHinhQuan1:
                ChonHinhTuGallary(RESULT_IMG1);
                break;
            case R.id.imgHinhQuan2:
                ChonHinhTuGallary(RESULT_IMG2);
                break;
            case R.id.imgHinhQuan3:
                ChonHinhTuGallary(RESULT_IMG3);
                break;
            case R.id.imgHinhQuan4:
                ChonHinhTuGallary(RESULT_IMG4);
                break;
            case R.id.imgHinhQuan5:
                ChonHinhTuGallary(RESULT_IMG5);
                break;
            case R.id.imgHinhQuan6:
                ChonHinhTuGallary(RESULT_IMG6);
                break;
            case R.id.imgVideo:
                Intent intent = new Intent();
                intent.setType("video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Chọn hình..."),RESULT_IMGVIDEO);
                break;
            case R.id.btnThemQuanAn:
                ThemQuanAn();
                break;
        }
    }

    private void ThemQuanAn(){
        String tenQuanAn = edTenQuan.getText().toString();
        long giaToiDa = Long.parseLong(edGiaToiDa.getText().toString());
        long giaToiThieu = Long.parseLong(edGiaToiThieu.getText().toString());
        int idRadioSelected = rdTrangThai.getCheckedRadioButtonId();
        boolean giaoHang;

        if(idRadioSelected == R.id.rdGiaoHang){
            giaoHang=true;
        }else {
            giaoHang = false;
        }
        DatabaseReference nodeRoot = FirebaseDatabase.getInstance().getReference();
        DatabaseReference nodeQuanAn = nodeRoot.child("quanans");
         maquanan = nodeQuanAn.push().getKey();
        nodeRoot.child("khuvucs").child(khuvuc).push().setValue(maquanan);

        for (String chinhanh : chiNhanhList){
            String urlGeoCoding = "https://maps.googleapis.com/maps/api/geocode/json?address="+
                    chinhanh.replace(" ","%20")+"&key=AIzaSyAyyP4Sdyli6NjF8fmeKmjk2MSFXVSmQsQ";
            DownloadToaDo downloadToaDo = new DownloadToaDo();
            downloadToaDo.execute(urlGeoCoding);

        }
        RestaurantModel restaurantModel = new RestaurantModel();
        restaurantModel.setTenquanan(tenQuanAn);
        restaurantModel.setGiatoida(giaToiDa);
        restaurantModel.setGiatoithieu(giaToiThieu);
        restaurantModel.setGiaohang(giaoHang);
        restaurantModel.setVideogioithieu(videoSelected.getLastPathSegment());
        restaurantModel.setTienich(selectedTienIchList);
        restaurantModel.setLuotthich("0");

        nodeQuanAn.child(maquanan).setValue(restaurantModel).addOnSuccessListener(unused ->
                Toast.makeText(ThemQuanAnActivity.this, "them thanh cong !", Toast.LENGTH_SHORT).show());

        FirebaseStorage.getInstance().getReference().child("video/"+videoSelected.getLastPathSegment()).putFile(videoSelected);

        for (Uri hinhquan : hinhQuanAn){
            FirebaseStorage.getInstance().getReference().child("hinhanh/"+hinhquan.getLastPathSegment()).putFile(hinhquan);
            nodeRoot.child("hinhanhquanans").child(maquanan).push().child(hinhquan.getLastPathSegment());
        }

        for (int i =0 ;i<themThucDonModelList.size();i++){
            nodeRoot.child("thucdonquanans").child(maquanan).child(themThucDonModelList.get(i).getMathucdon()).push().setValue(themThucDonModelList.get(i).getMonAnModel());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Bitmap bitmap = hinhDaChup.get(i);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
            byte[] data = baos.toByteArray();

            FirebaseStorage.getInstance().getReference().child("hinhanh/"+themThucDonModelList.get(i).getMonAnModel().getHinhanh()).putBytes(data);
        }
    }

    @SuppressLint("StaticFieldLeak")
    class DownloadToaDo extends AsyncTask<String ,Void,String >{

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder stringBuilder = new StringBuilder();
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader=  new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line=bufferedReader.readLine())!=null){
                    stringBuilder.append(line+"\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray results = jsonObject.getJSONArray("results");
                for (int i =0;i<results.length();i++){
                    JSONObject object = results.getJSONObject(i);
                    String address = object.getString("fomatted_address");
                    JSONObject geometry = object.getJSONObject("geometry");
                    JSONObject location = object.getJSONObject("location");

                    double latitude = (double) location.get("lat");
                    double longitude = (double) location.get("lng");

                    ChiNhanhQuanAnModel chiNhanhQuanAnModel = new ChiNhanhQuanAnModel();
                    chiNhanhQuanAnModel.setDiachi(address);
                    chiNhanhQuanAnModel.setLatitude(latitude);
                    chiNhanhQuanAnModel.setLongitude(longitude);
                    FirebaseDatabase.getInstance().getReference().child("chinhanhquanans").child(maquanan).push().setValue(chiNhanhQuanAnModel);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void ChonHinhTuGallary(int requestcode){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Chọn hình..."),requestcode);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()){
            case R.id.spinnerKhuVuc:
                khuvuc = khuVucList.get(i);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
