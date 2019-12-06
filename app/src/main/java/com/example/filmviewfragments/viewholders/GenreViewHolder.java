package com.example.filmviewfragments.viewholders;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.filmviewfragments.R;
import com.example.filmviewfragments.interfaces.OnItemClick;


public class GenreViewHolder extends RecyclerView.ViewHolder {
    private TextView genreItemText;


    public GenreViewHolder(@NonNull View itemView, ViewGroup parent) {
        super(itemView);
        RecyclerView.LayoutManager manager = ((RecyclerView) parent).getLayoutManager();

        if (manager instanceof StaggeredGridLayoutManager) {
            ((StaggeredGridLayoutManager.LayoutParams) itemView.getLayoutParams()).setFullSpan(true);
        }
        genreItemText = itemView.findViewById(R.id.genreItemText);

    }

    public void setGenre(final Context mainContext, final String genre) {
        genreItemText.setText(genre);

        genreItemText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((OnItemClick) mainContext).clickAdapter(genre);
            }
        });
    }

    public void setNewBackGround() {
        genreItemText.setBackgroundColor(Color.rgb(255, 240, 110));
    }

    public void setPrimaryBackGround() {
        genreItemText.setBackgroundColor(Color.TRANSPARENT);
    }

}
