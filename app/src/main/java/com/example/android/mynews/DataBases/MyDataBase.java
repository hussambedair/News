package com.example.android.mynews.DataBases;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.android.mynews.API.Models.NewsResponse.Article;
import com.example.android.mynews.API.Models.SourcesResponse.Source;
import com.example.android.mynews.DataBases.DAOs.ArticlesDao;
import com.example.android.mynews.DataBases.DAOs.SourcesDao;


@Database(entities = {Source.class, Article.class},
        version = 4, exportSchema = false)
public abstract class MyDataBase extends RoomDatabase {


   public abstract SourcesDao sourcesDao();

   public abstract ArticlesDao newsDao();



   private static MyDataBase myDataBase;


   public static void init (Context context) {
       if (myDataBase == null) { //Create new object

           myDataBase= Room.databaseBuilder(context.getApplicationContext(),
                   MyDataBase.class, "News-DataBase")
                   .fallbackToDestructiveMigration()
                   //.allowMainThreadQueries()
                   .build();

       }

   }

    public static MyDataBase getInstance() {

       return myDataBase;


    }






}
