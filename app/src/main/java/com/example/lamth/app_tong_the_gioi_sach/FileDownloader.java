package com.example.lamth.app_tong_the_gioi_sach;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.TextView;
import java.io.File;
import java.io.IOException;


public class FileDownloader {


    private static final String LOG_TAG_EXTERNAL_STORAGE = "EXTERNAL_STORAGE";

    private static final String TAG = "Download Task";
    private Context context;
    private TextView buttonText;
    private String downloadUrl = "";
    private String downloadFileName = "";

    private String url_anh = "" ;

    public FileDownloader(Context context, TextView buttonText, String downloadUrl) {
        this.context = context;
        this.buttonText = buttonText;
        this.downloadUrl = downloadUrl;

        url_anh = downloadUrl.replace('\\', '/');
        downloadFileName = url_anh.replace("http://adminsachvui.giaynct.com/UserFiles/files/", "");//Create file name by picking download file name from URL

        Log.e(TAG, downloadFileName);

        //Start Downloading Task
        new DownloadFile().execute(url_anh , downloadFileName);

    }

//    private class DownloadingTask extends AsyncTask<Void, Void, Void> {
//
//        File apkStorage = null;
//        File outputFile = null;
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            buttonText.setEnabled(false);
//        }
//
//        @Override
//        protected void onPostExecute(Void result) {
//
////            try {
////                if (outputFile != null) {
////                    buttonText.setEnabled(true);
////                    //buttonText.setText(R.string.downloadCompleted);//If Download completed then change button text
////                } else {
////                    //buttonText.setText(R.string.downloadFailed);//If download failed change button text
////                    new Handler().postDelayed(new Runnable() {
////                        @Override
////                        public void run() {
////                            buttonText.setEnabled(true);
////                            //buttonText.setText(R.string.downloadAgain);//Change button text again after 3sec
////                        }
////                    }, 3000);
////
////                    Log.e(TAG, "Download Failed");
////
////                }
////            } catch (Exception e) {
////                e.printStackTrace();
////
////                //Change button text if exception occurs
////                //buttonText.setText(                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           R.string.downloadFailed);
////                new Handler().postDelayed(new Runnable() {
////                    @Override
////                    public void run() {
////                        buttonText.setEnabled(true);
////                        //buttonText.setText(R.string.downloadAgain);
////                    }
////                }, 3000);
////                Log.e(TAG, "Download Failed with Exception - " + e.getLocalizedMessage());
////
////            }
//
//            super.onPostExecute(result);
//        }

//        @Override
//        protected Void doInBackground(Void... arg0) {
//            try {
//
////                if (Build.VERSION.SDK_INT >= 23){
////                    URL url = new URL(downloadUrl);//Create Download URl
////                    HttpURLConnection c = (HttpURLConnection) url.openConnection();//Open Url Connection
////                    c.setRequestMethod("GET");//Set Request Method to "GET" since we are grtting data
////                    c.connect();//connect the URL Connection
////
////                    //Get File if SD card is present
////                    File apkStorage = new File(Environment.getExternalStorageDirectory() + "/" + downloadDirectory );
////                    //If File is not present create directory
////                    if (!apkStorage.exists()) {
////                        if (!apkStorage.mkdirs()){
////                            apkStorage.mkdir();
////                            Log.e(TAG, "Directory Created.");
////                        }
////                    }
////
////                    outputFile = new File(apkStorage, downloadFileName);//Create Output file in Main File
////
////                    //Create New File if not present
////                    if (!outputFile.exists()) {
////                        outputFile.createNewFile();
////                        Log.e(TAG, "File Created");
////                    }
////
////                    FileOutputStream fos = new FileOutputStream(outputFile);//Get OutputStream for NewFile Location
////
////                    InputStream is = c.getInputStream();//Get InputStream for connection
////
////                    byte[] buffer = new byte[1024];//Set buffer type
////                    int len1 = 0;//init length
////                    while ((len1 = is.read(buffer)) != -1) {
////                        fos.write(buffer, 0, len1);//Write new file
////                    }
////
////                    //Close all connection after doing task
////                    fos.close();
////                    is.close();
////                }else{
////
////                    downloadUrl = strings[0];
////                    downloadFileName = strings[1];
////                    String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
////                    File folder = new File(extStorageDirectory, downloadDirectory);
////                    folder.mkdir();
////
////                    File pdfFile = new File(folder, downloadFileName);
////
////                    try{
////                        pdfFile.createNewFile();
////                    }catch (IOException e){
////                        e.printStackTrace();
////                    }
////                    Downloader.downloadFile(downloadUrl, pdfFile);
////
////                }
//
//                String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
//                String fileName = strings[1];  // -> maven.pdf
//                String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
//                File folder = new File(extStorageDirectory, downloadFileName);
//                folder.mkdir();
//
//                File pdfFile = new File(folder, fileName);
//
//                try{
//                    pdfFile.createNewFile();
//                }catch (IOException e){
//                    e.printStackTrace();
//                }
//                Downloader.downloadFile(fileUrl, pdfFile);
//
//
//
//
//
//
//            } catch (Exception e) {
//            }
//
//            return null;
//        }
//    }

        private static class DownloadFile extends AsyncTask<String, Void, Void> {

            @Override
            protected Void doInBackground(String... strings) {
                String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
                String fileName = strings[1];  // -> maven.pdf
                String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
                File folder = new File(extStorageDirectory, "Download/com.free.booksonline.thegioisach");
                folder.mkdir();

                File pdfFile = new File(folder, fileName);

                try{
                    pdfFile.createNewFile();
                }catch (IOException e){
                    e.printStackTrace();
                }
                Downloader.downloadFile(fileUrl, pdfFile);


                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        }

}
