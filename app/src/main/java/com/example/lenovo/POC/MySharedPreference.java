package com.example.lenovo.POC;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPreference {
    static final String CACHE = "cache";

    /*
    * Modifier la valeur de la variable json
    */
    public static void edit(Context ctx, String prefName, String val) {
        SharedPreferences.Editor editor = ctx.getSharedPreferences(prefName, ctx.MODE_PRIVATE).edit();
        editor.putString("json", val);
        editor.commit();
    }

    /*
     * Récuperer la valeur de la variable json
     * */
    public static String getCache(Context ctx, String prefName) {
        SharedPreferences prefs = ctx.getSharedPreferences(prefName, ctx.MODE_PRIVATE);
        return prefs.getString("json", CACHE);

    }
}
