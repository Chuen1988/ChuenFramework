package com.cblib.util;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.webkit.WebView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Locale;

public class CBUtil {

    /**
     * Do not initialze instance for static class only
     */
    private CBUtil() {
    }

    /**
     * Scale size
     *
     * @param _resources
     * @param size
     * @return
     */
    public static float scaleSize(Resources _resources, int size) {
        float scale = _resources.getDisplayMetrics().scaledDensity;
        int textSizeP = (int) (size / scale);
        return (float) textSizeP;
    }

    /**
     * Convert Byte Array to Bitmap
     * @param imageByteArray
     * @return
     */
    public static Bitmap convertByteArrayToBitmap(byte[] imageByteArray) {
        return BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
    }

    /**
     * Store Image to local phone Cache (Without Storage Permission)
     * @param context
     * @param imageBitmap
     * @param fileName
     * @param isResize
     * @return
     */
    public static String storeImageToCacheDir(Context context, Bitmap imageBitmap, String fileName, boolean isResize) {
        File imageFile = null;
        Bitmap normalBitmap = null;
        try {
            normalBitmap = Bitmap.createScaledBitmap(imageBitmap, imageBitmap.getWidth(), imageBitmap.getHeight(), false);

            double weight = imageBitmap.getWidth();
            double height = imageBitmap.getHeight();

            // 750 x 450 = 301kb, 600 x 360= 197 kb, 400 x 240 = 101kb
            if (isResize) {
                if (normalBitmap.getWidth() > normalBitmap.getHeight()) {
                    weight = 600;
                    height = weight * ((double) normalBitmap.getHeight() / (double) normalBitmap.getWidth());
                } else {
                    height = 600;
                    weight = height * ((double) normalBitmap.getWidth() / (double) normalBitmap.getHeight());
                }
            }

            imageBitmap = Bitmap.createScaledBitmap(normalBitmap, (int) weight, (int) height, false);
            imageFile = new File(context.getCacheDir(), fileName);
            OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(imageFile));
            outputStream.flush();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException npe) {
            npe.printStackTrace();
            return "";
        } finally {
            normalBitmap.recycle();
            imageBitmap.recycle();
            normalBitmap = null;
            imageBitmap = null;
        }

        return Uri.fromFile(imageFile).getPath();
    }

    /**
     * Update language in whole App
     *
     * @param _context
     * @param _languageCode
     */
    public static void updateLanguageForApp(Context _context, String _languageCode) {
        //TODO Update Global variable if any
        //ISubject.Companion.getManagerInstance().setLanguageCode(_languageCode);
        //TODO Update Shared Preference Language
        //SPreference.setAppLanguage(_context, _languageCode);

        Locale locale = new Locale(_languageCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        _context.getResources().updateConfiguration(config, _context.getResources().getDisplayMetrics());

        //For Nougat Version 24 & above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            new WebView(_context).destroy();
    }
}
