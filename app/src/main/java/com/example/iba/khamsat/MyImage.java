package com.example.iba.khamsat;

import android.graphics.Bitmap;

public class MyImage {

    private String description;
    private Bitmap bitmap;

    public MyImage(String description, Bitmap bitmap){
        this.description = description;
        this.bitmap = bitmap;
    }

    public String getDescription() {
        return description;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

}
