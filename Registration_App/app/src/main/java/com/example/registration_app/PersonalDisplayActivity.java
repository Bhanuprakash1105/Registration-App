package com.example.registration_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Map;

public class PersonalDisplayActivity extends AppCompatActivity {

    DataBase dataBase;
    long id;
    Map<String, String> details;
    ArrayAdapter <String> arrAdap;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_display);

        setTitle("USER INFORMATION");

        dataBase = new DataBase(getApplicationContext());
        id = getIntent().getLongExtra("User_ID", -1);
        lv = (ListView) findViewById(R.id.detailsListID);

        details = dataBase.givePersonalInfo(id);

        ArrayList<String> temp = new ArrayList<String>();
        temp.add("\tUser Name: " + details.get("name"));
        temp.add("\tUser email-ID: " + details.get("email"));
        temp.add("\tDate Of Birth: " + details.get("dob"));
        temp.add("\tGender: " + details.get("gender"));
        temp.add("\tContact Number: " + details.get("mobile"));

        arrAdap = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1 , temp);
        lv.setAdapter(arrAdap);
    }

    public void goBack(View v) {
        finish();
    }
}