package com.dyhdyh.io.asset.example;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.dyhdyh.manager.assets.AssetFile;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv = findViewById(R.id.rv);

        String path = getIntent().getStringExtra("path");
        setTitle(TextUtils.isEmpty(path) ? "Assets" : "Assets - " + path);

        List<AssetFile> files = AssetFile.listFiles(getAssets(), path);
        AssetFileAdapter adapter = new AssetFileAdapter(files);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
    }

    public static void start(Context context, String path) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("path", path);
        context.startActivity(intent);
    }
}
