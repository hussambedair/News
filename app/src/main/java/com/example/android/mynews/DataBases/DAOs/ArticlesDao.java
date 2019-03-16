package com.example.android.mynews.DataBases.DAOs;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.android.mynews.API.Models.NewsResponse.Article;
import com.example.android.mynews.API.Models.SourcesResponse.Source;

import java.util.List;

@Dao
public interface ArticlesDao {


    @Insert (onConflict = OnConflictStrategy.REPLACE)
    public void addArticles(List<Article> articles) ;


    @Query("select * from Article where mSourceId=:sourceId;")
    public List<Article> getArticlesBySourceId(String sourceId);



}
