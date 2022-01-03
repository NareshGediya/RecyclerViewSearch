package com.statussaver.firstapp;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {

    TextView name, name2, email, dob, country, gender, hobbies;
    RadioButton r1, r2;
    CheckBox checkBox1, checkBox2, checkBox3, checkBox4;
    String c1,c2,c3,c4;
    ImageView camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        name = findViewById(R.id.textView);
        email = findViewById(R.id.tEmail);
        name2 = findViewById(R.id.tName);
        dob = findViewById(R.id.tDob);
        country = findViewById(R.id.tCountry);

        checkBox1 = findViewById(R.id.checkBox);
        checkBox2 = findViewById(R.id.checkBox2);
        checkBox3 = findViewById(R.id.checkBox3);
        checkBox4 = findViewById(R.id.checkBox4);

        r1 = findViewById(R.id.radio_button_1);
        r2 = findViewById(R.id.radio_button_2);


        name.setText("Hello, " + getIntent().getStringExtra("name"));
        name2.setText(getIntent().getStringExtra("name"));
        email.setText(getIntent().getStringExtra("email"));
        dob.setText(getIntent().getStringExtra("dob"));
        country.setText(getIntent().getStringExtra("country"));


        c1= getIntent().getStringExtra("check1");
        c2 = getIntent().getStringExtra("check2");
        c3= getIntent().getStringExtra("check3");
        c4= getIntent().getStringExtra("check4");

        if (!c1.isEmpty()){
            checkBox1.setChecked(true);
        }
        if (!c2.isEmpty()){
            checkBox2.setChecked(true);
        }
        if (!c3.isEmpty()){
            checkBox3.setChecked(true);
        }
        if (!c4.isEmpty()){
            checkBox4.setChecked(true);
        }

        if (getIntent().getStringExtra("gender").equals("Male")) {
            r1.setChecked(true);
            r2.setChecked(false);
        } else {
            r2.setChecked(true);
            r1.setChecked(false);
        }


    }
}