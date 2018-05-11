package com.example.sqhan.artwork.permission;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

import com.example.sqhan.artwork.utils.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * 权限管理工具类
 * Modified by sqhan on 2018/5/11
 * <p>
 * 站在顶峰，看世界
 * 落在谷底，思人生
 */
public final class PermissionsDispatcher {
    private static boolean mIsShowDialog = true;
    public static final String TAG_PERMISSION_UNSHOW = "TAG_PERMISSION_UNSHOW";

    private PermissionsDispatcher() {
    }

    public static void checkPermissions(final Activity act, int requestCode, PermissionListener listener, boolean isShowDialog, String... permissions) {
        mIsShowDialog = isShowDialog;
        checkPermissions(act, requestCode, listener, permissions);
    }

    /**
     * check permissions are whether granted or not
     *
     * @param act
     * @param requestCode
     * @param listener
     * @param permissions
     */
    public static void checkPermissions(final Activity act, int requestCode, PermissionListener listener, String... permissions) {

        if (act == null) {
            if (listener != null) {
                listener.onPermissionsError(requestCode, null, "checkPermissions()-->param act :the activity is null", permissions);
            }
            return;
        }
        if (permissions == null || permissions.length < 1) {
            if (listener != null) {
                listener.onPermissionsError(requestCode, null, "checkPermissions()-->param permissions: is null or length is 0", permissions);
            }
            return;
        }

        //1.区分权限已授权和未授权
        PermissionUtils.sortGrantedAndDeniedPermissions(act, permissions);
        //2.已授权权限进行处理，调用 listener.onPermissionsGranted
        if (PermissionUtils.getGrantedPermissions().size() > 0) {
            List<String> grantedPermissionsList = PermissionUtils.getGrantedPermissions();
            String[] grantedPermissionsArr = grantedPermissionsList.toArray(new String[grantedPermissionsList.size()]);

            if (listener != null) {
                listener.onPermissionsGranted(requestCode, null, grantedPermissionsArr);
            }
        }
        //3.未授权权限进行处理，区分“不再提醒”还是“用户拒绝”
        if (PermissionUtils.getDeniedPermissions().size() > 0) {
            List<String> deniedPermissionsList = PermissionUtils.getDeniedPermissions();
            String[] deniedPermissionsArr = deniedPermissionsList.toArray(new String[deniedPermissionsList.size()]);
            if (deniedPermissionsArr.length > 0) {
                PermissionUtils.sortUnshowPermission(act, deniedPermissionsArr);
            }
        }
        //4.不再提醒权限处理，会回调listener.onShowRequestPermissionRationale，可在该回调方法中重新申请权限
        //在onShowRequestPermissionRationale方法中根据isShowRationale字段判断是“用户拒绝”还是“不再提醒”
        //TODO 应用首次申请权限时和不再提醒一样返回false,已处理
        if (PermissionUtils.getUnshowedPermissions().size() > 0) {
            List<String> unShowPermissionsList = PermissionUtils.getUnshowedPermissions();
            String[] unShowPermissionsArr = unShowPermissionsList.toArray(new String[unShowPermissionsList.size()]);

            //如果SharePreference中已存在该permission,说明不是首次检查,可弹出自定义弹框,否则首次只弹系统弹框,不弹自定义弹框
            int len = PermissionUtils.getUnshowedPermissions().size();
            boolean isCanShow = false;
            for (int i = 0; i < len; i++) {
                String permission = PermissionUtils.getUnshowedPermissions().get(i);
                if (SharedPreferencesUtil.contains(act, permission)) {
                    isCanShow = true;
                }
            }

            if (listener != null) {
                if (mIsShowDialog) {
                    StringBuilder message = getUnShowPermissionsMessage(unShowPermissionsList);
                    if (isCanShow) {
                        showMessageGotoSetting(message.toString(), act);
                    }
                }
                //如果是首次申请权限，则isCanShow为false，如果是“不再提醒”，isCanShow为true
                listener.onShowRequestPermissionRationale(requestCode, !isCanShow, unShowPermissionsArr);
            }
        }

        mIsShowDialog = true;
        //5.用户拒绝权限进行处理，和“不再提醒”一样会回调listener.onShowRequestPermissionRationale
        //在onShowRequestPermissionRationale方法中根据isShowRationale字段判断是“用户拒绝”还是“不再提醒”
        if (PermissionUtils.getNeedRequestPermissions().size() > 0) {//true 表示允许弹申请权限框
            List<String> needRequestPermissionsList = PermissionUtils.getNeedRequestPermissions();
            String[] needRequestPermissionsArr = needRequestPermissionsList.toArray(new String[needRequestPermissionsList.size()]);
            if (listener != null) {
                //如果用户拒绝权限申请，回调为true
                listener.onShowRequestPermissionRationale(requestCode, true, needRequestPermissionsArr);
            }
        }
    }

