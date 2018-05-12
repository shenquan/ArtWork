package com.example.sqhan.artwork.utils;

import android.content.Context;
import android.support.v4.util.LruCache;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * https://blog.csdn.net/xiechengfa/article/details/52699233
 * 参考上面的链接
 * <p>
 * 应用私有存储（内置存储）:
 * 获取方式：
 * Context.getFileDir()：获取内置存储下的文件目录，可以用来保存不能公开给其他应用的一些敏感数据如用户个人信息
 * Context.getCacheDir()：获取内置存储下的缓存目录，可以用来保存一些缓存文件如图片，当内置存储的空间不足时
 * 将系统自动被清除（然而具体多大，清除时的策略我也没查到。。）
 * 绝对路径：
 * Context.getFileDir()：/data/data/应用包名/files/
 * Context.getCacheDir()：/data/data/应用包名/cache/
 * 写权限：不需要申请
 * <p>
 * 这是手机的内置存储，没有root的过的手机是无法用文件管理器之类的工具查看的。而且这些数据也会随着用户卸载App而被一起删除。
 * 这两个目录其实就对应着设置->应用->你的App->存储空间下面的清除数据和清除缓存
 * <p>
 * 公共存储（SD卡）:
 * 获取方式：Environment.getExternalStorageDirectory()
 * 绝对路径：SDCard/你设置的文件夹名字/
 * 写权限：需要申请
 * 如果我们的App需要存储一些公共的文件，甚至希望下载下来的文件即使在我们的App被删除之后，还可以被其他App使用，
 * 那么就可以使用这个目录，这个目录是始终需要申请SD写入权限的。
 */

/**
 * //todo 一般情况下使用SAVE工具类即可。
 * 目前这种做法没有申请android.permission.WRITE_EXTERNAL_STORAGE权限也是可以的。
 * Modified by sqhan on 2018/5/12.
 */
public class MySave {
    private static final String TAG = "HSQ";
    private static final int cacheObjNum = 30;//可以自定义缓存对象个数
    private static final String CACHE_NAME = "MY_CACHE";

    private static LruCache<String, Object> mObjMemoryLruCache = new LruCache<String, Object>(cacheObjNum) {
        @Override
        protected int sizeOf(String key, Object value) {
//            return super.sizeOf(key, value);
            return 1;//系统本来默认的是权重1
        }

        @Override
        protected void entryRemoved(boolean evicted, String key, Object oldValue, Object newValue) {
            super.entryRemoved(evicted, key, oldValue, newValue);
            Log.e(TAG, "LruCache移除对象缓存" + key);
        }
    };

    private static LruCache<String, String> mMemoryLruCache = new LruCache<String, String>((int) Runtime.getRuntime().totalMemory() / 8) {
        @Override
        protected void entryRemoved(boolean evicted, String key, String oldValue, String newValue) {
            super.entryRemoved(evicted, key, oldValue, newValue);
            Log.e(TAG, "LruCache移除字符串缓存" + key);
        }

        @Override
        protected int sizeOf(String key, String value) {
            return value.getBytes().length;//大致占的字节数
        }
    };


    /**
     * 利用SP存放数据，SP其实也是文件存储，将对象缓存至SharedPreferences中。
     * 设置中清除缓存不会清除SP，适用于安装APP只显示一次的用户引导等。
     *
     * @param context
     * @param key
     * @param obj
     */
    public synchronized static void saveObjectToSp(Context context, String key, Object obj) {
        try {
            //先将序列化结果写到byte缓存中，其实就分配一个内存空间
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(bos);
            //将对象序列化写入byte缓存
            os.writeObject(obj);
            String res = Base64.encodeToString(bos.toByteArray(), Base64.NO_WRAP);
            AndroidUtil.putString(context, key, res);
        } catch (Exception e) {

        }
    }

    /**
     * 从sp中读取缓存对象
     *
     * @param context
     * @param key
     * @return
     */
    public synchronized static Object readObjectFromSp(Context context, String key) {
        Object o = null;
        try {
            String res = AndroidUtil.getString(context, key);
            //读取字节
            byte[] base64 = Base64.decode(res.getBytes(), Base64.NO_WRAP);
            //封装到字节流
            ByteArrayInputStream bais = new ByteArrayInputStream(base64);
            //再次封装
            ObjectInputStream bis = new ObjectInputStream(bais);
            o = bis.readObject();
        } catch (Exception e) {
            o = null;
        }
        return o;
    }


