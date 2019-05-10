package com.example.lamth.app_tong_the_gioi_sach.helper;

import android.graphics.Bitmap;

public class T_Item_quyensach { // Hàm ví dụ DeviceModel

    private int Id_QuyenSach;
    private Bitmap HinhAnh;
    private String TenQuyenSach;
    private int ID_LoaiSach;
    private String TenTheLoaiSach;

    public T_Item_quyensach(int id_QuyenSach, Bitmap hinhAnh, String tenQuyenSach, int ID_LoaiSach, String tenTheLoaiSach) {
        Id_QuyenSach = id_QuyenSach;
        HinhAnh = hinhAnh;
        TenQuyenSach = tenQuyenSach;
        this.ID_LoaiSach = ID_LoaiSach;
        TenTheLoaiSach = tenTheLoaiSach;
    }

    public T_Item_quyensach() {
    }

    public int getId_QuyenSach() {
        return Id_QuyenSach;
    }

    public void setId_QuyenSach(int id_QuyenSach) {
        Id_QuyenSach = id_QuyenSach;
    }

    public Bitmap getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(Bitmap hinhAnh) {
        HinhAnh = hinhAnh;
    }

    public String getTenQuyenSach() {
        return TenQuyenSach;
    }

    public void setTenQuyenSach(String tenQuyenSach) {
        TenQuyenSach = tenQuyenSach;
    }

    public int getID_LoaiSach() {
        return ID_LoaiSach;
    }

    public void setID_LoaiSach(int ID_LoaiSach) {
        this.ID_LoaiSach = ID_LoaiSach;
    }

    public String getTenTheLoaiSach() {
        return TenTheLoaiSach;
    }

    public void setTenTheLoaiSach(String tenTheLoaiSach) {
        TenTheLoaiSach = tenTheLoaiSach;
    }
}