    /**
     * 得到未授权的：包括“不再提醒”，“不再提醒”的数组
     * by sqhan
     *
     * @param act
     * @param permissions
     */
    public static void getNotAuthorizationPermissionsArray(final Activity act, String... permissions) {
        //1.区分权限已授权和未授权
        PermissionUtils.sortGrantedAndDeniedPermissions(act, permissions);
        //2.未授权权限进行处理，区分“不再提醒”还是“不再提醒”
        if (PermissionUtils.getDeniedPermissions().size() > 0) {
            List<String> deniedPermissionsList = PermissionUtils.getDeniedPermissions();
            String[] deniedPermissionsArr = deniedPermissionsList.toArray(new String[deniedPermissionsList.size()]);
            if (deniedPermissionsArr.length > 0) {
                PermissionUtils.sortUnshowPermission(act, deniedPermissionsArr);
            }
        }
    }

    /**
     * 对于不再提醒权限的处理
     * by sqhan
     */
    public static void handleNotRemind(final Activity act) {
        if (PermissionUtils.getUnshowedPermissions().size() > 0) {
            List<String> unShowPermissionsList = PermissionUtils.getUnshowedPermissions();
            StringBuilder message = getUnShowPermissionsMessage(unShowPermissionsList);
            new android.support.v7.app.AlertDialog.Builder(act)
                    .setMessage(message)
                    .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            gotoPermissionSetting(act);
                            act.finish();//关闭splash页面，防止用户没有真正的设置权限。使其重新进入app即可。
                        }
                    })
                    .create().show();
        }
    }


    private static StringBuilder getUnShowPermissionsMessage(List<String> list) {
        StringBuilder message = new StringBuilder("您已关闭了 ");
        String permisson;
        boolean hasCALENDAR = false;
        boolean hasCAMERA = false;
        boolean hasCONTACTS = false;
        boolean hasLOCATION = false;
        boolean hasMICROPHONE = false;
        boolean hasPHONE = false;
        boolean hasSENSORS = false;
        boolean hasSMS = false;
        boolean hasSTORAGE = false;

        if (list.size() == 1) {
            permisson = list.get(0);
            if (permisson.contains("CALENDAR")) {
                message.append("日历 ");
            } else if (permisson.contains("CAMERA")) {
                message.append("相机 ");

            } else if (permisson.contains("CONTACTS") || permisson.equals("android.permission.GET_ACCOUNTS")) {
                message.append("通讯录 ");

            } else if (permisson.contains("LOCATION")) {
                message.append("定位 ");

            } else if (permisson.equals("android.permission.RECORD_AUDIO")) {
                message.append("耳麦 ");

            } else if (permisson.contains("PHONE")
                    || permisson.contains("CALL_LOG")
                    || permisson.contains("ADD_VOICEMAIL")
                    || permisson.contains("USE_SIP")
                    || permisson.contains("PROCESS_OUTGOING_CALLS")) {
                message.append("电话 ");

            } else if (permisson.contains("BODY_SENSORS")) {
                message.append("身体传感 ");

            } else if (permisson.contains("SMS")
                    || permisson.contains("RECEIVE_WAP_PUSH")
                    || permisson.contains("RECEIVE_MMS")
                    || permisson.contains("READ_CELL_BROADCASTS")) {
                message.append("短信 ");

            } else if (permisson.contains("STORAGE")) {
                message.append("手机存储 ");

            }
        } else {
            for (int i = 0; i < list.size(); i++) {
                permisson = list.get(i);
                if (permisson.contains("CALENDAR") && hasCALENDAR == false) {
                    message.append("日历");
                    hasCALENDAR = true;
                } else if (permisson.contains("CAMERA") && hasCAMERA == false) {
                    message.append("相机");
                    hasCAMERA = true;
                } else if (permisson.contains("CONTACTS")
                        || permisson.equals("android.permission.GET_ACCOUNTS")
                        && hasCONTACTS == false) {
                    message.append("通讯录");
                    hasCONTACTS = true;
                } else if (permisson.contains("LOCATION") && hasLOCATION == false) {
                    message.append("定位");
                    hasLOCATION = true;
                } else if (permisson.equals("android.permission.RECORD_AUDIO") && hasMICROPHONE == false) {
                    message.append("耳麦");
                    hasMICROPHONE = true;
                } else if (permisson.contains("PHONE")
                        || permisson.contains("CALL_LOG")
                        || permisson.contains("ADD_VOICEMAIL")
                        || permisson.contains("USE_SIP")
                        || permisson.contains("PROCESS_OUTGOING_CALLS") && hasPHONE == false) {
                    message.append("电话");
                    hasPHONE = true;
                } else if (permisson.contains("BODY_SENSORS") && hasSENSORS == false) {
                    message.append("身体传感");
                    hasSENSORS = true;
                } else if (permisson.contains("SMS")
                        || permisson.contains("RECEIVE_WAP_PUSH")
                        || permisson.contains("RECEIVE_MMS")
                        || permisson.contains("READ_CELL_BROADCASTS") && hasSMS == false) {
                    message.append("短信");
                    hasSMS = true;
                } else if (permisson.contains("STORAGE") && hasSTORAGE == false) {
                    message.append("手机存储");
                    hasSTORAGE = true;
                }
                if (i < list.size() - 1) {
                    message.append(",");
                }
            }
        }

        message.append("访问权限，为了保证功能的正常使用，请前往系统设置页面开启");
        return message;
    }

    private static void gotoPermissionSetting(Activity act) {
        Uri packageURI = Uri.parse("package:" + act.getPackageName());
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
        act.startActivity(intent);
    }

    private static void showMessageGotoSetting(final String message, final Activity act) {
        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                PermissionConfig.instance().showPermissionDialog(message, act, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        gotoPermissionSetting(act);
                    }
                });
            }
        });
    }

    /**
     * request permissions to be granted
     *
     * @param act
     * @param requestCode
     * @param permissions
     */
    public static void requestPermissions(Activity act, int requestCode, String... permissions) {
        ActivityCompat.requestPermissions(act, permissions, requestCode);
        //申请权限后将权限保存至SharePreference中,待显示弹框时确认是否为第一次,key和value都为permission值
        for (String permission : permissions) {
            SharedPreferencesUtil.put(act, permission, permission);
        }
    }

    public static void checkPermissionsByFragment(Fragment fragment, int requestCode, PermissionListener listener, boolean isShowDialog, String... permissions) {
        mIsShowDialog = isShowDialog;
        checkPermissionsByFragment(fragment, requestCode, listener, permissions);
    }

    /**
     * check permissions are whether granted or not for fragment
     *
     * @param fragment
     * @param requestCode
     * @param listener
     * @param permissions
     */
    public static void checkPermissionsByFragment(Fragment fragment, int requestCode, PermissionListener listener, String... permissions) {

        if (fragment == null) {
            if (listener != null) {
                listener.onPermissionsError(requestCode, null, "checkPermissions()-->param act :the activity is null", permissions);
            }
            return;
        }
        if (permissions == null || permissions.length < 1) {
            if (listener != null) {
                listener.onPermissionsError(requestCode, null, "checkPermissions()-->param permissions: is null or length is 0", permissions);
            }
            return;
        }

        PermissionUtils.sortGrantedAndDeniedPermissions(fragment.getContext(), permissions);

        if (PermissionUtils.getGrantedPermissions().size() > 0) {
            List<String> grantedPermissionsList = PermissionUtils.getGrantedPermissions();
            String[] grantedPermissionsArr = grantedPermissionsList.toArray(new String[grantedPermissionsList.size()]);

            if (listener != null) {
                listener.onPermissionsGranted(requestCode, null, grantedPermissionsArr);
            }
        }

        if (PermissionUtils.getDeniedPermissions().size() > 0) {
            List<String> deniedPermissionsList = PermissionUtils.getDeniedPermissions();
            String[] deniedPermissionsArr = deniedPermissionsList.toArray(new String[deniedPermissionsList.size()]);
            if (deniedPermissionsArr.length > 0) {
                PermissionUtils.sortUnshowPermissionByFragment(fragment, deniedPermissionsArr);
            }
        }

        if (PermissionUtils.getUnshowedPermissions().size() > 0) {
            List<String> unShowPermissionsList = PermissionUtils.getUnshowedPermissions();
            String[] unShowPermissionsArr = unShowPermissionsList.toArray(new String[unShowPermissionsList.size()]);
            //如果SharePreference中已存在该permission,说明不是首次检查,可弹出自定义弹框,否则首次只弹系统弹框,不弹自定义弹框
            int len = PermissionUtils.getUnshowedPermissions().size();
            boolean isCanShow = false;
            for (int i = 0; i < len; i++) {
                String permission = PermissionUtils.getUnshowedPermissions().get(i);
                if (SharedPreferencesUtil.contains(fragment.getActivity(), permission)) {
                    isCanShow = true;
                }
            }

            if (listener != null) {
                if (true == mIsShowDialog) {
                    StringBuilder message = getUnShowPermissionsMessage(unShowPermissionsList);
                    if (isCanShow) {
                        showMessageGotoSetting(message.toString(), fragment.getActivity());
                    }
                }

                listener.onShowRequestPermissionRationale(requestCode, !isCanShow, unShowPermissionsArr);
            }
        }

        mIsShowDialog = true;

        if (PermissionUtils.getNeedRequestPermissions().size() > 0) {//true 表示允许弹申请权限框
            List<String> needRequestPermissionsList = PermissionUtils.getNeedRequestPermissions();
            String[] needRequestPermissionsArr = needRequestPermissionsList.toArray(new String[needRequestPermissionsList.size()]);
            if (listener != null) {
                listener.onShowRequestPermissionRationale(requestCode, true, needRequestPermissionsArr);
            }
        }
    }

    /**
     * request permissions to be granted for fragment
     *
     * @param fragment
     * @param requestCode
     * @param permissions
     */
    public static void requestPermissionsByFragment(Fragment fragment, int requestCode, String... permissions) {

        fragment.requestPermissions(permissions, requestCode);
        //申请权限后将权限保存至SharePreference中,待显示弹框时确认是否为第一次,key和value都为permission值
        for (String permission : permissions) {
            SharedPreferencesUtil.put(fragment.getActivity(), permission, permission);
        }
    }


    /**
     * do their permissions results for fragment
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     * @param listener
     */
    public static void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults, PermissionListener listener) {

        List<String> grantedPermissions = new ArrayList<String>();
        List<String> deniedPermissions = new ArrayList<String>();
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                deniedPermissions.add(permissions[i]);
            } else {
                grantedPermissions.add(permissions[i]);
            }
        }
        if (grantedPermissions.size() > 0) {
            String[] grantedPermissionsArr = grantedPermissions.toArray(new String[grantedPermissions.size()]);

            if (listener != null) {
                listener.onPermissionsGranted(requestCode, null, grantedPermissionsArr);
            }
        }
        if (deniedPermissions.size() > 0) {
            String[] deniedPermissionsArr = deniedPermissions.toArray(new String[deniedPermissions.size()]);

            if (listener != null) {
                listener.onPermissionsDenied(requestCode, null, deniedPermissionsArr);
            }
        }
    }
}

