package com.example.filmviewfragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.filmviewfragments.viewholders.FilmViewHolder;
import com.example.filmviewfragments.viewholders.GenreViewHolder;
import com.example.filmviewfragments.viewholders.TitleViewHolder;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mainContext;

    private final int TYPE_ITEM_FILM = 1;
    private final int TYPE_ITEM_GENRE = 2;
    private final int TYPE_ITEM_TITLE = 3;

    private LayoutInflater inflater;
    private List<Object> mixedArray;
    private String currentGenre;


    @Override
    public int getItemViewType(int position) {
        if (mixedArray.get(position) instanceof String) {
            if ((mixedArray.get(position).equals("Жанры") ||
                    mixedArray.get(position).equals("Фильмы")))
                return TYPE_ITEM_TITLE;
            else {
                return TYPE_ITEM_GENRE;
            }
        } else
            return TYPE_ITEM_FILM;
    }

    public DataAdapter(Context context, List<Object> mixedArray, String currentGenre) {
        this.mixedArray = mixedArray;
        this.mainContext = context;
        this.inflater = LayoutInflater.from(context);
        this.currentGenre = currentGenre;
    }

    public void updateData(List<Object> newMixedArray, String newCurrentGenre) {
        this.mixedArray.clear();
        this.mixedArray.addAll(newMixedArray);
        this.currentGenre = newCurrentGenre;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case TYPE_ITEM_FILM:
                view = inflater.inflate(R.layout.item_film, parent, false);
                return new FilmViewHolder(view, parent);
            case TYPE_ITEM_GENRE:
                view = inflater.inflate(R.layout.item_genre, parent, false);
                return new GenreViewHolder(view, parent);
            default:
                view = inflater.inflate(R.layout.item_title, parent, false);
                return new TitleViewHolder(view, parent);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        int type = getItemViewType(position);

        switch (type) {
            case TYPE_ITEM_TITLE:
                TitleViewHolder titleViewHolder = (TitleViewHolder) holder;
                titleViewHolder.setTitle((String) mixedArray.get(position));
                break;
            case TYPE_ITEM_GENRE:
                GenreViewHolder genreViewHolder = (GenreViewHolder) holder;
                final String genre = (String) mixedArray.get(position);
                genreViewHolder.setGenre(mainContext, genre);
                if (genre.equals(currentGenre)) {
                    genreViewHolder.setNewBackGround();
                } else {
                    genreViewHolder.setPrimaryBackGround();
                }
                break;
            case TYPE_ITEM_FILM:
                FilmViewHolder filmViewHolder = (FilmViewHolder) holder;
                filmViewHolder.setFilm(mainContext, (FilmItemGson) mixedArray.get(position));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mixedArray.size();
    }
}


