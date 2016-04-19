package by.maesens.android.services;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import by.maesens.android.events.EventUpdateUser;
import by.maesens.android.model.User;
import by.maesens.android.network.ServiceHelper;

/**
 * Created by Никита on 09.04.2016.
 */
public class UploadUserInfoService extends IntentService {

    public static final String ARG_FILE_PATH = "imagePath";
    public static final String ARG_USER = "userObject";

    public UploadUserInfoService() {
        super("uploadUserInfoService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("UploadUserInfoService", "onHandleIntent");
        User user = intent.getParcelableExtra(ARG_USER);
        String filePath = intent.getStringExtra(ARG_FILE_PATH);
        if (filePath == null){
            Log.d("UploadUserInfoService", "filePath null");
            user.setAvatar("");
        }else {
            String encodedImage = getEncodedImage(filePath);
            if (encodedImage != null){
                Log.d("UploadUserInfoService", "encodedImage NOT null");
                user.setAvatar("\"data:image/png;base64," + encodedImage + "\"");
            } else {
                Log.d("UploadUserInfoService", "encodedImage NULL");
                user.setAvatar("");
            }
        }

        EventUpdateUser eventUpdateUser = new EventUpdateUser();
        eventUpdateUser.setUpdateUser(user);
        try {
            ServiceHelper.getInstance().getData(new String[]{}, eventUpdateUser, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getEncodedImage(String filePath) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        //Bitmap bitmap = BitmapFactory.decodeFile(mPhotoFile.getAbsolutePath());
        Bitmap bitmap = decodeFile(new File(filePath));
        if (bitmap != null) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
            byte[] b = baos.toByteArray();
            return Base64.encodeToString(b, Base64.DEFAULT);
        } else
            return null;
    }

    private Bitmap decodeFile(File f) {
        try {
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            // The new size we want to scale to
            final int REQUIRED_SIZE = 300;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {
            Log.d("decodeFile", e.getMessage());
        }
        return null;
    }
}
