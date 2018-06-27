package com.dyhdyh.manager.assets;


/**
 * @author dengyuhan
 *         created 2018/6/27 23:02
 */
public interface AssetFileFilter {
    /**
     * @param file
     * @return true表示添加, false不添加
     */
    boolean accept(AssetFile file);
}
