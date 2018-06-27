package com.dyhdyh.manager.assets;

import java.util.Arrays;
import java.util.List;

/**
 * 过滤Asset系统相关
 *
 * @author dengyuhan
 *         created 2018/6/27 23:05
 */
public class SystemAssetFileFilter implements AssetFileFilter {
    private final String[] SYSTEM_FILE_NAME_ARRAY = {"device_features", "huangli.idf", "hybrid", "images", "keys", "license", "operators.dat", "pinyinindex.idf", "sounds", "telocation.idf", "webkit", "xiaomi_mobile.dat"};
    private List<String> mSystemFileName;

    public SystemAssetFileFilter() {
        mSystemFileName = Arrays.asList(SYSTEM_FILE_NAME_ARRAY);
    }

    @Override
    public boolean accept(AssetFile file) {
        return !mSystemFileName.contains(file.getName());
    }
}
