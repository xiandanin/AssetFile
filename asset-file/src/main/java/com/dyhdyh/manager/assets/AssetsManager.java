package com.dyhdyh.manager.assets;

import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author dengyuhan
 *         created 2018/6/27 17:55
 */
public class AssetsManager {
    private AssetManager mAssetManager;

    public AssetsManager(Context context) {
        mAssetManager = context.getAssets();
    }

    /**
     * 复制Asset文件夹
     *
     * @param assetSource
     * @param outputDir
     */
    public boolean copyAsset(AssetFile assetSource, File outputDir) {
        try {
            File outputFile = new File(outputDir, assetSource.getName());

            String assetPath = assetSource.getAssetPath();
            final String[] list = mAssetManager.list(assetPath);
            if (list.length <= 0) {
                //文件
                copyAssetFile(assetPath, outputFile);
            } else {
                //目录
                if (!outputFile.exists()) {
                    outputFile.mkdirs();
                }
                for (String child : list) {
                    copyAsset(new AssetFile(assetPath, child), outputFile);
                }
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 复制Asset文件
     *
     * @param assetPath
     * @param outputFile
     * @return
     */
    public boolean copyAssetFile(String assetPath, File outputFile) {
        try {
            InputStream is = mAssetManager.open(assetPath);
            int byteRead = 0;
            FileOutputStream fs = new FileOutputStream(outputFile);
            byte[] buffer = new byte[1024];
            while ((byteRead = is.read(buffer)) != -1) {
                fs.write(buffer, 0, byteRead);
            }
            fs.close();
            is.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    private String getAssetName(String parentName, String assetName) {
        return TextUtils.isEmpty(parentName) ? assetName : parentName + File.separator + assetName;
    }
}
