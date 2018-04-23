package com.example.sqhan.artwork.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AndroidUtil {

    public static final String NAME = "My_share_pref";

    public static Toast mToast;

    /**
     * 只显示一个toast
     */
    public static void showOneToast(Context context, String text) {
        if (mToast == null) {
            mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
        }
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.show();
    }

    /**
     * toast显示在屏幕中间
     *
     * @param context
     * @param text
     * @param showCenter   是否显示在屏幕中间
     * @param longDuration 显示时间是否为LONG，否则为SHORT
     */
    public static void showOneToast(Context context, String text, boolean showCenter, boolean longDuration) {
        if (mToast == null) {
            mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
        }
        if (showCenter) {
            mToast.setGravity(Gravity.CENTER, 0, 0);
        }
        if (longDuration) {
            mToast.setDuration(Toast.LENGTH_LONG);
        } else {
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    /**
     * toast居中
     *
     * @param context
     * @param title
     */
    public static void showToastCenter(Context context, String title) {
        Toast toast = Toast.makeText(context, title, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * 得到屏幕的宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        if (null == context) {
            return 0;
        }
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 得到屏幕的高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        if (null == context) {
            return 0;
        }
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(Context context, float dpValue) {
        if (null == context) {
            return 0;
        }
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param context
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        if (null == context) {
            return 0;
        }
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * ● android:versionCode:主要是用于版本升级所用，是INT类型的，第一个版本定义为1，以后递增，
     * 这样只要判断该值就能确定是否需要升级，该值不显示给用户。。
     * return
     */
    public static int getIntVersionCode(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            int flags = PackageManager.GET_CONFIGURATIONS;
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), flags);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return 0;
    }

    /**
     * 检查当前网络是否可用
     *
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        Object connectivityService = context.getSystemService(Context.CONNECTIVITY_SERVICE);
        ConnectivityManager connectivityManager = (ConnectivityManager) connectivityService;
        if (connectivityManager == null) {
            return false;
        } else {
            NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();
            if (networkInfos != null && networkInfos.length > 0) {
                for (NetworkInfo networkInfo : networkInfos) {
                    if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    /**
     * 使用java正则表达式去掉多余的.与0
     *
     * @param s
     * @return
     */
    public static String subZeroAndDot(String s) {
        if (s == null) return "";
        if (s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }

    public static boolean isDebugEnv(Context context) {
        ApplicationInfo info = context.getApplicationInfo();
        boolean isDug = (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0 ? true : false;
        return isDug;
    }

    /**
     * 获取软件版本号
     * return 2.0
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            int flags = PackageManager.GET_CONFIGURATIONS;
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), flags);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return null;
    }

    /**
     * 利用SP存放数据，SP其实也是文件存储
     *
     * @param context
     * @param key
     * @param value
     */
    public static void putString(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
    }

    /**
     * 带默认值的
     *
     * @param context
     * @param key
     * @param def
     * @return
     */
    public static String getString(Context context, String key, String def) {
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getString(key, def);
    }

    /**
     * 不带默认值的
     *
     * @param context
     * @param key
     * @return
     */
    public static String getString(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getString(key, "");
    }


    /**
     * 获取软件版本号,获取的是Main中的build.gradle的versionName。
     * ● android:versionName:这个是我们常说明的版本号，
     * 由三部分组成<major>.<minor>.<point>,该值是个字符串，可以显示给用户。
     * return 2.0
     */
    public static String getStringVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            int flags = PackageManager.GET_CONFIGURATIONS;
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), flags);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return "";
    }

    /**
     * 得到国家与区域
     *
     * @param context
     * @return
     */
    public static String getCountryAndRegion(Context context) {
        return context.getResources().getConfiguration().locale.getCountry();
    }

    /**
     * 展示图片的函数
     */
    /*public static void showUrlImageCommon(ImageView imageView, String imgUrl, boolean isDefaultRectangle) {
        DisplayImageOptions.Builder optsBuilder = new DisplayImageOptions.Builder();
        if (isDefaultRectangle) {
            optsBuilder.showStubImage(R.drawable.nopic_rectangle)
                    .showImageForEmptyUri(R.drawable.nopic_rectangle)
                    .showImageOnFail(R.drawable.nopic_rectangle)
                    .cacheInMemory(true)
                    .cacheOnDisc(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .build();
        } else {
            optsBuilder.showStubImage(R.drawable.nopic_square)
                    .showImageForEmptyUri(R.drawable.nopic_square)
                    .showImageOnFail(R.drawable.nopic_square)
                    .cacheInMemory(true)
                    .cacheOnDisc(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .build();
        }
        ImageLoader.getInstance().displayImage(imgUrl, imageView, optsBuilder.build());
    }*/
    public static String getJsonStr(String UriStr, String key) {
        String str = "";
        if (!TextUtils.isEmpty(UriStr)) {
            try {
                JSONObject jsonObject = new JSONObject(UriStr);
                str = jsonObject.getString(key);
            } catch (Exception e) {
                e.printStackTrace();
                str = "";
            }
        }
        return str;
    }

    public static int getJsonInt(String UriStr, String key) {
        int str = 0;
        if (!TextUtils.isEmpty(UriStr)) {
            try {
                JSONObject jsonObject = new JSONObject(UriStr);
                str = jsonObject.getInt(key);
            } catch (Exception e) {
                e.printStackTrace();
                str = 0;
            }
        }
        return str;
    }

    public static Long getJsonLong(String UriStr, String key) {
        long str = 0;
        if (!TextUtils.isEmpty(UriStr)) {
            try {
                JSONObject jsonObject = new JSONObject(UriStr);
                str = jsonObject.getLong(key);
            } catch (Exception e) {
                e.printStackTrace();
                str = 0;
            }
        }
        return str;
    }

    public static boolean getJsonBoolean(String UriStr, String key) {
        boolean flag = false;
        if (!TextUtils.isEmpty(UriStr)) {
            try {
                JSONObject jsonObject = new JSONObject(UriStr);
                flag = jsonObject.getBoolean(key);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return flag;
    }

    /**
     * 日期转换
     *
     * @param timestamp
     * @return
     */
    public static String buildRefreshTimeString(String timestamp) {
        try {
            if (TextUtils.isEmpty(timestamp)) {
                return "";
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm", Locale.getDefault());
            return sdf.format(new Date(Long.valueOf(timestamp)));
        } catch (Exception e) {

        }
        return "";
    }

    /**
     * 判断邮箱是否合法
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (null == email || "".equals(email)) return false;
        //Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配
        Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /*
     * 将时间转换为时间戳
     */
    public static long dateToStamp(String s) {
        long ts;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = simpleDateFormat.parse(s);
            ts = date.getTime();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            ts = 0;
        }

        return ts;
    }
}
