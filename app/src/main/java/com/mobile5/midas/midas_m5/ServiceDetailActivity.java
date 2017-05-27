package com.mobile5.midas.midas_m5;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ServiceDetailActivity extends AppCompatActivity {

    TextView title, where, point, info;
    //ImageView img;
    ServiceDTO serviceInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_detail);

        title = (TextView)findViewById(R.id.serviceName);
        where = (TextView)findViewById(R.id.where);
        point = (TextView)findViewById(R.id.point);
        info = (TextView)findViewById(R.id.Info);

        // img = (ImageView)findViewById(R.id.poster);

        serviceInfo = new ServiceDTO(1, "17th Run", "seoul", 5000,
                "서울 지역구민과 함께하는 달리기 행사","http://i.imgur.com/DvpvklR.png", true );

        title.setText(serviceInfo.mTitle);
        //피카소 불러오기 주석처리
/*        Picasso.with(this)
                .load(serviceInfo.mImageUrl)
                .into(img);*/
        where.setText(serviceInfo.mLocation);
        //point.setText(serviceInfo.mPointPerHour);
        info.setText(serviceInfo.mDetail);
    }

    public class ServiceDTO {
        private int mId;
        private String mTitle;
        private String mLocation;
        private int mPointPerHour;
        private String mDetail;
        private String mImageUrl;
        private boolean mStete;

        public ServiceDTO() {
            this(-1, null, null, -1, null, null, false);
        }

        public ServiceDTO(int id, String title, String location, int pointPerHour, String detail, String imageUrl, boolean state) {
            mId = id;
            mTitle = title;
            mLocation = location;
            mPointPerHour = pointPerHour;
            mDetail = detail;
            mImageUrl = imageUrl;
            mStete = state;
        }
    }
}
