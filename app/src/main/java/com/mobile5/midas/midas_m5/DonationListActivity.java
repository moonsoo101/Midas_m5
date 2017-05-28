package com.mobile5.midas.midas_m5;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.mobile5.midas.midas_m5.DB.DB;
import com.mobile5.midas.midas_m5.DB.MySharedPreferences;
import com.mobile5.midas.midas_m5.dto.DonationDTO;
import com.mobile5.midas.midas_m5.dto.ServiceDTO;
import com.mobile5.midas.midas_m5.list.DonationArrayAdapter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class DonationListActivity extends AppCompatActivity {
    ListView mDonationListView;

    List<DonationDTO> mDonationList;
    DonationArrayAdapter mDonationArrayAdapter;

    List<ServiceDTO> mServiceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_list);

        mDonationListView = (ListView) findViewById(R.id.donation_list);
        mDonationList = new ArrayList<>();
        mDonationArrayAdapter = new DonationArrayAdapter(DonationListActivity.this, R.layout.layout_donation_list_item, mDonationList);
        mDonationListView.setAdapter(mDonationArrayAdapter);
        mDonationListView.setOnItemClickListener(mOnItemClickListener);

        mServiceList = new ArrayList<>();

        Button btn1 = (Button) findViewById(R.id.service_btn);
        btn1.setOnClickListener(mOnClickListener);
        Button btn2 = (Button) findViewById(R.id.donation_btn);
        btn2.setOnClickListener(mOnClickListener);
        Button btn3 = (Button) findViewById(R.id.mypage_btn);
        btn3.setOnClickListener(mOnClickListener);
        Button btn4 = (Button) findViewById(R.id.qr_btn);
        btn4.setOnClickListener(mOnClickListener);

        new GetServiceList().execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDonationArrayAdapter.clear();
        new GetDonationList().execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IntentIntegrator.REQUEST_CODE) {
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            int serviceId = Integer.parseInt(result.getContents());
            MySharedPreferences pref = new MySharedPreferences(DonationListActivity.this);
            String title = "";
            int point = 0;
            for (ServiceDTO item : mServiceList) {
                if (item.getId() == serviceId) {
                    title = item.getTitle();
                    point = item.getPointPerHour();
                }
            }
            boolean isMyService = false;
            try {
                isMyService = new IsMyService().execute(serviceId).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            if (isMyService) {
                if (pref.isServiceIng(serviceId)) {
                    int times = pref.getServiceTime();
                    String[] info = pref.getUserInfo();
                    new AddPoint().execute(Integer.parseInt(info[0]), point * times);
                    Toast.makeText(DonationListActivity.this, title + " 봉사활동을 종료합니다. " + (point * times) + " 적립되었습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    pref.startService(serviceId);
                    Toast.makeText(DonationListActivity.this, title + " 봉사활동을 시작합니다.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(DonationListActivity.this, "먼저 신청을 해주세요.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.service_btn:
                {
                    Intent intent = new Intent(DonationListActivity.this, ServiceListActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                }
                case R.id.donation_btn:
                {
                    break;
                }
                case R.id.mypage_btn:
                {
                    Intent intent = new Intent(DonationListActivity.this, MyInfoActivity.class);
                    startActivity(intent);
                    break;
                }
                case R.id.qr_btn:
                {
                    new IntentIntegrator(DonationListActivity.this).setCaptureActivity(QRCodeScanActivity.class).setOrientationLocked(false).initiateScan();
                    break;
                }
            }
        }
    };

    private AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            DonationDTO item = mDonationList.get(position);
            if (item.getState()) {
                Intent intent = new Intent(DonationListActivity.this, DonationDetailActivity.class);
                intent.putExtra("item", item);
                startActivity(intent);
            } else {
                Toast.makeText(DonationListActivity.this, "종료된 기부활동입니다.", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private class GetDonationList extends AsyncTask<Void, Integer, List<DonationDTO>> {

        @Override
        protected List<DonationDTO> doInBackground(Void... params) {
            DB db = new DB("donationList.php");
            String result = db.post(new String[0]);
            Log.e("!!!", result);
            try {
                JSONParser jsonParser = new JSONParser();
                JSONObject jsonObject = (JSONObject) jsonParser.parse(result);
                JSONArray jsonArray = (JSONArray) jsonObject.get("result");
                for (int i = 0; i < jsonArray.size(); i++) {
                    DonationDTO item = new DonationDTO();
                    JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                    item.setId(Integer.parseInt((String) jsonObject1.get("ID")));
                    item.setLocation((String) jsonObject1.get("Location"));
                    item.setTitle((String) jsonObject1.get("Title"));
                    item.setImageUrl((String) jsonObject1.get("IMG"));
                    item.setTotalPoint(Integer.parseInt((String) jsonObject1.get("point")));
                    item.setState((jsonObject1.get("State")).equals("1"));
                    mDonationList.add(item);
                    Log.e("!!!", "" + item.getId() + ", " + item.getLocation() + ", " + item.getTitle() + ", " + item.getImageUrl() + ", " + item.getTotalPoint() + ", " + item.getState());
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<DonationDTO> donationDTOs) {
            super.onPostExecute(donationDTOs);
            mDonationArrayAdapter.notifyDataSetChanged();
        }
    }

    private class AddPoint extends AsyncTask<Integer, Void, Void> {

        @Override
        protected Void doInBackground(Integer... params) {
            int id = params[0];
            int point = params[1];
            DB db = new DB("service_complete.php");
            String[] posts = {String.valueOf(id), String.valueOf(point)};
            db.post(posts);
            return null;
        }
    }

    private class IsMyService extends AsyncTask<Integer, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Integer... params) {
            int serviceId = params[0];
            MySharedPreferences pref = new MySharedPreferences(DonationListActivity.this);
            String[] info = pref.getUserInfo();
            DB db = new DB("isMyService.php");
            String[] posts = {info[0], String.valueOf(serviceId)};
            String result = db.post(posts);
            return result.equals("ok");
        }
    }

    private class GetServiceList extends AsyncTask<Void, Integer, List<ServiceDTO>> {

        @Override
        protected List<ServiceDTO> doInBackground(Void... params) {
            DB db = new DB("serviceList.php");
            String result = db.post(new String[0]);
            Log.e("!!!", result);
            try {
                JSONParser jsonParser = new JSONParser();
                JSONObject jsonObject = (JSONObject) jsonParser.parse(result);
                JSONArray jsonArray = (JSONArray) jsonObject.get("result");
                for (int i = 0; i < jsonArray.size(); i++) {
                    ServiceDTO item = new ServiceDTO();
                    JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                    item.setId(Integer.parseInt((String) jsonObject1.get("ID")));
                    item.setLocation((String) jsonObject1.get("Location"));
                    item.setTitle((String) jsonObject1.get("title"));
                    item.setImageUrl((String) jsonObject1.get("Img"));
                    item.setPointPerHour(Integer.parseInt((String) jsonObject1.get("Point")));
                    item.setmStete((jsonObject1.get("State")).equals("1"));
                    item.setDetail((String) jsonObject1.get("Description"));
                    mServiceList.add(item);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<ServiceDTO> list) {
            super.onPostExecute(list);
        }
    }
}
