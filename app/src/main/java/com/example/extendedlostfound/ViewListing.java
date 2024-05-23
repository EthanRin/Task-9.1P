package com.example.extendedlostfound;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ViewListing extends AppCompatActivity {

    TextView status, name, phone, desc, date, location;
    String _id, _status, _name, _phone, _desc, _date, _lat, _lng;
    Button remove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_listing);

        status = findViewById(R.id.status);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        desc = findViewById(R.id.desc);
        date = findViewById(R.id.date);
        location = findViewById(R.id.location);
        remove = findViewById(R.id.removeButton);
        getSetIntentDate();
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();
            }
        });
    }

    void getSetIntentDate(){
        if (getIntent().hasExtra("id") && getIntent().hasExtra("name")
                && getIntent().hasExtra("phone") && getIntent().hasExtra("desc")
                && getIntent().hasExtra("date") && getIntent().hasExtra("status")
                && getIntent().hasExtra("latitude") && getIntent().hasExtra("longitude")){
            _id = getIntent().getStringExtra("id");
            _status = getIntent().getStringExtra("status");
            _name = getIntent().getStringExtra("name");
            _phone = getIntent().getStringExtra("phone");
            _desc = getIntent().getStringExtra("desc");
            _date = getIntent().getStringExtra("date");
            _lat = getIntent().getStringExtra("latitude");
            _lng = getIntent().getStringExtra("longitude");

            name.setText(_name);
            phone.setText(_phone);
            desc.setText(_desc);
            status.setText(_status);
            location.setText("Lat: " + _lat + " Long: " + _lng);
            date.setText(_date);
        } else {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + _name + " ?");
        builder.setMessage("Are you sure you want to delete this advert ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyDatebase myDB = new MyDatebase(ViewListing.this);
                myDB.deleteRow(_id);
                setResult(RESULT_OK);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }
}