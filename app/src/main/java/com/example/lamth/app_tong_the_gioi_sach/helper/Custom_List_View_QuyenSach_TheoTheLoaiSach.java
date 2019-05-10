package com.example.lamth.app_tong_the_gioi_sach.helper;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lamth.app_tong_the_gioi_sach.MainActivity;
import com.example.lamth.app_tong_the_gioi_sach.MainActivity_Home;
import com.example.lamth.app_tong_the_gioi_sach.MainActivity_QuyenSach;
import com.example.lamth.app_tong_the_gioi_sach.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;

import static com.example.lamth.app_tong_the_gioi_sach.Url_config.banner_full;

public class Custom_List_View_QuyenSach_TheoTheLoaiSach extends BaseAdapter {

    InterstitialAd mInterstitialAd;
    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest);
    }

    private ArrayList<List_View_QuyenSach_TheoTheLoaiSach> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public Custom_List_View_QuyenSach_TheoTheLoaiSach(Context context,ArrayList<List_View_QuyenSach_TheoTheLoaiSach> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(context);
        notifyDataSetChanged();
        this.context = context;
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

        TextView textView11;
        RelativeLayout re_xemthem;

        LinearLayout re_item1 , re_item2 , re_item3 , re_item4;

        ImageView HinhAnh;
        TextView TieuDe;

        ImageView HinhAnh2;
        TextView TieuDe2;

        ImageView HinhAnh3;
        TextView TieuDe3;

        ImageView HinhAnh4;
        TextView TieuDe4;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null)
        {
            convertView = layoutInflater.inflate(R.layout.gridview_list_all_quyensachnew_update, null);
            holder = new ViewHolder();

            holder.textView11 = (TextView) convertView.findViewById(R.id.textView11);
            holder.re_xemthem = (RelativeLayout) convertView.findViewById(R.id.re_xemthem);

            holder.HinhAnh = (ImageView) convertView.findViewById(R.id.imageView9);
            holder.TieuDe = (TextView) convertView.findViewById(R.id.textView8);

            holder.HinhAnh2 = (ImageView) convertView.findViewById(R.id.imageView11);
            holder.TieuDe2 = (TextView) convertView.findViewById(R.id.textView10);

            holder.HinhAnh3 = (ImageView) convertView.findViewById(R.id.imageView110);
            holder.TieuDe3 = (TextView) convertView.findViewById(R.id.textView100);

            holder.HinhAnh4 = (ImageView) convertView.findViewById(R.id.imageView13);
            holder.TieuDe4 = (TextView) convertView.findViewById(R.id.textView12);

            holder.re_item1 = (LinearLayout) convertView.findViewById(R.id.re_item1);
            holder.re_item2 = (LinearLayout) convertView.findViewById(R.id.re_item2);
            holder.re_item3 = (LinearLayout) convertView.findViewById(R.id.re_item3);
            holder.re_item4 = (LinearLayout) convertView.findViewById(R.id.re_item4);

            mInterstitialAd = new InterstitialAd(context);
            mInterstitialAd.setAdUnitId(banner_full);
            requestNewInterstitial();
            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    // Code to be executed when an ad finishes loading.
                }

                @Override
                public void onAdFailedToLoad(int errorCode) {
                    // Code to be executed when an ad request fails.
                }

                @Override
                public void onAdOpened() {
                    // Code to be executed when the ad is displayed.
                }

                @Override
                public void onAdLeftApplication() {
                    // Code to be executed when the user has left the app.
                }

                @Override
                public void onAdClosed() {
                    // Code to be executed when the interstitial ad is closed.
                }
            });

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }


        if(listData.size()>0){

            List_View_QuyenSach_TheoTheLoaiSach List_View_QuyenSach_TheoTheLoaiSach = listData.get(position);

            final ArrayList<T_Item_quyensach> new_list = List_View_QuyenSach_TheoTheLoaiSach.getListItem();
            try{

                final T_Item_quyensach item1 = new_list.get(0);
                final T_Item_quyensach item2 = new_list.get(1);
                final T_Item_quyensach item3 = new_list.get(2);
                final T_Item_quyensach item4 = new_list.get(3);

                holder.HinhAnh.setImageBitmap(item1.getHinhAnh());
                holder.TieuDe.setText(item1.getTenQuyenSach());

                holder.HinhAnh2.setImageBitmap(item2.getHinhAnh());
                holder.TieuDe2.setText(item2.getTenQuyenSach());

                holder.HinhAnh3.setImageBitmap(item3.getHinhAnh());
                holder.TieuDe3.setText(item3.getTenQuyenSach());

                holder.HinhAnh4.setImageBitmap(item4.getHinhAnh());
                holder.TieuDe4.setText(item4.getTenQuyenSach());

                if (item1.getTenQuyenSach().length() >= 28){
                    String sub = item1.getTenQuyenSach().substring(0,28);
                    holder.TieuDe.setText(sub + " ...");
                }else {
                    holder.TieuDe.setText(item1.getTenQuyenSach());
                }

                if (item2.getTenQuyenSach().length() >= 28){
                    String sub = item2.getTenQuyenSach().substring(0,28);
                    holder.TieuDe2.setText(sub + " ...");
                }else {
                    holder.TieuDe2.setText(item2.getTenQuyenSach());
                }

                if (item3.getTenQuyenSach().length() >= 28){
                    String sub = item3.getTenQuyenSach().substring(0,28);
                    holder.TieuDe3.setText(sub + " ...");
                }else {
                    holder.TieuDe3.setText(item3.getTenQuyenSach());
                }

                if (item4.getTenQuyenSach().length() >= 28){
                    String sub = item4.getTenQuyenSach().substring(0,28);
                    holder.TieuDe4.setText(sub + " ...");
                }else {
                    holder.TieuDe4.setText(item4.getTenQuyenSach());
                }

                //action
                holder.HinhAnh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context.getApplicationContext(), MainActivity_QuyenSach.class);
                        intent.putExtra("id_quyensach", item1.getId_QuyenSach());
                        intent.putExtra("id_LoaiSach", item1.getID_LoaiSach()); // string
                        intent.putExtra("id_type", 1); // string
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        //showInterstitial();

                    }
                });

                holder.HinhAnh2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context.getApplicationContext(), MainActivity_QuyenSach.class);
                        intent.putExtra("id_quyensach", item2.getId_QuyenSach());
                        intent.putExtra("id_LoaiSach", item2.getID_LoaiSach()); // string
                        intent.putExtra("id_type", 1); // string
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        //showInterstitial();

                    }
                });

                holder.HinhAnh3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context.getApplicationContext(), MainActivity_QuyenSach.class);
                        intent.putExtra("id_quyensach", item3.getId_QuyenSach());
                        intent.putExtra("id_LoaiSach", item3.getID_LoaiSach()); // string
                        intent.putExtra("id_type", 1); // string
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        //showInterstitial();

                    }
                });


                holder.HinhAnh4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context.getApplicationContext(), MainActivity_QuyenSach.class);
                        intent.putExtra("id_quyensach", item4.getId_QuyenSach());
                        intent.putExtra("id_LoaiSach", item4.getID_LoaiSach()); // string
                        intent.putExtra("id_type", 1); // string
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        //showInterstitial();

                    }
                });

                // Lấy luôn item1 vì chúng nó có cùng thể loại sách lấy dc item1 thì lấy đc tất cả hết item.getTenTheLoaiSach
                holder.textView11.setText(item1.getTenTheLoaiSach());

                holder.re_xemthem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context.getApplicationContext(), MainActivity.class);
                        intent.putExtra("id_TenLoaiSach", item1.getTenTheLoaiSach());
                        intent.putExtra("id_LoaiSach", item1.getID_LoaiSach()); // string
                        intent.putExtra("id_type",1); // string
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        showInterstitial();
                    }
                });


                holder.TieuDe.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context.getApplicationContext(), MainActivity_QuyenSach.class);
                        intent.putExtra("id_quyensach", item1.getId_QuyenSach());
                        intent.putExtra("id_LoaiSach", item1.getID_LoaiSach()); // string
                        intent.putExtra("id_type", 1); // string
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                });

                holder.TieuDe2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context.getApplicationContext(), MainActivity_QuyenSach.class);
                        intent.putExtra("id_quyensach", item2.getId_QuyenSach());
                        intent.putExtra("id_LoaiSach", item2.getID_LoaiSach()); // string
                        intent.putExtra("id_type", 1); // string
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                });

                holder.TieuDe3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context.getApplicationContext(), MainActivity_QuyenSach.class);
                        intent.putExtra("id_quyensach", item3.getId_QuyenSach());
                        intent.putExtra("id_LoaiSach", item3.getID_LoaiSach()); // string
                        intent.putExtra("id_type", 1); // string
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                });

                holder.TieuDe4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context.getApplicationContext(), MainActivity_QuyenSach.class);
                        intent.putExtra("id_quyensach", item4.getId_QuyenSach());
                        intent.putExtra("id_LoaiSach", item4.getID_LoaiSach()); // string
                        intent.putExtra("id_type", 1); // string
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                });


            }catch (Exception ex){}
        }
        return convertView;
    }

    private void showInterstitial() {
        if (mInterstitialAd.isLoaded() && mInterstitialAd != null ) {
            mInterstitialAd.show();
        }
    }
}
