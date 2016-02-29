package com.example.lenovo.bey2olak_task;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LENOVO on 29/02/2016.
 */
public class POI_RecyclerViewAdapter extends RecyclerView.Adapter<POI_RecyclerViewAdapter.CustomViewHolder> {

    private ArrayList<POI_Data> poi_dataArrayList;
    private Context mContext;
    POI_Data poi_data;

    public POI_RecyclerViewAdapter(Context context, ArrayList<POI_Data> poi_dataArrayList) {
        this.poi_dataArrayList = poi_dataArrayList;
        this.mContext = context;

    }

    @Override
    public POI_RecyclerViewAdapter.CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_pois, null);

        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(POI_RecyclerViewAdapter.CustomViewHolder holder, final int position) {
        poi_data = poi_dataArrayList.get(position);

        String imgUrl = poi_data.getImg();
//        //Download image using picasso library
        Picasso.with(mContext).load(imgUrl)
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(holder.thumbnail);

        holder.name.setText(poi_data.getName());
        holder.address.setText(poi_data.getAddress());
        holder.checkBox.setChecked(poi_dataArrayList.get(position).isSelected());

        holder.checkBox.setTag(poi_dataArrayList.get(position));


        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                POI_Data contact = (POI_Data) cb.getTag();

                contact.setIsSelected(cb.isChecked());
                poi_dataArrayList.get(position).setIsSelected(cb.isChecked());

                Toast.makeText(
                        v.getContext(),
                        "Clicked on Checkbox: " + cb.getText() + " is "
                                + cb.isChecked(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return poi_dataArrayList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected RelativeLayout container;
        protected TextView name, address;
        protected ImageView thumbnail;
        protected CheckBox checkBox;


        public CustomViewHolder(View view) {
            super(view);
            this.checkBox = (CheckBox) view.findViewById(R.id.poi_checkbox);
            this.name = (TextView) view.findViewById(R.id.poi_name_tv);
            this.address = (TextView) view.findViewById(R.id.address_tv);
            this.thumbnail = (ImageView) view.findViewById(R.id.poi_thumbnail);
            this.container = (RelativeLayout) view.findViewById(R.id.poi_container);

        }
    }

    public List<POI_Data> getlist() {
        return poi_dataArrayList;
    }
}
