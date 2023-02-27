package com.example.foody2.Controller;

import com.example.foody2.Model.BinhLuanModel;

import java.util.List;

public class BinhLuanController {
    BinhLuanModel binhLuanModel;

    public BinhLuanController() {
        binhLuanModel = new BinhLuanModel();

    }

    public void ThemBinhLuan(String maquanan,BinhLuanModel binhLuanModel, List<String > listHinh){
        binhLuanModel.ThemBinhLuan(maquanan,binhLuanModel,listHinh);
    }
}
