package com.example.myapplication;

import android.net.Uri;

import java.io.Serializable;
import java.net.URI;

public class MyItem implements Serializable {
    private int img;
    private String imgname;
    private Uri uriImage;
    private int id;
    private String courseName;



    public MyItem( String imgname, Uri uriImage, String courseName, int id) {
        this.imgname = imgname;
        this.uriImage = uriImage;
        this.id = id;
        this.courseName = courseName;
    }
    public MyItem( String imgname, Uri uriImage, String courseName) {
        this.imgname = imgname;
        this.uriImage = uriImage;
        this.id = id;
        this.courseName = courseName;
    }

    public MyItem(){
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Uri getUriImage() {
        return uriImage;
    }

    public void setUriImage(Uri uriImage) {
        this.uriImage = uriImage;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getImgname() {
        return imgname;
    }

    public void setImgname(String imgname) {
        this.imgname = imgname;
    }
}
