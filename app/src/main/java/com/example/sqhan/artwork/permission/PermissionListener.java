package com.example.sqhan.artwork.permission;

/**
 * a permissions check listener
 * Created by pengjiang on 2015/10/8.
 */
public interface PermissionListener {

    /**
     * call when permissions are granted
     *
     * @param requestCode
     * @param grantResults
     * @param permissions
     */

    public void onPermissionsGranted(int requestCode, int[] grantResults, String... permissions);

    /**
     * call when one or some permissions are denied
     *
     * @param requestCode
     * @param grantResults
     * @param permissions
     */
    public void onPermissionsDenied(int requestCode, int[] grantResults, String... permissions);

    /**
     * show the permissions rationale whether or not(why your app need their permissions?)
     *
     * @param requestCode
     * @param isShowRationale
     * @param permissions
     */
    public void onShowRequestPermissionRationale(int requestCode, boolean isShowRationale, String... permissions);

    /**
     * get a permissions error: almost params are wrong
     *
     * @param requestCode
     * @param grantResults
     * @param errorMsg
     * @param permissions
     */
    public void onPermissionsError(int requestCode, int[] grantResults, String errorMsg, String... permissions);

}
