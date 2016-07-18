package com.example.kolya.news24.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kolya.news24.R;
import com.example.kolya.news24.SelectedItemActivity;
import com.example.kolya.news24.SimpleParse.NewsItem;

import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder>  {


    public static class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ItemViewHolder.OnItemClickListener mItemClickListener;
        CardView cardView;
        TextView newsTitle;
        TextView newsPubDate;
        ImageView newsImage;
        String newsUrl;
        Context context;


        ItemViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            newsTitle = (TextView) itemView.findViewById(R.id.title_news);
            newsPubDate = (TextView) itemView.findViewById(R.id.pub_date);
            newsImage = (ImageView) itemView.findViewById(R.id.image_news);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context,SelectedItemActivity.class);
            intent.putExtra("NEWS_URL", newsUrl);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }

        public interface OnItemClickListener {
            void onItemClick(View view, int position);
        }

        public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
            this.mItemClickListener = mItemClickListener;
        }
    }


    List<NewsItem> itemForLists;
    Context context;

    public RecyclerViewAdapter(List<NewsItem> itemForLists, Context context) {
        this.itemForLists = itemForLists;
        this.context = context;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int index) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;
    }



    @Override
    public void onBindViewHolder(ItemViewHolder itemViewHolder, int index) {
        itemViewHolder.newsTitle.setText(itemForLists.get(index).getTitle());
        itemViewHolder.newsPubDate.setText(itemForLists.get(index).getPubDate());
        String imageLink = itemForLists.get(index).getImageUrl();
        Glide.with(context).load(imageLink).fitCenter().into(itemViewHolder.newsImage);
        itemViewHolder.context = context;
        itemViewHolder.newsUrl = itemForLists.get(index).getLink();

    }

    @Override
    public int getItemCount() {
        return itemForLists.size();

    }


    public void clear() {
        itemForLists.clear();
        notifyDataSetChanged();
    }


    public void addAll(List<NewsItem> list) {
        itemForLists.addAll(list);
        notifyDataSetChanged();
    }


}
