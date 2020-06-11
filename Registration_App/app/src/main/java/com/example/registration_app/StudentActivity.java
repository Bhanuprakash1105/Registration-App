package com.example.registration_app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;
import java.util.Objects;

public class StudentActivity extends AppCompatActivity {

    long id;
    Intent intent;
    Button course, fee, personal;
    TextView status;
    DataBase dataBase;
    String cr, fp, pinf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        id = getIntent().getLongExtra("User_ID", -1);

        dataBase = new DataBase(getApplicationContext());

        status = (TextView) findViewById(R.id.statusTextId);
        course = (Button) findViewById(R.id.courseButtonId);
        fee = (Button) findViewById(R.id.feeButtonId);
        personal = (Button) findViewById(R.id.personalButtonId);

        executeFunc();
    }

    public void registerFunc(View v) {
        Map<String, String> tmp = dataBase.giveEveryThing(id);
        if(tmp.get("fee").equals("UNPAID")) {
            Toast.makeText(getApplicationContext(), "Course Registration cannot be done without Fee payment", Toast.LENGTH_LONG).show();
            return;
        }
        if(cr.equals("Pending")) {
            intent = new Intent(StudentActivity.this, CourseRegisterActivity.class);
            intent.putExtra("User_ID", id);
            startActivityForResult(intent, 0);
        } else {
            Toast.makeText(getApplicationContext(), "Course Registration is completed, Courses cannot be changed", Toast.LENGTH_LONG).show();
        }
    }

    public void logOutFunc(View v) {
        finish();
    }

    public void personalDisplayFunc(View v) {
        intent = new Intent(StudentActivity.this, PersonalDisplayActivity.class);
        intent.putExtra("User_ID", id);
        startActivity(intent);
    }

    public void feesPay(View v) {
        if(executeFunc()) {
            intent = new Intent(StudentActivity.this, FeePayment.class);
            intent.putExtra("FeeStatus", fp.equals("Pending"));
            intent.putExtra("User_ID", id);
            startActivityForResult(intent, 2);
        }
    }

    public boolean executeFunc() {
        cr = "Pending"; fp = "Pending"; pinf = "Completed";
        String temp = dataBase.checkFee(id);
        if(temp.equals("PAID")) {
            fp = "Completed";
        }
        temp = dataBase.checkCourseReg(id);
        if(!temp.isEmpty()) {
            cr = "Completed";
        }
        status.setText(String.format("\n%s  >>  Course Registration\n\n%s  >>  Fee Payment\n\n%s  >>  Personal Information", cr, fp, pinf));
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        executeFunc();
    }
}
