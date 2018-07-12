package com.daniel.test_reign.core.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

public class HitsObject implements Parcelable {

    @Expose
    private String story_title;
    @Expose
    private String title;
    @Expose
    private String author;
    @Expose
    private String created_at;
    @Expose
    private String story_url;
    @Expose
    private int story_id;

    public String getStory_title() {
        return story_title;
    }

    public void setStory_title(String story_title) {
        this.story_title = story_title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getStory_url() {
        return story_url;
    }

    public void setStory_url(String story_url) {
        this.story_url = story_url;
    }

    public int getStory_id() {
        return story_id;
    }

    public void setStory_id(int story_id) {
        this.story_id = story_id;
    }

    public HitsObject() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.story_title);
        dest.writeString(this.title);
        dest.writeString(this.author);
        dest.writeString(this.created_at);
        dest.writeString(this.story_url);
        dest.writeInt(this.story_id);
    }

    protected HitsObject(Parcel in) {
        this.story_title = in.readString();
        this.title = in.readString();
        this.author = in.readString();
        this.created_at = in.readString();
        this.story_url = in.readString();
        this.story_id = in.readInt();
    }

    public static final Creator<HitsObject> CREATOR = new Creator<HitsObject>() {
        @Override
        public HitsObject createFromParcel(Parcel source) {
            return new HitsObject(source);
        }

        @Override
        public HitsObject[] newArray(int size) {
            return new HitsObject[size];
        }
    };
}
