package com.example.registration_app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;
import java.util.Objects;

public class FeePayment extends AppCompatActivity {

    Switch feeCheck;
    TextView displayText;
    Button save;
    DataBase dataBase;
    String status_Str;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fee_payment);

        dataBase = new DataBase(getApplicationContext());

        feeCheck = (Switch) findViewById(R.id.feeCheckId);
        displayText = (TextView) findViewById(R.id.feeTextId);
        save = (Button) findViewById(R.id.saveFeeId);

        id = getIntent().getLongExtra("User_ID", -1);

        boolean status = getIntent().getBooleanExtra("FeeStatus", false);
        feeCheck.setChecked(!status);
        status_Str = "PAID";
        if(status) {
            status_Str = "UNPAID";
        }

        displayText.setText(String.format("Current fee payment status is:\n\n%s", status_Str));

        feeCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    status_Str = "PAID";
                    displayText.setText(String.format("Current fee payment status is:\n\n%s", status_Str));
                } else {
                    status_Str = "UNPAID";
                    displayText.setText(String.format("Current fee payment status is:\n\n%s", status_Str));
                }
            }
        });
    }

    public void saveStatus(View v) {
        Map<String, String> x = dataBase.giveEveryThing(id);
        if(x.get("fee").equals("PAID") && status_Str.equals("UNPAID")) {
            //Toast.makeText(FeePayment.this, "Payment is Done, cannot be cancelled", Toast.LENGTH_LONG).show();
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage("Payment is Done, cannot be cancelled");
            dialog.setTitle("Warning");
            dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alertDialog = dialog.create();
            alertDialog.show();
            feeCheck.setChecked(true);
            return;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("fee", status_Str);
        dataBase.update(String.valueOf(id), contentValues);
        setResult(3);
        finish();
    }
}