    /**
     * 对象存入到内置存储的目录文件夹下（一般存储对象用这个函数）
     *
     * @param context
     * @param name
     * @param obj
     */
    ///data/data/com.xxx.xxx/files/xxx
    public synchronized static void saveObjectToFile(Context context, String name, Object obj) {
        //1，存入内存
        if (!TextUtils.isEmpty(name) && null != obj) {
            mObjMemoryLruCache.put(name, obj);
        }

        //2，存入磁盘
        FileOutputStream bos = null;
        ObjectOutputStream os = null;
        File file = null;
        try {
            File directory = new File(context.getFilesDir().getAbsolutePath() + "/" + CACHE_NAME);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            file = new File(directory, name);//AndroidUtil.newFile(CACHNAME,name);
            bos = new FileOutputStream(file);
            os = new ObjectOutputStream(bos);
            os.writeObject(obj);
        } catch (Exception e) {

        } finally {
            try {
                if (bos != null) {
                    bos.close();
                }
                if (os != null) {
                    os.close();
                }
            } catch (Exception e) {

            }

        }
    }

    /**
     * 从目录文件夹下读取对象
     *
     * @param context
     * @param name
     * @return
     */
    ///data/data/com.xxx.xxx/files
    public synchronized static Object readObjectFromFile(Context context, String name) {
        //如果内存中有，则读内存，否则读磁盘缓存
        if (!TextUtils.isEmpty(name)) {
            Object objTemp = mObjMemoryLruCache.get(name);//name不能为null
            if (null != objTemp) {
                return objTemp;
            }
        }

        FileInputStream in = null;
        ObjectInputStream oin = null;
        File file = null;
        Object obj = null;
        try {
            file = new File(context.getFilesDir().getAbsolutePath() + "/" + CACHE_NAME, name);//AndroidUtil.newFile(CACHNAME,name);
            if (!file.exists()) {
                return null;
            }
            in = new FileInputStream(file);
            oin = new ObjectInputStream(in);
            obj = oin.readObject();
            //走到这里说明没有存入内存，此处再次存入内存
            if (!TextUtils.isEmpty(name) && null != obj) {
                mObjMemoryLruCache.put(name, obj);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (oin != null) {
                    oin.close();
                }
            } catch (Exception e) {

            }

        }
        return obj;
    }

    /**
     * 存入字符串对象到内置存储目录文件夹下（存储字符串，常用）
     *
     * @param context
     * @param name
     * @param obj
     */
    public synchronized static void saveStringToFile(Context context, String name, String obj) {
        //1，存入内存
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(obj)) {
            mMemoryLruCache.put(name, obj);
        }

        //2，存入磁盘
        FileOutputStream bos = null;
        File file = null;
        try {
            File directory = new File(context.getFilesDir().getAbsolutePath() + "/" + CACHE_NAME);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            file = new File(directory, name);//AndroidUtil.newFile(CACHNAME,name);
            bos = new FileOutputStream(file);
            bos.write(obj.getBytes());
        } catch (Exception e) {

        } finally {
            try {
                if (bos != null) {
                    bos.close();
                }
            } catch (Exception e) {

            }

        }
    }

    /**
     * 读取字符串对象
     *
     * @param context
     * @param name
     * @return
     */
    public synchronized static String readStringFromFile(Context context, String name) {
        //如果内存中有，则读内存，否则读磁盘缓存
        if (!TextUtils.isEmpty(name)) {
            String result = mMemoryLruCache.get(name);//name不能为null
            if (!TextUtils.isEmpty(result)) {
                return result;
            }
        }

        FileInputStream bos = null;
        File file = null;
        String obj = null;
        try {
            file = new File(context.getFilesDir().getAbsolutePath() + "/" + CACHE_NAME, name);//AndroidUtil.newFile(CACHNAME,name);
            if (!file.exists()) {
                return null;
            }
            bos = new FileInputStream(file);
            int size = bos.available();
            byte[] data = new byte[size];
            bos.read(data);
            obj = new String(data, "UTF-8");
            //走到这里说明没有存入内存，此处再次存入内存
            if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(obj)) {
                mMemoryLruCache.put(name, obj);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bos != null) {
                    bos.close();
                }
            } catch (Exception e) {

            }

        }
        return obj;
    }

    public synchronized static String getSaveFileTime(Context context, String name) {
        try {
            File file = new File(context.getFilesDir().getAbsolutePath() + "/" + CACHE_NAME, name);//AndroidUtil.newFile(CACHNAME,name);
            if (!file.exists()) {
                return null;
            }
            long t = file.lastModified();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            return df.format(new Date(t));
        } catch (Exception e) {
            return null;
        }
    }

    public synchronized static LruCache<String, Object> getmObjMemoryLruCache() {
        return mObjMemoryLruCache;
    }

    public synchronized static void setmObjMemoryLruCache(LruCache<String, Object> mObjMemoryLruCache) {
        MySave.mObjMemoryLruCache = mObjMemoryLruCache;
    }

    public synchronized static LruCache<String, String> getmMemoryLruCache() {
        return mMemoryLruCache;
    }

    public synchronized static void setmMemoryLruCache(LruCache<String, String> mMemoryLruCache) {
        MySave.mMemoryLruCache = mMemoryLruCache;
    }
}
