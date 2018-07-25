package com.example.admin.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.MyViewHolder> {
    private final int INIT_SIZE = 0;
    private List<String> mListImage;
    private Context mContext;

    public GalleryAdapter(List<String> listImage, Context context) {
        this.mContext = context;
        this.mListImage = listImage;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_gallery, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Bitmap bitmap = BitmapFactory.decodeFile(mListImage.get(position));
        holder.mImageGallery.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return mListImage != null ? mListImage.size() : INIT_SIZE;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageGallery;

        public MyViewHolder(View itemView) {
            super(itemView);

            mImageGallery = (ImageView) itemView.findViewById(R.id.image_gallery);
        }
    }
}
