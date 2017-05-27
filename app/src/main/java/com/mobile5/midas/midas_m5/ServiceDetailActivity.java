package com.mobile5.midas.midas_m5;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile5.midas.midas_m5.DB.DB;
import com.mobile5.midas.midas_m5.DB.MySharedPreferences;
import com.mobile5.midas.midas_m5.dto.ServiceDTO;
import com.squareup.picasso.Picasso;

public class ServiceDetailActivity extends AppCompatActivity implements View.OnClickListener {

    TextView title, where, point, info;
    ImageView img;
    Button regist;
    MySharedPreferences sharedPreferences;

    ServiceDTO serviceInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_detail);
        sharedPreferences = new MySharedPreferences(ServiceDetailActivity.this);

        title = (TextView)findViewById(R.id.serviceName);
        where = (TextView)findViewById(R.id.where);
        point = (TextView)findViewById(R.id.point);
        info = (TextView)findViewById(R.id.Info);
        regist = (Button)findViewById(R.id.regist);
        regist.setOnClickListener(this);
        img = (ImageView)findViewById(R.id.poster);

        Intent intent = getIntent();
        serviceInfo = (ServiceDTO) intent.getSerializableExtra("item");

        title.setText(serviceInfo.getTitle());
        Picasso.with(this).load("http://ec2-13-124-108-18.ap-northeast-2.compute.amazonaws.com/mobile5/" + serviceInfo.getImageUrl()).into(img);
        where.setText(serviceInfo.getLocation());
        point.setText(String.valueOf(serviceInfo.getPointPerHour()));
        info.setText(serviceInfo.getDetail());

        searchToDatabase(String.valueOf(sharedPreferences.getUserInfo()), String.valueOf(serviceInfo.getId()));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.regist) {
            if(regist.getText().equals("신청하기")) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle("신청 하시겠습니까?");

                dialog.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        InsertToDatabase(String.valueOf(sharedPreferences.getUserInfo()), String.valueOf(serviceInfo.getId()));
                    }
                });
                dialog.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                dialog.show();
            }else {
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle("취소 하시겠습니까?");

                dialog.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DeleteToDatabase(String.valueOf(sharedPreferences.getUserInfo()), String.valueOf(serviceInfo.getId()));
                    }
                });
                dialog.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                dialog.show();

            }
        }
    }

    private void searchToDatabase(String UserID, String ServiceID){

        class searchData extends AsyncTask<String, Void, String> {
            String UserID;
            String ServiceID;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(s.equals("ok")) {
                    Toast.makeText(getApplicationContext(),"취소 가능한 행사입니다.",Toast.LENGTH_SHORT).show();
                    regist.setText("취소하기");
                    regist.setBackgroundColor(Color.parseColor("#FFB6C1"));
                }
                else {
                    Toast.makeText(getApplicationContext(),"신청 가능한 행사입니다.",Toast.LENGTH_SHORT).show();
                    regist.setText("신청하기");
                    regist.setBackgroundColor(Color.parseColor("#4169E1"));
                }
            }
            @Override
            protected String doInBackground(String... params) {

                String[] info = sharedPreferences.getUserInfo();

                UserID = info[0];
                ServiceID = params[1];


                String[] posts = {UserID, ServiceID};
                DB db = new DB("isMyService.php");
                String result = db.post(posts);
                Log.d(result,"result~~~~~~");
                return result;
            }
        }
        //searchData task = new searchData(Application.this);
        searchData task = new searchData();
        //task.execute(ServiceID, UserID);
        task.execute(UserID, ServiceID);
    }


    private void InsertToDatabase(String UserID, String ServiceID){

        class searchData extends AsyncTask<String, Void, String> {
            String UserID;
            String ServiceID;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(s.equals("ok")) {
                    Toast.makeText(getApplicationContext(),"신청되었습니다. ",Toast.LENGTH_SHORT).show();
                    regist.setText("취소하기");
                    regist.setBackgroundColor(Color.parseColor("#FFB6C1"));
                }
                else {
                    Toast.makeText(getApplicationContext(),"오류가 발생하였습니다.",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            protected String doInBackground(String... params) {

                String[] info = sharedPreferences.getUserInfo();

                UserID = info[0];
                ServiceID = params[1];

                String[] posts = {UserID, ServiceID};
                DB db = new DB("volunte.php");
                String result = db.post(posts);
                Log.d(result,"result~~~~~~");
                return result;
            }
        }

        searchData task = new searchData();
        task.execute(UserID, ServiceID);
    }

    private void DeleteToDatabase(String UserID, String ServiceID){

        class searchData extends AsyncTask<String, Void, String> {
            String UserID;
            String ServiceID;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(s.equals("ok")) {
                    Toast.makeText(getApplicationContext(),"취소되었습니다. ",Toast.LENGTH_SHORT).show();
                    regist.setText("신청하기");
                    regist.setBackgroundColor(Color.parseColor("#4169E1"));
                }
                else {
                    Toast.makeText(getApplicationContext(),"오류가 발생하였습니다.",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            protected String doInBackground(String... params) {

                String[] info = sharedPreferences.getUserInfo();

                UserID = info[0];
                ServiceID = params[1];

                String[] posts = {UserID, ServiceID};
                DB db = new DB("volute_cancel.php");
                String result = db.post(posts);
                Log.d(result,"result~~~~~~");
                return result;
            }
        }

        searchData task = new searchData();
        task.execute(UserID, ServiceID);
    }

}
