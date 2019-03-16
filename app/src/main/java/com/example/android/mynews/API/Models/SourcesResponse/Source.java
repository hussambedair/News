
package com.example.android.mynews.API.Models.SourcesResponse;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;


@Entity
public class Source {

    @ColumnInfo
    @SerializedName("category")
    private String mCategory;

    @ColumnInfo
    @SerializedName("country")
    private String mCountry;

    @ColumnInfo
    @SerializedName("description")
    private String mDescription;

    @ColumnInfo
    @PrimaryKey
    @NonNull
    @SerializedName("id")
    private String mId;

    @ColumnInfo
    @SerializedName("language")
    private String mLanguage;

    @ColumnInfo
    @SerializedName("name")
    private String mName;

    @ColumnInfo
    @SerializedName("url")
    private String mUrl;

    public Source() {

    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        mCategory = category;
    }

    public String getCountry() {
        return mCountry;
    }

    public void setCountry(String country) {
        mCountry = country;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getLanguage() {
        return mLanguage;
    }

    public void setLanguage(String language) {
        mLanguage = language;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

}
