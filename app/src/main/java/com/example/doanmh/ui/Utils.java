package com.example.doanmh.ui;

import android.content.Context;
import android.graphics.Typeface;

public class Utils {
    public static final  String SHARE_PREFERENCES_APP = "share_preferences_app";
    public static final  String KEY_ACCOUNT = "key_account";
    public static final  String KEY_IS_LOGIN = "key_is_login";
    public static final  String KEY_USER = "key_user";
    public static final  String KEY_USER_PROFILE = "key_user_profile";
    private static Typeface oswaldTypeface;
    private static Typeface ralewayTypeface;
    private static Typeface playfairTypeface;

    public static Typeface getOswaldTypeface(Context context) {
        if(oswaldTypeface == null){
            oswaldTypeface = Typeface.createFromAsset(context.getAssets(), "font/Oswald-Regular.ttf");
        }
        return oswaldTypeface;
    }

    public static Typeface getRalewayTypeface(Context context) {
        if(ralewayTypeface == null){
            ralewayTypeface = Typeface.createFromAsset(context.getAssets(), "font/Raleway-Regular.ttf");
        }
        return ralewayTypeface;
    }

    public static Typeface getPlayfairTypeface(Context context) {
        if(playfairTypeface == null){
            playfairTypeface = Typeface.createFromAsset(context.getAssets(), "font/PlayfairDisplay-BlackItalic.otf");
        }
        return playfairTypeface;
    }
}
