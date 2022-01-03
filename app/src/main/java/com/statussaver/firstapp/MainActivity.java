package com.statussaver.firstapp;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    EditText eName, eEmail, ePass;
    String dob = "";
    TextView date, filePath;
    RadioGroup radioGroup;
    CheckBox checkBox1, checkBox2, checkBox3, checkBox4;
    String geneder = "";
    int currentyear, month, day;
    Calendar calendar;
    RadioButton r1, r2;

    String hoobies;
    String country;
    String email, emailPattern;
    ImageView camera, profileImage;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_EXTERNAL_STORAGE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calendar = Calendar.getInstance();

        eName = findViewById(R.id.eName);
        ePass = findViewById(R.id.ePass);
        eEmail = findViewById(R.id.eEmail);
        date = findViewById(R.id.dob);
        r1 = findViewById(R.id.radio_button_1);
        r2 = findViewById(R.id.radio_button_2);
        camera= findViewById(R.id.camera);
        profileImage= findViewById(R.id.profile_image);
        filePath= findViewById(R.id.filePath);

        checkBox1 = findViewById(R.id.checkBox);
        checkBox2 = findViewById(R.id.checkBox2);
        checkBox3 = findViewById(R.id.checkBox3);
        checkBox4 = findViewById(R.id.checkBox4);

        currentyear = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        radioGroup = findViewById(R.id.radioGroup);

//        Toast.makeText(this, ""+month+" "+ day, Toast.LENGTH_SHORT).show();


        email = eEmail.getText().toString().trim();

        emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

//        eEmail.addTextChangedListener(new TextWatcher() {
//            public void afterTextChanged(Editable s) {
//
//                if (email.matches(emailPattern) && s.length() > 0)
//                {
//                    Toast.makeText(getApplicationContext(),"Please enter valid email address",Toast.LENGTH_SHORT).show();
//                }
//                else
//                {
//                    Toast.makeText(getApplicationContext(),"Invalid email address",Toast.LENGTH_SHORT).show();
//                }
//            }
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                // other stuffs
//            }
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                // other stuffs
//            }
//        });


        String[] strings = {"India", "Japan", "USA", "Canada", "England"};
        Spinner spinner = (Spinner) findViewById(R.id.country_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, strings);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){


                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

//                    cameraIntent();
                }else {
                    requestStoragePermission();
                }
                // dispatchTakePictureIntent();
            }
        });


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                country = strings[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                country = strings[0];

            }
        });

        geneder = r1.getText().toString();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_button_1:
                        // do operations specific to this selection
                        geneder = r1.getText().toString();
                        break;
                    case R.id.radio_button_2:
                        // do operations specific to this selectiongeneder=
                        geneder = r2.getText().toString();
                        break;

                }
            }
        });


    }

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale
                (this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            new AlertDialog.Builder(this)
                    .setTitle("Permisssion Needed")
                    .setMessage("Permission Required For Capture Image")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(MainActivity.this,new String[] {Manifest.permission
                                    .WRITE_EXTERNAL_STORAGE},REQUEST_EXTERNAL_STORAGE);
                        }
                    }).setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).create().show();
        }else {
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission
                    .WRITE_EXTERNAL_STORAGE},REQUEST_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_EXTERNAL_STORAGE){
            if (grantResults.length> 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Granted", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "Not Granted", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bundle bundle = data.getExtras();
            Bitmap bitmap = (Bitmap) bundle.get("data");

            profileImage.setImageBitmap(bitmap);
        }
        if (requestCode == 121 && resultCode == RESULT_OK){

            Uri uri = data.getData();
            filePath.setText(getRealPathFromURI(uri));

            Log.d("TAG", getRealPathFromURI(uri));

//            File imgFile = new  File(getRealPathFromURI(uri));
//
//            if(imgFile.exists()){
//
//                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
//
//                profileImage.setImageBitmap(myBitmap);
//
//            }

            Glide.with(this)
                    .load(uri)
                    .placeholder(R.drawable.bg2)
                    .into(profileImage);



        }

    }
    public String getRealPathFromURI(Uri contentUri)
    {
        String[] proj = { MediaStore.Audio.Media.DATA };
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


//    private String getRealPathFromURI(Uri contentURI) {
//        String result;
//        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
//        if (cursor == null) { // Source is Dropbox or other similar local file path
//            result = contentURI.getPath();
//        } else {
//            cursor.moveToFirst();
//            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
//            result = cursor.getString(idx);
//            cursor.close();
//        }
//        return result;
//    }

    public void showDatePiker(View view) {

        DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {


            @Override
            public void onDateSet(DatePicker view, int year1, int month1, int dayOfMonth) {
                month = month1;

                month1 = month1 + 1;

                currentyear = year1;

                day = dayOfMonth;

                dob = dayOfMonth + "/" + month1 + "/" + year1;
                date.setTextColor(getResources().getColor(R.color.black));
                date.setText(dob);

            }
        }, currentyear, month, day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }


    public void submitForm(View view) {

        StringBuilder result = new StringBuilder();
        String check1 = "",
                check2= "" , check3 = "", check4= "";

        if (checkBox1.isChecked()) {
            result.append("\n" + checkBox1.getText());
            check1 = checkBox1.getText().toString();

        }
        if (checkBox2.isChecked()) {
            result.append("\n" + checkBox2.getText());
            check2 = checkBox2.getText().toString();

        }
        if (checkBox3.isChecked()) {
            result.append("\n" + checkBox3.getText());
            check3 = checkBox3.getText().toString();

        }
        if (checkBox4.isChecked()) {
            result.append("\n" + checkBox4.getText());
            check4 = checkBox4.getText().toString();
        }


        if (eName.getText().toString().trim().isEmpty()) {
            eName.setError("Please Provide Name");
        }

        if (eEmail.getText().toString().trim().isEmpty()) {
            eEmail.setError("Please Provide Email");
        }
        if (!eEmail.getText().toString().trim().matches(emailPattern)) {
            eEmail.setError("Entered Email is not valid");

        }

        if (ePass.getText().toString().trim().isEmpty()) {
            ePass.setError("Please Provide Password");
        } else {
            if (ePass.getText().toString().trim().length() < 8) {
                ePass.setError("Password must have atleast 8 charcters");
            }
        }

        if (dob.isEmpty()) {
            Toast.makeText(this, "Plaese Select DOB", Toast.LENGTH_SHORT).show();
        }


        //It is for Final Submit Button...Action
        if (!eName.getText().toString().trim().isEmpty() &&
                !eEmail.getText().toString().trim().isEmpty() &&
                !ePass.getText().toString().trim().isEmpty() &&
                eEmail.getText().toString().trim().matches(emailPattern) &&
                !dob.isEmpty()
                &&
                ePass.getText().toString().trim().length() >= 8
        ) {
            Intent intent = new Intent(this, MainActivity2.class);
            intent.putExtra("name", eName.getText().toString());
            intent.putExtra("email", eEmail.getText().toString());
            intent.putExtra("dob", dob);
            intent.putExtra("country", country);
            intent.putExtra("gender", geneder);
            intent.putExtra("hobbies", result.toString());
            intent.putExtra("check1", check1);
            intent.putExtra("check2", check2);
            intent.putExtra("check3", check3);
            intent.putExtra("check4", check4);

            startActivity(intent);
        }


    }

    public void chooseFromGallery(View view) {
        Intent galleryIntent = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent,121);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
//            startActivityForResult(Intent.createChooser(takePictureIntent,"select image"),121);
//            overridePendingTransition(R.anim.bottomin,R.anim.bottomout);
//        }
    }

}