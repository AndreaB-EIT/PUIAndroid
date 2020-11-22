package com.example.puiandroid.task;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.puiandroid.R;
import com.example.puiandroid.model.Image;
import com.example.puiandroid.utils.SerializationUtils;
import com.example.puiandroid.utils.network.ModelManager;
import com.example.puiandroid.utils.network.exceptions.ServerCommunicationError;

public class SaveNewImageTask extends AsyncTask<Void, Void, Boolean> {

    private Bitmap bitmap;
    private int orderIfEdit;
    private int idArticle;
    private Activity activity;
    private ProgressBar progressBar;
    private String message;

    public SaveNewImageTask(Bitmap bitmap, int orderIfEdit, int idArticle, String message, Activity activity) {
        this.bitmap = bitmap;
        this.orderIfEdit = orderIfEdit;
        this.idArticle = idArticle;
        this.activity = activity;
        this.message = message;
    }

    private void setMessage() {
        switch (message) {
            case "edit":
                message = "Image updated successfully!";
                break;
            case "undoEdit":
                message = "Restored the previous image!";
                break;
            default:
                break;
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        activity.findViewById(R.id.btn_details_back).setEnabled(false);

        progressBar = activity.findViewById(R.id.pgb_details_image_edit);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setProgress(0);

        setMessage();
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            Image image = new Image(orderIfEdit, "", idArticle, SerializationUtils.imgToBase64String(bitmap));
            progressBar.setProgress(20);
            ModelManager.saveImage(image);
            progressBar.setProgress(80);
        } catch (ServerCommunicationError authenticationError) {
            authenticationError.printStackTrace();
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean aVoid) {
        super.onPostExecute(aVoid);

        progressBar.setVisibility(View.GONE);
        ImageView img = activity.findViewById(R.id.img_details_image);
        img.setImageBitmap(bitmap);
        if (message.equals("Restored the previous image!"))
            activity.findViewById(R.id.btn_details_undo_edit).setEnabled(false);
        activity.findViewById(R.id.btn_details_back).setEnabled(true);


        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }
}
