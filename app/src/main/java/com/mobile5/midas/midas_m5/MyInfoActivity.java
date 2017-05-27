package com.mobile5.midas.midas_m5;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile5.midas.midas_m5.Adapter.MyListAdapter;
import com.mobile5.midas.midas_m5.DB.DB;
import com.mobile5.midas.midas_m5.dto.MyListDTO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class MyInfoActivity extends AppCompatActivity {
    TextView userName, companyNum, myPoint ;
    RecyclerView myServiceList;
    MyListAdapter myServiceListAdapter;
    LinearLayoutManager myServiceListManager;
    ArrayList<MyListDTO> myServiceListDTOs = new ArrayList<>();
    RecyclerView myDonationList;
    MyListAdapter myDonationListAdapter;
    LinearLayoutManager myDonationListManager;
    ArrayList<MyListDTO> myDonationListDTOs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        userName = (TextView) findViewById(R.id.userName);
        companyNum = (TextView) findViewById(R.id.companyNum);
        myPoint = (TextView) findViewById(R.id.myPoint);
        myServiceList = (RecyclerView) findViewById(R.id.myServiceList);
        myServiceListAdapter = new MyListAdapter(myServiceListDTOs,R.layout.mylist_item);
        myServiceListManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        myServiceList.setAdapter(myServiceListAdapter);
        myServiceList.setLayoutManager(myServiceListManager);
        myDonationList = (RecyclerView) findViewById(R.id.myDonationList);
        myDonationListAdapter = new MyListAdapter(myDonationListDTOs,R.layout.mylist_item);
        myDonationListManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        myDonationList.setAdapter(myDonationListAdapter);
        myDonationList.setLayoutManager(myDonationListManager);
        searchMyService("201701");
//        searchMyDonation("201701");
    }
    protected void showMyService(String jsonResult){
        try {
            String[] title = null;
            int[] point = null;
            String[] imgURL = null;
            int[] serviceID = null;
            String[] location = null;
            String[] description = null;
            boolean[] state = null;
            if (myServiceListDTOs != null)
                myServiceListDTOs.clear();
            JSONObject jsonObj = new JSONObject(jsonResult);
            JSONArray posts = jsonObj.getJSONArray("result");
            if(posts.length()>0) {
                title = new String[posts.length()];
                point = new int[posts.length()];
                imgURL = new String[posts.length()];
                serviceID = new int[posts.length()];
                location = new String[posts.length()];
                description = new String[posts.length()];
                state = new boolean[posts.length()];
                for (int i = 0; i < posts.length(); i++) {
                    JSONObject c = posts.getJSONObject(i);
                    title[i] = c.getString("Title");
                    point[i] = Integer.parseInt(c.getString("Point"));
                    imgURL[i] = c.getString("Img");
                    serviceID[i] = Integer.parseInt(c.getString("ServiceID"));
                    location[i] = c.getString("Location");
                    description[i] = c.getString("Description");
                    state[i] = Boolean.parseBoolean(c.getString("State"));
                    myServiceListDTOs.add(new MyListDTO(serviceID[i],title[i], point[i], imgURL[i], location[i], description[i], state[i]));
                }
            }
            myServiceListAdapter.notifyDataSetChanged();
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
    protected void showMyDonation(String jsonResult){
        try {
            String[] title = null;
            int[] point = null;
            String[] imgURL = null;
            int[] serviceID = null;
            String[] location = null;
            String[] description = null;
            boolean[] state = null;
            if (myServiceListDTOs != null)
                myServiceListDTOs.clear();
            JSONObject jsonObj = new JSONObject(jsonResult);
            JSONArray posts = jsonObj.getJSONArray("result");
            if(posts.length()>0) {
                title = new String[posts.length()];
                point = new int[posts.length()];
                imgURL = new String[posts.length()];
                serviceID = new int[posts.length()];
                location = new String[posts.length()];
                description = new String[posts.length()];
                state = new boolean[posts.length()];
                for (int i = 0; i < posts.length(); i++) {
                    JSONObject c = posts.getJSONObject(i);
                    title[i] = c.getString("Title");
                    point[i] = Integer.parseInt(c.getString("Point"));
                    imgURL[i] = c.getString("Img");
                    serviceID[i] = Integer.parseInt(c.getString("ServiceID"));
                    location[i] = c.getString("Location");
                    description[i] = c.getString("Description");
                    state[i] = Boolean.parseBoolean(c.getString("State"));
                    myServiceListDTOs.add(new MyListDTO(serviceID[i],title[i], point[i], imgURL[i], location[i], description[i], state[i]));
                }
            }
            myServiceListAdapter.notifyDataSetChanged();
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
    private void searchMyService(String id){

        class searchData extends AsyncTask<String, Void, String> {
            String id;
            /*WeakReference<Activity> mActivityReference;

            public searchData(Activity activity){
                this.mActivityReference = new WeakReference<Activity>(activity);

            }*/

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                showMyService(s);
            }
            @Override
            protected String doInBackground(String... params) {

                id = (String) params[0];
                String[] posts = {id};
                DB db = new DB("myServiceList.php");
                String result = db.post(posts);
                Log.d("MyInfoResult",result);
                return result;
            }
        }
        //searchData task = new searchData(Application.this);
        searchData task = new searchData();
        task.execute(id);
    }
    private void searchMyDonation(String id){

        class searchData extends AsyncTask<String, Void, String> {
            String id;
            /*WeakReference<Activity> mActivityReference;

            public searchData(Activity activity){
                this.mActivityReference = new WeakReference<Activity>(activity);

            }*/

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                showMyService(s);
            }
            @Override
            protected String doInBackground(String... params) {

                id = (String) params[0];
                String[] posts = {id};
                DB db = new DB("myDonationList.php");
                String result = db.post(posts);
                Log.d("MyDonationListResult",result);
                return result;
            }
        }
        //searchData task = new searchData(Application.this);
        searchData task = new searchData();
        task.execute(id);
    }
}
