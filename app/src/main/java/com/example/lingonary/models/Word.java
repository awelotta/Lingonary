package com.example.lingonary.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "words")
public class Word implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String learning;
    private String nativeLang;
    private int timesCorrect;
    private boolean hasBeenInQuiz;
    private long dateAdded;

    // Needed by Room — must be EMPTY
    public Word() { }

    // Your custom constructor
    public Word(String learning, String nativeLang) {
        this.learning = learning;
        this.nativeLang = nativeLang;
        this.timesCorrect = 0;
        this.hasBeenInQuiz = false;
        this.dateAdded = System.currentTimeMillis();
    }

    // Parcelable constructor
    protected Word(Parcel in) {
        id = in.readInt();
        learning = in.readString();
        nativeLang = in.readString();
        timesCorrect = in.readInt();
        hasBeenInQuiz = in.readByte() != 0;
        dateAdded = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(learning);
        dest.writeString(nativeLang);
        dest.writeInt(timesCorrect);
        dest.writeByte((byte) (hasBeenInQuiz ? 1 : 0));
        dest.writeLong(dateAdded);
    }

    @Override
    public int describeContents() { return 0; }

    public static final Creator<Word> CREATOR = new Creator<Word>() {
        @Override
        public Word createFromParcel(Parcel in) { return new Word(in); }
        @Override
        public Word[] newArray(int size) { return new Word[size]; }
    };

    // ---- GETTERS ----
    public int getId() { return id; }
    public String getLearning() { return learning; }
    public String getNativeLang() { return nativeLang; }
    public int getTimesCorrect() { return timesCorrect; }
    public boolean hasBeenInQuiz() { return hasBeenInQuiz; }
    public long getDateAdded() { return dateAdded; }

    // ---- SETTERS (Required by Room) ----
    public void setId(int id) { this.id = id; }
    public void setLearning(String learning) { this.learning = learning; }
    public void setNativeLang(String nativeLang) { this.nativeLang = nativeLang; }
    public void setTimesCorrect(int timesCorrect) { this.timesCorrect = timesCorrect; }
    public void setHasBeenInQuiz(boolean hasBeenInQuiz) { this.hasBeenInQuiz = hasBeenInQuiz; }
    public void setDateAdded(long dateAdded) { this.dateAdded = dateAdded; }

    // Extra helper
    public void incrementTimesCorrect() { this.timesCorrect++; }
}