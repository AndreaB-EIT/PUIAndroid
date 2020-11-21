package com.example.puiandroid.utils;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FileUtils {
    private static final String TAG_NAME = "FileUtils";

    public static void writeToFile(Context ctx, String filename, String content) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(ctx.openFileOutput(filename, Context.MODE_PRIVATE));
            outputStreamWriter.write(content);
            outputStreamWriter.close();
            Log.i(TAG_NAME, "Data stored in the file");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e(TAG_NAME, "File not found");
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG_NAME, "Error while writing in the file");
        }
    }

    public static String readFromFile(Context ctx, String filename) {
        String results = "";
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(ctx.openFileInput(filename)));
            results = bufferedReader.readLine();
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e(TAG_NAME, "File not found");
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG_NAME, "Error wile reading from file");
        }
        return results;
    }
}