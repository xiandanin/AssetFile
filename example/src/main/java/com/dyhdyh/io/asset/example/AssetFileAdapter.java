package com.dyhdyh.io.asset.example;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dyhdyh.manager.assets.AssetFile;

import java.util.List;

/**
 * @author dengyuhan
 *         created 2018/6/27 22:26
 */
public class AssetFileAdapter extends RecyclerView.Adapter<AssetFileAdapter.ItemHolder> {
    private List<AssetFile> mFiles;

    public AssetFileAdapter(List<AssetFile> files) {
        mFiles = files;
    }


    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_asset_file, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        final AssetFile item = mFiles.get(position);
        final boolean isDirectory = item.isDirectory(holder.itemView.getContext().getAssets());
        holder.iv_icon.setImageResource(isDirectory ? R.drawable.ic_directory : R.drawable.ic_file);
        holder.tv_name.setText(item.getName());
        holder.tv_path.setText(item.getAssetPath());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDirectory) {
                    MainActivity.start(v.getContext(), item.getAssetPath());
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ExternalActivity.start(v.getContext(), item.getAssetPath(),"");
                Toast.makeText(v.getContext(), "请选择要复制的文件夹", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFiles.size();
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
