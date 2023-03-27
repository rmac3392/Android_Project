package com.example.myapplication;
//28:11
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
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
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
    AdapterView.AdapterContextMenuInfo info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseHelper(this);
        list = db.getAllStudent();
        adapter = new ItemAdapter(this,slist);
        lv = findViewById(R.id.listView1);

        registerForContextMenu(lv);

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
                        slist.add(new MyItem(list.get(i).getImgname(),list.get(i).getUriImage(),list.get(i).getCourseName()));
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
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.contextmenu,menu);
        info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle(list.get(info.position).getImgname());
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        int ok = -1;
        switch(id){
            case R.id.edit:

                break;
            case R.id.delete:
                slist.remove(info.position);
                ok = db.removeStudent(list.get(info.position).getId());
                    list.remove(info.position);
                    Toast.makeText(this,(ok>0)? "Student Removed":"Failed to remove student", Toast.LENGTH_SHORT).show();
                    this.adapter.notifyDataSetChanged();
                    break;

            case R.id.call:
        }
        return super.onContextItemSelected(item);
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
                    list.add(new MyItem(txt,uri,course));
                    slist.clear();
                    slist.addAll(list);
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }

}