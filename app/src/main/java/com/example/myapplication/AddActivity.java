package com.example.myapplication;

import static android.provider.CalendarContract.CalendarCache.URI;
import static android.widget.Toast.LENGTH_SHORT;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    EditText txtNewString;
    Button button;
    Button cancel;
    Uri uriImg;
    Spinner course_list;
    ImageView myImage;
    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;
    private String selected_course;

    DatabaseHelper db;
    // end here

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        db = new DatabaseHelper(this);

        txtNewString = findViewById(R.id.newString);
        button = findViewById(R.id.button);
        cancel = findViewById(R.id.button3);
        course_list = findViewById(R.id.spinner);
        //start here
        myImage = findViewById(R.id.imageaView2);
        myImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)){
                    if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_DENIED){
                        String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permission,PERMISSION_CODE);
                    }
                    else{
                        pickImage();
                    }
                }
                else{
                    pickImage();
                }

            }
        });
        //end here
        button.setOnClickListener(this);
        cancel.setOnClickListener(this);
        course_list.setOnItemSelectedListener(this);
    }
    //start here
    private void pickImage(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_PICK_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    pickImage();
                } else {
                    Toast.makeText(this, "Permission Denied!", LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            myImage.setImageURI(data.getData());
            uriImg = data.getData();

        }
    }
    //end here




    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){


            case R.id.button:
                if(txtNewString.getText().toString().trim()==null  || course_list.getSelectedItem()==null)
                {
                    Toast.makeText(this, "Permission Denied!", LENGTH_SHORT).show();
                    break;
                }
                else {
                    String data = txtNewString.getText().toString();
                    Intent intent = new Intent();
                    intent.putExtra("textdata", data);
                    intent.putExtra("imgdata", uriImg);
                    intent.putExtra("course", selected_course);
                    intent.getData();
                    setResult(Activity.RESULT_OK, intent);
                    String message = "Error adding student";
                    long result = db.addStudent(new MyItem(data,uriImg,selected_course));
                    if(result>0){
                        message = "New Student added!";
                    }
                    Toast.makeText(this,message,Toast.LENGTH_LONG).show();
                    finish();
                }break;
            case R.id.button3:
                finish();
                break;
        }

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        selected_course = course_list.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}