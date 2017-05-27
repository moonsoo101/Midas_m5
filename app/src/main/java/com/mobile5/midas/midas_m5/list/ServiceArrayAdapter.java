package com.mobile5.midas.midas_m5.list;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobile5.midas.midas_m5.R;
import com.mobile5.midas.midas_m5.dto.ServiceDTO;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ServiceArrayAdapter extends ArrayAdapter<ServiceDTO> {
    private Context mContext;
    private int mResId;
    private List<ServiceDTO> mItemList;

    public ServiceArrayAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<ServiceDTO> objects) {
        super(context, resource, objects);
        mContext = context;
        mResId = resource;
        mItemList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = li.inflate(mResId,null);
        }
        ServiceDTO item = getItem(position);
        ImageView image = (ImageView) v.findViewById(R.id.service_image);
        TextView title = (TextView) v.findViewById(R.id.service_title);
        TextView location = (TextView) v.findViewById(R.id.service_location);
        TextView point = (TextView) v.findViewById(R.id.service_point);
        TextView state = (TextView) v.findViewById(R.id.service_state);
        TextView detail = (TextView) v.findViewById(R.id.service_detail);
        if (item != null) {
            Picasso.with(mContext).load("http://ec2-13-124-108-18.ap-northeast-2.compute.amazonaws.com/mobile5/" + item.getImageUrl()).into(image);
            image.setColorFilter(Color.parseColor("#77000000"));
            title.setText(item.getTitle());
            location.setText(item.getLocation());
            point.setText(String.valueOf(item.getPointPerHour()));
            state.setText(item.getState() ? "진행중" : "종료");
            detail.setText(item.getDetail());
        }
        return v;
    }
}
