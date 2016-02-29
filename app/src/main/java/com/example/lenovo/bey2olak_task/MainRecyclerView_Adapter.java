package com.example.lenovo.bey2olak_task;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LENOVO on 28/02/2016.
 */
public class MainRecyclerView_Adapter extends RecyclerView.Adapter<MainRecyclerView_Adapter.CustomViewHolder> {
    private List<POI_Data> poi_dataArrayList;
    private Context mContext;
    DatabaseHandler db;

    @Override
    public MainRecyclerView_Adapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_main, null);

        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    public MainRecyclerView_Adapter(Context context, List<POI_Data> poi_dataArrayList) {
        this.mContext = context;
        this.poi_dataArrayList = poi_dataArrayList;
        db = new DatabaseHandler(context);

    }

    @Override
    public void onBindViewHolder(MainRecyclerView_Adapter.CustomViewHolder holder, int position) {

        for (POI_Data poi_data : poi_dataArrayList) {
            String name = poi_data.getName();
            holder.textView.setText(name);

        }
    }

    @Override
    public int getItemCount() {
        return poi_dataArrayList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected RelativeLayout container;
        protected TextView textView;

        public CustomViewHolder(View view) {
            super(view);
            this.textView = (TextView) view.findViewById(R.id.title_tv);
            this.container = (RelativeLayout) view.findViewById(R.id.main_container);

        }
    }
}
