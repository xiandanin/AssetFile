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


    public boolean isDirectory(AssetManager assetManager) {
        if (directory == null) {
            try {
                directory = assetManager.list(assetPath).length > 0;
            } catch (Exception e) {
                e.printStackTrace();
                directory = false;
            }
        }
        return directory;
    }

    public List<AssetFile> listFiles(AssetManager assetManager) {
        return listFiles(assetManager, new SystemAssetFileFilter());
    }

    public List<AssetFile> listFiles(AssetManager assetManager, AssetFileFilter filter) {
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

    public boolean exists(AssetManager assetManager) {
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
