package com.example.lamth.app_tong_the_gioi_sach;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lamth.app_tong_the_gioi_sach.helper.CustomListQuyenSach;
import com.example.lamth.app_tong_the_gioi_sach.helper.ListQuyenSach;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.sdsmdg.tastytoast.TastyToast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import dmax.dialog.SpotsDialog;

import static com.example.lamth.app_tong_the_gioi_sach.Url_config.banner_footer;
import static com.example.lamth.app_tong_the_gioi_sach.Url_config.banner_full;
import static com.example.lamth.app_tong_the_gioi_sach.Url_config.url_get_ListAllQuyenSach;

public class MainActivity extends AppCompatActivity {

    EditText editText_mp;

    private TextView so_page , timkiem , tv_TenLoaiSach;

    private String _gettimkiem ;

    ArrayList<ListQuyenSach> listdata;

    private ListView listView;

    ArrayAdapter<ListQuyenSach> adapter;
    final Context context = this;

    private int is_show_search = 0;
    private ImageView re_next, re_previous , img_search;

    private int currentPage = 1;

    RelativeLayout re_search , re_back , re_ads;

    int _istart = 1;

    RelativeLayout re_next_and_previous;

    String Id_TheLoaiSach = "";
    String TenLoaiSach = "";
    String Id_Trang = "";
    private android.app.AlertDialog pdLoading;

    private AdView av;
    InterstitialAd mInterstitialAd;
    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText_mp = (EditText) findViewById(R.id.editText);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // ra 8 cap ga ca

        Intent iin = getIntent();
        Bundle b = iin.getExtras();

        if (b != null) {
            // Ép kiểu currentPage = [ Bundle.int("page_back") <=> (tương đương) Page ]
            // Ở đây không cần dùng String -> dùng chính int của nó
            Id_Trang =  String.valueOf(b.getInt("id_type"));

            if(Id_Trang.equals("1")) { // trang Home
                Id_TheLoaiSach = String.valueOf(b.getInt("id_LoaiSach"));
                TenLoaiSach = String.valueOf(b.getString("id_TenLoaiSach"));
            }else if(Id_Trang.equals("2")) { // Trang Activitymain
                currentPage = b.getInt("page");
                Id_TheLoaiSach = String.valueOf(b.getString("id_LoaiSach"));
                TenLoaiSach = String.valueOf(b.getString("id_TenLoaiSach"));
            }else {}

        }

        if (!Url_config._isCheckConnection(getApplicationContext())) {
            Toast.makeText(getApplicationContext(), "Vui lòng kết nối internet và Loading lại!", Toast.LENGTH_SHORT).show();
        } else {

            try {
                new _LoadDulieu().execute();
            }catch (Exception ep){}

        }

        listdata = new ArrayList<ListQuyenSach>();

        re_next_and_previous = (RelativeLayout) findViewById(R.id.re_next_and_previous);
        so_page = (TextView) findViewById(R.id.textView3);
        re_search = (RelativeLayout) findViewById(R.id.re_search);
        re_previous = (ImageView) findViewById(R.id.imageView6);
        re_next = (ImageView) findViewById(R.id.imageView7);
        img_search = (ImageView) findViewById(R.id.img_search);

        timkiem = (TextView) findViewById(R.id.btn_timkiem);

        re_back = (RelativeLayout) findViewById(R.id.re_back);

        tv_TenLoaiSach = (TextView) findViewById(R.id.tv_TenLoaiSach);

        tv_TenLoaiSach.setText(TenLoaiSach);

        timkiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _istart = 0;
                if (_istart == 0) {
                    try{
                        new _LoadDulieu().execute();
                        re_next_and_previous.setVisibility(View.GONE);
                    }catch (Exception ep){}

                }
            }
        });

        re_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        re_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPage = currentPage + 1;

                new _LoadDulieu().execute();
                if(listdata.size()<20 && currentPage>1){
                    //Toasty.info(MainActivity.this, "Đang ở trang cuối.", Toast.LENGTH_SHORT, true).show();
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
                    //Toasty.info(MainActivity.this, "Đang ở trang đầu.", Toast.LENGTH_SHORT, true).show();
                    TastyToast.makeText(getApplicationContext(), "Đang ở trang đầu!", TastyToast.LENGTH_LONG,
                            TastyToast.WARNING);
                }
            }
        });

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

    }

    // lấy dữ liệu từ webiste sever hay gọi là website api về
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
            String sthhg = e.toString();
        } finally {
            urlConnection.disconnect();
        }
        return result;
    }


    // Load danh sach quyen sách theo ID_TheLoaiSach
    private class _LoadDulieu extends AsyncTask<Void, Void, Void> {
        String result = "";

       @Override
        protected Void doInBackground(Void... params) {

            try{
                if (_istart == 1) { // View toàn bộ dữ liệu dạng phân trang (Code từ Procude SQL Sever từ Api chuyển qua Android )

                    if(listdata.size()>0){
                        listdata.clear();
                    } // xóa dữ liệu

                    String _getIdQuyenSach = get_dulieu(url_get_ListAllQuyenSach + Id_TheLoaiSach + "&page=" + currentPage); //currentPage

                    // Kiểm tra _getIdQuyenSach có ký tự == 0 và phân trang có bé hơn 2 ko - Nếu thỏa mản Đk :
                    if(_getIdQuyenSach.trim().length() == 0 && currentPage>2){
                        currentPage = currentPage - 1; // Phân trang tương lai = phân trang hiện tại - 1.
                    }
                    // Kiểm tra _getIdQuyenSach có ký tự == 0 và phân trang == 0 - Nếu thỏa mản Đk :
                    if(_getIdQuyenSach.trim().length() == 0 && currentPage==0){
                        currentPage = 1; // Thì ép phân trang lại thành phân trang 1 .
                    }
                    // 2 ĐK trên cái nào thỏa mản thì chạy xuống hàm này .
                    _getIdQuyenSach = get_dulieu(url_get_ListAllQuyenSach + Id_TheLoaiSach + "&page=" + currentPage);

                    try{
                        String[] list_item = _getIdQuyenSach.split("@"); // Tạo biến Mảng List String để loại bỏ @ dùng .split
                        for (int j = 0; j < list_item.length; j++) {
                            String it1 = list_item[j];
                            String[] jh = it1.split("\\|");

                            ListQuyenSach iui = new ListQuyenSach();
                            iui.setId_QuyenSach(Integer.parseInt(jh[0])); // biến đổi về Int
                            Bitmap mHinhAnh = null; // Nếu hình ảnh thì dùng Bitmap
                            try{
                                if(jh[1] != null && jh[1]!= ""){
                                    try {
                                        String url_anh = "http://adminsachvui.giaynct.com"+ jh[1].toString();
                                        url_anh = url_anh.replace('\\', '/');
                                        InputStream in = new java.net.URL(url_anh).openStream();
                                        //mIcon11 = BitmapFactory.decodeStream(in);
                                        mHinhAnh = Url_config.getResizedBitmap(BitmapFactory.decodeStream(in),130);
                                    } catch (Exception e) {
                                        mHinhAnh = null;
                                        Log.e("Error", e.getMessage());
                                        e.printStackTrace();
                                    }
                                }
                            }catch (Exception ko){
                                mHinhAnh = null;
                                iui.setHinhAnh(mHinhAnh);
                            }
                            iui.setHinhAnh(mHinhAnh); // xong setHinhAnh truyền biến (Bitmap vào)
                            iui.setTieuDe(jh[2]);
                            iui.setTacGia(jh[3]);
                            //iui.setTheLoaiTruyen(jh[4]);
                            try{
                                if(jh[4] != null && jh[4].length() > 0){
                                    iui.setTheLoaiTruyen(jh[4]);
                                }else
                                    iui.setTheLoaiTruyen("1");
                            }catch (Exception ep){
                                iui.setTheLoaiTruyen("1");
                            }
                            iui.setNgayUp(jh[5]);
                            iui.setLuotXem(Integer.parseInt(jh[6]));
                            listdata.add(iui);
                        }
                    }catch (Exception ep){}
                }

            }catch (Exception ex){
                Log.e("ERROR", ex.getMessage(), ex);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            pdLoading.dismiss();
            adapter = new ArrayAdapter<ListQuyenSach>(getApplicationContext(), android.R.layout.activity_list_item, listdata);
            listView = (ListView) findViewById(R.id.listview);
            listView.setAdapter(adapter);
            if (listdata.size() > 0) {
                //listview
                if (listdata.size() > 0) {
                    listView.setAdapter(new CustomListQuyenSach(getApplicationContext(), listdata));
                }
                adapter.notifyDataSetChanged();
                try{
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity_QuyenSach.class);
                            intent.putExtra("id_quyensach", listdata.get(position).getId_QuyenSach());
                            intent.putExtra("id_LoaiSach", Id_TheLoaiSach); // string
                            intent.putExtra("id_type",2);
                            intent.putExtra("page", currentPage);// int
                            intent.putExtra("id_TenLoaiSach", TenLoaiSach);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

                            showInterstitial();
                        }
                    });
                }catch (Exception ex){ }

                so_page.setText("" + (currentPage));

            }
//            else{
//                adapter.clear();
//                adapter.notifyDataSetChanged();
//                FancyToast.makeText(getApplicationContext(),"Tác giả hoặc quyển sách không tồn tài!",FancyToast.LENGTH_SHORT,FancyToast.ERROR,true).show();
//            }
            super.onPostExecute(aVoid);
        }

        @Override
        protected void onPreExecute() {
            pdLoading = new SpotsDialog.Builder().setContext(context).build();
            pdLoading.setMessage("Đang loading ...");
            pdLoading.setCancelable(true);
            pdLoading.show();
            super.onPreExecute();
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

    //nút quay lại trên điện thoại.
    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainActivity_Home.class));
        super.onBackPressed();
        finish();
    }
}
