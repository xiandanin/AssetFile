package com.dyhdyh.io.asset.example;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.dyhdyh.manager.assets.AssetFile;
import com.dyhdyh.manager.assets.AssetsManager;

import java.io.File;

/**
 * @author dengyuhan
 *         created 2018/6/27 23:29
 */
public class ExternalActivity extends AppCompatActivity {
    RecyclerView rv;

    private String assetPath;
    private File externalFile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external);
        rv = findViewById(R.id.rv);

        Intent intent = getIntent();
        assetPath = intent.getStringExtra("asset_path");
        String externalPath = getIntent().getStringExtra("path");
        externalFile = TextUtils.isEmpty(externalPath) ? Environment.getExternalStorageDirectory() : new File(externalPath);
        setTitle(TextUtils.isEmpty(externalPath) ? "存储卡" : "存储卡 - " + externalFile.getName());

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
        }

        loadExternalFile();
    }

    private void loadExternalFile() {
        File[] files = externalFile.listFiles();
        ExternalFileAdapter adapter = new ExternalFileAdapter(assetPath, files == null ? new File[0] : files);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
    }

    public static void start(Context context, String assetPath, String path) {
        Intent intent = new Intent(context, ExternalActivity.class);
        intent.putExtra("path", path);
        intent.putExtra("asset_path", assetPath);
        context.startActivity(intent);
    }

    public void clickPaste(View view) {
        boolean asset = AssetsManager.copyAsset(this, new AssetFile(assetPath), externalFile);
        String message = asset ? "复制成功-->" + externalFile.getAbsolutePath() : "复制失败";
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        loadExternalFile();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (requestCode == 100) {
                loadExternalFile();
            }
        }

    }
}
