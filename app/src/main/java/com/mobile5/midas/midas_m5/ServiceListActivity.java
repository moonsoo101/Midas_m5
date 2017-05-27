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
import com.mobile5.midas.midas_m5.dto.ServiceDTO;
import com.mobile5.midas.midas_m5.list.ServiceArrayAdapter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;

public class ServiceListActivity extends AppCompatActivity {
    ListView mServiceListView;

    List<ServiceDTO> mServiceList;
    ServiceArrayAdapter mServiceArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_list);

        mServiceListView = (ListView) findViewById(R.id.service_list);
        mServiceList = new ArrayList<>();
        mServiceArrayAdapter = new ServiceArrayAdapter(ServiceListActivity.this, R.layout.layout_service_list_item, mServiceList);
        mServiceListView.setAdapter(mServiceArrayAdapter);
        mServiceListView.setOnItemClickListener(mOnItemClickListener);

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IntentIntegrator.REQUEST_CODE) {
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            Toast.makeText(ServiceListActivity.this, result.getContents(), Toast.LENGTH_LONG).show();
        }
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.service_btn:
                {
                    break;
                }
                case R.id.donation_btn:
                {
                    Intent intent = new Intent(ServiceListActivity.this, DonationListActivity.class);
                    startActivity(intent);
                    break;
                }
                case R.id.mypage_btn:
                {
                    Intent intent = new Intent(ServiceListActivity.this, MyInfoActivity.class);
                    startActivity(intent);
                    break;
                }
                case R.id.qr_btn:
                {
                    new IntentIntegrator(ServiceListActivity.this).setCaptureActivity(QRCodeScanActivity.class).setOrientationLocked(false).initiateScan();
                    break;
                }
            }
        }
    };

    private AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ServiceDTO item = mServiceList.get(position);
            Intent intent = new Intent(ServiceListActivity.this, ServiceDetailActivity.class);
            intent.putExtra("item", item);
            startActivity(intent);
        }
    };

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
            mServiceArrayAdapter.notifyDataSetChanged();
        }
    }
}
