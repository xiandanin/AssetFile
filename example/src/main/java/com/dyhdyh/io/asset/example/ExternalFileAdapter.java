package com.dyhdyh.io.asset.example;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

/**
 * @author dengyuhan
 *         created 2018/6/27 22:26
 */
public class ExternalFileAdapter extends RecyclerView.Adapter<ExternalFileAdapter.ItemHolder> {
    private File[] mFiles;
    private String mAssetPath;

    public ExternalFileAdapter(String assetPath, File[] files) {
        mAssetPath = assetPath;
        mFiles = files;
    }


    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_asset_file, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        final File item = mFiles[position];
        final boolean isDirectory = item.isDirectory();
        holder.iv_icon.setImageResource(isDirectory ? R.drawable.ic_directory : R.drawable.ic_file);
        holder.tv_name.setText(item.getName());
        holder.tv_path.setText(item.getAbsolutePath());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDirectory) {
                    ExternalActivity.start(v.getContext(), mAssetPath, item.getAbsolutePath());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFiles.length;
    }

    static class ItemHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        TextView tv_path;
        ImageView iv_icon;

        public ItemHolder(View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_path = itemView.findViewById(R.id.tv_path);
            iv_icon = itemView.findViewById(R.id.iv_icon);
        }
    }
}
