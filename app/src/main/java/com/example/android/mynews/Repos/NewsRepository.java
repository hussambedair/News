package com.example.android.mynews.Repos;

import android.util.Log;

import com.example.android.mynews.API.APIManager;
import com.example.android.mynews.API.Models.NewsResponse.Article;
import com.example.android.mynews.API.Models.NewsResponse.NewsResponse;
import com.example.android.mynews.API.Models.SourcesResponse.Source;
import com.example.android.mynews.API.Models.SourcesResponse.SourcesResponse;
import com.example.android.mynews.DataBases.MyDataBase;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepository {

    private static String apiKey = "1286a5188ef34cfab3c5cd1f1b813eca";

    String lang;

    public NewsRepository(String lang) {
        this.lang = lang;

    }

    public void getSources(final OnSourcesPreparedListener onSourcesPreparedListener) {

        APIManager.getAPIs()
                .getSources(apiKey, lang)
                .enqueue(new Callback<SourcesResponse>() {
                    @Override
                    public void onResponse(Call<SourcesResponse> call,
                                           Response<SourcesResponse> response) {

                        if (response.isSuccessful()
                                && "ok".equals(response.body().getStatus())) {

                            if (onSourcesPreparedListener != null) {

                                onSourcesPreparedListener.onSourcesPrepared(response.body().getSources());

                                //insert the sources coming within the response into the database
                                InsertSourcesIntoDataBase thread =
                                        new InsertSourcesIntoDataBase(response.body().getSources());

                                thread.start();


                            }


                        }


                    }

                    @Override
                    public void onFailure(Call<SourcesResponse> call, Throwable t) {

                        // handle database call
                        GetSourcesFromDataBase thread =
                                new GetSourcesFromDataBase(onSourcesPreparedListener);
                        thread.start();

                    }
                });


    }

    public void getArticlesBySourceId(String lang, final String sourceId,
                                      final OnArticlesPreparedListener onArticlesPreparedListener) {
        APIManager.getAPIs()
                .getNewsBySourceId(apiKey, lang, sourceId)
                .enqueue(new Callback<NewsResponse>() {
                    @Override
                    public void onResponse(Call<NewsResponse> call,
                                           Response<NewsResponse> response) {

                        if (response.isSuccessful() &&
                                "ok".equals(response.body().getStatus())) {


                            if (onArticlesPreparedListener != null) {
                                onArticlesPreparedListener.onArticlesPrepared(response.body().getArticles());


                                //insert the articles coming within the response into the database
                                InsertArticlesIntoDataBase thread =
                                        new InsertArticlesIntoDataBase(response.body().getArticles());

                                thread.start();


                            }


                        }


                    }

                    @Override
                    public void onFailure(Call<NewsResponse> call, Throwable t) {

                        // handle database call
                        GetArticlesFromDataBase thread =
                                new GetArticlesFromDataBase(onArticlesPreparedListener, sourceId);
                        thread.start();


                    }
                });

    }


    //notify me when the sources are prepared and ready
    //So the main purpose of this interface is to link between
    // NewsRepository class & the HomeActivity class
    // the NewsRepository class will get the sources, and will
    //notify the HomeActivity when they're ready
    public interface OnSourcesPreparedListener {

        public void onSourcesPrepared(List<Source> sourcesList);
    }


    public interface OnArticlesPreparedListener {

        public void onArticlesPrepared(List<Article> newsList);
    }


    //We must use a Background Thread to handle queries from database

    //this is a Background Thread that insert data (sources) into database
    class InsertSourcesIntoDataBase extends Thread {

        List<Source> mSources;

        public InsertSourcesIntoDataBase(List<Source> sources) {
            mSources = sources;

        }

        @Override
        public void run() {

            MyDataBase.getInstance()
                    .sourcesDao()
                    .addSources(mSources);

            Log.e("sources thread", "Insertion success");


        }
    }


    //this is a Background Thread that gets data (sources) from database
    class GetSourcesFromDataBase extends Thread {

        //notify me when you get the sources from the database
        OnSourcesPreparedListener mListener;

        GetSourcesFromDataBase(OnSourcesPreparedListener listener) {
            mListener = listener;

        }


        @Override
        public void run() {

            List<Source> sources = MyDataBase.getInstance()
                    .sourcesDao()
                    .getAllSources();


            //when the sources from the database are prepared and ready, notify me
            mListener.onSourcesPrepared(sources);

            Log.e("sources thread", "Insertion success");


        }
    }


    //this is a Background Thread that insert data (articles) into database
    class InsertArticlesIntoDataBase extends Thread {

        List<Article> mArticles;

        public InsertArticlesIntoDataBase(List<Article> articles) {
            mArticles = articles;

        }

        @Override
        public void run() {

            for (int i = 0; i < mArticles.size(); i++) {
                Article article = mArticles.get(i);
                article.setmSourceId(article.getSource().getId());
                article.setmSourceName(article.getSource().getName());

            }

            MyDataBase.getInstance()
                    .newsDao()
                    .addArticles(mArticles);

            Log.e("articles thread", "Insertion success");


        }
    }


    //this is a Background Thread that gets data (articles) from database
    class GetArticlesFromDataBase extends Thread {

        //notify me when you get the articles from the database
        OnArticlesPreparedListener mListener;

        String mSourceId;

        GetArticlesFromDataBase(OnArticlesPreparedListener listener, String sourceId) {
            mListener = listener;
            mSourceId = sourceId;

        }


        @Override
        public void run() {

            List<Article> articles = MyDataBase.getInstance()
                    .newsDao()
                    .getArticlesBySourceId(mSourceId);


            //when the sources from the database are prepared and ready, notify me
            mListener.onArticlesPrepared(articles);


        }
    }


}
