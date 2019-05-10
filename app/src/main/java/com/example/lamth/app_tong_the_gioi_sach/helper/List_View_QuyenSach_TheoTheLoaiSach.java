package com.example.lamth.app_tong_the_gioi_sach.helper;
import java.util.ArrayList;
import java.util.List;


public class List_View_QuyenSach_TheoTheLoaiSach { // Hàm ví dụ CompanyModel - Ojbet Cha

    // Khởi tạo biến ArrayList<String> từ thằng T_Item_quyensach
    private ArrayList<T_Item_quyensach> listItem;

    public List_View_QuyenSach_TheoTheLoaiSach() {
    }

    public List_View_QuyenSach_TheoTheLoaiSach(ArrayList<T_Item_quyensach> listItem) {
        this.listItem = listItem;
    }

    public ArrayList<T_Item_quyensach> getListItem() {
        return listItem;
    }

    public void setListItem(ArrayList<T_Item_quyensach> listItem) {
        this.listItem = listItem;
    }
}
