package com.dyhdyh.manager.assets.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.dyhdyh.manager.assets.AssetFile;

public class MainActivity extends AppCompatActivity {
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv = findViewById(R.id.rv);

        AssetFile assetFile = new AssetFile("test/test2/test3.jpg");
        Log.d("------------->", assetFile.getName() + "," + assetFile.getParent() + "," + AssetFile.isDirectory(getAssets(), assetFile.getAssetPath()));


        AssetFile assetDir = new AssetFile("test");

        AssetFile[] listFiles = AssetFile.listFiles(getAssets(), assetDir.getAssetPath());
        StringBuffer sb = new StringBuffer();
        for (AssetFile file : listFiles) {
            sb.append(AssetFile.isDirectory(getAssets(), file.getAssetPath()));
            sb.append(",");
            sb.append(file.getName());
            sb.append(",");
            sb.append(file.getAssetPath());
            sb.append("\n");
        }
        Log.d("------------->", sb.toString());
    }
}
