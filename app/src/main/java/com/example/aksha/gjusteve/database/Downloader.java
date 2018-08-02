package com.example.aksha.gjusteve.database;
import android.os.Parcel;
import android.os.Parcelable;

class Downloader  implements Parcelable{

    Downloader(){

    }

    private int progress;
    private int currentFileSize;
    private int totalFileSize;

    int getProgress() {
        return progress;
    }

    void setProgress(int progress) {
        this.progress = progress;
    }

    int getCurrentFileSize() {
        return currentFileSize;
    }

    void setCurrentFileSize(int currentFileSize) {
        this.currentFileSize = currentFileSize;
    }

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public int getTotalFileSize() {
//        return totalFileSize;
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)

    void setTotalFileSize(int totalFileSize) {
        this.totalFileSize = totalFileSize;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(progress);
        dest.writeInt(currentFileSize);
        dest.writeInt(totalFileSize);
    }

    private Downloader(Parcel in) {

        progress = in.readInt();
        currentFileSize = in.readInt();
        totalFileSize = in.readInt();
    }

    public static final Parcelable.Creator<Downloader> CREATOR = new Parcelable.Creator<Downloader>() {
        public Downloader createFromParcel(Parcel in) {
            return new Downloader(in);
        }

        public Downloader[] newArray(int size) {
            return new Downloader[size];
        }
    };
}