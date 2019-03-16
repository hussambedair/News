
package com.example.android.mynews.API.Models.NewsResponse;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.example.android.mynews.API.Models.SourcesResponse.Source;
import com.google.gson.annotations.SerializedName;

import static android.arch.persistence.room.ForeignKey.CASCADE;


@Entity (foreignKeys = @ForeignKey(entity = Source.class,
        parentColumns = "mId",
        childColumns = "mSourceId",
        onDelete = CASCADE ),
        indices = {@Index(value = "mSourceId", unique = false)})
public class Article {

    @SerializedName("author")
    private String mAuthor;

    @SerializedName("content")
    private String mContent;

    @SerializedName("description")
    private String mDescription;

    @SerializedName("publishedAt")
    private String mPublishedAt;

    @Ignore
    @SerializedName("source")
    private Source mSource;

    public String mSourceId;

    public String mSourceName;


    @SerializedName("title")
    private String mTitle;

    @PrimaryKey
    @NonNull
    @SerializedName("url")
    private String mUrl;

    @SerializedName("urlToImage")
    private String mUrlToImage;

    public String getmSourceName() {
        return mSourceName;
    }

    public void setmSourceName(String mSourceName) {
        this.mSourceName = mSourceName;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        mAuthor = author;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getPublishedAt() {
        return mPublishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        mPublishedAt = publishedAt;
    }

    public Source getSource() {
        return mSource;
    }

    public void setSource(Source source) {
        mSource = source;
    }

    public String getmSourceId() {
        return mSourceId;
    }

    public void setmSourceId(String mSourceId) {
        this.mSourceId = mSourceId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getUrlToImage() {
        return mUrlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        mUrlToImage = urlToImage;
    }

}
