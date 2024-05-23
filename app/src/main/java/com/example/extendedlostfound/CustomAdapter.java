package com.example.extendedlostfound;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.extendedlostfound.R;
import com.example.extendedlostfound.ViewListing;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    Context context;
    ArrayList ad_id, ad_status, ad_name, ad_phone, ad_desc, ad_date, ad_lat, ad_lng;
    Activity activity;

    CustomAdapter(Context context,
                  ArrayList ad_id,
                  ArrayList ad_status,
                  ArrayList ad_name,
                  ArrayList ad_phone,
                  ArrayList ad_desc,
                  ArrayList ad_date,
                  ArrayList ad_lat,
                  ArrayList ad_lng,
                  Activity activity) {
        this.activity = activity;
        this.context = context;
        this.ad_name = ad_name;
        this.ad_id = ad_id;
        this.ad_status = ad_status;
        this.ad_phone = ad_phone;
        this.ad_desc = ad_desc;
        this.ad_date = ad_date;
        this.ad_lat = ad_lat;
        this.ad_lng = ad_lng;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.view_row, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.adStatus.setText(String.valueOf(ad_status.get(position)));
        holder.adName.setText(String.valueOf(ad_name.get(position)));
        holder.adDate.setText(String.valueOf(ad_date.get(position)));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewListing.class);
                intent.putExtra("id", String.valueOf(ad_id.get(holder.getAdapterPosition())));
                intent.putExtra("status", String.valueOf(ad_status.get(holder.getAdapterPosition())));
                intent.putExtra("name", String.valueOf(ad_name.get(holder.getAdapterPosition())));
                intent.putExtra("phone", String.valueOf(ad_phone.get(holder.getAdapterPosition())));
                intent.putExtra("date", String.valueOf(ad_date.get(holder.getAdapterPosition())));
                intent.putExtra("desc", String.valueOf(ad_desc.get(holder.getAdapterPosition())));
                intent.putExtra("latitude", String.valueOf(ad_lat.get(holder.getAdapterPosition())));
                intent.putExtra("longitude", String.valueOf(ad_lng.get(holder.getAdapterPosition())));
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ad_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView adStatus, adName, adDate;
        ConstraintLayout mainLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            adStatus = itemView.findViewById(R.id.ad_status);
            adName = itemView.findViewById(R.id.ad_name);
            adDate = itemView.findViewById(R.id.ad_date);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
