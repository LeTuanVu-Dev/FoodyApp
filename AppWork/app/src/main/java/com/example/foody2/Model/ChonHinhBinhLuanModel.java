package com.example.foody2.Model;

public class ChonHinhBinhLuanModel {
    String duondan;
    boolean isCheck;

    public ChonHinhBinhLuanModel(String duondan, boolean isCheck) {
        this.duondan = duondan;
        this.isCheck = isCheck;
    }

    public String getDuondan() {
        return duondan;
    }

    public void setDuondan(String duondan) {
        this.duondan = duondan;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
