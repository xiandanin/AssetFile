package com.dyhdyh.manager.assets;

import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;
import android.util.Log;

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

    public boolean copyAssetDir(String assetDirName, String outputDirPath) {
        return copyAssetDir(null, assetDirName, outputDirPath);
    }

    public boolean copyAssetDir(String parentName, String assetDirName, String outputDirPath) {
        File outputDir = new File(outputDirPath);

        return copyAssetDir(parentName, assetDirName, outputDir);
    }

    public boolean copyAssetDir(String assetDirName, File outputDir) {
        return copyAssetDir(null, assetDirName, outputDir);
    }

    /**
     * 复制文件夹
     *
     * @param assetDirName
     * @param outputDir
     */
    public boolean copyAssetDir(String parentName, String assetDirName, File outputDir) {
        try {
            String assetName = getAssetName(parentName, assetDirName);
            Log.d("------------>", assetName);
            final String[] list = mAssetManager.list(assetName);
            if (list.length <= 0) {
                //文件
                copyAssetFile(parentName, assetDirName, new File(outputDir, assetDirName));
            } else {
                //目录
                for (String child : list) {
                    final File dir = new File(outputDir, assetDirName);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    copyAssetDir(assetName, child, dir);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean copyAssetFile(String assetFileName, File outputFile) {
        return copyAssetFile(null, assetFileName, outputFile);
    }

    public boolean copyAssetFile(String parentName, String assetFileName, File outputFile) {
        try {
            String assetName = TextUtils.isEmpty(parentName) ? assetFileName : parentName + File.separator + assetFileName;
            InputStream is = mAssetManager.open(assetName);
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
