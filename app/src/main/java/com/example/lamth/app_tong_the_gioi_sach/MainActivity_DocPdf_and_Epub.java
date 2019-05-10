package com.example.lamth.app_tong_the_gioi_sach;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.sdsmdg.tastytoast.TastyToast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.lamth.app_tong_the_gioi_sach.Url_config.banner_footer;
import static com.example.lamth.app_tong_the_gioi_sach.Url_config.checkNetworkStatus;
import static com.example.lamth.app_tong_the_gioi_sach.Url_config.url_get_InfoChuongPDF;
import static com.example.lamth.app_tong_the_gioi_sach.Url_config.url_image_server;


public class MainActivity_DocPdf_and_Epub extends AppCompatActivity {

    String Id_quyensachPdf = "";
    String Id_QuyenSach = "";
    String LinkQuyenSach = "";

    private WebView myWebView ;

    String pdf = "";
    String url = "";

    String new_namepdf ="";
    String namepdf = "";
    private ProgressDialog pdLoading;
    RelativeLayout re_home_more_share_rate , re_ads;
    String tenquyensach = "";
    TextView tv_tenquyensach , textView26 ;

    private AdView av;

    private ImageView Dowload;

    private PopupWindow popupWindow;

    private static final int REQUEST_STORAGE = 112;

