package com.kidssaveocean.fatechanger.letters.list;

import com.kidssaveocean.fatechanger.letters.data.source.LettersRepository;

public class LettersListPresenter implements LettersListContract.Presenter {
    private final LettersRepository repository;
    private final LettersListContract.View view;

    public LettersListPresenter(LettersRepository repository, LettersListContract.View view) {
        this.repository = repository;
        this.view = view;
    }
}