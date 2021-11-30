package com.example.android.camera2video;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


// I might have to change this class to run asynchronously so to not block when being called in
 // onSurfaceTextureUpdated of Camera2VideoFragment
public final class FrameProcessor {

    private static final String TAG = "FrameProcessor";
    private static int count = -1;
    private static final String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() +
            "/ImagesFromTextureView";

    /**
     * we need to ask for permission to write to storage at runtime.
     * I have to bypass this
     */
    public static void saveBitmapToFile(Bitmap bmp) {

        count++;

        Log.v(TAG, "Saving Bitmap To File. Path:");
        Log.v(TAG, file_path);

        File dir = new File(file_path);
        if(!dir.exists())
            Log.v(TAG, "created directory");
            dir.mkdirs();
        File file = new File(dir, "sketchpad" + count + ".png");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                Log.w(TAG, "File did not exist, so tried to create it, but failed at file.createNewFile");
                e.printStackTrace();
            }
        }
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 85, fOut);
            fOut.flush();
            fOut.close();
        } catch (FileNotFoundException e) {
            Log.w(TAG, "File OutputStream encountered problems writing File");
            e.printStackTrace();
        } catch (IOException ex) {

            ex.printStackTrace();
        }


    }
}
