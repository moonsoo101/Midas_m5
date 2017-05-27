package com.mobile5.midas.midas_m5;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile5.midas.midas_m5.DB.DB;
import com.mobile5.midas.midas_m5.dto.ServiceDTO;
import com.squareup.picasso.Picasso;

public class ServiceDetailActivity extends AppCompatActivity implements View.OnClickListener {

    TextView title, where, point, info;
    ImageView img;
    Button regist;

    ServiceDTO serviceInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_detail);

        title = (TextView)findViewById(R.id.serviceName);
        where = (TextView)findViewById(R.id.where);
        point = (TextView)findViewById(R.id.point);
        info = (TextView)findViewById(R.id.Info);
        regist = (Button)findViewById(R.id.regist);

        img = (ImageView)findViewById(R.id.poster);

        Intent intent = getIntent();
        serviceInfo = (ServiceDTO) intent.getSerializableExtra("item");

        title.setText(serviceInfo.getTitle());
        Picasso.with(this).load("http://ec2-13-124-108-18.ap-northeast-2.compute.amazonaws.com/mobile5/" + serviceInfo.getImageUrl()).into(img);
        where.setText(serviceInfo.getLocation());
        point.setText(String.valueOf(serviceInfo.getPointPerHour()));
        info.setText(serviceInfo.getDetail());

        searchToDatabase(String.valueOf(serviceInfo.getId()), "201701");

    }

    @Override
    public void onClick(View v) {
/*        int id = v.getId();
        if (id == R.id.btn1) {
            Intent intent = new Intent(this, SingleCalendarActivity.class);
            startActivity(intent);
        } else if (id == R.id.btn2) {
            Intent intent = new Intent(this, MultiCalendarActivity.class);
            startActivity(intent);
        }*/
    }

    private void searchToDatabase(String UserID, String ServiceID){

        class searchData extends AsyncTask<String, Void, String> {
            String ServiceID;
            String UserID;

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
                }
                else {
                    Toast.makeText(getApplicationContext(),"신청 가능한 행사입니다.",Toast.LENGTH_SHORT).show();
                    regist.setText("신청하기");
                }
            }
            @Override
            protected String doInBackground(String... params) {

                ServiceID = params[0];
                UserID = "201701";
                //UserID = params[1];
                //String[] posts = {ServiceID, UserID};
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

}
