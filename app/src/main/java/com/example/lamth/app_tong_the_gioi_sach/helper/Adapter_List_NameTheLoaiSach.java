package com.example.lamth.app_tong_the_gioi_sach.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lamth.app_tong_the_gioi_sach.R;

import java.util.ArrayList;

public class Adapter_List_NameTheLoaiSach  extends BaseAdapter {
    private ArrayList<item_Name_TheLoaiSach> listData;
    private LayoutInflater layoutInflater;


    public Adapter_List_NameTheLoaiSach(Context aContext, ArrayList<item_Name_TheLoaiSach> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }


    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        TextView textView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_name_theloaisach, null);
            holder = new ViewHolder();

            holder.textView = (TextView) convertView.findViewById(R.id.textView1);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.textView.setText(listData.get(position).getTenTheLoaiSach());

        return convertView;

    }
}
