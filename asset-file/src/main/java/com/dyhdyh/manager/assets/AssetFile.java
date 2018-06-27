package com.dyhdyh.manager.assets;

import android.content.res.AssetManager;
import android.net.Uri;
import android.text.TextUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dengyuhan
 *         created 2018/6/27 20:57
 */
public class AssetFile {
    private String assetPath;
    private String name;
    private Boolean directory;

    public AssetFile() {
        this("");
    }


    public AssetFile(AssetFile parentFile, String child) {
        this(parentFile.getAssetPath(), child);
    }

    public AssetFile(String parent, String child) {
        this(parserPath(parent, child));
    }

    public AssetFile(String assetPath) {
        this.assetPath = assetPath == null ? "" : assetPath;
        this.name = parserName(this.assetPath);
    }

    public String getParent() {
        int index = assetPath.lastIndexOf(File.separatorChar);
        return assetPath.substring(0, index);
    }

    public AssetFile getParentFile() {
        return new AssetFile(getParent());
    }

    public Uri getUri() {
        return Uri.parse("file:///android_asset/" + (isRootDir() ? "" : assetPath));
    }


    public boolean isRootDir() {
        return assetPath.equals(File.separator);
    }

    protected Boolean getDirectory() {
        return directory;
    }

    public static String parserPath(String parentPath, String child) {
        if (TextUtils.isEmpty(parentPath)) {
            return child;
        }
        return parentPath + File.separator + child;
    }

    public static String parserName(String assetPath) {
        if (TextUtils.isEmpty(assetPath)) {
            return "";
        }
        int index = assetPath.lastIndexOf(File.separatorChar);
        return assetPath.substring(index + 1, assetPath.length());
    }


    public static boolean isDirectory(AssetManager assetManager, AssetFile assetFile) {
        if (assetFile.getDirectory() == null) {
            assetFile.directory = isDirectory(assetManager, assetFile.getAssetPath());
        }
        return assetFile.getDirectory();
    }

    public static boolean isDirectory(AssetManager assetManager, String assetPath) {
        try {
            return assetManager.list(assetPath).length > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<AssetFile> listFiles(AssetManager assetManager, String assetPath) {
        return listFiles(assetManager, assetPath, new SystemAssetFileFilter());
    }

    public static List<AssetFile> listFiles(AssetManager assetManager, String assetPath, AssetFileFilter filter) {
        try {
            String newAssetPath = TextUtils.isEmpty(assetPath) ? "" : assetPath;
            String[] list = assetManager.list(newAssetPath);
            List<AssetFile> fileList = new ArrayList<>();
            for (int i = 0; i < list.length; i++) {
                AssetFile file = new AssetFile(newAssetPath, list[i]);
                if (filter != null) {
                    if (filter.accept(file)) {
                        fileList.add(file);
                    }
                } else {
                    fileList.add(file);
                }
            }
            return fileList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static boolean exists(AssetManager assetManager, String assetPath) {
        try {
            assetManager.list(assetPath);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getName() {
        return name;
    }

    public String getAssetPath() {
        return assetPath;
    }
}
