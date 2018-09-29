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
        Letter l1 = new Letter("1", "Findland", 1456);
        LETTER_SERVICE_DATA.put(l1.getId(), l1);

        Letter l2 = new Letter("12", "United States", 1451);
        LETTER_SERVICE_DATA.put(l2.getId(), l2);

        Letter l3 = new Letter("13", "Brazil", 933);
        LETTER_SERVICE_DATA.put(l3.getId(), l3);

        Letter l4 = new Letter("14", "Austria", 701);
        LETTER_SERVICE_DATA.put(l4.getId(), l4);

        Letter l5 = new Letter("15", "Germany", 645);
        LETTER_SERVICE_DATA.put(l5.getId(), l5);

        Letter l6 = new Letter("16", "Australia", 511);
        LETTER_SERVICE_DATA.put(l6.getId(), l6);

        Letter l7 = new Letter("17", "Poland", 492);
        LETTER_SERVICE_DATA.put(l7.getId(), l7);

        Letter l8 = new Letter("18", "Thailand", 439);
        LETTER_SERVICE_DATA.put(l8.getId(), l8);

        Letter l9 = new Letter("19", "Argentina", 387);
        LETTER_SERVICE_DATA.put(l9.getId(), l9);

        Letter l10 = new Letter("10", "Canada", 201);
        LETTER_SERVICE_DATA.put(l10.getId(), l10);

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
