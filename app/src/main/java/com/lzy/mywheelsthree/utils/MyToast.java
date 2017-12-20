package com.lzy.mywheelsthree.utils;

import android.widget.Toast;

import com.lzy.mywheelsthree.app.MyApplication;


/**
 * Created by bullet on 2017/11/10.
 */

public class MyToast {
    private static String oldMsg;
    protected static Toast toast = null;
    private static long oneTime=0;
    private static long twoTime=0;

    public static void makeText( String s){
        if(toast==null){
            toast = Toast.makeText(MyApplication.getInstance(), s, Toast.LENGTH_SHORT);
            toast.show();
            oneTime= System.currentTimeMillis();
        }else{
            twoTime= System.currentTimeMillis();
            if(s.equals(oldMsg)){
                if(twoTime-oneTime> Toast.LENGTH_SHORT){
                    toast.show();
                }
            }else{
                oldMsg = s;
                toast.setText(s);
                toast.show();
            }
        }
        oneTime=twoTime;
    }


    public static void makeText( int resId){
        makeText(MyApplication.getInstance().getString(resId));
    }

}
