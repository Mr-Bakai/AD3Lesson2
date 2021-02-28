package com.hfad.ad3lesson2.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hfad.ad3lesson2.R;
import com.hfad.ad3lesson2.data.model.Film;
import com.hfad.ad3lesson2.data.remote.FilmStorage;

import java.util.ArrayList;
import java.util.List;

public class BaseFragment extends Fragment {

    private RecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private List<Film> listFilm = new ArrayList<>();
    private NavController navController;

    public BaseFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_base, container, false);
        navController = Navigation.findNavController(getActivity(),
                R.id.nav_host_fragment);
        loadFilm(view);
        return view;
    }

    private void loadFilm(View view) {
        FilmStorage.getListFilm(new FilmStorage.Result() {
            @Override
            public void onSuccess(List<Film> film) {
                listFilm.addAll(film);
                init(view);
            }

            @Override
            public void onFailure(String error) {
            }
        });
    }

    private void init(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        adapter = new RecyclerAdapter();
        adapter.setListener(new RecyclerAdapter.Listener() {
            @Override
            public void onFilmClick(Film film) {
                String filmId = film.getId();

                Bundle bundle = new Bundle();
                bundle.putString("film", filmId);

                NavController navController = Navigation.findNavController(getActivity(),
                        R.id.nav_host_fragment);
                navController.navigate(R.id.filmFragment, bundle);}});

        adapter.addList(listFilm);
        recyclerView.setAdapter(adapter);
    }
}