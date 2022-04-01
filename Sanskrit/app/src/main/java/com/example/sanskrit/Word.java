package com.example.sanskrit;

public class Word {
    private String mEnglish;
    private String mSanskrit;
    private int mImage = NO_IMAGE;
    private static final int NO_IMAGE = -1;
    private int mMusic;

    public Word(String English, String Sanskrit,int music) {
        mEnglish = English;
        mMusic = music;
        mSanskrit = Sanskrit;
    }

    public Word(String English, String Sanskrit, int image,int music) {
        mEnglish = English;
        mSanskrit = Sanskrit;
        mImage = image;
        mMusic = music;
    }

    public int getmImage() {
        return mImage;
    }

    public void setmImage(int mImage) {
        this.mImage = mImage;
    }

    public String getmEnglish() {
        return mEnglish;
    }

    public String getmSanskrit() {
        return mSanskrit;
    }

    public void setmEnglish(String mEnglish) {
        this.mEnglish = mEnglish;
    }

    public void setmSanskrit(String mSanskrit) {
        this.mSanskrit = mSanskrit;
    }

    public void setmMusic(int mMusic) {
        this.mMusic = mMusic;
    }

    public int getmMusic() {
        return mMusic;
    }
}


