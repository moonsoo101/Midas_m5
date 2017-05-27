package com.mobile5.midas.midas_m5.list;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.mobile5.midas.midas_m5.dto.DonationDTO;

import java.util.ArrayList;
import java.util.List;

public class DonationArrayAdapter extends ArrayAdapter<DonationDTO> {
    private Context mContext;
    private int mResId;
    private List<DonationDTO> mItemList;

    public DonationArrayAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<DonationDTO> objects) {
        super(context, resource, objects);
        mContext = context;
        mResId = resource;
        mItemList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }
}
