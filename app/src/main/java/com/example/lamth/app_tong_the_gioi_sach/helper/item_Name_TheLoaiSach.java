package com.example.lamth.app_tong_the_gioi_sach.helper;

public class item_Name_TheLoaiSach {

    private int Id;
    private String TenTheLoaiSach;

    public item_Name_TheLoaiSach() {
    }

    public item_Name_TheLoaiSach(int id, String tenTheLoaiSach) {
        Id = id;
        TenTheLoaiSach = tenTheLoaiSach;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTenTheLoaiSach() {
        return TenTheLoaiSach;
    }

    public void setTenTheLoaiSach(String tenTheLoaiSach) {
        TenTheLoaiSach = tenTheLoaiSach;
    }
}
