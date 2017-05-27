package com.mobile5.midas.midas_m5.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mobile5.midas.midas_m5.DonationDetailActivity;
import com.mobile5.midas.midas_m5.R;
import com.mobile5.midas.midas_m5.ServiceDetailActivity;
import com.mobile5.midas.midas_m5.Util.BitmapDownloaderTask;
import com.mobile5.midas.midas_m5.dto.DonationDTO;
import com.mobile5.midas.midas_m5.dto.MyDonationDTO;
import com.mobile5.midas.midas_m5.dto.MyListDTO;

import java.util.List;


/**
 * Created by wisebody on 2016. 8. 2..
 */
public class MyDonationListAdapter extends RecyclerView.Adapter<MyDonationListAdapter.ViewHolder> {

    private List<MyDonationDTO> items;
    private int itemLayout;
    Context context;
    ViewHolder viewHolder;



    public MyDonationListAdapter(List<MyDonationDTO> items, int itemLayout) {
        this.items = items;
        this.itemLayout = itemLayout;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        context = parent.getContext();
        viewHolder = new ViewHolder(v);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final MyDonationDTO item = items.get(position);
        holder.title.setText(item.getTitle());
        holder.point.setText(Integer.toString(item.getMyPoint())+"P");
//        double num = Double.parseDouble(item.getGold());
//        DecimalFormat df = new DecimalFormat("#,##0");
//        holder.gold.setText("Gold " + df.format(num).toString());
        holder.progressBar.setVisibility(View.VISIBLE);
        holder.bitmapDownloaderTask = new BitmapDownloaderTask(holder.thumb,holder.progressBar,context);
        holder.bitmapDownloaderTask.download("http://ec2-13-124-108-18.ap-northeast-2.compute.amazonaws.com/mobile5/"+item.getImageUrl(),holder.thumb);
        holder.thumb.setColorFilter(Color.parseColor("#88000000"));
        holder.thumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(context.getApplicationContext(), DonationDetailActivity.class);
                intent.putExtra("item",new DonationDTO(item.getId(), item.getTitle(), item.getLocation(), item.getImageUrl(), item.getTotalPoint(), item.getState()));
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return items.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView title, point;
        ImageView thumb;
        ProgressBar progressBar;
        BitmapDownloaderTask bitmapDownloaderTask;
        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            point = (TextView) itemView.findViewById(R.id.point);
            thumb = (ImageView) itemView.findViewById(R.id.thumb);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
        }
    }
}