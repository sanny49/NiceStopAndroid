package com.example.nicestop.Model;

import android.content.Context;
import android.content.SharedPreferences;

public class Login {
    public static void save(Context ctx, String name, String value){
        SharedPreferences s = ctx.getSharedPreferences("clipcodes",Context.MODE_PRIVATE);
        SharedPreferences.Editor edt = s.edit();
        edt.putString(name,value);
        edt.apply();

    }
    public static String read(Context ctx, String name, String defultvalue) {
        SharedPreferences s = ctx.getSharedPreferences("clipcodes", Context.MODE_PRIVATE);
        return s.getString(name, defultvalue);
    }
}