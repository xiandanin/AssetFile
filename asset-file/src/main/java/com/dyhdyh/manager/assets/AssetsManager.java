package com.dyhdyh.manager.assets;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author dengyuhan
 *         created 2018/6/27 17:55
 */
public class AssetsManager {

    public static boolean copyAsset(Context context, AssetFile assetSource, File outputDir) {
        return copyAsset(context.getAssets(), assetSource, outputDir);
    }

    /**
     * 复制Asset文件夹
     *
     * @param assetSource
     * @param outputDir
     */
    public static boolean copyAsset(AssetManager assetManager, AssetFile assetSource, File outputDir) {
        try {
            File outputFile = new File(outputDir, assetSource.getName());

            String assetPath = assetSource.getAssetPath();
            final String[] list = assetManager.list(assetPath);
            if (list.length <= 0) {
                //文件
                copyAssetFile(assetManager, assetPath, outputFile);
            } else {
                //目录
                if (!outputFile.exists()) {
                    outputFile.mkdirs();
                }
                for (String child : list) {
                    copyAsset(assetManager, new AssetFile(assetPath, child), outputFile);
                }
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean copyAssetFile(Context context, String assetPath, File outputFile) {
        return copyAssetFile(context.getAssets(), assetPath, outputFile);
    }


    /**
     * 复制Asset文件
     *
     * @param assetPath
     * @param outputFile
     * @return
     */
    public static boolean copyAssetFile(AssetManager assetManager, String assetPath, File outputFile) {
        try {
            InputStream is = assetManager.open(assetPath);
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

}
