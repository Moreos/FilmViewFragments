package com.example.filmviewfragments.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.filmviewfragments.FilmItemGson;
import com.example.filmviewfragments.R;
import com.example.filmviewfragments.interfaces.InterfaceActionBar;
import com.squareup.picasso.Picasso;

public class FragmentFilmActivity extends Fragment {
    private Context mainContext;
    private FilmItemGson film;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_film, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        this.mainContext = context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (film != null) setThisFilm();
    }

    public void setFilmData(FilmItemGson currentFilm) {
        this.film = currentFilm;

        setThisFilm();
    }

    private void setThisFilm() {
        String imageUrl = film.getImage_url();
        String ruName = film.getLocalized_name();
        String enName = film.getName();
        String year = String.valueOf(film.getYear());
        String rating = String.valueOf(film.getRating());
        String description = film.getDescription();

        ((InterfaceActionBar) mainContext).updateActionBar(ruName, true);

//        if(imageUrl.isEmpty() || imageUrl == null) imageUrl = "fhjksd";
        if(enName.isEmpty()) enName = " ";
        if(year.isEmpty()) year = " ";
        if(rating.isEmpty()) rating = " ";
//        if(description.isEmpty()) description = " ";

        ImageView imageView = getView().findViewById(R.id.filmImage);
        TextView textEnName = getView().findViewById(R.id.filmEnName);
        TextView textYear = getView().findViewById(R.id.filmYear);
        TextView textRating = getView().findViewById(R.id.filmRating);
        TextView textDescription = getView().findViewById(R.id.filmDescription);

        Picasso.with(mainContext)
                .load(imageUrl)
                //.placeholder(R.drawable.error)
                .into(imageView);
        textEnName.setText(enName);
        textYear.setText(R.string.year);
        textYear.append(year);
        textRating.setText(R.string.rating);
        textRating.append(rating);
        textDescription.setText(description);
    }
}
