package com.example.lamth.app_tong_the_gioi_sach.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lamth.app_tong_the_gioi_sach.R;

import java.util.ArrayList;

public class CustomListQuyenSachCoChuong extends BaseAdapter {

    private ArrayList<ListQuyenSachCoChuong> listData;
    private LayoutInflater layoutInflater;

    public CustomListQuyenSachCoChuong(Context aContext, ArrayList<ListQuyenSachCoChuong> listData) {
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

        TextView tieudechuong;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.listview_chuong_layout, null);
            holder = new ViewHolder();

            holder.tieudechuong = (TextView) convertView.findViewById(R.id.textView2); // Nằm trong item list chi tiết.

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tieudechuong.setText(listData.get(position).getTieuDeChuong());

        return convertView;
    }
}
