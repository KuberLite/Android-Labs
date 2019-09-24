package com.example.companies;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.telecom.Call;
import android.telecom.InCallService;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class ViewListContent extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1;
    DatabaseHelper db;
    ListView listView;
    List<CompanyModel> rowItems;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);
        List<CompanyModel> rowItems;

        listView = findViewById(R.id.listViewInfo);
        db = new DatabaseHelper(this);
        Cursor data = db.getListContents();
        if (data.getCount() == 0) {
            Toast.makeText(getApplicationContext(), "No info", Toast.LENGTH_SHORT).show();
        } else {
            while (data.moveToNext()) {
                rowItems = new ArrayList<>();
                CompanyModel item = new CompanyModel(data.getString(0), data.getString(1), data.getString(2), data.getString(3));
                rowItems.add(item);
                listView = findViewById(R.id.listViewInfo);
                CustomListViewAdapter adapter = new CustomListViewAdapter(this,
                        R.layout.singleview, rowItems);
                listView.setAdapter(adapter);
            }
        }
    }

    private void makeCall(){
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel: +1245124625"));
        if (checkSelfPermission(android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            requestPermission();
        }
        else{
            startActivity(intent);
        }
    }

    public void phoneClick(View view) {
        Toast.makeText(this, "Clickable", Toast.LENGTH_SHORT);
        //makeCall();
    }

    private void requestPermission()
    {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.CALL_PHONE))
        {
        }
        else {
            ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    makeCall();
                }
                break;
        }
    }
}
