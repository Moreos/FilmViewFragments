package com.example.filmviewfragments.fragments;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.filmviewfragments.Controller;
import com.example.filmviewfragments.DataAdapter;
import com.example.filmviewfragments.FilmItemGson;
import com.example.filmviewfragments.FilmsItems;
import com.example.filmviewfragments.MainActivity;
import com.example.filmviewfragments.R;
import com.example.filmviewfragments.interfaces.InterfaceActionBar;
import com.example.filmviewfragments.interfaces.RetrofitService;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import static com.example.filmviewfragments.MainActivity.TAG;

public class FragmentGenresActivity extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private Context mainContext;

    private String lastCurrentGenre;

    private DataAdapter myDataAdapter;

    private List<FilmItemGson> films = new ArrayList<>();
    private List<FilmItemGson> usableFilms = new ArrayList<>();
    private List<Object> genres_and_title = new ArrayList<>();

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private WeakReference activityWeakReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_genres, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((InterfaceActionBar) mainContext).updateActionBar("Фильмы", false);

        activityWeakReference = new WeakReference<>((MainActivity) mainContext);

        mSwipeRefreshLayout = view.findViewById(R.id.swipeContainer);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        if (films.isEmpty()) loadFilms();
        else createAllData(false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mainContext = context;
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Отменяем анимацию обновления
                mSwipeRefreshLayout.setRefreshing(false);

                activityWeakReference = new WeakReference<>((MainActivity) mainContext);

                lastCurrentGenre = null;
                genres_and_title.clear();
                usableFilms.clear();
                films.clear();
                loadFilms();
            }
        }, 500);
    }

    private void loadFilms() {
        RetrofitService retrofitService = Controller.getApi();

        retrofitService.getFilms().enqueue(new Callback<FilmsItems>() {
            @Override
            public void onResponse(@NonNull Call<FilmsItems> call, @NonNull Response<FilmsItems> response) {
                if (response.body() != null) {
                    films.addAll(response.body().getFilms());
                    Collections.sort(films, FilmItemGson.nameCompare);
//                    Log.d(TAG, "Response string: " + films.toString());

                    loadGenres();
                } else {
                    Toast.makeText(mainContext,
                            "Ошибка с подключением. \nПовторите попытку позже или проверьте подключение.",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<FilmsItems> call, @NonNull Throwable t) {
                Toast.makeText(mainContext,
                        "Ошибка с подключением. \nПовторите попытку позже или проверьте подключение.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadGenres() {
        HashSet<String> genres = new HashSet<>();
        String[] currentGenres;
        for (int i = 0; i < films.size(); i++) {
            currentGenres = films.get(i).getGenres();
            if (currentGenres.length == 1) {
                genres.add(currentGenres[0]);
            } else {
                genres.addAll(Arrays.asList(currentGenres));
            }
        }
//        Log.d(TAG, genres.toString());

        genres_and_title.add("Жанры");
        genres_and_title.addAll(genres);
        genres_and_title.add("Фильмы");

        usableFilms.addAll(films);

        createAllData(false);
    }

    private void createAllData(boolean update) {
        final List<Object> allData = new ArrayList<>();

        allData.addAll(genres_and_title);
        allData.addAll(usableFilms);

        startRecyclerView(allData, update);
    }

    private void startRecyclerView(final List<Object> mixedArray, boolean update) {
        if (activityWeakReference != null) {
            if (!update) {
                RecyclerView recyclerView = getView().findViewById(R.id.recyclerView);

                myDataAdapter = new DataAdapter(mainContext, mixedArray, lastCurrentGenre);

                StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL);

                recyclerView.setLayoutManager(manager);

                recyclerView.setNestedScrollingEnabled(false);
                recyclerView.setHasFixedSize(true);

                recyclerView.setAdapter(myDataAdapter);
            } else {
                myDataAdapter.updateData(mixedArray, lastCurrentGenre);
            }
        }
    }

    public void onClickGenre(String currentGenre) {
        if (lastCurrentGenre == null) {
            lastCurrentGenre = currentGenre;
        } else if (currentGenre.equals(lastCurrentGenre)) {
            lastCurrentGenre = null;
        } else {
            lastCurrentGenre = currentGenre;
        }
        reloadFilms();
    }

    private void reloadFilms() {
        usableFilms.clear();
        if (lastCurrentGenre != null) {
            for (FilmItemGson film : films) {
                if (Arrays.asList(film.getGenres()).contains(lastCurrentGenre)) usableFilms.add(film);
            }
        } else {
            usableFilms.addAll(films);
        }
        Collections.sort(usableFilms, FilmItemGson.nameCompare);
        createAllData(true);
    }
}