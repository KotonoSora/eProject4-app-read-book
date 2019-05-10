package com.example.lamth.app_tong_the_gioi_sach;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lamth.app_tong_the_gioi_sach.helper.ListQuyenSachCoChuong;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static com.example.lamth.app_tong_the_gioi_sach.Url_config.banner_footer;
import static com.example.lamth.app_tong_the_gioi_sach.Url_config.banner_full;
import static com.example.lamth.app_tong_the_gioi_sach.Url_config.url_get_get_InfoQuyenSach;
import static com.example.lamth.app_tong_the_gioi_sach.Url_config.url_image_server;

public class MainActivity_QuyenSach extends AppCompatActivity {

    ImageView tv_HinhAnh , im_plus , img_minu ;

    private  TextView tv_TieudeQuyenSach , tv_tenquyensach , tv_tacgia , tv_loaitruyen , tv_mota  , tv_page , tv_luotxem ,tv_chuong;

    String Id_quyensach = "";
    String Anh_quyensach = "";
    String TenQuyenSach = "";
    String TacGia = "";
    String LoaiTruyen = "";
    String MoTa = "";
    String LuotXem = "";
    String  Id_LoaiTrang = "";
    String url_anh = "";
    String TongChuong = "";

    private int Page = 1;

    private int currentPage = 1;

    String Id_TheLoaiSach = "";
    String Ten_TheLoaiSach ="";

    ArrayList<ListQuyenSachCoChuong> listdata;

    private RelativeLayout re_truyenchu , re_back , re_truyenfile ;

    private AdView av;
    InterstitialAd mInterstitialAd;
    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__quyen_sach);

        try{
            Intent iin = getIntent();
            Bundle b = iin.getExtras();

            if (b != null) {
                Id_LoaiTrang = String.valueOf(b.getInt("id_type"));
                if(Id_LoaiTrang.contains("1")){
                    Id_quyensach = String.valueOf(b.getInt("id_quyensach"));
                    Id_TheLoaiSach = String.valueOf(b.getInt("id_LoaiSach"));
                }else if(Id_LoaiTrang.contains("2")){
                    Id_quyensach = String.valueOf(b.getInt("id_quyensach"));
                    Id_TheLoaiSach = String.valueOf(b.getString("id_LoaiSach"));
                    Page = Integer.parseInt(String.valueOf(b.getInt("page")));
                    Ten_TheLoaiSach = String.valueOf(b.getString("id_TenLoaiSach"));
                }else {
                    Id_quyensach = String.valueOf(b.getInt("id_quyensach"));
                    Id_TheLoaiSach = String.valueOf(b.getInt("id_LoaiSach"));
                }
            }

            new LoadApplication().execute();

            _define();

            //re_NativeAd();
            // onBackPressed();

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

        }catch (Exception kl){}

    }

    protected void _define() {

        try{

            tv_tenquyensach = (TextView) findViewById(R.id.textView5);
            tv_tacgia = (TextView) findViewById(R.id.textView6);
            tv_loaitruyen = (TextView) findViewById(R.id.textView7);
            tv_mota = (TextView) findViewById(R.id.textView10);

            tv_HinhAnh = (ImageView) findViewById(R.id.imageView4);

            listdata = new ArrayList<ListQuyenSachCoChuong>();

            re_truyenfile = (RelativeLayout) findViewById(R.id.re_truyenfile);

            re_truyenchu = (RelativeLayout) findViewById(R.id.re_truyenchu);

            //re_next_and_previous = (RelativeLayout) findViewById(R.id.re_next_and_previous);

            tv_page = (TextView) findViewById(R.id.textView3);

            tv_chuong = (TextView) findViewById(R.id.textView34);

            re_back = (RelativeLayout) findViewById(R.id.re_back);
            re_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        onBackPressed();
                }
            });

            tv_TieudeQuyenSach = (TextView) findViewById(R.id.textView1);

            tv_luotxem = (TextView) findViewById(R.id.textView13);




        }catch (Exception ep){}
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

    // Xử lý dữ liệu
    private class LoadApplication extends AsyncTask<Void, Void, Void> {
        String result;
        String get_IdQuyensach = "";
        String[] item_quyensach;

        @Override
        protected Void doInBackground(Void... params) {

              try{
                  //Id_QuyenSach
                  get_IdQuyensach = get_dulieu(url_get_get_InfoQuyenSach + Id_quyensach + "&Id_TheLoaiSach=" + Id_TheLoaiSach);
                  //id @ hinhanh @Tenquyensach @ TacGia @ Id_Loaitruyen @ MoTa .
                  item_quyensach = get_IdQuyensach.split("@");
                  Id_quyensach = item_quyensach[0];
                  //Anh_quyensach = url_image_server + item_quyensach[1];

                  if (item_quyensach.length > 1){
                      Anh_quyensach = url_image_server + item_quyensach[1];
                      //url_anh = Anh_quyensach.replace('\\', '/');
                  }

                  TenQuyenSach = item_quyensach[2];
                  TacGia = item_quyensach[3];
                  LoaiTruyen = item_quyensach[4];
                  MoTa = item_quyensach[5];
                  LuotXem = item_quyensach[6];
                  TongChuong = item_quyensach[8];


              }catch (Exception ep){

              }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            try{

                if (TenQuyenSach.length() >= 33){
                    String sub = TenQuyenSach.substring(0,33);
                    tv_TieudeQuyenSach.setText(sub + " ...");
                }else {
                    tv_TieudeQuyenSach.setText(TenQuyenSach);
                }
                tv_tenquyensach.setText(TenQuyenSach);
                tv_tacgia.setText(TacGia);
                tv_luotxem.setText(LuotXem + " lượt view ");
                tv_chuong.setText("(Số chương : " + TongChuong + ")");
                if (LoaiTruyen.equals("1")){
                    tv_loaitruyen.setText("Truyện tranh");
                    try{
                        tv_chuong.setVisibility(View.VISIBLE);
                        re_truyenchu.setVisibility(View.VISIBLE);
                        re_truyenfile.setVisibility(View.GONE);
                        re_truyenchu.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getApplicationContext(), MainActivity_ViewListChuong.class);
                                intent.putExtra("id_listqs" , Id_quyensach);
                                intent.putExtra("tenquyensach",TenQuyenSach);
                                //intent.putExtra("tongchuong",TongChuong);
                                startActivity(intent);
                                showInterstitial();
                            }
                        });

                    }catch (Exception ep){}
                }else {
                    tv_loaitruyen.setText("Truyện file PDF");
                    try{
//                        tv_chuong.setVisibility(View.INVISIBLE);
//                        re_truyenfile.setVisibility(View.VISIBLE);
//                        re_truyenchu.setVisibility(View.GONE);
//                        re_truyenfile.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                Intent intent = new Intent(getApplicationContext(), MainActivity_DocPdf_and_Epub.class);
//                                intent.putExtra("id_chuongpdf",Id_quyensach);
//                                intent.putExtra("tenquyensach",TenQuyenSach);
//                                startActivity(intent);
//                                showInterstitial();
//                            }
//                        });

                    }catch (Exception ex){}
                }
                // tv_loaitruyen.setText(LoaiTruyen);
                tv_mota.setText(Html.fromHtml(MoTa));
                // tv_mota.setMaxLines(8);

                //listview
