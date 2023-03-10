package com.example.foody2.Model;

import android.os.AsyncTask;

import com.google.type.LatLng;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;

public class DownloadPolyline extends AsyncTask<String ,Void, String > {
    @Override
    protected String doInBackground(String... strings) {
        StringBuffer stringBuffer= new StringBuffer();;
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            InputStream inputStream =   connection.getInputStream();
            InputStreamReader reader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line = "" ;
            while ((line=bufferedReader.readLine())!=null){
                stringBuffer.append(line);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }

    @Override
    protected void onPostExecute(String dataJson) {
        super.onPostExecute(dataJson);
    }
}
