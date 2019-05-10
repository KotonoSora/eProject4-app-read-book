package com.example.lamth.app_tong_the_gioi_sach;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

import static com.example.lamth.app_tong_the_gioi_sach.Url_config.banner_footer;


public class MainActivity_DocSach extends AppCompatActivity {

    private int id_chuong = 1;

    String id_quyensach = "";

    ImageView img_prev , img_next;

    private WebView myWebView;

    String load_url = "";

    private static String POPUP_CONSTANT = "mPopup";
    private static String POPUP_FORCE_SHOW_ICON = "setForceShowIcon";

    private View view;

    private RelativeLayout re_prev , re_next , re_ads;

    private String[] list_ ;

    String tenquyensach = "";
    String tieude_chuong = "";
    TextView tv_tieudesach , tv_tieudechuong;

    private AdView av;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__doc_sach);

        Intent iin = getIntent();
        Bundle extras = iin.getExtras();
        if (extras != null) {
            id_chuong = extras.getInt("id_chuong");
            id_quyensach = String.valueOf(extras.getInt("id_qs_theochuong"));
            tenquyensach = extras.getString("tenquyensach");
            tieude_chuong = extras.getString("tieude_chuong");
        }

//        TextView tv_TenQuyenSach = (TextView) findViewById(R.id.textView1) ;
//        tv_TenQuyenSach.setText(id_quyensach);

//        TextView tv_TieuDeSach = (TextView) findViewById(R.id.textView5);
//        tv_TieuDeSach.setText(id_quyensach);

        if(Url_config._isCheckConnection(getApplicationContext())){
            new LoadApplication().execute();
        }else{
            //Toast.makeText(getApplicationContext(), "Không có kết nối Internet", Toast.LENGTH_SHORT).show();
            TastyToast.makeText(getApplicationContext(), "Không có kết nối Internet!", TastyToast.LENGTH_LONG,
                    TastyToast.ERROR);
        }

        myWebView = (WebView) findViewById(R.id.webview);

        tv_tieudesach = (TextView) findViewById(R.id.textView1);

        tv_tieudechuong = (TextView) findViewById(R.id.textView17);

        re_prev = (RelativeLayout) findViewById(R.id.re_prev);
        re_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    id_chuong = id_chuong - 1 ;
                    new LoadApplication().execute();
                    tv_tieudechuong.setText(tieude_chuong);
                } catch (Exception ep) {}
            }
        });

        re_next = (RelativeLayout) findViewById(R.id.re_next);
        re_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    id_chuong = id_chuong + 1 ;
                    new LoadApplication().execute();
                    tv_tieudechuong.setText(tieude_chuong);
                }catch (Exception ep){}
            }
        });

        RelativeLayout i_back = (RelativeLayout) findViewById(R.id.re_back);
        i_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    onBackPressed();
                }catch (Exception ep){}
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

        @Override
        protected Void doInBackground(Void... params) {

            try{

                load_url = Url_config.url_detail_Chuong + id_chuong + "&id_qs=" + id_quyensach;

                String _get_listChuong = get_dulieu(Url_config.url_get_all_idChuong + id_quyensach);
                list_ = _get_listChuong.split("@");

            }catch (Exception ep){}


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            try{
                if (tenquyensach.length() >= 33){
                    String sub = tenquyensach.substring(0,33);
                    tv_tieudesach.setText(sub + " ...");
                }else {
                    tv_tieudesach.setText(tenquyensach);
                }

                myWebView.getSettings().setJavaScriptEnabled(true);
                myWebView.getSettings().setDomStorageEnabled(true) ;
                myWebView.getSettings().setAllowFileAccess(true);
                myWebView.loadUrl(load_url);

            }catch (Exception ep){}

            try{ // Kiểm tra điều kiện phân trang
                // Ở đây list_ dùng mảng string nên kiểm tra so sánh cũng phải dùng string ép kiểu nó về
                // kiểm tra so sánh phẩn tử đầu tiên .
                if(list_[0].equals(String.valueOf(id_chuong))){ // nếu id_chuong = với phần từ 1 của list thì gone re_prev
                    //Toasty.info(getApplicationContext(), "Đang ở trang đầu.", Toast.LENGTH_SHORT, true).show();
                    TastyToast.makeText(getApplicationContext(), "Đang ở trang đầu!", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    re_prev.setVisibility(View.GONE);
                } // kiểm tra phần tử cuối cùng
                else if (list_[list_.length - 1].equals(String.valueOf(id_chuong))) {
                    //Toasty.info(getApplicationContext(), "Đang ở trang cuối.", Toast.LENGTH_SHORT, true).show();
                    TastyToast.makeText(getApplicationContext(), "Đang ở trang cuối!", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    re_next.setVisibility(View.GONE);
                } // Và cuối cùng là không kiểm tra phần tử nào cả .
                else {
                    re_prev.setVisibility(View.VISIBLE);
                    re_next.setVisibility(View.VISIBLE);
                }

            }catch (Exception ep){}

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
        finish();
        super.onBackPressed();
    }

}
