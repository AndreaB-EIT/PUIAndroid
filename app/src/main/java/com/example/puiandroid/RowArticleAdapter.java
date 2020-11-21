package com.example.puiandroid;

import android.content.Context;
import android.graphics.Bitmap;
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
    Context context;

    public RowArticleAdapter(Context context) {
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
        return data.get(position).getId();
    }

    // click on a row = new activity, get single article

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.article_card_layout, null);
        InputStream stream = null;

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
