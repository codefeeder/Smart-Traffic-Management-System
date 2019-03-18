package com.example.uday_vig.mapmyindia;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mapbox.mapboxsdk.geometry.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GetData extends AsyncTask<String, Void, String> {

    String url;
    String yolo;
    int count = 0;

    Context context;

    LatLng[] arr;

    public GetData(int count, LatLng[] arr, Context context) {
        this.count = count;
        this.arr = arr;
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings){
        try{
            url = strings[0];
            DownloadUrl downloadUrl = new DownloadUrl();
            yolo = downloadUrl.readUrl(url);
            Log.e("Response", "doInBackground: " + yolo);
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return yolo;
    }

    @Override
    protected void onPostExecute(String s) {
        try {
            JSONObject root = new JSONObject(s);
            JSONArray array = root.getJSONArray("results");
            JSONObject obj = array.getJSONObject(0);
            String dist = obj.getString("length");
            String duration = obj.getString("duration");
            Log.e("YOLO", "onPostExecute: " + "dist: " + dist);
            Log.e("YOLO", "onPostExecute: " + "duration: " + duration);

//            String myurl = "http://localhost:3000/time";
//            try{
//                URL url = new URL(myurl);
//                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
//                httpURLConnection.setRequestMethod("POST");
//                httpURLConnection.setDoOutput(true);
//                httpURLConnection.setDoInput(true);
//                OutputStream outputStream = httpURLConnection.getOutputStream();
//                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
//                String post_data = URLEncoder.encode(duration,"UTF-8");
//                bufferedWriter.write(post_data);
//                bufferedWriter.flush();
//                bufferedWriter.close();
//                outputStream.close();
//
//                httpURLConnection.disconnect();
//
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

//            new Post().execute(duration);

            try {
                RequestQueue requestQueue = Volley.newRequestQueue(context);
                String URL = "http://10.105.31.255:8080/";
                JSONObject jsonBody = new JSONObject();
                jsonBody.put("Title", "Android Volley Demo");
                jsonBody.put("Author", "BNK");
                final String requestBody = jsonBody.toString();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("YOLO", response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("YOLO", error.toString());
                    }
                }) {
                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8";
                    }

                    @Override
                    public byte[] getBody() throws AuthFailureError {
                        try {
                            return requestBody == null ? null : requestBody.getBytes("utf-8");
                        } catch (UnsupportedEncodingException uee) {
                            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                            return null;
                        }
                    }

                    @Override
                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                        String responseString = "";
                        if (response != null) {
                            responseString = String.valueOf(response.statusCode);
                            // can get more details such as response.headers
                        }
                        return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                    }
                };

                stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                        50000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                requestQueue.add(stringRequest);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            final Handler handler = new Handler();
            final int delay = 5000; //milliseconds

            handler.postDelayed(new Runnable(){
                public void run(){
                    //do something
                    if(count >= arr.length - 1){
                        Log.e("YOLO", "Loop left");
                    }else{
                        Log.e("YOLO", "run: " + count);
                        new GetData(++count, arr, context).execute("https://apis.mapmyindia.com/advancedmaps/v1/Your MapMyIndia API Key/distance?center=" + arr[count - 1].getLatitude() + "," + arr[count - 1].getLongitude() + "%7c&pts=" + arr[count].getLatitude() + "," + arr[count].getLongitude());
                    }
                }
            }, delay);
        } catch (JSONException e) {
            Log.e("SOLO", "onPostExecute: JSON ERROR!");
        }
    }
}
