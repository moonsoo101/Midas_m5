package com.mobile5.midas.midas_m5;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MyInfoActivity extends AppCompatActivity {
    String url = "http://ec2-13-124-108-18.ap-northeast-2.compute.amazonaws.com/mobile5/myInfo.php";
    TextView point, count;

    public GettingPHP gPHP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);

        point = (TextView)findViewById(R.id.point);
        count = (TextView)findViewById(R.id.count);

        gPHP = new GettingPHP();
        // MyInfo URL 전달
        gPHP.execute(url);
    }

    class GettingPHP extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            StringBuilder jsonHtml = new StringBuilder();
            try {

                String data = URLEncoder.encode("ID", "UTF-8") + "=" + URLEncoder.encode("201701", "UTF-8");

                // 매개변수 통해 전달된 URL
                URL phpUrl = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection)phpUrl.openConnection();
                Log.i("connect", "!!!!!!!!!!!!!!!!!!!!!");

                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                wr.write(data);
                wr.flush();

                if ( conn != null ) {
                    conn.setConnectTimeout(10000);
                    conn.setUseCaches(false);

                    // DB에 연결
                    if ( conn.getResponseCode() == HttpURLConnection.HTTP_OK ) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                        while ( true ) {
                            String line = br.readLine();
                            if ( line == null )
                                break;
                            //데이터 읽어오기
                            jsonHtml.append(line + "\n");
                        }
                        br.close();
                    }
                    conn.disconnect();
                }
            } catch ( Exception e ) {
                e.printStackTrace();
            }
            return jsonHtml.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {

                // PHP에서 받아온 JSON 데이터를 JSON오브젝트로 변환
                JSONObject jObject = new JSONObject(s);
                Log.e("dddddd", jObject.toString());

                //  JSON key 값 확인 후 등록 필요
                JSONArray results = jObject.getJSONArray("result");
                String temppoint = "";
                String tempcount = "";
                //jObject.get("status");
                //jObject.get("num_result");


                for ( int i = 0; i < results.length(); ++i ) {
                    JSONObject temp = results.getJSONObject(i);
                    temp.get("ID");
                    temp.get("Password");
                    temppoint += temp.get("Point");
                    tempcount += temp.get("Name");
                }
                point.setText(temppoint);
                point.setText(tempcount);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
