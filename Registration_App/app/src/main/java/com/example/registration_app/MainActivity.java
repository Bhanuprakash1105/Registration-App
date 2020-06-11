package com.example.registration_app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    boolean studentORnot;
    EditText emailObj, passwordObj;
    Button logObj;
    RadioButton sobj, fobj;
    long id;
    public String email, password;
    DataBase dataBase;

    Intent firstTime;

    ArrayList <Object> infoListStu, infoListFac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sobj = (RadioButton) findViewById(R.id.student_id);
        fobj = (RadioButton) findViewById(R.id.faculty_id);

        dataBase = new DataBase(getApplicationContext());

        sobj.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(sobj.isChecked()) {
                    fobj.setChecked(false);
                }
            }
        });

        fobj.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(fobj.isChecked()) {
                    sobj.setChecked(false);
                }
            }
        });
    }

    boolean verifyDetails(String e, String p, int x) {
        Pattern regPatternEmail = Pattern.compile("(^[a-z0-9+._-]+)@([a-z0-9+._-]+)[.]([a-z0-9]+)");
        Matcher m = regPatternEmail.matcher(e);
        if(p.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password Length >= 6", Toast.LENGTH_LONG).show();
            return false;
        }
        if(!(m.find() && (p.length() >= 6))) {
            Toast.makeText(getApplicationContext(), "Invalid Email Type, Please enter valid email and password", Toast.LENGTH_LONG).show();
            return false;
        }
        if(x == 1) {
            infoListStu = dataBase.checkExistsStu("email", e);
            id = (long) infoListStu.get(0);
            if(id != -1) {
                //Log.e("Password Check", p + " == " + infoListStu.get(1));
                if(!p.equals(infoListStu.get(1))) {
                    Toast.makeText(getApplicationContext(), "Incorrect Password, Login Failed", Toast.LENGTH_LONG).show();
                    return false;
                }
            } else {
                ContentValues stuEmailPass = new ContentValues();
                stuEmailPass.put("email", e);
                stuEmailPass.put("password", p);
                id = dataBase.insertStudent(stuEmailPass);
                firstTime = new Intent(MainActivity.this, PersonalActivity.class);
                firstTime.putExtra("User_ID", id);
                startActivityForResult(firstTime, 100);
                return false;

            }
        } else if(x == 2) {
            if(!dataBase.eligibleForFac(e)) {
                Toast.makeText(getApplicationContext(), "Email-ID is used by student, provide another email", Toast.LENGTH_LONG).show();
                return false;
            }
            infoListFac = dataBase.checkExistsFac("email", e);
            id = (long) infoListFac.get(0);
            if(id != -1) {
                if(!p.equals(infoListFac.get(1))) {
                    Toast.makeText(getApplicationContext(), "Incorrect Password, Login Failed", Toast.LENGTH_LONG).show();
                    return false;
                }
            } else {
                ContentValues facEmailPass = new ContentValues();
                facEmailPass.put("email", e);
                facEmailPass.put("password", p);
                id = dataBase.insertFaculty(facEmailPass);
                return id != -1;
            }
        }
        return true;
    }

    public void check_Proceed(View v) {
        emailObj = (EditText) findViewById(R.id.email_id);
        passwordObj = (EditText) findViewById(R.id.password_id);

        email = emailObj.getText().toString();
        password = passwordObj.getText().toString();

        int i = 0;
        if(sobj.isChecked()) { i = 1; } else if(fobj.isChecked()) { i = 2; } else {
            Toast.makeText(getApplicationContext(), "Login Failed, Select a category Student or Faculty", Toast.LENGTH_LONG).show();
            return;
        }

        if(verifyDetails(email, password, i)) {
            Intent intentObj;
            if(i == 1) {
                studentORnot = true;
                intentObj = new Intent(MainActivity.this, StudentActivity.class);
            } else {
                studentORnot = false;
                intentObj = new Intent(MainActivity.this, FacultyActivity.class);
            }
            intentObj.putExtra("User_ID", id);
            startActivity(intentObj);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100) {
            if(resultCode == 200) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setMessage("Signing up is Done\n\nPlease Login again to verify");
                dialog.setTitle("Verification");
                dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = dialog.create();
                alertDialog.show();
            } else {
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setMessage("Signing up is Cancelled");
                dialog.setTitle("Verification");
                dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = dialog.create();
                alertDialog.show();
                dataBase.deleteStu(email);
            }
        }
    }
}