    private static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__doc_pdf_and__epub);

        Intent iin = getIntent();
        Bundle extras = iin.getExtras();
        if (extras != null) {
            Id_QuyenSach = (String) extras.getString("id_chuongpdf");
            tenquyensach = (String) extras.getString("tenquyensach");
        }

        if (checkNetworkStatus(MainActivity_DocPdf_and_Epub.this))
        {//check internet connection if true
            new LoadApplication().execute();
        }else {// else show error toast
            Toast.makeText(this, "internet connection problem !", Toast.LENGTH_SHORT).show();
        }

        RelativeLayout i_back = (RelativeLayout) findViewById(R.id.re_back);
        i_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    onBackPressed();
                }catch (Exception ep){}
            }
        });


        tv_tenquyensach = (TextView) findViewById(R.id.tv_tenquyensach);

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

        myWebView = (WebView) findViewById(R.id.webview);
        textView26 = (TextView) findViewById(R.id.textView26);

        Dowload = (ImageView) findViewById(R.id.downloadPdf);
        Dowload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //new FileDownloader(MainActivity_DocPdf_and_Epub.this, downloadPdf, downloadPdfUrl);

                LayoutInflater layoutInflater = (LayoutInflater) MainActivity_DocPdf_and_Epub.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View customView = layoutInflater.inflate(R.layout.popup_download,null);

                final TextView downloadPdf = (TextView) customView.findViewById(R.id.textView32);

                TextView textView27 = (TextView) customView.findViewById(R.id.textView27);

                TextView textView29 = (TextView) customView.findViewById(R.id.textView29);

                final TextView textView31 = (TextView) customView.findViewById(R.id.textView31);

                RelativeLayout re_popup = (RelativeLayout)customView.findViewById(R.id.re_popup);

               // final RelativeLayout re_link = (RelativeLayout)customView.findViewById(R.id.re_link);

                //instantiate popup window
                popupWindow = new PopupWindow(customView, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                //display the popup window
                popupWindow.showAtLocation(re_popup, Gravity.CENTER, 0, 0);

                downloadPdf.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       try{

                           if(pdf.contains("epub")){
                               String newpdf = pdf.replace(".epub",".pdf");
                               new FileDownloader(MainActivity_DocPdf_and_Epub.this, downloadPdf, newpdf );
                               TastyToast.makeText(getApplicationContext(), "Download thành công!", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                           }else {
                               new FileDownloader(MainActivity_DocPdf_and_Epub.this, downloadPdf, pdf );
                               TastyToast.makeText(getApplicationContext(), "Download thành công!", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                           }

                           // Code này lấy đc tên của file PDF .
                           namepdf = pdf.replace('\\', '/');
                           new_namepdf = namepdf.replace("http://adminsachvui.giaynct.com/UserFiles/files/", "");

                           if (Build.VERSION.SDK_INT >= 23) {

                               if (new_namepdf.contains("epub")){
                                   String new_name_replace = new_namepdf.replace(".epub",".pdf");
                                   textView31.setText("Link download : " + Environment.getExternalStorageDirectory().toString() + "/Download/com.free.booksonline.thegioisach" + "/" + new_name_replace);
                               }else {
                                   textView31.setText("Link download : " + Environment.getExternalStorageDirectory().toString() + "/Download/com.free.booksonline.thegioisach" + "/" + new_namepdf);
                               }
                           }else {
                               if (new_namepdf.contains("epub")){
                                   String new_name_replace = new_namepdf.replace(".epub",".pdf");
                                   textView31.setText("Link download : " + Environment.getExternalStorageDirectory().toString() + "/Download/com.free.booksonline.thegioisach" + "/" + new_name_replace);
                               }else {
                                   textView31.setText("Link download : " + Environment.getExternalStorageDirectory().toString() + "/Download/com.free.booksonline.thegioisach" + "/" + new_namepdf);
                               }
                           }

                       }catch (Exception ex){}
                    }
                });

                // Show;
                textView27.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openDownloadedFolder();
                        customView.setVisibility(View.INVISIBLE);
                    }
                });


                //close the popup window on button click
                textView29.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        //textView26.setVisibility(View.VISIBLE);
                    }
                });


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
            String stt = e.toString();
        } finally {
            urlConnection.disconnect();
        }
        return result;
    }

    private class LoadApplication extends AsyncTask<Void, Void, Void> {

        String get_IdQuyensachPdf = "";
        String[] item_quyensach;
        String Filepath;

        @Override
        protected Void doInBackground(Void... params) {

            try{

                get_IdQuyensachPdf = get_dulieu(url_get_InfoChuongPDF + Id_QuyenSach);
                item_quyensach  = get_IdQuyensachPdf.split("@");

                Id_quyensachPdf = item_quyensach[0];
                Id_QuyenSach = item_quyensach[1];

                if (item_quyensach.length > 2) {
                    LinkQuyenSach = url_image_server + item_quyensach[2];
                }

            }catch (Exception ep){}


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            pdLoading.dismiss();

            if (tenquyensach.length() >= 33){
                String sub = tenquyensach.substring(0,33);
                tv_tenquyensach.setText(sub + " ...");
            }else {
                tv_tenquyensach.setText(tenquyensach);
            }

            pdf = LinkQuyenSach ;

            if (pdf != null){

//                  Đọc bằng webview có sẵn trong android .
                    myWebView.getSettings().setJavaScriptEnabled(true);
                    myWebView.getSettings().setDomStorageEnabled(true) ;
                    myWebView.getSettings().setAllowFileAccess(true);
                    myWebView.getSettings().setDisplayZoomControls(true);
                    myWebView.setWebViewClient(new myWebClient());
                    if(pdf.contains("epub")){
                        String newpdf = pdf.replace(".epub",".pdf");
                        url = "<iframe src='http://docs.google.com/gview?embedded=true&url=" + newpdf + "'" + "width='100%' height='100%' style='border: none;'></iframe>";
                        //myWebView.loadData(url , "text/html", "UTF-8");
                        myWebView.loadData(url, "text/html", "UTF-8");
                    }else {
                        // Lưu trữ nội bộ - Internal Storage
                        url = "<iframe src='http://docs.google.com/gview?embedded=true&url=" + pdf + "'" + "width='100%' height='100%' style='border: none;'></iframe>";
                        myWebView.loadData(url, "text/html", "UTF-8");
                    }

                // Đọc bằng Intent Acitivi mới
//                      if(pdf.contains("epub")){
//                        String newpdf = pdf.replace(".epub",".pdf");;
//                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(newpdf));
//                        startActivity(browserIntent);
//                        textView26.setVisibility(View.VISIBLE);
//                    }else {
//                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(pdf));
//                        startActivity(browserIntent);
//                        textView26.setVisibility(View.VISIBLE);
//                    }

            }


            Toast toast = TastyToast.makeText(getApplicationContext(), "Đang load dữ liệu vui lòng chờ ...!", TastyToast.LENGTH_LONG, TastyToast.CONFUSING);
            toast.setGravity(Gravity.BOTTOM, 0, 0);
            toast.show();

            super.onPostExecute(aVoid);
        }

        @Override
        protected void onPreExecute() {
            pdLoading = new ProgressDialog(MainActivity_DocPdf_and_Epub.this);
            pdLoading.setMessage("\tPlease wait...");
            pdLoading.setCancelable(true);
            pdLoading.show();
            super.onPreExecute();
        }
    }

    //Open downloaded folder
    private void openDownloadedFolder() {
        //First check if SD Card is present or not
        if (new CheckForSDCard().isSDCardPresent()) {

                namepdf = pdf.replace('\\', '/');
                new_namepdf = namepdf.replace("http://adminsachvui.giaynct.com/UserFiles/files/", "");

                if (new_namepdf.contains("epub")){
                    String new_name_replace = new_namepdf.replace(".epub",".pdf");
                    String storage =Environment.getExternalStorageDirectory().toString();
                    File file = new File(storage, "Download/com.free.booksonline.thegioisach" + "/" + new_name_replace);
                    Uri uri;
                    if (Build.VERSION.SDK_INT < 23) {
                        uri = Uri.fromFile(file);
                    } else {
                        uri = Uri.parse(file.getPath()); // My work-around for new SDKs, causes ActivityNotFoundException in API 10.
                    }
                    Intent viewFile = new Intent(Intent.ACTION_VIEW);
                    viewFile.setDataAndType(uri, "*/*");
                    startActivity(viewFile);
                }else {
                    String storage =Environment.getExternalStorageDirectory().toString();
                    File file = new File(storage, "Download/com.free.booksonline.thegioisach" + "/" + new_namepdf);
                    Uri uri;
                    if (Build.VERSION.SDK_INT < 23) {
                        uri = Uri.fromFile(file);
                    } else {
                        uri = Uri.parse(file.getPath()); // My work-around for new SDKs, causes ActivityNotFoundException in API 10.
                    }
                    Intent viewFile = new Intent(Intent.ACTION_VIEW);
                    viewFile.setDataAndType(uri, "*/*");
                    startActivity(viewFile);
                }



        } else
            Toast.makeText(MainActivity_DocPdf_and_Epub.this, "Oops!! There is no SD Card.", Toast.LENGTH_SHORT).show();
    }

    public class myWebClient extends WebViewClient
    {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if(Uri.parse(url).getHost().length() == 0) {
                return false;
            }

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            view.getContext().startActivity(intent);
            return true;
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
