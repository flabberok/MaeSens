package by.maesens.android.loaders;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import by.maesens.android.network.TrustedHttpClient;

/**
 * Created by Виктор on 18.02.2016.
 */
public class PicassoLoader {

    private static Picasso mInstance = null;

    private PicassoLoader(Context context) {

        mInstance = new Picasso.Builder(context)
                .downloader(new OkHttp3Downloader(TrustedHttpClient.getInstance()))
                .listener(new Picasso.Listener() {
                    @Override
                    public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                        Log.e("PICASSO", exception.toString());
                    }
                }).build();
    }

    public static Picasso getInstance(Context context) {
        if (mInstance == null) {
            new PicassoLoader(context);
        }
        return mInstance;
    }
}