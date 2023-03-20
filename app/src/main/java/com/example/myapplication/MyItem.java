package com.example.myapplication;

import android.net.Uri;

import java.io.Serializable;
import java.net.URI;

public class MyItem implements Serializable {
    private int img;
    private String imgname;
    private Uri uriImage;

    private String courseName;

    public MyItem(int img, String imgname) {
        this.img = img;
        this.imgname = imgname;
    }

    public MyItem(Uri uriImage,String imgname,String courseName ) {
        this.imgname = imgname;
        this.uriImage = uriImage;
        this.courseName = courseName;
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
