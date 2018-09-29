package com.kidssaveocean.fatechanger.letters.repository;

import com.kidssaveocean.fatechanger.letters.Letter;
import com.kidssaveocean.fatechanger.letters.Repository;
import com.kidssaveocean.fatechanger.letters.Specification;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;

public class FakeLettersRemoteDataSource implements Repository<Letter> {

    private static FakeLettersRemoteDataSource INSTANCE;

    private static final Map<String, Letter> LETTER_SERVICE_DATA = new LinkedHashMap<>();

    // Prevent direct instantiation.
    private FakeLettersRemoteDataSource() {
    }

    public static FakeLettersRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FakeLettersRemoteDataSource();
        }
        return INSTANCE;
    }

    //region implementation Repository

    @Override
    public void add(Letter item) {
        LETTER_SERVICE_DATA.put(item.getId(), item);
    }

    @Override
    public void update(Letter item) {
        LETTER_SERVICE_DATA.put(item.getId(), item);
    }

    @Override
    public void remove(Letter item) {
        LETTER_SERVICE_DATA.remove(item.getId());
    }

    @Override
    public Flowable<List<Letter>> query(Specification specification) {
        Collection<Letter> values = LETTER_SERVICE_DATA.values();
        return Flowable.fromIterable(values).toList().toFlowable();
    }

    //endregion
}
