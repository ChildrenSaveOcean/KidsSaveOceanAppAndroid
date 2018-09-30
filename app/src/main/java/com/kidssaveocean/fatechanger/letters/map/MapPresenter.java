package com.kidssaveocean.fatechanger.letters.map;

import com.kidssaveocean.fatechanger.letters.data.source.LettersRepository;

public class MapPresenter implements MapContract.Presenter {
    private final LettersRepository repository;
    private final MapContract.View view;

    public MapPresenter(LettersRepository repository, MapContract.View view) {
        this.repository = repository;
        this.view = view;
    }

    //region implementation MapContract.Presenter

    @Override
    public void loadLetter() {

    }

    //endregion
}
