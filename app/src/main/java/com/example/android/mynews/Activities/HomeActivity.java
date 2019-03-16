package com.example.android.mynews.Activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.mynews.API.Models.NewsResponse.Article;
import com.example.android.mynews.API.Models.SourcesResponse.Source;
import com.example.android.mynews.Adapters.NewsRecyclerAdapter;

import com.example.android.mynews.DataBases.MyDataBase;
import com.example.android.mynews.R;
import com.example.android.mynews.Repos.NewsRepository;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    RecyclerView newsRecyclerView;
    NewsRecyclerAdapter newsAdapter;
    RecyclerView.LayoutManager layoutManager;
    List <Article> articles;


    TabLayout tabLayout;


    NewsRepository newsRepository;
    String language = "en";

    View loadingIndicator;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        newsRecyclerView = findViewById(R.id.news_recycler_view);

        newsAdapter = new NewsRecyclerAdapter(null);

        layoutManager = new LinearLayoutManager(HomeActivity.this);

        newsRecyclerView.setAdapter(newsAdapter);
        newsRecyclerView.setLayoutManager(layoutManager);



        tabLayout = findViewById(R.id.tab_layout);

        loadingIndicator=findViewById(R.id.loading_indicator);



        newsRepository = new NewsRepository(language);
        loadingIndicator.setVisibility(View.VISIBLE);
        newsRepository.getSources(mSourcesListener);





    }


    NewsRepository.OnSourcesPreparedListener mSourcesListener = new NewsRepository.OnSourcesPreparedListener() {
        @Override
        public void onSourcesPrepared( final List<Source> sourcesList) {

            // to guaruntee that we are running our code on the Main Thread
            // not on a background thread,
            // we must use runOnUiThread method
            // Because only the original thread that created a view hierarchy
            // can touch its views.
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    loadingIndicator.setVisibility(View.GONE);
                    addSourcesToTabLayout(sourcesList);

                }
            });


        }
    };


    private void addSourcesToTabLayout(List<Source> sourcesList) {
        if (sourcesList == null) {
            return;
        }

        tabLayout.removeAllTabs();
        for (int i = 0; i < sourcesList.size(); i++) {
            Source source = sourcesList.get(i);
            TabLayout.Tab tab = tabLayout.newTab();
            tab.setText(source.getName());
            tab.setTag(source); //to get the source id ,, this will store the whole Source object into the tab
            tabLayout.addTab(tab);

        }
        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                Source source = ((Source) tab.getTag());
                newsRepository.getArticlesBySourceId(language,source.getId(), mArticlesListener);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

                Source source = ((Source) tab.getTag());
                newsRepository.getArticlesBySourceId(language,source.getId(), mArticlesListener);

            }
        });
        tabLayout.getTabAt(0).select();
    }

    NewsRepository.OnArticlesPreparedListener mArticlesListener = new NewsRepository.OnArticlesPreparedListener() {
        @Override
        public void onArticlesPrepared(final List<Article> articlesList) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    loadingIndicator.setVisibility(View.GONE);

                    newsAdapter.changeData(articlesList);

                }
            });




        }
    };

}
