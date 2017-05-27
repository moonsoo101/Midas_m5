package com.mobile5.midas.midas_m5;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.mobile5.midas.midas_m5.DB.DB;
import com.mobile5.midas.midas_m5.dto.ServiceDTO;
import com.mobile5.midas.midas_m5.list.ServiceArrayAdapter;

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

        new GetServiceList().execute();
    }

    private class GetServiceList extends AsyncTask<String, Integer, List<ServiceDTO>> {

        @Override
        protected List<ServiceDTO> doInBackground(String... params) {
            DB db = new DB("serviceList.php");
            String result = db.post(new String[0]);
            Log.e("!!!", "doInBackground");
            Log.e("!!!", result);
            return null;
        }

        @Override
        protected void onPostExecute(List<ServiceDTO> list) {
            super.onPostExecute(list);
        }
    }
}
