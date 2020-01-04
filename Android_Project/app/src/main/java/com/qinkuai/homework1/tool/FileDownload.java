package com.qinkuai.homework1.tool;

import android.content.Context;
import android.os.Environment;

import com.qinkuai.homework1.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FileDownload {

    public static void download(String path, String storePath, String fileName){
        new Thread(()->{
            try {
                URL url = new URL(path);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);
                connection.setRequestProperty("Charset", "utf-8");
                connection.setRequestMethod("GET");

                if (connection.getResponseCode() == 200){
                    InputStream is = connection.getInputStream();
                    FileOutputStream fos = null;
                    if (is != null){
                        File store = new File(storePath);
                        if (!store.exists()){
                            store.mkdirs();
                        }
                        fos = new FileOutputStream(new File(store.getAbsolutePath() + "/" + fileName));
                        byte[] buf = new byte[4096];
                        int length = 0;
                        while((length = is.read(buf)) != -1){
                            fos.write(buf, 0, length);
                        }

                        fos.close();
                    }
                }
            }catch (IOException e){
                e.printStackTrace();
            }

        }).start();
    }
}
