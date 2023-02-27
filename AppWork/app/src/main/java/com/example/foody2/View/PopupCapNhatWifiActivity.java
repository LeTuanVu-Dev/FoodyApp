package com.example.foody2.View;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foody2.Controller.CapNhatWifiController;
import com.example.foody2.Model.WifiQuanAnModel;
import com.example.foody2.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PopupCapNhatWifiActivity extends AppCompatActivity {

    EditText txtMatKhauWifi,txtTenWifi;
    Button btnDongY,btnHuy;
    CapNhatWifiController capNhatWifiController;
    String maquanan;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_popup_capnhatwifi);

        txtMatKhauWifi = findViewById(R.id.txtMatKhauWifi);
        txtTenWifi= findViewById(R.id.txtTenWifi);
        btnDongY= findViewById(R.id.btnDongY);
        btnHuy= findViewById(R.id.btnHuy);
        capNhatWifiController = new CapNhatWifiController(this);
        maquanan = getIntent().getStringExtra("maquanan");

        btnDongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ten = txtTenWifi.getText().toString();
                String pass = txtMatKhauWifi.getText().toString();

                if(ten.trim().isEmpty() && pass.trim().isEmpty() || ( ten.trim().isEmpty() || pass.trim().isEmpty())  ){
                    Toast.makeText(PopupCapNhatWifiActivity.this, "vui lòng điền đầy đủ thông tin !", Toast.LENGTH_SHORT).show();
                }
                else {
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat simpleDateFormat=  new SimpleDateFormat("dd/MM/yyyy");
                    String ngaydang = simpleDateFormat.format(calendar.getTime());

                    WifiQuanAnModel wifiQuanAnModel = new WifiQuanAnModel();
                    wifiQuanAnModel.setTen(ten);
                    wifiQuanAnModel.setMatkhau(pass);
                    wifiQuanAnModel.setNgaydang(ngaydang);
                    capNhatWifiController.ThemWifi(getApplicationContext(),wifiQuanAnModel,maquanan);
                }
            }
        });

        btnHuy.setOnClickListener(v-> onBackPressed());
    }
}
