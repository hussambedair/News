package com.example.android.mynews.DataBases.DAOs;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.android.mynews.API.Models.SourcesResponse.Source;


import java.util.List;

@Dao
public interface SourcesDao {

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    public void addSources(List <Source> sources) ;


    @Query("select * from Source;")
    public List<Source> getAllSources();







}
