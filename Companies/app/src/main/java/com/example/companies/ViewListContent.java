package com.example.companies;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ViewListContent extends AppCompatActivity {

    DatabaseHelper db;
    ListView listView;

    protected  void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);
        List<CompanyModel> rowItems;

        listView = findViewById(R.id.listViewInfo);
        db = new DatabaseHelper(this);

        ArrayList<String> theList = new ArrayList<>();
        Cursor data = db.getListContents();
        if (data.getCount() == 0){
            Toast.makeText(getApplicationContext(), "No info", Toast.LENGTH_SHORT).show();
        }
        else{
            while(data.moveToNext()){
                theList.add(data.getString(0));
                ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, theList);
                listView.setAdapter(listAdapter);
            }
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String s = listView.getItemAtPosition(i).toString();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }
        });
    }
}
