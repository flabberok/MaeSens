package by.maesens.android.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.photo.VKImageParameters;
import com.vk.sdk.api.photo.VKUploadImage;
import com.vk.sdk.dialogs.VKShareDialog;
import com.vk.sdk.dialogs.VKShareDialogBuilder;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Sol on 24.03.2016.
 */
public class SocialNetwork {

    private static final String[] sMyScope = new String[]{
            VKScope.WALL,
            VKScope.PHOTOS,
            VKScope.NOHTTPS,
            VKScope.MESSAGES,
            VKScope.OFFLINE,
    };

    /**
     * Checks if the user logged in VK. If not, offers to log in.
     * If user logged in shows dialog with sharing to social network.
     */
    public static void vkShare(Context context,String title,String description,String url,Drawable drawable) {
        if (VKSdk.isLoggedIn()) {
            showDialog(context,title,description,url,drawable);
        } else {
            VKSdk.login((Activity)context, sMyScope);
        }
    }

    /**
     * Shows dialog to share information to Facebook social network
     */
    public static void fbShare(Context context,String title,String description,String url,String imageUrl) {
        CallbackManager callbackManager = CallbackManager.Factory.create();
        ShareDialog shareDialog = new ShareDialog((Activity)context);
        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
                Log.d("AuctionFragment", "onSuccess " + result);
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("AuctionFragment", "onError " + error);
            }
        });
        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setContentTitle(title)
                    .setContentDescription(description)
                    .setContentUrl(Uri.parse(url)) //https://maesens.by/lot/ + mAuctionId
                            .setImageUrl(Uri.parse(imageUrl))
                            .build();

            shareDialog.show(linkContent);
        }
    }

    /**
     * Shows dialog to share information to VK social network
     */
    private static void showDialog(Context context,String title,String description,String url,Drawable drawable){
        final Bitmap image = ((BitmapDrawable) drawable).getBitmap();
        new VKShareDialogBuilder()
                .setAttachmentLink(title,
                        url) //https://maesens.by/lot/ + mAuctionId
                .setText(description)
                .setAttachmentImages(new VKUploadImage[]{
                        new VKUploadImage(image, VKImageParameters.pngImage())
                })
                .setShareDialogListener(new VKShareDialog.VKShareDialogListener() {
                    @Override
                    public void onVkShareComplete(int postId) {
                        Log.d("AuctionFragment", "onVkShareComplete " + postId);
                    }

                    @Override
                    public void onVkShareCancel() {
                    }

                    @Override
                    public void onVkShareError(VKError error) {
                        Log.d("AuctionFragment", "onVkShareError " + error);
                    }
                })
                .show(((Activity) context).getFragmentManager(), "VK_SHARE_DIALOG");
    }

    public static void twitterShare(Context context,String title, String url){
        URL urlProject = null;
        try {
            urlProject = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        final TweetComposer.Builder builder
                = new TweetComposer.Builder(context)
                .text(title)
                .url(urlProject);
        builder.show();
    }
}
