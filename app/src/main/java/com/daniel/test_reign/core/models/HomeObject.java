package com.daniel.test_reign.core.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class HomeObject implements Parcelable {

    @Expose
    private List<HitsObject> hits = new ArrayList<>();
    @Expose
    private float nbHits;
    @Expose
    private int page;
    @Expose
    private int nbPages;
    @Expose
    private int hitsPerPage;
    @Expose
    private int processingTimeMS;
    @Expose
    private boolean exhaustiveNbHits;
    @Expose
    private String query;
    @Expose
    private String params;

    public List<HitsObject> getHits() {
        return hits;
    }

    public void setHits(List<HitsObject> hits) {
        this.hits = hits;
    }

    public float getNbHits() {
        return nbHits;
    }

    public void setNbHits(float nbHits) {
        this.nbHits = nbHits;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getNbPages() {
        return nbPages;
    }

    public void setNbPages(int nbPages) {
        this.nbPages = nbPages;
    }

    public int getHitsPerPage() {
        return hitsPerPage;
    }

    public void setHitsPerPage(int hitsPerPage) {
        this.hitsPerPage = hitsPerPage;
    }

    public int getProcessingTimeMS() {
        return processingTimeMS;
    }

    public void setProcessingTimeMS(int processingTimeMS) {
        this.processingTimeMS = processingTimeMS;
    }

    public boolean isExhaustiveNbHits() {
        return exhaustiveNbHits;
    }

    public void setExhaustiveNbHits(boolean exhaustiveNbHits) {
        this.exhaustiveNbHits = exhaustiveNbHits;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.hits);
        dest.writeFloat(this.nbHits);
        dest.writeInt(this.page);
        dest.writeInt(this.nbPages);
        dest.writeInt(this.hitsPerPage);
        dest.writeInt(this.processingTimeMS);
        dest.writeByte(this.exhaustiveNbHits ? (byte) 1 : (byte) 0);
        dest.writeString(this.query);
        dest.writeString(this.params);
    }

    public HomeObject() {
    }

    protected HomeObject(Parcel in) {
        this.hits = in.createTypedArrayList(HitsObject.CREATOR);
        this.nbHits = in.readFloat();
        this.page = in.readInt();
        this.nbPages = in.readInt();
        this.hitsPerPage = in.readInt();
        this.processingTimeMS = in.readInt();
        this.exhaustiveNbHits = in.readByte() != 0;
        this.query = in.readString();
        this.params = in.readString();
    }

    public static final Creator<HomeObject> CREATOR = new Creator<HomeObject>() {
        @Override
        public HomeObject createFromParcel(Parcel source) {
            return new HomeObject(source);
        }

        @Override
        public HomeObject[] newArray(int size) {
            return new HomeObject[size];
        }
    };
}
