package com.mobile5.midas.midas_m5;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobile5.midas.midas_m5.dto.ServiceDTO;
import com.squareup.picasso.Picasso;

public class ServiceDetailActivity extends AppCompatActivity {

    TextView title, where, point, info;
    ImageView img;
    ServiceDTO serviceInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_detail);

        title = (TextView)findViewById(R.id.serviceName);
        where = (TextView)findViewById(R.id.where);
        point = (TextView)findViewById(R.id.point);
        info = (TextView)findViewById(R.id.Info);

         img = (ImageView)findViewById(R.id.poster);

        Intent intent = getIntent();
        serviceInfo = (ServiceDTO) intent.getSerializableExtra("item");

        title.setText(serviceInfo.getTitle());
        Picasso.with(this).load("http://ec2-13-124-108-18.ap-northeast-2.compute.amazonaws.com/mobile5/" + serviceInfo.getImageUrl()).into(img);
        where.setText(serviceInfo.getLocation());
        //point.setText(serviceInfo.mPointPerHour);
        info.setText(serviceInfo.getDetail());
    }

}
