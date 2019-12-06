package com.example.filmviewfragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.example.filmviewfragments.fragments.FragmentFilmActivity;
import com.example.filmviewfragments.fragments.FragmentGenresActivity;
import com.example.filmviewfragments.interfaces.FilmOnClick;
import com.example.filmviewfragments.interfaces.InterfaceActionBar;
import com.example.filmviewfragments.interfaces.OnItemClick;

public class MainActivity extends AppCompatActivity implements FilmOnClick,
        InterfaceActionBar,
        OnItemClick {
//    public static final String TAG = "myTag";

    private int fragmentContainerLink;

    @SuppressLint("StaticFieldLeak")
    public static FragmentFilmActivity fragFilm;
    @SuppressLint("StaticFieldLeak")
    public static FragmentGenresActivity fragGenres;
    public static FragmentTransaction fragTrans;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentContainerLink = R.id.fragmentContainer;

        if (savedInstanceState == null) fragGenres = new FragmentGenresActivity();

        appStart();
    }

    public void appStart() {
        setSupportActionBar((Toolbar) findViewById(R.id.myToolBar));

        fragTrans = getSupportFragmentManager().beginTransaction();

        if(getSavedStateRegistry().isRestored() && fragFilm != null) {
            fragFilm.setRetainInstance(true);
            fragTrans.replace(fragmentContainerLink, fragFilm);
        } else {
            fragGenres.setRetainInstance(true);
            fragTrans.replace(fragmentContainerLink, fragGenres);
        }

        fragTrans.commit();
    }

    @Override
    public void onClickFilm(final FilmItemGson filmData) {
        fragFilm = new FragmentFilmActivity();

        fragFilm.setRetainInstance(true);

        fragTrans = getSupportFragmentManager().beginTransaction();
        fragTrans.replace(fragmentContainerLink, fragFilm);
        fragTrans.addToBackStack(null);

        fragTrans.commit();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fragFilm.setFilmData(filmData);
            }
        },150);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getSupportFragmentManager().popBackStack();

            fragTrans = getSupportFragmentManager().beginTransaction();

            fragTrans.replace(fragmentContainerLink, fragGenres);
            fragTrans.commit();

            fragFilm = null;

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        fragFilm = null;
    }

    @Override
    public void updateActionBar(String text, Boolean addHomeButton) {
        TextView textViewToolBar = findViewById(R.id.myToolBarTextView);

        if (addHomeButton) textViewToolBar.setGravity(Gravity.NO_GRAVITY);
        else textViewToolBar.setGravity(Gravity.CENTER);

        textViewToolBar.setText(text);

        getSupportActionBar().setHomeButtonEnabled(addHomeButton);
        getSupportActionBar().setDisplayHomeAsUpEnabled(addHomeButton);
    }

    @Override
    public void clickAdapter(Object item) {
        if (item instanceof FilmItemGson) onClickFilm((FilmItemGson) item);
        else if (item instanceof String) fragGenres.onClickGenre((String) item);
    }
}