package com.example.sqhan.artwork.utils;

import android.content.Context;
import android.util.Base64;

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
 * 目前这种做法没有申请android.permission.WRITE_EXTERNAL_STORAGE权限也是可以的。
 * Modified by sqhan on 2018/5/12.
 */

public class Save {

    private static final String CACHE_NAME = "MY_CACHE";

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
        FileOutputStream bos = null;
        ObjectOutputStream os = null;
        File file = null;
        try {
            File directory = new File(context.getFilesDir().getAbsolutePath() + "/" + CACHE_NAME);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            file = new File(directory, name);
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

    ///data/data/com.xxx.xxx/files
    public synchronized static Object readObjectFromFile(Context context, String name) {
        FileInputStream in = null;
        ObjectInputStream oin = null;
        File file = null;
        try {
            file = new File(context.getFilesDir().getAbsolutePath() + "/" + CACHE_NAME, name);
            if (!file.exists()) {
                return null;
            }
            in = new FileInputStream(file);
            oin = new ObjectInputStream(in);
            Object obj = oin.readObject();
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
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
    }

    /**
     * 存入字符串对象到内置存储目录文件夹下（存储字符串，常用）
     *
     * @param context
     * @param name
     * @param obj
     */
    public synchronized static void saveStringToFile(Context context, String name, String obj) {
        FileOutputStream bos = null;
        File file = null;
        try {
            File directory = new File(context.getFilesDir().getAbsolutePath() + "/" + CACHE_NAME);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            file = new File(directory, name);
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

    public synchronized static String readStringFromFile(Context context, String name) {
        FileInputStream bos = null;
        File file = null;
        try {
            file = new File(context.getFilesDir().getAbsolutePath() + "/" + CACHE_NAME, name);
            if (!file.exists()) {
                return null;
            }
            bos = new FileInputStream(file);
            int size = bos.available();
            byte[] data = new byte[size];
            bos.read(data);
            return new String(data, "UTF-8");
        } catch (Exception e) {
            return null;
        } finally {
            try {
                if (bos != null) {
                    bos.close();
                }
            } catch (Exception e) {

            }

        }
    }

    public synchronized static String getSaveFileTime(Context context, String name) {
        try {
            File file = new File(context.getFilesDir().getAbsolutePath() + "/" + CACHE_NAME, name);
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
}
