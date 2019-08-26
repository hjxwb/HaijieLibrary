package com.haijie.haijielibrary;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv_utils_library)
    TextView tvUtilsLibrary;
    @BindView(R.id.btn_InternetUtils_isNetworkAvailable)
    Button btnInternetUtilsIsNetworkAvailable;
    @BindView(R.id.btn_SoulPermission_checkAndRequestPermission)
    Button btnSoulPermissionCheckAndRequestPermission;
    @BindView(R.id.btn_SoulPermission_goPermissionSettings)
    Button btnSoulPermissionGoPermissionSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_InternetUtils_isNetworkAvailable, R.id.btn_SoulPermission_checkAndRequestPermission, R.id.btn_SoulPermission_goPermissionSettings})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_InternetUtils_isNetworkAvailable:
                boolean result = UtilsLibraryTest.getInstance().InternetUtilsIsNetworkAvailableTest(this);
                Toast.makeText(this, "Network is available: " + result, Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_SoulPermission_checkAndRequestPermission:
                UtilsLibraryTest.getInstance().SoulPermissionCheckAndRequestPermissionTest();
                break;
            case R.id.btn_SoulPermission_goPermissionSettings:
                UtilsLibraryTest.getInstance().SoulPermissionGoPermissionSettingsTest();
                break;
        }
    }
}
