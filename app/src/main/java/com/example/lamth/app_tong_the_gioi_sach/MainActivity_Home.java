package com.example.lamth.app_tong_the_gioi_sach;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.lamth.app_tong_the_gioi_sach.common_slide.ViewPagerAdapter;
import com.example.lamth.app_tong_the_gioi_sach.helper.Custom_List_View_QuyenSach_TheoTheLoaiSach;
import com.example.lamth.app_tong_the_gioi_sach.helper.List_View_QuyenSach_TheoTheLoaiSach;
import com.example.lamth.app_tong_the_gioi_sach.helper.T_Item_quyensach;
import com.example.lamth.app_tong_the_gioi_sach.helper.cl_cm;
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
import java.util.Timer;
import java.util.TimerTask;


import dmax.dialog.SpotsDialog;

import static com.example.lamth.app_tong_the_gioi_sach.Url_config.Url_more;
import static com.example.lamth.app_tong_the_gioi_sach.Url_config.Url_rate;
import static com.example.lamth.app_tong_the_gioi_sach.Url_config.banner_footer;
import static com.example.lamth.app_tong_the_gioi_sach.Url_config.banner_full;
import static com.example.lamth.app_tong_the_gioi_sach.Url_config.showpop_up_tinh_getlistview;
import static com.example.lamth.app_tong_the_gioi_sach.Url_config.url_get_3QuyenSach_TheoTheLoaiSach;

public class MainActivity_Home extends AppCompatActivity {

    ArrayList<List_View_QuyenSach_TheoTheLoaiSach> listdata;

    //ArrayList<T_Item_quyensach> listt_all_item ;

    private GridView listView ;

    ArrayAdapter<List_View_QuyenSach_TheoTheLoaiSach> adapter;

    private int currentPage = 1;

    private android.app.AlertDialog pdLoading;

    final Context context = this;

    private RelativeLayout re_Sreach;

    // Silder
    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    RelativeLayout  re_menu;

