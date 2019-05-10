package com.example.lamth.app_tong_the_gioi_sach.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lamth.app_tong_the_gioi_sach.R;

import java.util.ArrayList;

public class CustomListQuyenSach extends BaseAdapter {


    private ArrayList<ListQuyenSach> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public CustomListQuyenSach(Context context,ArrayList<ListQuyenSach> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(context);
        notifyDataSetChanged();
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

        TextView Id_qs;
        ImageView HinhAnh;
        TextView TieuDe;
        TextView TacGia;
        TextView TheLoaiTruyen;
        TextView LuotXem;
        ImageView luotview;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null)
        {
            convertView = layoutInflater.inflate(R.layout.listview_quyensach_layout, null);
            holder = new ViewHolder();

            holder.HinhAnh = (ImageView) convertView.findViewById(R.id.imageView1);
            holder.TieuDe = (TextView) convertView.findViewById(R.id.TextView1);
            holder.TacGia = (TextView) convertView.findViewById(R.id.TextView2);
            holder.TheLoaiTruyen = (TextView) convertView.findViewById(R.id.TextView3);
            holder.LuotXem = (TextView) convertView.findViewById(R.id.TextView4);
            holder.luotview = (ImageView) convertView.findViewById(R.id.imageView10);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }


        if(listData.size()>0){
            ListQuyenSach listQuyenSach = listData.get(position);
            if (listQuyenSach != null) {
                //holder.Id_qs.setText(listQuyenSach.getId_QuyenSach());
                if(listQuyenSach.getHinhAnh() != null){
                    holder.HinhAnh.setImageBitmap(listQuyenSach.getHinhAnh());
                }
                holder.TieuDe.setText(listQuyenSach.getTieuDe());
                holder.TacGia.setText(listQuyenSach.getTacGia());
                //holder.NgayUp.setText("Ngày Up : " + listQuyenSach.getNgayUp());
                if (listQuyenSach.getTheLoaiTruyen().equals("1")){
                    holder.TheLoaiTruyen.setText("Truyện Chữ");
                }else {
                    holder.TheLoaiTruyen.setText("Truyện PDF");
                }
                holder.luotview.setImageResource(R.drawable.icon_view);
                holder.LuotXem.setText(" " + String.valueOf(listQuyenSach.getLuotXem() + " lượt view ")); // kiểu int biến đổi về string
            }
        }

        return convertView;
    }
}
