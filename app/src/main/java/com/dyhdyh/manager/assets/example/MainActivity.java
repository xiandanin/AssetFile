package com.dyhdyh.manager.assets.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.dyhdyh.manager.assets.AssetsManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new AssetsManager(this)
                .copyAssetDir("test", getExternalCacheDir());

    }
}
