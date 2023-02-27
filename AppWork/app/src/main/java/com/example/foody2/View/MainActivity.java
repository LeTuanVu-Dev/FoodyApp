package com.example.foody2.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.foody2.Adapters.AdapterViewpagerMain;
import com.example.foody2.R;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener {

    ViewPager viewpager_main;
    RadioButton radio_where,radio_eat_what;
    RadioGroup radio_group;
    ImageView imgThemQuanAn;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewpager_main = findViewById(R.id.viewpager_main);
        radio_eat_what = findViewById(R.id.radio_eat_what);
        radio_where = findViewById(R.id.radio_where);
        radio_group = findViewById(R.id.radio_group);
        imgThemQuanAn = findViewById(R.id.imgThemQuanAn);

        AdapterViewpagerMain adapterViewpagerMain = new AdapterViewpagerMain(getSupportFragmentManager());
        viewpager_main.setAdapter(adapterViewpagerMain);

        viewpager_main.addOnPageChangeListener(this);
        radio_group.setOnCheckedChangeListener(this);
        imgThemQuanAn.setOnClickListener(view -> startActivity(new Intent(MainActivity.this,ThemQuanAnActivity.class)));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position){
            case 0:
                radio_where.setChecked(true);
                break;
            case 1:
                radio_eat_what.setChecked(true);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    // click chuyển page where & eat what
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i){
            case R.id.radio_where:
                viewpager_main.setCurrentItem(0);
                break;
            case R.id.radio_eat_what:
               // startActivity(new Intent(this,ThemQuanAnActivity.class));
                viewpager_main.setCurrentItem(1);
                break;
        }
    }
}