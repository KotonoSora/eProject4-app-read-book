package com.example.lamth.app_tong_the_gioi_sach;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lamth.app_tong_the_gioi_sach.helper.CustomListQuyenSach;
import com.example.lamth.app_tong_the_gioi_sach.helper.ListQuyenSach;
import com.example.lamth.app_tong_the_gioi_sach.helper.cl_cm;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.sdsmdg.tastytoast.TastyToast;

import java.io.InputStream;
import java.util.ArrayList;

import static com.example.lamth.app_tong_the_gioi_sach.Url_config._isCheckConnection;
import static com.example.lamth.app_tong_the_gioi_sach.Url_config.banner_footer;
import static com.example.lamth.app_tong_the_gioi_sach.Url_config.banner_full;
import static com.example.lamth.app_tong_the_gioi_sach.Url_config.getResizedBitmap;
import static com.example.lamth.app_tong_the_gioi_sach.Url_config.get_dulieu;
import static com.example.lamth.app_tong_the_gioi_sach.Url_config.showpop_up_Load;
import static com.example.lamth.app_tong_the_gioi_sach.Url_config.showpop_up_tinh_getlistview;
import static com.example.lamth.app_tong_the_gioi_sach.Url_config.url_get_TimKiemTong_QuyenSach;
import static com.example.lamth.app_tong_the_gioi_sach.Url_config.url_image_server;

public class MainActivity_TimKiem_QuyenSach extends AppCompatActivity {

    private ListView listview;

    ArrayList<ListQuyenSach> listdata;

    ArrayAdapter<ListQuyenSach> adapter;

    RelativeLayout re_back , re_theloaisach , re_timkiem , re_next_prove , re_previous , re_next ;

    TextView tv_theloaisach , so_page;

    private EditText editText ;

    //define bien
    String id_type = "";
    private int id_theloaisch = 0;
    private String keyword ="";

    private int currentPage = 1; // phan trang

    // Quảng Cáo - Ads Admob

    private AdView av;
    InterstitialAd mInterstitialAd;
    private void requestNewInterstitial() {
        AdRequest adRequest = new  AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__tim_kiem__quyen_sach);

