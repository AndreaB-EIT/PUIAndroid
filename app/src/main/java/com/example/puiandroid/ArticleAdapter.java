package com.example.puiandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.puiandroid.model.Article;

import java.util.LinkedList;
import java.util.List;

public class ArticleAdapter extends BaseAdapter {
    // the adapter is the link between data and UI
    private List<Article> data = new LinkedList<>();
    Context context;

    public ArticleAdapter(Context context) {
        this.context = context;
    }

    public void addArticles(List<Article> list) {
        data.addAll(list);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.article_card_layout, null);

        return view;
    }
}
