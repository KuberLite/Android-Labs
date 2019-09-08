package com.example.grahovskiy;

import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        final Button button = findViewById(R.id.CheckButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView result = findViewById(R.id.ResultTextView);
                result.setText(null);
                RadioGroup rg = findViewById(R.id.GenderRadioGroup);
                EditText ageText = findViewById(R.id.AgeEditText);
                EditText growthText = findViewById(R.id.GrowthEditText);
                EditText weightText = findViewById(R.id.WeightEditText);
                Spinner activitySpinner = findViewById(R.id.ActivitySpinner);
                switch(rg.getCheckedRadioButtonId()){
                    case R.id.ManRadioButton:{
                        result.setText(Double.toString(HBCalc(BMRCalcForMan(Integer.parseInt(weightText.getText().toString()),
                                                                            Integer.parseInt(growthText.getText().toString()),
                                                                            Integer.parseInt(ageText.getText().toString())),
                                                              SetActivity(activitySpinner))));
                        break;
                    }
                    case R.id.WomanRadioButton:{
                        result.setText(Double.toString(HBCalc(BMRCalcForWoman(Integer.parseInt(weightText.getText().toString()),
                                Integer.parseInt(growthText.getText().toString()),
                                Integer.parseInt(ageText.getText().toString())),
                                SetActivity(activitySpinner))));
                        break;
                    }
                }
            }
        });
    }

    public double SetActivity(Spinner spinner){
        switch (spinner.getSelectedItem().toString()) {
            case "Passive lifestyle":{
                return 1.2;
            }
            case "Half-average activity":{
                return  1.375;
            }
            case "Average":{
                return 1.55;
            }
            case "Active":{
                return 1.725;
            }
            case "Sportsman":{
                return 1.9;
            }
            default: return 0;
        }
    }

    public double BMRCalcForMan(int weight, int growth, int age){
        double BMR = 655.0955 + (9.5634 * weight) + (1.8496 * growth) - (4.6756 * age);
        return BMR;
    }

    public double BMRCalcForWoman(int weight, int growth, int age){
        double BMR = 66.4730 + (13.7516 * weight) + (5.0033 * growth) - (6.7550 * age);
        return BMR;
    }

    public  double HBCalc(double BMR, double AMR){
        double HB = BMR * AMR;
        return  (double) Math.round(HB * 100) / 100;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
