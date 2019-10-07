package com.example.helloworld;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private final int PERMISSION_REQUEST_CODE = 100;
    private PermissionService permissionService;
    private final String PERMISSION = Manifest.permission.READ_PHONE_STATE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        permissionService = new PermissionService(MainActivity.this);
    }

    public void onShowId(View v) {
        if(permissionService.permissionExists(PERMISSION)){
            showId();
        } else {
            permissionService.requestPermission(PERMISSION, PERMISSION_REQUEST_CODE);
        }
    }

    private void showId() {
        Toast.makeText(MainActivity.this, this.getId(), Toast.LENGTH_SHORT).show();
    }

    private String getId() {
        if(permissionService.permissionExists(PERMISSION)){
            return Secure.getString(getContentResolver(), Secure.ANDROID_ID);
        }
        return "";
    }

    public void showNeedPermissionToast() {
        Toast.makeText(MainActivity.this,
                R.string.need_permission_toast,
                Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE && grantResults.length == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showId();
            } else {
                showNeedPermissionToast();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