        try{

            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

            listdata = new ArrayList<ListQuyenSach>();

            Bundle extras = getIntent().getExtras();

            if (extras != null) {
                id_type = extras.getString("id_type");
                // and get whatever type user account id is
            }

            re_back = (RelativeLayout) findViewById(R.id.re_back);
            re_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });


            if (!_isCheckConnection(getApplicationContext())) {
                TastyToast.makeText(getApplicationContext(), "Vui lòng kết nối internet!", TastyToast.LENGTH_LONG, TastyToast.ERROR);
            } else {

                //load box text
                loadboxtex();
                new _LoadDulieu().execute();
            }


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

            mInterstitialAd = new InterstitialAd(this);
            mInterstitialAd.setAdUnitId(banner_full);
            requestNewInterstitial();

            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    showInterstitial();
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


        }catch (Exception po){}


    }

    private void loadboxtex(){
        re_theloaisach = (RelativeLayout) findViewById(R.id.re_theloaisach);

        re_timkiem = (RelativeLayout) findViewById(R.id.re_timkiem);
        tv_theloaisach = (TextView) findViewById(R.id.textView17);
        editText = (EditText) findViewById(R.id.editText);
        so_page = (TextView) findViewById(R.id.textView3);
        re_previous = (RelativeLayout) findViewById(R.id.re_prev);
        re_next = (RelativeLayout) findViewById(R.id.re_next);
        re_next_prove = (RelativeLayout) findViewById(R.id.re_next_prove);
        // Load Id và hiện tên thể loại sách
        re_theloaisach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   try{
                       final cl_cm item = showpop_up_tinh_getlistview(MainActivity_TimKiem_QuyenSach.this);
                       item.getListview().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                           @Override
                           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                               tv_theloaisach.setText(item.getList().get(position).getTenTheLoaiSach());
                               id_theloaisch = item.getList().get(position).getId();
                               item.getDg().dismiss();
                           }
                       });
                   }catch (Exception pp){

               }
            }
        });


        //
        re_timkiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    // Kiểm tra keyword có dữ liệu và bấm nút timfkiem thì đóng keybord lại ngược lại thì mở lên để sợt
                    if (keyword != null){
                        keyword = editText.getText().toString().trim();
                        new _LoadDulieu().execute();
                        closeKeyboard();
                    }else {
                        showKeyboard(editText);
                    }

                }catch (Exception od){}
            }
        });

        re_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPage = currentPage + 1;

                new _LoadDulieu().execute();
                if(listdata.size()<40 && currentPage>1){
                    TastyToast.makeText(getApplicationContext(), "Đang ở trang cuối!", TastyToast.LENGTH_LONG,
                            TastyToast.WARNING);
                }

            }
        });

        re_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPage = currentPage - 1;

                new _LoadDulieu().execute();
                if(currentPage==1){
                    TastyToast.makeText(getApplicationContext(), "Đang ở trang đầu!", TastyToast.LENGTH_LONG,
                            TastyToast.WARNING);
                }
            }
        });

    }

    private void closeKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }

    private void showKeyboard(EditText editText) {
        editText.requestFocus();
        editText.setFocusableInTouchMode(true);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.RESULT_UNCHANGED_SHOWN);
        editText.setSelection(editText.getText().length());
    }

    private class _LoadDulieu extends AsyncTask<Object, Object, String> {
        String result = "";
        AlertDialog bg = null;
        @Override
        protected void onPreExecute() {
            bg = showpop_up_Load(MainActivity_TimKiem_QuyenSach.this);
        }

        @Override
        protected String doInBackground(Object... params) {

            try {
                    if(listdata.size()>0){
                        listdata.clear();
                    }

                    //String msg_link = url_get_TimKiemTong_QuyenSach + keyword + "&id_loaisach=" + id_theloaisch + "&page=" + currentPage;
                    String chuoidulieu = get_dulieu(url_get_TimKiemTong_QuyenSach + keyword + "&id_loaisach=" + id_theloaisch + "&page=" + currentPage);
                    if(chuoidulieu.trim().length() == 0 && currentPage>2){
                        currentPage = currentPage - 1; // Phân trang tương lai = phân trang hiện tại - 1.
                    }
                    // Kiểm tra _getIdQuyenSach có ký tự == 0 và phân trang == 0 - Nếu thỏa mản Đk :
                    if(chuoidulieu.trim().length() == 0 && currentPage==0){
                        currentPage = 1; // Thì ép phân trang lại thành phân trang 1 .
                    }
                    // 2 ĐK trên cái nào thỏa mản thì chạy xuống hàm này .
                    chuoidulieu = get_dulieu(url_get_TimKiemTong_QuyenSach + keyword + "&id_loaisach=" + id_theloaisch + "&page=" + currentPage);

                    if(chuoidulieu.trim().length()>0){
                        try{
                            String[] listcap1 = chuoidulieu.split("@");

                            for (int i = 0; i < listcap1.length; i++) {

                                    String item0 = listcap1[i].toString();
                                    String[] olist = item0.split("\\|");
                                    ListQuyenSach iui = new ListQuyenSach();;
                                    iui.setId_QuyenSach(Integer.parseInt(olist[0])); // biến đổi về Int
                                    Bitmap mHinhAnh = null;
                                    try{
                                        if(olist[1] != null && olist[1] != ""){
                                            try {
                                                String url_anh = url_image_server + olist[1].toString();
                                                url_anh = url_anh.replace('\\', '/');
                                                InputStream in = new java.net.URL(url_anh).openStream();
                                                mHinhAnh = getResizedBitmap(BitmapFactory.decodeStream(in),140);
                                            } catch (Exception e) {
                                                mHinhAnh = null;
                                                Log.e("Error", e.getMessage());
                                                e.printStackTrace();
                                            }
                                        }
                                    }catch (Exception kod){
                                        mHinhAnh = null;
                                        iui.setHinhAnh(mHinhAnh);
                                    }
                                    iui.setHinhAnh(mHinhAnh);
                                    iui.setTieuDe(olist[2]);
                                    iui.setTacGia(olist[3]);
                                    try{
                                        if(olist[4] != null && olist[4].length() > 0){
                                            iui.setTheLoaiTruyen(olist[4]);
                                         }else
                                            iui.setTheLoaiTruyen("1");
                                    }catch (Exception ep){
                                        iui.setTheLoaiTruyen("1");
                                    }
                                    iui.setLuotXem(Integer.parseInt(olist[5]));
                                    iui.setId_loaisach_timkiem(Integer.parseInt(olist[6]));
                                    listdata.add(iui);

                            }
                        }catch (Exception ex){}
                    }

            }catch (Exception eo){
                Log.e("ERROR", eo.getMessage(), eo);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String args) {

                adapter = new ArrayAdapter<ListQuyenSach>(getApplicationContext(), android.R.layout.activity_list_item, listdata);
                listview = (ListView) findViewById(R.id.listview);
                listview.setAdapter(adapter);

                if (listdata.size() > 0){

                        if (listdata.size() > 0) {
                            listview.setAdapter(new CustomListQuyenSach(getApplicationContext(), listdata));
                            adapter.notifyDataSetChanged();
                        }
                        TastyToast.makeText(getApplicationContext(), "Dữ liệu đang cập nhật!", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                        try
                        {
                            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    if(id_theloaisch > 0){
                                        Intent intent = new Intent(getApplicationContext(), MainActivity_QuyenSach.class);
                                        intent.putExtra("id_quyensach", listdata.get(position).getId_QuyenSach());
                                        intent.putExtra("id_LoaiSach", id_theloaisch); // string
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        //showInterstitial();
                                    }else {
                                        Intent intent = new Intent(getApplicationContext(), MainActivity_QuyenSach.class);
                                        intent.putExtra("id_quyensach", listdata.get(position).getId_QuyenSach());
                                        intent.putExtra("id_LoaiSach", listdata.get(position).getId_loaisach_timkiem()); // string
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        //showInterstitial();
                                    }
                                    // showInterstitial();
                                }
                            });
                        }catch (Exception pd){}

                        so_page.setText("" + (currentPage));

                }else {
                    adapter.clear();
                    adapter.notifyDataSetChanged();
                    TastyToast.makeText(getApplicationContext(),"Tác giả hoặc quyển sách không tồn tài!", Toast.LENGTH_SHORT, TastyToast.ERROR).show();
                }

                if(listdata.size() >= 49){
                    re_next_prove.setVisibility(View.VISIBLE);
                }

                bg.dismiss();
                super.onPostExecute(args);
        }
    }

    // Quảng cáo
    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
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
        startActivity(new Intent(getApplicationContext(),MainActivity_Home.class));
        super.onBackPressed();
        finish();
    }
}
