package com.example.uday_vig.mapmyindia;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Post extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... strings) {
        URL url = null;
        HttpURLConnection client = null;
        try {
            url = new URL("http://localhost:3000/time");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            client = (HttpURLConnection) url.openConnection();
            client.setRequestMethod("POST");
            client.setRequestProperty("body", strings[0]);
            client.setDoOutput(true);

            OutputStream outputPost = new BufferedOutputStream(client.getOutputStream());
            outputPost.write(Integer.parseInt(strings[0]));
            outputPost.flush();
            outputPost.close();
        } catch (IOException e) {
            Log.e("YOLO", "doInBackground: ERROR!");
            e.printStackTrace();
        } finally {
            if(client != null) // Make sure the connection is not null.
                client.disconnect();
        }
        /*HttpURLConnection urlConnection = null;
        StringBuffer response = new StringBuffer();
        try {
            Log.e("YOLO", "doInBackground: " + "1");
            URL url = new URL("http://localhost:3000/time");
            Log.e("YOLO", "doInBackground: " + "2");
            urlConnection = (HttpURLConnection) url.openConnection();
            Log.e("YOLO", "doInBackground: " + "3");
            urlConnection.setDoInput(true);
            Log.e("YOLO", "doInBackground: " + "4");
            urlConnection.setDoOutput(true);
            Log.e("YOLO", "doInBackground: " + "5");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            Log.e("YOLO", "doInBackground: " + "6");
            urlConnection.setRequestMethod("POST");
            Log.e("YOLO", "doInBackground: " + "7");
            urlConnection.setConnectTimeout(5000);
            Log.e("YOLO", "doInBackground: " + "8");

            // Send the post body
            DataOutputStream wr = new DataOutputStream (urlConnection.getOutputStream());
            Log.e("YOLO", "doInBackground: " + "9");
            wr.writeBytes(strings[0]);
            Log.e("YOLO", "doInBackground: " + "10");
            wr.flush ();
            Log.e("YOLO", "doInBackground: " + "11");
            wr.close ();
            Log.e("YOLO", "doInBackground: " + "12");

            *//*if (urlConnection.getResponseCode() ==  HttpURLConnection.HTTP_OK) {
                //Get Response
                InputStream is = urlConnection.getInputStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                String line;

                while((line = rd.readLine()) != null) {
                    response.append(line);
                    response.append('\n');
                }
                rd.close();
            } else {
                response.append("HTTP Response code not OK - " + urlConnection.getResponseCode());
            }*//*
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("YOLO", "doInBackground: ERROR!");
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return strings[0];*/
        return strings[0];
    }

    @Override
    protected void onPostExecute(String s) {
        Log.e("YOLO", "onPostExecute: " + s);
    }
}
