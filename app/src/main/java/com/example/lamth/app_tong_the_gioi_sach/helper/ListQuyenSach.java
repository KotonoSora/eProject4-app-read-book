package com.example.lamth.app_tong_the_gioi_sach.helper;

import android.graphics.Bitmap;

public class ListQuyenSach {

    private int Id_QuyenSach;
    private Bitmap HinhAnh;
    private String TieuDe;
    private String TacGia;
    private String TheLoaiTruyen;
    private String NgayUp;
    private int LuotXem;
    private int Id_loaisach_timkiem;

    public ListQuyenSach() {
    }

    public ListQuyenSach(int id_QuyenSach, Bitmap hinhAnh, String tieuDe, String tacGia, String theLoaiTruyen, String ngayUp, int luotXem, int id_loaisach_timkiem) {
        Id_QuyenSach = id_QuyenSach;
        HinhAnh = hinhAnh;
        TieuDe = tieuDe;
        TacGia = tacGia;
        TheLoaiTruyen = theLoaiTruyen;
        NgayUp = ngayUp;
        LuotXem = luotXem;
        Id_loaisach_timkiem = id_loaisach_timkiem;
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

    public String getTieuDe() {
        return TieuDe;
    }

    public void setTieuDe(String tieuDe) {
        TieuDe = tieuDe;
    }

    public String getTacGia() {
        return TacGia;
    }

    public void setTacGia(String tacGia) {
        TacGia = tacGia;
    }

    public String getTheLoaiTruyen() {
        return TheLoaiTruyen;
    }

    public void setTheLoaiTruyen(String theLoaiTruyen) {
        TheLoaiTruyen = theLoaiTruyen;
    }

    public String getNgayUp() {
        return NgayUp;
    }

    public void setNgayUp(String ngayUp) {
        NgayUp = ngayUp;
    }

    public int getLuotXem() {
        return LuotXem;
    }

    public void setLuotXem(int luotXem) {
        LuotXem = luotXem;
    }

    public int getId_loaisach_timkiem() {
        return Id_loaisach_timkiem;
    }

    public void setId_loaisach_timkiem(int id_loaisach_timkiem) {
        Id_loaisach_timkiem = id_loaisach_timkiem;
    }
}
