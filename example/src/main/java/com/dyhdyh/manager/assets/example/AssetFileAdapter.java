package com.dyhdyh.manager.assets.example;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.dyhdyh.manager.assets.AssetFile;

/**
 * @author dengyuhan
 *         created 2018/6/27 22:26
 */
public class AssetFileAdapter extends RecyclerView.Adapter<AssetFileAdapter.ItemHolder> {
    private AssetFile[] mFiles;

    private AssetFileAdapter(AssetFile[] files) {
        mFiles = files;
    }


    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mFiles.length;
    }

    static class ItemHolder extends RecyclerView.ViewHolder {

        public ItemHolder(View itemView) {
            super(itemView);
        }
    }
}
