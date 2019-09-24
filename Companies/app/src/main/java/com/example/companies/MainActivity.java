package com.example.companies;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper db;
    Button addButton;
    Button viewButton;
    EditText emailText;
    EditText locationText;
    EditText phoneText;
    EditText linkText;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailText = findViewById(R.id.emailTextEdit);
        locationText = findViewById(R.id.locationTextEdit);
        phoneText = findViewById(R.id.phoneTextEdit);
        linkText = findViewById(R.id.linkTextEdit);
        addButton = findViewById(R.id.sendButton);
        viewButton = findViewById(R.id.viewButton);

        db = new DatabaseHelper(this);

        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ViewListContent.class);
                startActivity(intent);
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(emailText.getText().length() != 0 && locationText.getText().length() != 0 && phoneText.getText().length() != 0 && linkText.getText().length() != 0){
                    addData(getCompany(emailText.getText().toString(),
                            locationText.getText().toString(),
                            phoneText.getText().toString(),
                            linkText.getText().toString()));
                    emailText.setText("");
                    locationText.setText("");
                    phoneText.setText("");
                    linkText.setText("");
                }else{
                    Toast.makeText(getApplicationContext(), "Fields shouldn't be empty!!!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public CompanyModel getCompany(String email, String location, String phone, String socialnetworklink){
        CompanyModel companyModel = new CompanyModel(
                email,
                location,
                phone,
                socialnetworklink
        );
        return companyModel;
    }

    public void addData(CompanyModel model){
        boolean insertData = db.addData(model.email.trim(), model.location.trim(), model.phone.trim(), model.socialNetworkLink.trim());
        if(insertData){
            Toast.makeText(getApplicationContext(), "Successfully entered", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(), "Failed!", Toast.LENGTH_SHORT).show();
        }
    }
}
