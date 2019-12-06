package com.example.filmviewfragments.viewholders;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.filmviewfragments.FilmItemGson;
import com.example.filmviewfragments.R;
import com.example.filmviewfragments.interfaces.OnItemClick;
import com.squareup.picasso.Picasso;

public class FilmViewHolder extends RecyclerView.ViewHolder {
    private RelativeLayout filmContainer;
    private ImageView filmImage;
    private LinearLayout filmTitleContainer;
    private TextView filmTitle;

    public FilmViewHolder(@NonNull View itemView, ViewGroup parent) {
        super(itemView);

        RecyclerView.LayoutManager manager = ((RecyclerView) parent).getLayoutManager();

        if (manager instanceof StaggeredGridLayoutManager) {
            ((StaggeredGridLayoutManager.LayoutParams) itemView.getLayoutParams()).height = 512;
        }

        filmContainer = itemView.findViewById(R.id.filmItemContainer);
        filmImage = itemView.findViewById(R.id.filmItemImage);
        filmTitleContainer = itemView.findViewById(R.id.filmItemTitleContainer);
        filmTitle = itemView.findViewById(R.id.filmItemTitle);
    }

    public void setFilm(final Context mainContext, final FilmItemGson thisFilm) {
        String title;
        StringBuilder titleCorrect = new StringBuilder();
        if (thisFilm.getLocalized_name().isEmpty()) title = thisFilm.getName();
        else title = thisFilm.getLocalized_name();

        if (title.length() > 20) {
            for (int i = 0; i < 20; i++) titleCorrect.append(title.charAt(i));
            title = titleCorrect + "...";
        }

        filmTitle.setText(title);
        filmTitleContainer.setBackgroundColor(Color.argb(200, 125, 125, 125));

        Picasso.with(mainContext)
                .load(thisFilm.getImage_url())
                .placeholder(R.drawable.error23)
                .into(filmImage);

        filmContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((OnItemClick) mainContext).clickAdapter(thisFilm);
            }
        });
    }
}