//            ArrayAdapter<ListQuyenSachCoChuong> adapter = new ArrayAdapter<ListQuyenSachCoChuong>(getApplicationContext(), android.R.layout.activity_list_item, listdata);
////            ListView listView = (ListView) findViewById(R.id.view_listchuong); //view_listchuong
////            listView.setAdapter(adapter);
////
////            if (listdata.size() > 0) {
////
////                if (listdata.size() > 0) {
////                    listView.setAdapter(new CustomListQuyenSachCoChuong(getApplicationContext(), listdata));
////                }
////
////                adapter.notifyDataSetChanged();
////                try{
////                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
////                        @Override
////                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////                            //int lay_id =  listdata.get(position).getId_QuyenSach();
////                            Intent intent = new Intent(getApplicationContext(), MainActivity_DocSach.class);
////                            intent.putExtra("id_chuong", listdata.get(position).getId_chuongtheo_quyensach());
////                            intent.putExtra("id_qs_theochuong", listdata.get(position).getId_QuyenSach());
////                            startActivity(intent);
////                        }
////                    });
////                }catch (Exception ex){ }
////
////                tv_page.setText("Trang " + (currentPage));
////
////
////
////            }else {
////
////            }
                //load anh

                new DownloadImageTask(tv_HinhAnh).execute(Anh_quyensach);

                // kiểm tra trang đầu và trang cuối .
//            if(listdata.size()<20 && currentPage > 1){
//                //Toast.makeText(MainActivity.this, "Đang ở trang cuối", Toast.LENGTH_SHORT).show();
//                Toasty.info(MainActivity_QuyenSach.this, "Đang ở trang cuối.", Toast.LENGTH_SHORT, true).show();
//            }
//
//            if(currentPage==1){
//                // Toast.makeText(MainActivity.this, "Đang ở trang đầu", Toast.LENGTH_SHORT).show();
//                Toasty.info(MainActivity_QuyenSach.this, "Đang ở trang đầu.", Toast.LENGTH_SHORT, true).show();
//            }

            }catch (Exception ep){

            }

            super.onPostExecute(aVoid);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }


        private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

        public DownloadImageTask(ImageView img) {
            return;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                urldisplay = urldisplay.replace('\\', '/');
                InputStream in = new java.net.URL(urldisplay).openStream();
                //InputStream in = new URL(urldisplay).openStream();
                mIcon11 = Url_config.getResizedBitmap(BitmapFactory.decodeStream(in),280);
            } catch (Exception e) {
                mIcon11 = null;
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            tv_HinhAnh.setImageBitmap(result);
        }

    }

    private void showInterstitial() {
        if (mInterstitialAd.isLoaded() && mInterstitialAd != null) {
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
        if (Id_LoaiTrang.equals("1")){
            Intent intent = new Intent(getApplicationContext(), MainActivity_Home.class);
            intent.putExtra("id_LoaiSach", Id_TheLoaiSach);
            intent.putExtra("Id_LoaiTrang", Id_LoaiTrang);
            intent.putExtra("id_type",Id_LoaiTrang);
            startActivity(intent);
        }else if(Id_LoaiTrang.equals("2")){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("id_LoaiSach", Id_TheLoaiSach);
            intent.putExtra("id_type",2);
            intent.putExtra("page", Page);// int
            intent.putExtra("id_TenLoaiSach", Ten_TheLoaiSach);// int
            startActivity(intent);
        }else {
            Intent intent = new Intent(getApplicationContext(), MainActivity_TimKiem_QuyenSach.class);
            intent.putExtra("id_LoaiSach", Id_TheLoaiSach);
            intent.putExtra("id_quyensach",Id_quyensach);
            startActivity(intent);
        }
        super.onBackPressed();
        finish();
    }
}
