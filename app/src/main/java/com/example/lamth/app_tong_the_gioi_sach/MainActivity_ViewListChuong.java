package com.example.lamth.app_tong_the_gioi_sach;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lamth.app_tong_the_gioi_sach.helper.CustomListQuyenSachCoChuong;
import com.example.lamth.app_tong_the_gioi_sach.helper.ListQuyenSachCoChuong;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.sdsmdg.tastytoast.TastyToast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static com.example.lamth.app_tong_the_gioi_sach.Url_config.banner_footer;

public class MainActivity_ViewListChuong extends AppCompatActivity {

    ArrayList<ListQuyenSachCoChuong> listdata;
    private int currentPage = 1;

    private RelativeLayout re_back , re_next , re_prev , re_vien ,re_listview;

    private LinearLayout li_next_prev;

    String Id_qs = "";
    String tenquyensach = "";
    TextView tv_page , tv_tenquyensach , textView1;

    private AdView av;

    String TongChuong = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__view_list_chuong);

        Intent iin = getIntent();
        Bundle b = iin.getExtras();

        if (b != null) {
            Id_qs = (String) b.getString("id_listqs"); //b.getString("id_listqs");
            tenquyensach = (String) b.getString("tenquyensach"); //b.getString("id_listqs");
            //TongChuong = (String) b.getString("tongchuong");
        }

        listdata = new ArrayList<ListQuyenSachCoChuong>();

        new LoadApplication().execute();

        re_back = (RelativeLayout) findViewById(R.id.re_back);
        re_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    onBackPressed();
                }catch (Exception ep){}
            }
        });

        re_next = (RelativeLayout) findViewById(R.id.re_next);
        re_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                currentPage = currentPage + 1;

                new LoadApplication().execute();

                // kiểm tra trang đầu và trang cuối .
                if(listdata.size() < 60 && currentPage > 1){
                    TastyToast.makeText(getApplicationContext(), "Đang ở trang cuối!", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                }

            }
        });
        re_prev = (RelativeLayout) findViewById(R.id.re_prev);
        re_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                currentPage = currentPage - 1;

                new LoadApplication().execute();

                if(currentPage == 1 && listdata.size() < 0){
                    TastyToast.makeText(getApplicationContext(), "Đang ở trang đầu!", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                }
            }
        });

        re_vien = (RelativeLayout) findViewById(R.id.re_vien);
        re_listview  = (RelativeLayout) findViewById(R.id.re_listview);
        tv_page = (TextView) findViewById(R.id.textView3);
        li_next_prev = (LinearLayout) findViewById(R.id.li_next_prev);
        textView1 = (TextView) findViewById(R.id.textView1);

        tv_tenquyensach = (TextView) findViewById(R.id.textView4);
        if (tenquyensach.length() >= 33){
            String sub = tenquyensach.substring(0,33);
            tv_tenquyensach.setText(sub + " ...");
        }else {
            tv_tenquyensach.setText(tenquyensach);
        }

        //textView1.setText("Danh sách chương : " + TongChuong + " chương");
        // Quảng cáo
        av = new AdView(this);
        av.setAdSize(AdSize.BANNER);
        av.setAdUnitId(banner_footer);
        AdRequest adRequest = new AdRequest.Builder().build();
        av.loadAd(adRequest);

        av.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                RelativeLayout layout = (RelativeLayout) findViewById(R.id.adView);
                if (layout != null) {
                    layout.removeAllViews();
                    layout.addView(av);
                }
            }
        });
    }

    public String get_dulieu(String url_param) {
        String result = "";
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(url_param);
            urlConnection = (HttpURLConnection) url.openConnection();

            int code = urlConnection.getResponseCode();

            if (code == 200) {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                if (in != null) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                    String line = "";

                    while ((line = bufferedReader.readLine()) != null)
                        result += line;
                }
                in.close();
            }
        } catch (Exception e) {
        } finally {
            urlConnection.disconnect();
        }
        return result;
    }


    private class LoadApplication extends AsyncTask<Void, Void, Void> {
        String result;
        String get_IdQuyensach = "";
        String[] item_quyensach;

        @Override
        protected Void doInBackground(Void... params) {

            try{

                if(listdata.size()>0){
                    listdata.clear();
                }
                // Prev trang đầu , nếu là trang đầu không cần load nữa
                String _getListChuong = get_dulieu(Url_config.url_get_ListAllChuong + Id_qs + "&page=" + currentPage);
                if(_getListChuong.trim().length() == 0 && currentPage>1){
                    currentPage = currentPage - 1;
                }
                // Kết thúc Prev trang đầu , nếu là trang đầu không cần load nữa

                // Next trang cuối load nhẹ 1 lần nữa
                if(_getListChuong.trim().length() == 0 && currentPage==0){
                    currentPage = 1;
                }
                _getListChuong = get_dulieu(Url_config.url_get_ListAllChuong + Id_qs + "&page=" + currentPage);
                // Kết thúc Next trang cuối load nhẹ 1 lần nữa

                String[] listchuong_item = _getListChuong.split("@");
                // TH 1 nếu dùng api vòng for(i--) bỏ phần tử @ thứ nhất thì j = 1 // @10225|962|Chương 1: Những Dấu Hiệu Của Sự Lừa Dối@10226|962|Chương 2: Cách Phát Hiện Kẻ Dối Trá@10227|962|Chương 3: Chiến Thuật Phát Giác Sự Dối Trá Và Thu Thập Thông Tin Trong Các Cuộc Đàm Thoại Tình Cờ@10228|962|Chương 5: Những Kỹ Thuật Tiên Tiến@10229|962|Chương 6: Tâm Lý Ở Phía Bạn@10230|962|Chương 7: Những Rào Cản Bên Trong: Lời Nói Dối Tồi Tệ Nhất Là Lời Nói Dối Chính Mình10231|962|Chương 8: Những Rào Cản Bên Ngoài: Mánh Lới Trong Nghề
                // TH 2 nếu dùng api vòng for(i+) bỏ phần tử @ kế tiếp thì j= 0 //10225|962|Chương 1: Những Dấu Hiệu Của Sự Lừa Dối@10226|962|Chương 2: Cách Phát Hiện Kẻ Dối Trá@10227|962|Chương 3: Chiến Thuật Phát Giác Sự Dối Trá Và Thu Thập Thông Tin Trong Các Cuộc Đàm Thoại Tình Cờ@10228|962|Chương 5: Những Kỹ Thuật Tiên Tiến@10229|962|Chương 6: Tâm Lý Ở Phía Bạn@10230|962|Chương 7: Những Rào Cản Bên Trong: Lời Nói Dối Tồi Tệ Nhất Là Lời Nói Dối Chính Mình@10231|962|Chương 8: Những Rào Cản Bên Ngoài: Mánh Lới Trong Nghề
                for (int j = 0; j < listchuong_item.length; j++) {
                    String it1 = listchuong_item[j];
                    String[] jh = it1.split("\\|");
                    ListQuyenSachCoChuong iui = new ListQuyenSachCoChuong();
                    iui.setId_chuongtheo_quyensach(Integer.parseInt(jh[0]));
                    iui.setId_QuyenSach(Integer.parseInt(jh[1]));
                    iui.setTieuDeChuong(jh[2]);
                    listdata.add(iui);
                }
            }catch (Exception ep){}

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {


            //listview
            ArrayAdapter<ListQuyenSachCoChuong> adapter = new ArrayAdapter<ListQuyenSachCoChuong>(getApplicationContext(), android.R.layout.activity_list_item, listdata);
            ListView listView = (ListView) findViewById(R.id.view_listchuong);
             listView.setAdapter(adapter);
            if (listdata.size() > 0) {
                if (listdata.size() > 0) {
                    listView.setAdapter(new CustomListQuyenSachCoChuong(getApplicationContext(), listdata));
                }
                adapter.notifyDataSetChanged();
                try{
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity_DocSach.class);
                            intent.putExtra("id_chuong", listdata.get(position).getId_chuongtheo_quyensach());
                            intent.putExtra("id_qs_theochuong", listdata.get(position).getId_QuyenSach());
                            intent.putExtra("tieude_chuong", listdata.get(position).getTieuDeChuong());
                            intent.putExtra("tenquyensach", tenquyensach);
                            startActivity(intent);
                        }
                    });
                }catch (Exception ex){ }

                tv_page.setText("" + (currentPage));

            }else {
                re_vien.setVisibility(View.VISIBLE);
                re_listview.setVisibility(View.INVISIBLE);
            }


            if(listdata.size() >= 60){
                li_next_prev.setVisibility(View.VISIBLE);
            }

            super.onPostExecute(aVoid);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }


    @Override
    public void onPause() {
        if (av != null) {
            av.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (av != null) {
            av.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (av != null) {
            av.destroy();
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


}
