package com.example.registration_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class PersonalActivity extends AppCompatActivity {

    long id;

    DataBase dataBase;

    String name, dob_string, gender, mobileNum;

    EditText inObj;
    TextView dob;

    Calendar myCalendar;
    DatePickerDialog datePickerDialog;
    DatePickerDialog.OnDateSetListener date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        id = getIntent().getLongExtra("User_ID", -1);

        Button pobj = (Button) findViewById(R.id.proceed_Id);
        Button cobj = (Button) findViewById(R.id.cancel_Id);

        final RadioButton r1obj, r2obj;
        r1obj = (RadioButton) findViewById(R.id.male_Id);
        r2obj = (RadioButton) findViewById(R.id.female_Id);
        r1obj.setChecked(true);
        gender = "Male";
        r1obj.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(r1obj.isChecked()) {
                    r2obj.setChecked(false);
                    gender = "Male";
                }
            }
        });

        r2obj.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(r2obj.isChecked()) {
                    r1obj.setChecked(false);
                    gender = "Female";
                }
            }
        });

        myCalendar = Calendar.getInstance();
        date = (view, year, month, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String myFormat = "dd/MM/yyyy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
            dob.setText(sdf.format(myCalendar.getTime()));
        };

        datePickerDialog = new DatePickerDialog(PersonalActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));

        myCalendar.add(Calendar.YEAR,-24);
        datePickerDialog.getDatePicker().setMinDate(myCalendar.getTimeInMillis());
        myCalendar.add(Calendar.YEAR,7);
        datePickerDialog.getDatePicker().setMaxDate(myCalendar.getTimeInMillis());

        dob = (TextView) findViewById(R.id.dob_Id2);

        dob.setOnClickListener(x -> datePickerDialog.show());
    }

    public void proceedFunc(View v) {

        inObj = (EditText) findViewById(R.id.name_Id2);
        if(inObj.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Null input error in name entry, please enter name", Toast.LENGTH_LONG).show();
            return;
        } else {
            name = inObj.getText().toString();
        }

        inObj = (EditText) findViewById(R.id.mnum_Id2);
        if(inObj.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Null input error in Mobile number, please enter 10 digit Number", Toast.LENGTH_LONG).show();
            return;
        } else if(inObj.getText().toString().length() != 10) {
            Toast.makeText(getApplicationContext(), "Mobile Number should be of 10 digit length", Toast.LENGTH_LONG).show();
            return;
        } else {
            mobileNum = inObj.getText().toString();
        }

        dob = (TextView) findViewById(R.id.dob_Id2);
        dob_string = dob.getText().toString();
        if(dob_string.equals("")) {
            Toast.makeText(getApplicationContext(), "Null input error in DOB, please pick a valid date", Toast.LENGTH_LONG).show();
            return;
        }

        dataBase = new DataBase(getApplicationContext());
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("gender", gender);
        contentValues.put("dob", dob_string);
        contentValues.put("mobile", mobileNum);
        dataBase.update(String.valueOf(id), contentValues);

        setResult(200);
        finish();
    }

    public void cancelFunc(View v) {
        setResult(150);
        finish();
    }
}
