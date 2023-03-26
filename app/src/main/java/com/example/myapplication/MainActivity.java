package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;

import java.net.URI;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper db;
    ListView lv;
    EditText txtSearch;
    ArrayList<MyItem> list = new ArrayList<MyItem>();
    ArrayList<MyItem> slist = new ArrayList<MyItem>();
    ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseHelper(this);
        list = db.getAllStudent();
        //


        //
        adapter = new ItemAdapter(this,slist);
        lv = findViewById(R.id.listView1);
        lv.setAdapter(adapter);
        txtSearch = findViewById(R.id.searchTxt);
        slist.addAll(list);
        //
        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                slist.clear();
                Pattern p = Pattern.compile(s.toString());
                for(int i = 0; i < list.size();i++){
                    Matcher m = p.matcher(list.get(i).getImgname().toLowerCase());
                    if(m.find()){
                        slist.add((list.get(i).getUriImage()==null)? new MyItem(list.get(i).getImg(),list.get(i).getImgname()):new MyItem(list.get(i).getUriImage(),list.get(i).getImgname(),list.get(i).getCourseName()));
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.mymenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(this,AddActivity.class);
        startActivityForResult(intent,0);
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode== Activity.RESULT_OK){
            if(requestCode==0){
                Bundle b = data.getExtras();
                if(b!=null) {
                    Uri uri = b.getParcelable("imgdata");
                    String txt = b.getString("textdata");
                    String course = b.getString("course");
                    //String img = uri.toString();
                    list.add(new MyItem(uri, txt,course));
                    slist.clear();
                    slist.addAll(list);
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }

}