    // Quảng cáo - Ads admob
    private AdView av;
    InterstitialAd mInterstitialAd;
    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest);
    }

    // Cấp quyền Permession
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity_Home.this, "Permision Write File is Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity_Home.this, "Permision Write File is Denied", Toast.LENGTH_SHORT).show();

            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void initPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                //Permisson don't granted
                if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE))
                {
                    Toast.makeText(MainActivity_Home.this, "Permission isn't granted ", Toast.LENGTH_SHORT).show();
                }
                // Permisson don't granted and dont show dialog again.
                else {
                    Toast.makeText(MainActivity_Home.this, "Permisson don't granted and dont show dialog again ", Toast.LENGTH_SHORT).show();
                }
                //Register permission
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

            }
        }
    }

    // Silder ảnh dùng mảng chứ ko kéo từ Api về.
    public void View_Pager_Slide(){

        viewPager = (ViewPager) findViewById(R.id.viewPager);

        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getApplicationContext());

        viewPager.setAdapter(viewPagerAdapter);

        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];

        for(int i = 0; i < dotscount; i++){

            dots[i] = new ImageView(getApplicationContext());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 2, 8, 6); // cách lề của cục slide

            sliderDotspanel.addView(dots[i], params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for(int i = 0; i< dotscount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(), 2000, 4000);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__home);
        initPermission();

        try{
            View_Pager_Slide();

            if (!Url_config._isCheckConnection(getApplicationContext())) {
                TastyToast.makeText(getApplicationContext(), "Vui lòng kết nối internet và Loading lại!", TastyToast.LENGTH_LONG,TastyToast.ERROR);
            } else {
                try {
                    new _LoadDulieu().execute();
                }catch (Exception ep){}
            }

            listdata = new ArrayList<List_View_QuyenSach_TheoTheLoaiSach>(); // Hàm này <=> Hàm anhxa
            // Qua trang Tìm kiếm
            re_Sreach = (RelativeLayout) findViewById(R.id.re_Sreach);
            re_Sreach.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(),MainActivity_TimKiem_QuyenSach.class);
                    intent.putExtra("id_type",1);
                    startActivity(intent);
                    showInterstitial();
                }
            });


            re_menu = (RelativeLayout) findViewById(R.id.re_menu);

            re_menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try{
                        final AlertDialog.Builder popDialog = new AlertDialog.Builder(MainActivity_Home.this);

                        final LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

                        final View Viewlayout = inflater.inflate(R.layout.bg_load_menu, null);

                        popDialog.setView(Viewlayout);

                        popDialog.create();

                        final AlertDialog dg = popDialog.show();

                        RelativeLayout re_home_now = (RelativeLayout) Viewlayout.findViewById(R.id.re_home_now);
                        RelativeLayout re_rate = (RelativeLayout) Viewlayout.findViewById(R.id.re_rate);
                        RelativeLayout re_more = (RelativeLayout) Viewlayout.findViewById(R.id.re_more);
                        RelativeLayout re_share = (RelativeLayout) Viewlayout.findViewById(R.id.re_share);
                        RelativeLayout re_Cancel = (RelativeLayout) Viewlayout.findViewById(R.id.re_Cancel);
                        RelativeLayout re_view_theloaisach = (RelativeLayout) Viewlayout.findViewById(R.id.re_view_theloaisach);
                        RelativeLayout re_timkiem = (RelativeLayout) Viewlayout.findViewById(R.id.re_timkiem);


                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                            dg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                            //dg.getWindow().setLayout(750,2000);
                            dg.getWindow().setGravity(Gravity.TOP |Gravity.LEFT | Gravity.BOTTOM);
                        }else {
                            dg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                            //dg.getWindow().setLayout(500,2000);
                            dg.getWindow().setGravity(Gravity.TOP |Gravity.LEFT | Gravity.BOTTOM);
                            re_Cancel.setVisibility(View.VISIBLE);
                        }



                        re_home_now.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dg.dismiss();
                                Intent intent = new Intent(getApplicationContext(), MainActivity_Home.class);
                                startActivity(intent);
                            }
                        });

                        // Qua Trang thể loại sách
                        re_view_theloaisach.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // Cho view tới thư viện
                                try{
                                    final cl_cm item = showpop_up_tinh_getlistview(MainActivity_Home.this);
                                    item.getListview().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            //String tv_theloaisach = setText(item.getList().get(position).getTenTheLoaiSach());
                                            //id_theloaisch = item.getList().get(position).getId();
                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            intent.putExtra("id_TenLoaiSach", item.getList().get(position).getTenTheLoaiSach());
                                            intent.putExtra("id_LoaiSach", item.getList().get(position).getId()); // string
                                            intent.putExtra("id_type",1); // string
                                            startActivity(intent);
                                            showInterstitial();

                                            item.getDg().dismiss();

                                        }
                                    });
                                }catch (Exception pp){

                                }
                            }
                        });

                        re_timkiem.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getApplicationContext(),MainActivity_TimKiem_QuyenSach.class);
                                intent.putExtra("id_type",1);
                                startActivity(intent);
                                showInterstitial();
                            }
                        });


                        re_rate.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Url_rate)));
                            }
                        });

                        re_more.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Url_more)));
                            }
                        });

                        re_share.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                final String appPackageName = context.getPackageName();
                                Intent sendIntent = new Intent();
                                sendIntent.setAction(Intent.ACTION_SEND);
                                //sendIntent.putExtra(Intent.EXTRA_TEXT, "Check out the App at: https://play.google.com/store/apps/details?id=" + appPackageName);
                                sendIntent.putExtra(Intent.EXTRA_TEXT, Url_rate); // cần thay đường link
                                sendIntent.setType("text/plain");
                                context.startActivity(sendIntent);
                            }
                        });

                        re_Cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dg.dismiss();
                            }
                        });

                    }catch (Exception ep){

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

        }catch (Exception po){}

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

    private class _LoadDulieu extends AsyncTask<Void, Void, Void> {
        String result = "";

        @Override
        protected void onPreExecute() { // Hàm này luôn luôn chạy đầu tiên quá trình load bắt đâu "Start"
            pdLoading = new SpotsDialog.Builder().setContext(context).build();
            pdLoading.setMessage("Đang tải dữ liệu xin vui lòng chờ trong giây lát ...!");
            pdLoading.setCancelable(true);
            pdLoading.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            // Tiếp theo đến hàm này thực hiện các tác vụ chạy ngầm
            // Tuyệt đối k vẽ giao diện trong hàm này
            try{
                if(listdata !=null && listdata.size()>0){
                    listdata.clear();
                } // xóa dữ liệu

                String _getIdQuyenSach = get_dulieu(url_get_3QuyenSach_TheoTheLoaiSach);

                try{
                    String[] list_item = _getIdQuyenSach.split("@"); // Tạo biến Mảng List String để loại bỏ @ dùng .split

                    ArrayList<T_Item_quyensach> listt_all_item = new ArrayList<>();

                    for (int j = 0; j < list_item.length; j++) {

                        if(j%4==0){
                            listt_all_item = new ArrayList<>();
                        }

                        String it1 = list_item[j];
                        String[] jh = it1.split("\\|");

                        T_Item_quyensach iui = new T_Item_quyensach();
                        iui.setId_QuyenSach(Integer.parseInt(jh[0])); // biến đổi về Int
                        Bitmap mHinhAnh = null; // Nếu hình ảnh thì dùng Bitmap
                        if(jh[1] != null && jh[1]!= ""){
                            try {
                                String url_anh = "http://adminsachvui.giaynct.com"+ jh[1].toString();
                                url_anh = url_anh.replace('\\', '/');
                                InputStream in = new java.net.URL(url_anh).openStream();
                                //mIcon11 = BitmapFactory.decodeStream(in);
                                mHinhAnh = Url_config.getResizedBitmap(BitmapFactory.decodeStream(in), 150 );
                            } catch (Exception e) {
                                Log.e("Error", e.getMessage());
                                e.printStackTrace();
                            }
                        }
                        iui.setHinhAnh(mHinhAnh); // xong setHinhAnh truyền biến (Bitmap vào)
                        iui.setTenQuyenSach(jh[2]);
                        iui.setID_LoaiSach(Integer.parseInt(jh[3])); // biến đổi về Int
                        iui.setTenTheLoaiSach(jh[5]);
                        listt_all_item.add(iui);
                        //listt_all_item.clear(); // xóa dữ liệu dư thừa đi

                        if(j%4==3){
                            //List_View_QuyenSach_TheoTheLoaiSach item = new List_View_QuyenSach_TheoTheLoaiSach();
                            //item.setListItem(listt_all_item);
                            listdata.add(new List_View_QuyenSach_TheoTheLoaiSach(listt_all_item));// d lieu tren nay co
                        }
                    }
                }catch (Exception ep){
                    Log.e("ERROR", ep.getMessage(), ep);
                }

            }catch (Exception ex){
                Log.e("ERROR", ex.getMessage(), ex);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //Cuối cùng là hàm này - thực hiện khi tiến trình kết thúc
            pdLoading.dismiss();
            try{
                //adapter = new ArrayAdapter<List_View_QuyenSach_TheoTheLoaiSach>(getApplicationContext(), android.R.layout.activity_list_item, listdata);
                listView = (GridView) findViewById(R.id.listview_home);
                if (listdata.size() > 0) {  //xuong day mat thang adapter anh ko phai
                    //listview
                    if (listdata.size() > 0) {
                        listView.setAdapter(new Custom_List_View_QuyenSach_TheoTheLoaiSach(getApplicationContext(), listdata));
                        //showInterstitial();
                    }
                }
                super.onPostExecute(aVoid);
            }catch (Exception po){
                Log.e("ERROR", po.getMessage(), po);
            }
        }

    }

    public class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            MainActivity_Home.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                        if(viewPager.getCurrentItem() == 0){
                            viewPager.setCurrentItem(1);
                        } else if(viewPager.getCurrentItem() == 1){
                            viewPager.setCurrentItem(2);
                        } else if(viewPager.getCurrentItem() == 2){
                            viewPager.setCurrentItem(3);
                        }else if(viewPager.getCurrentItem() == 3){
                            viewPager.setCurrentItem(4);
                        }else {
                            viewPager.setCurrentItem(0);
                        }
                }
            });
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
        startActivity(new Intent(getApplicationContext(), MainActivityRateMoreShare.class));
        super.onBackPressed();
        showInterstitial();
    }

}
