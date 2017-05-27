package com.mobile5.midas.midas_m5;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.mobile5.midas.midas_m5.dto.DonationDTO;
import com.mobile5.midas.midas_m5.list.DonationArrayAdapter;

import java.util.List;

public class DonationListActivity extends AppCompatActivity {
    ListView mDonationListView;

    List<DonationDTO> mDonationList;
    DonationArrayAdapter mDonationArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_list);
    }
}
