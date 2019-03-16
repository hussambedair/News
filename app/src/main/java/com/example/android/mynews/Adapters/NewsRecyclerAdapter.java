package com.example.android.mynews.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.mynews.API.Models.NewsResponse.Article;
import com.example.android.mynews.Activities.HomeActivity;
import com.example.android.mynews.R;

import org.w3c.dom.Text;

import java.util.List;

public class NewsRecyclerAdapter
        extends RecyclerView.Adapter<NewsRecyclerAdapter.ViewHolder> {

    List <Article> mArticles;

    OnItemClickListener onFavoriteClickListener;

    public void setOnFavoriteClickListener(OnItemClickListener onFavoriteClickListener) {
        this.onFavoriteClickListener = onFavoriteClickListener;
    }

    public NewsRecyclerAdapter(List<Article> mArticles) {
        this.mArticles = mArticles;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_article, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int postion) {
        final Article article = mArticles.get(postion);

        if (article.getSource() == null) {
            viewHolder.sourceName.setText(article.getmSourceName());
        } else {
            viewHolder.sourceName.setText(article.getSource().getName());
        }

        viewHolder.headLine.setText(article.getTitle());


        viewHolder.timePublished.setText(article.getPublishedAt());

        //attach image by url using Glide library
        Glide.with(viewHolder.itemView)
                .load(article.getUrlToImage()).into(viewHolder.articleImage);

        if (onFavoriteClickListener != null) {
            viewHolder.likeIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onFavoriteClickListener.onItemClick(postion,article);

                }
            });
        }





    }

    @Override
    public int getItemCount() {
        if (mArticles == null) {
            return 0;
        }
        return mArticles.size();
    }

    public void changeData (List <Article> articles) {
        mArticles = articles;
        notifyDataSetChanged();

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView sourceName;
        TextView headLine;
        TextView timePublished;
        ImageView articleImage;

        //you may not define these, since they don't change
        ImageView likeIcon;
        ImageView shareIcon;
        TextView shareText;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sourceName = itemView.findViewById(R.id.source_text_view);
            headLine = itemView.findViewById(R.id.headline_text_view);
            timePublished = itemView.findViewById(R.id.time_text_view);
            articleImage= itemView.findViewById(R.id.article_image);

            // ==========================================
            likeIcon = itemView.findViewById(R.id.icon_favorite);
            shareIcon = itemView.findViewById(R.id.icon_share);
            shareText = itemView.findViewById(R.id.share_text_view);
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(int pos, Article article);
    }















}
