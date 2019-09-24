package com.example.calendar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String FILE_NAME = "reminders.json";
    EditText editText;
    TextView textView;
    List<EventModel> modelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        modelList = ReadEventList();

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        CalendarView calendarView = (CalendarView) findViewById(R.id.MainCalendarView);
        textView = (TextView) findViewById(R.id.DateTextView);
        editText = findViewById(R.id.editText);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                String text = "";
                String dateValue = String.valueOf(dayOfMonth) + "/" + String.valueOf(month + 1) + "/" + String.valueOf(year);
                textView.setText(dateValue);
                for (int i = 0; i < modelList.size(); i++) {
                    EventModel model = modelList.get(i);

                    if (model.dateEvent.equals(dateValue)) {
                        textView.setText(model.dateEvent);
                        editText.setText(model.event);
                    }
                    else
                    {
                        editText.getText().clear();
                    }
                }
            }
        });
    }

    public void delete(View v){
        for (int i = 0; i < modelList.size(); i++) {
            EventModel model = modelList.get(i);

            if (model.dateEvent.equals(textView.getText().toString())) {
                modelList.remove(i);
            }
            else
            {
                editText.getText().clear();
            }
        }

        SaveEventList(modelList);
    }

    public void save(View v) {
        EventModel newEvent = new EventModel(
                textView.getText().toString(),
                editText.getText().toString()
        );

        modelList.add(newEvent);

        SaveEventList(modelList);
        editText.getText().clear();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private List<EventModel> ReadEventList() {
        Gson gson = new Gson();
        String filePath = GetJsonFilePath();
        String json = ReadFile(filePath);

        if (json == null) {
            return new ArrayList<EventModel>();
        }

        Type collectionType = new TypeToken<List<EventModel>>(){}.getType();
        return gson.fromJson(json, collectionType);
    }

    private void SaveEventList(List<EventModel> eventList) {
        Gson gson = new Gson();
        String filePath = GetJsonFilePath();
        String json = gson.toJson(eventList);

        SaveFile(filePath, json);
    }

    private void SaveFile(String path, String text) {
        try {
            File myFile = new File(path);
            FileOutputStream fileOutputStream = new FileOutputStream(myFile, false);
            fileOutputStream.write(text.getBytes());
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private String ReadFile(String filePath) {
        String text = null;

        try {
            File file = new File(filePath);
            if (file.exists()) {
                StringBuilder stringBuilder = new StringBuilder();
                InputStream inputStream = new FileInputStream(file);
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                while ((text = bufferedReader.readLine()) != null) {
                    stringBuilder.append(text);
                }
                return stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        return text;
    }

    private String GetJsonFilePath() {
        return MessageFormat.format("{0}/{1}", getApplicationContext().getFilesDir(), FILE_NAME);
    }
}
