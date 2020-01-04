package com.qinkuai.homework1.tool;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

public class ToastShow {
    public static void toastShow(Context context, String text){
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }
}
