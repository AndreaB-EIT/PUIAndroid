package com.example.puiandroid;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.puiandroid.task.LoadSingleArticleTask;
import com.example.puiandroid.utils.network.ModelManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ArticleDetailsActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_OPEN_IMAGE = 1;
    private static Bitmap before;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_details);

        int articleId = getIntent().getExtras().getInt("id");
        LoadSingleArticleTask task = new LoadSingleArticleTask(this, articleId);
        task.execute();

        Button back = findViewById(R.id.btn_details_back);
        back.setOnClickListener(v -> {
            Intent backToMain = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(backToMain);
        });

        Button editPic = findViewById(R.id.btn_details_editpic);
        Button undoEdit = findViewById(R.id.btn_details_undo_edit);
        if(!ModelManager.isConnected()) {
            editPic.setVisibility(View.INVISIBLE);
            editPic.setEnabled(false);
            undoEdit.setVisibility(View.INVISIBLE);
            undoEdit.setEnabled(false);
        } else {
            editPic.setVisibility(View.VISIBLE);
            editPic.setEnabled(true);
            undoEdit.setVisibility(View.VISIBLE);
        }
        editPic.setOnClickListener(v -> {
            ImageView imageView = findViewById(R.id.img_details_image);
            imageView.invalidate();
            BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
            before = drawable.getBitmap();
            undoEdit.setEnabled(true);
            undoEdit.setOnClickListener(v1 -> {
                imageView.setImageBitmap(before);
            });
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(intent, REQUEST_CODE_OPEN_IMAGE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CODE_OPEN_IMAGE:
                if(resultCode == Activity.RESULT_OK) {
                    InputStream stream = null;
                    try {
                        stream = getContentResolver().openInputStream(data.getData());
                        Bitmap bitmap = BitmapFactory.decodeStream(stream);
                        ImageView img = findViewById(R.id.img_details_image);
                        img.setImageBitmap(bitmap);

                    }
                    catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    finally {
                        if (stream != null) {
                            try {
                                stream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                else
                    Toast.makeText(this, "An error occurred, please try again", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

}