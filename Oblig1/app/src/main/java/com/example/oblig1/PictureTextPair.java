package com.example.oblig1;

import android.graphics.Bitmap;

public class PictureTextPair {
    private String text;
    private Bitmap picture;

    public PictureTextPair(String text, Bitmap picture){
        this.text = text;
        this.picture = picture;
    }

    public String getText(){
        return text;
    }
    public void setText(String text){
        this.text = text;
    }

    public Bitmap getPicture() {
        return picture;
    }

    public void setPicture(Bitmap picture) {
        this.picture = picture;
    }
}
