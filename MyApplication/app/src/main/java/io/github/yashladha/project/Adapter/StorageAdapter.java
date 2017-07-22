package io.github.yashladha.project.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import java.io.File;
import java.util.ArrayList;

import io.github.yashladha.project.Models.StorageFiles;
import io.github.yashladha.project.R;

public class StorageAdapter extends RecyclerView.Adapter<StorageAdapter.ViewHolder>{

  private ArrayList<StorageFiles> files;
  private Context mContext;

  public StorageAdapter(ArrayList<StorageFiles> files, Context mContext) {
    this.files = files;
    this.mContext = mContext;
  }

  @Override
  public StorageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.storage_cards, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(StorageAdapter.ViewHolder holder, int position) {
    StorageFiles temp = files.get(position);
    File fileName = new File(Uri.parse(temp.getFileUri()).getPath());
    holder.textView.setText(fileName.getName());
    holder.imageView.setImageURI(Uri.parse(temp.getThumbnailUri()));
  }

  @Override
  public int getItemCount() {
    return files.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {

    private ImageView imageView;
    private TextView textView;

    public ViewHolder(View itemView) {
      super(itemView);
      imageView = (ImageView) itemView.findViewById(R.id.iv_storage_file_index);
      textView = (TextView) itemView.findViewById(R.id.tv_storage_file_index);
    }
  }
}
