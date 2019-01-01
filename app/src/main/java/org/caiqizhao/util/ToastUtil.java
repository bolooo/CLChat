package org.caiqizhao.util;

import android.content.Context;
import android.widget.Toast;

import com.example.bolo.chat.R;

/**
 * Created by caiqizhao on 2018/12/21.
 */

public class ToastUtil {
    public static void showToast(Context context , String str){
        Toast.makeText(context, str,Toast.LENGTH_SHORT).show();
    }
}
