package com.example.lamth.app_tong_the_gioi_sach;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.lamth.app_tong_the_gioi_sach.helper.Adapter_List_NameTheLoaiSach;
import com.example.lamth.app_tong_the_gioi_sach.helper.cl_cm;
import com.example.lamth.app_tong_the_gioi_sach.helper.item_Name_TheLoaiSach;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class Url_config {

    public static String url_get_ListAllQuyenSach = "http://truyentranh.math1to12.com/api/get_ListAllQuyenSach.ashx?id_allquyensach="; // page

    public static String url_get_ListAllChuong = "http://truyentranh.math1to12.com/api/get_ListAllChuong.ashx?id_chuong="; // page

    public static String url_get_get_InfoQuyenSach = "http://truyentranh.math1to12.com/api/get_InfoQuyenSach.ashx?Id_quyensach="; //

    public static String url_detail_Chuong = "http://truyentranh.math1to12.com/api/View_ChuongQuyenSach.aspx?id_chuong="; // id_qs

    public static String url_get_all_idChuong = "http://truyentranh.math1to12.com/api/get_ListIdChuong.ashx?id_c_quyensach="; // id_quyensach

    public static  String url_image_server = "http://truyentranh.math1to12.com" ;

    public static String url_get_3QuyenSach_TheoTheLoaiSach = "http://truyentranh.math1to12.com/api/get_3QuyenSach_TheoTheLoaiSach.ashx";

    public static String url_get_TimKiemTong_QuyenSach = "http://truyentranh.math1to12.com/api/get_TimKiemTong_QuyenSach.ashx?timkiem="; // + &id_loaisach= &page=

    public static String url_get_Name_TheLoaiSach = "http://truyentranh.math1to12.com/api/get_Name_TheLoaiSach.ashx";

    public static String Url_rate = "https://play.google.com/store/apps/details?id=com.free.booksonline.thegioisach";
            //"https://play.google.com/store/apps/details?id=com.sachhay.tamly.kynangsong";

    public static  String Url_more = "https://play.google.com/store/apps/developer?id=Math+Academy+Ltd";

    public static final String downloadDirectory = "Download/View.File.PDF/com.free.booksonline.thegioisach" ;

    public final static String banner_footer = "ca-app-pub-2432109083481493/40229413861";
    public final static String banner_full = "ca-app-pub-2432109083481493/64777143361";

    // show popup load
    public static AlertDialog showpop_up_Load(Context content) {
        final AlertDialog.Builder popDialog = new AlertDialog.Builder(content);

        final LayoutInflater inflater = (LayoutInflater) content.getSystemService(LAYOUT_INFLATER_SERVICE);

        final View Viewlayout = inflater.inflate(R.layout.bg_load, null);

        popDialog.setView(Viewlayout);

        popDialog.create();
        final AlertDialog dg = popDialog.show();
        dg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        //
        return  dg;
    }


    //show popup Tên loại sách
    public static cl_cm  showpop_up_tinh_getlistview(Context context) {
        cl_cm item = new cl_cm();
        final AlertDialog.Builder popDialog = new AlertDialog.Builder(context);

        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);

        final View Viewlayout = inflater.inflate(R.layout.list_ten_theloaisach, null);

        popDialog.setView(Viewlayout);

        popDialog.create();
        final AlertDialog dg = popDialog.show();
        //
        final ArrayList<item_Name_TheLoaiSach> list = get_ListNameTheLoaiSach();

        ListView ls_theloaisach = (ListView) Viewlayout.findViewById(R.id.ls_theloaisach);
        ArrayAdapter<item_Name_TheLoaiSach> adapter = new ArrayAdapter<item_Name_TheLoaiSach>(context, android.R.layout.activity_list_item, list);
        ls_theloaisach.setAdapter(adapter);
        if (list.size() > 0) {
            ls_theloaisach.setAdapter(new Adapter_List_NameTheLoaiSach(context, list));
        }
        item.setDg(dg);
        item.setListview(ls_theloaisach);
        item.setList(list);

        return  item;
    }

    //get list tên loại sách
    public  static ArrayList<item_Name_TheLoaiSach> get_ListNameTheLoaiSach(){
        ArrayList<item_Name_TheLoaiSach> list = new ArrayList<>();
        list.add(new item_Name_TheLoaiSach(0,"-- Thể loại sách --"));

        String chuoi = null;
        try {
            chuoi = new _getchuoistring().execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] list_cap1 = chuoi.split("@");

        for (int i = 0; i<list_cap1.length;i++){
            String st1 = list_cap1[i];
            String[] st2 = st1.split("\\|");
            int idd = Integer.parseInt(st2[0]);

            item_Name_TheLoaiSach  item = new item_Name_TheLoaiSach();
            item.setId(idd);;
            item.setTenTheLoaiSach(st2[1]);
            list.add(item);
        }

        return  list;
    }

    //lay du lieu tu api
    public static String get_dulieu(String url_param) {
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

    private static class _getchuoistring extends AsyncTask<Object, Object, String> {
        String str = "";
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(Object... params) {
            str = get_dulieu(url_get_Name_TheLoaiSach);
            return str;
        }
        @Override
        protected void onPostExecute(String args) {
            super.onPostExecute(args);
        }
    }


    // Check xem có mạng mới dọc đc webview
    public static boolean _isCheckConnection(Context context){
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    // khợi tạo 1 hàm giảm kích thước ảnh getResizedBitmap
    public static Bitmap getResizedBitmap(Bitmap bm, int newWidth) {
            int width = bm.getWidth();
            int height = bm.getHeight();
            float scaleWidth = ((float) newWidth) / width;
            float scaleHeight = scaleWidth;
            // CREATE A MATRIX FOR THE MANIPULATION
            Matrix matrix = new Matrix();
            // RESIZE THE BIT MAP
            matrix.postScale(scaleWidth, scaleHeight);

            // "RECREATE" THE NEW BITMAP
            Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
            bm.recycle();
            return resizedBitmap;
    }

    public static boolean checkNetworkStatus(Context context) {
        boolean HaveConnectedWifi = false;
        boolean HaveConnectedMobile = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) context .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo[] netInfo = cm.getAllNetworkInfo();
            for (NetworkInfo ni : netInfo) {
                if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                    if (ni.isConnected()) HaveConnectedWifi = true;
                if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                    if (ni.isConnected()) HaveConnectedMobile = true;
            }
        } catch (Exception e) { e.printStackTrace();
        }
        return HaveConnectedWifi || HaveConnectedMobile;
    }


}
