package com.example.lingonary.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Word implements Parcelable {
    private String learning;
    private String nativeLang;
    private int timesCorrect;

    public Word(String learning, String nativeLang) {
        this.learning = learning;
        this.nativeLang = nativeLang;
        this.timesCorrect = 0;
    }

    protected Word(Parcel in) {
        learning = in.readString();
        nativeLang = in.readString();
        timesCorrect = in.readInt();
    }

    public static final Creator<Word> CREATOR = new Creator<Word>() {
        @Override
        public Word createFromParcel(Parcel in) {
            return new Word(in);
        }

        @Override
        public Word[] newArray(int size) {
            return new Word[size];
        }
    };

    public String getLearning() { return learning; }
    public String getNativeLang() { return nativeLang; }
    public int getTimesCorrect() { return timesCorrect; }

    public void incrementTimesCorrect() { this.timesCorrect++; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(learning);
        dest.writeString(nativeLang);
        dest.writeInt(timesCorrect);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
