package com.example.lamth.app_tong_the_gioi_sach.helper;

import android.support.v7.app.AlertDialog;
import android.widget.ListView;

import java.util.ArrayList;

public class cl_cm {
    private ListView listview;
    private AlertDialog dg;
    private ArrayList<item_Name_TheLoaiSach> list;

    public cl_cm(AlertDialog dg, ListView listview,ArrayList<item_Name_TheLoaiSach> list) {
        this.dg = dg;
        this.listview = listview;
        this.list = list;
    }

    public cl_cm() {
    }

    public AlertDialog getDg() {
        return dg;
    }

    public void setDg(AlertDialog dg) {
        this.dg = dg;
    }

    public ListView getListview() {
        return listview;
    }

    public void setListview(ListView listview) {
        this.listview = listview;
    }

    public ArrayList<item_Name_TheLoaiSach> getList() {
        return list;
    }

    public void setList(ArrayList<item_Name_TheLoaiSach> list) {
        this.list = list;
    }

}
