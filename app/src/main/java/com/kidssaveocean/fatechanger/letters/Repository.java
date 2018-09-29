package com.kidssaveocean.fatechanger.letters;

import java.util.List;

import io.reactivex.Flowable;

public interface Repository<T> {
    void add(T item);

    void update(T item);

    void remove(T item);

//    void remove(Specification specification);

    Flowable<List<T>> getAll();

    void removeAll();

}