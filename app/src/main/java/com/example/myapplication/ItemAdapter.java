package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ItemAdapter extends BaseAdapter {

    Context context;
    ArrayList<MyItem> list;

    LayoutInflater inflater;
    public ItemAdapter(Context context, ArrayList<MyItem> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemHandler handler = null;
        if(convertView==null){
            handler = new ItemHandler();
            convertView=inflater.inflate(R.layout.itemlayout,null);
            handler.iv = convertView.findViewById(R.id.imageView);
            handler.name = convertView.findViewById(R.id.textView);
            handler.course = convertView.findViewById(R.id.textView3);
            convertView.setTag(handler);
        }
            handler =(ItemHandler)convertView.getTag();
            handler.iv.setImageURI(list.get(position).getUriImage());
            handler.name.setText(list.get(position).getImgname());
            handler.course.setText(list.get(position).getCourseName());

        return convertView;
    }

    static class ItemHandler{
        ImageView iv;
        TextView name,course;
    }
}
