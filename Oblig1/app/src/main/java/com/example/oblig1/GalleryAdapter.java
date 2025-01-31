package com.example.oblig1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {
    private List<PictureTextPair> pictureTextPairs;

    public GalleryAdapter(List<PictureTextPair> pictureTextPairs) {
        this.pictureTextPairs = pictureTextPairs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_picture_text, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the current PictureTextPair
        PictureTextPair pair = pictureTextPairs.get(position);

        // Bind data to the views
        holder.imageView.setImageBitmap(pair.getPicture());
        holder.textView.setText(pair.getText());
    }

    @Override
    public int getItemCount() {
        return pictureTextPairs.size(); // Number of items in the list
    }

    // ViewHolder class to hold item views
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view_picture);
            textView = itemView.findViewById(R.id.text_view_description);
        }
    }
}
