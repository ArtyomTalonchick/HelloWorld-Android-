package com.example.helloworld;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

public class PermissionService {

    private Context context;
    private PreferencesService preferencesService;

    public PermissionService(Context context) {
        this.context = context;
        preferencesService = new PreferencesService(context);
    }

    public boolean permissionExists(String permission) {
        return ActivityCompat.checkSelfPermission(context, permission) ==
                PackageManager.PERMISSION_GRANTED;
    }

    private boolean permissionDenied(String permission) {
        return ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, permission);
    }

    private void setPermission(String permission, final int permissionRequestCode) {
        ActivityCompat.requestPermissions((Activity) context,
                new String[]{permission},
                permissionRequestCode);
    }

    public void requestPermission(String permission, final int permissionRequestCode) {
        if (!permissionExists(permission)) {
            if (permissionDenied(permission)) {
                showNeedPermissionDialog(permission, permissionRequestCode);
            } else {
                if (preferencesService.getBoolean(permission, false)) {
                    showNeedPermissionToast();
                } else {
                    setPermission(permission, permissionRequestCode);
                }
            }
        }
        preferencesService.putBoolean(permission, true);
    }

    public void showNeedPermissionDialog(final String permission, final int permissionRequestCode) {
        new AlertDialog.Builder(context)
                .setTitle(R.string.need_permission_dialog_title)
                .setMessage(R.string.need_permission_dialog_message)
                .setPositiveButton(R.string.need_permission_dialog_allow, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setPermission(permission, permissionRequestCode);
                    }
                })
                .setNegativeButton(R.string.need_permission_dialog_deny, null)
                .show();
    }

    public void showNeedPermissionToast() {
        Toast.makeText(context,
                R.string.need_permission_in_settings_toast,
                Toast.LENGTH_SHORT)
                .show();
    }

}
