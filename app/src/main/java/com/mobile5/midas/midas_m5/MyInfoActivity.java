package com.mobile5.midas.midas_m5;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.mobile5.midas.midas_m5.Adapter.MyDonationListAdapter;
import com.mobile5.midas.midas_m5.Adapter.MyServiceListAdapter;
import com.mobile5.midas.midas_m5.DB.DB;
import com.mobile5.midas.midas_m5.DB.MySharedPreferences;
import com.mobile5.midas.midas_m5.dto.DonationDTO;
import com.mobile5.midas.midas_m5.dto.MyDonationDTO;
import com.mobile5.midas.midas_m5.dto.MyListDTO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyInfoActivity extends AppCompatActivity {
    TextView userName, companyNum, myPoint ;
    RecyclerView myServiceList;
    MyServiceListAdapter myServiceListAdapter;
    LinearLayoutManager myServiceListManager;
    ArrayList<MyListDTO> myServiceListDTOs = new ArrayList<>();
    RecyclerView myDonationList;
    MyDonationListAdapter myDonationListAdapter;
    LinearLayoutManager myDonationListManager;
    ArrayList<MyDonationDTO> myDonationListDTOs = new ArrayList<>();

    String id;
    String pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        userName = (TextView) findViewById(R.id.userName);
        companyNum = (TextView) findViewById(R.id.companyNum);
        myPoint = (TextView) findViewById(R.id.myPoint);
        myServiceList = (RecyclerView) findViewById(R.id.myServiceList);
        myServiceListAdapter = new MyServiceListAdapter(myServiceListDTOs,R.layout.mylist_item);
        myServiceListManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        myServiceList.setAdapter(myServiceListAdapter);
        myServiceList.setLayoutManager(myServiceListManager);
        myDonationList = (RecyclerView) findViewById(R.id.myDonationList);
        myDonationListAdapter = new MyDonationListAdapter(myDonationListDTOs,R.layout.mylist_item);
        myDonationListManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        myDonationList.setAdapter(myDonationListAdapter);
        myDonationList.setLayoutManager(myDonationListManager);
        MySharedPreferences pref = new MySharedPreferences(this);
        String[] array = pref.getUserInfo();
        id = array[0];
        pass = array[1];
        searchMyService(id);
        searchMyDonation(id);
        searchMyInfo(id, pass);
    }
    protected void showMyInfo(String jsonResult){
        try {
            String name = null;
            String point= null;
            JSONObject jsonObj = new JSONObject(jsonResult);
            JSONArray posts = jsonObj.getJSONArray("result");

                for (int i = 0; i < posts.length(); i++) {
                    JSONObject c = posts.getJSONObject(i);
                    name = c.getString("Name");
                    point = c.getString("Point");
                }
                userName.setText(name);
                companyNum.setText(id);
            myPoint.setText(point);
            }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
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
                    Log.d("list",serviceID[i]+title[i]+point[i]+imgURL[i]+location[i]+description[i]+state[i]);
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
            int[] donationID = null;
            String[] location = null;
            boolean[] state = null;
            int[] acc_Point = null;
            if (myDonationListDTOs != null)
                myDonationListDTOs.clear();
            JSONObject jsonObj = new JSONObject(jsonResult);
            JSONArray posts = jsonObj.getJSONArray("result");
            if(posts.length()>0) {
                title = new String[posts.length()];
                point = new int[posts.length()];
                imgURL = new String[posts.length()];
                donationID = new int[posts.length()];
                location = new String[posts.length()];
                state = new boolean[posts.length()];
                acc_Point = new int[posts.length()];
                for (int i = 0; i < posts.length(); i++) {
                    JSONObject c = posts.getJSONObject(i);
                    title[i] = c.getString("Title");
                    point[i] = Integer.parseInt(c.getString("Point"));
                    imgURL[i] = c.getString("Img");
                    donationID[i] = Integer.parseInt(c.getString("DonationID"));
                    location[i] = c.getString("Location");
                    state[i] = Boolean.parseBoolean(c.getString("State"));
                    acc_Point[i] = Integer.parseInt(c.getString("acc_Point"));
                    myDonationListDTOs.add(new MyDonationDTO(donationID[i],title[i], location[i], imgURL[i], acc_Point[i], point[i], state[i]));
                }
            }
            myDonationListAdapter.notifyDataSetChanged();
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
                showMyDonation(s);
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
    private void searchMyInfo(String id , String pass){

        class searchData extends AsyncTask<String, Void, String> {
            String id;
            String pass;
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
                showMyInfo(s);
            }
            @Override
            protected String doInBackground(String... params) {

                id = (String) params[0];
                pass = (String) params[1];
                String[] posts = {id, pass};
                DB db = new DB("getMyInfo.php");
                String result = db.post(posts);
                Log.d("MyDonationListResult",result);
                return result;
            }
        }
        //searchData task = new searchData(Application.this);
        searchData task = new searchData();
        task.execute(id, pass);
    }

}
