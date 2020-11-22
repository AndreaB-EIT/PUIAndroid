package com.example.puiandroid;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.puiandroid.model.Article;
import com.example.puiandroid.utils.SerializationUtils;
import com.example.puiandroid.utils.network.exceptions.ServerCommunicationError;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

public class RowArticleAdapter extends BaseAdapter {
    // the adapter is the link between data and UI
    private List<Article> data = new LinkedList<>();
    private Context context;
    private String filter = "All";

    public RowArticleAdapter(Context context) {
        this.context = context;
    }

    public RowArticleAdapter(Context context, String filter) {
        this.context = context;
        this.filter = filter;
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
        return data.get(position).getId();
    }

    // click on a row = new activity, get single article

    public List<Article> getFilteredList() {
        List<Article> filtered = new LinkedList<>();

        for (Article article : data)
            if (article.getCategory() == filter)
                filtered.add(article);

        return filtered;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(filter != "All") {
            data = getFilteredList();
        }

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.article_card_layout, null);

        ImageView imageViewThumbnail = view.findViewById(R.id.img_row_image);
        Bitmap image = null;
        try {
            image = SerializationUtils.base64StringToImg(data.get(position).getImage().getImage());
        } catch (ServerCommunicationError serverCommunicationError) {
            serverCommunicationError.printStackTrace();
        }
        if (image == null)
            imageViewThumbnail.setImageDrawable(context.getResources().getDrawable(R.drawable.upmlogo, context.getTheme()));
        else
            imageViewThumbnail.setImageBitmap(image);

        TextView textViewCategory = view.findViewById(R.id.lbl_row_category);
        textViewCategory.setText(data.get(position).getCategory());

        TextView textViewTitle = view.findViewById(R.id.lbl_row_title);
        textViewTitle.setText(data.get(position).getTitleText());

        TextView textViewAbstract = view.findViewById(R.id.lbl_row_aabstract);
        textViewAbstract.setText(data.get(position).getAbstractText());

        return view;
    }
}
