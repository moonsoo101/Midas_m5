package com.mobile5.midas.midas_m5;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile5.midas.midas_m5.DB.DB;
import com.mobile5.midas.midas_m5.DB.MySharedPreferences;
import com.mobile5.midas.midas_m5.dto.DonationDTO;
import com.squareup.picasso.Picasso;

public class DonationDetailActivity extends AppCompatActivity  implements View.OnClickListener {
    MySharedPreferences sharedPreferences;
    TextView title, where, totalPoint;
    ImageView img;
    Button donate;
    EditText etEdit;

    DonationDTO donateInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_detail);
        sharedPreferences = new MySharedPreferences(DonationDetailActivity.this);

        title = (TextView)findViewById(R.id.serviceName);
        where = (TextView)findViewById(R.id.where);
        totalPoint = (TextView)findViewById(R.id.totalpoint);
        donate = (Button)findViewById(R.id.donate);

        donate.setOnClickListener(this);

        img = (ImageView)findViewById(R.id.poster);

        Intent intent = getIntent();
        donateInfo = (DonationDTO) intent.getSerializableExtra("item");

        title.setText(donateInfo.getTitle());
        Picasso.with(this).load("http://ec2-13-124-108-18.ap-northeast-2.compute.amazonaws.com/mobile5/" + donateInfo.getImageUrl()).into(img);
        where.setText(donateInfo.getLocation());
        totalPoint.setText(String.valueOf(donateInfo.getTotalPoint()));
        etEdit = new EditText(this);
    }

    private void InsertToDatabase(String UserID, String DonateID, String Point){

        class searchData extends AsyncTask<String, Void, String> {
            String UserID;
            String DonateID;
            String Point;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                // 포인트 부족으로 인한 기부 불가능
                if(s.equals("거지")) {
                    Toast.makeText(getApplicationContext(),"포인트가 부족합니다.",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(),Point+" 포인트를 기부하였습니다.",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            protected String doInBackground(String... params) {

                String[] info = sharedPreferences.getUserInfo();

                UserID = info[0];
                DonateID = params[1];
                Point = params[2];

                String[] posts = {UserID, DonateID, Point};
                DB db = new DB("donation.php");
                String result = db.post(posts);

                Log.d(result,"Donation result~~~~~~");
                return result;
            }
        }
        searchData task = new searchData();
        task.execute(UserID, DonateID, Point);
    }

    @Override
    public void onClick(View v) {
            switch(v.getId()) {
                case R.id.donate: {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                    dialog.setTitle("기부 포인트 입력");
                    dialog.setView(etEdit);

                    dialog.setPositiveButton("기부", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String inputValue = etEdit.getText().toString();
                            InsertToDatabase(String.valueOf(sharedPreferences.getUserInfo()), String.valueOf(donateInfo.getId()), inputValue);
                        }
                    });
                    dialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    dialog.show();
                }
            }
    }

}
