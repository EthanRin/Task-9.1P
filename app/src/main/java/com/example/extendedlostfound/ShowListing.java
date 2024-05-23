package com.example.extendedlostfound;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;
import java.util.ArrayList;

public class ShowListing extends AppCompatActivity {

    RecyclerView adListing;
    MyDatebase myDB;
    ArrayList<String> ad_id, ad_status, ad_name, ad_phone, ad_desc, ad_date, ad_lat, ad_lng;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_listing);

        adListing = findViewById(R.id.advertListings);
        myDB = new MyDatebase(ShowListing.this);
        ad_id = new ArrayList<>();
        ad_status = new ArrayList<>();
        ad_name = new ArrayList<>();
        ad_phone = new ArrayList<>();
        ad_desc = new ArrayList<>();
        ad_date = new ArrayList<>();
        ad_lat = new ArrayList<>();
        ad_lng = new ArrayList<>();

        storeAndSortData();

        customAdapter = new CustomAdapter(this, ad_id, ad_status, ad_name, ad_phone, ad_desc, ad_date, ad_lat, ad_lng, ShowListing.this);
        adListing.setAdapter(customAdapter);
        adListing.setLayoutManager(new LinearLayoutManager(ShowListing.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            storeAndSortData();
            recreate();
        }
    }

    void storeAndSortData() {
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(ShowListing.this, "No data!", Toast.LENGTH_SHORT).show();
        } else {
            ArrayList<Advert> ads = new ArrayList<>();
            while (cursor.moveToNext()) {
                Advert ad = new Advert(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7)
                );
                ads.add(ad);
            }

            // Clear existing data
            ad_id.clear();
            ad_status.clear();
            ad_name.clear();
            ad_phone.clear();
            ad_desc.clear();
            ad_date.clear();
            ad_lat.clear();
            ad_lng.clear();

            // Populate sorted data into ArrayLists
            for (Advert ad : ads) {
                ad_id.add(ad.getId());
                ad_status.add(ad.getStatus());
                ad_name.add(ad.getName());
                ad_phone.add(ad.getPhone());
                ad_desc.add(ad.getDescription());
                ad_lat.add(ad.getLat());
                ad_lng.add(ad.getLng());
                ad_date.add(ad.getDate());
            }
        }
    }

    public class Advert {
        private String id;
        private String status;
        private String name;
        private String phone;
        private String description;
        private String date;
        private String lat;
        private String lng;

        public Advert(String id, String status, String name, String phone,
                      String description, String lat, String lng, String date) {
            this.id = id;
            this.status = status;
            this.name = name;
            this.phone = phone;
            this.description = description;
            this.date = date;
            this.lat = lat;
            this.lng = lng;
        }

        public String getId() {
            return id;
        }
        public String getStatus() {return status;}

        public String getName() {return name;}
        public String getPhone() { return phone;}
        public String getDescription() {
            return description;
        }
        public String getDate() {
            return date;
        }
        public String getLat() {return lat;}
        public String getLng() {return lng;}
    }
}