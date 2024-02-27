package com.nikhil.nicapp.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class BitmapUtils {

    public static Uri saveBitmap(Context context, Bitmap bitmap) {
        Uri imageUri = null;
        try {
            // Save the bitmap to local app storage
            File imagesDir = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "images");
            if (!imagesDir.exists()) {
                imagesDir.mkdirs();
            }
            File imageFile = new File(imagesDir, "image_" + System.currentTimeMillis() + ".jpg");
            OutputStream outputStream = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();

            // Add the image to the device's MediaStore to make it accessible by other apps
            String imageFileName = "IMG_" + System.currentTimeMillis() + ".jpg";
            MediaStore.Images.Media.insertImage(context.getContentResolver(), imageFile.getAbsolutePath(), imageFileName, null);

            // Get the URI of the saved image
            imageUri = Uri.fromFile(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageUri;
    }
}
