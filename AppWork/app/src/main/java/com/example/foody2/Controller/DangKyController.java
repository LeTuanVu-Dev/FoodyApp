package com.example.foody2.Controller;

import com.example.foody2.Model.ThanhVienModel;

public class DangKyController {
    ThanhVienModel thanhVienModel;

    public DangKyController() {
        thanhVienModel = new ThanhVienModel();
    }
    public void ThemThongTinThanhVienController(ThanhVienModel thanhVienModel,String uid){
        thanhVienModel.ThemThongTinThanhVien(thanhVienModel,uid);

    }
}
