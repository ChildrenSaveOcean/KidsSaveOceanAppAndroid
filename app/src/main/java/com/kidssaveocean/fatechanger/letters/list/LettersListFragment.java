package com.kidssaveocean.fatechanger.letters.list;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kidssaveocean.fatechanger.R;
import com.kidssaveocean.fatechanger.letters.LetterInjection;
import com.kidssaveocean.fatechanger.letters.data.Letter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LettersListFragment extends Fragment implements LettersListContract.View {

    private MyLetterAdapter mAdapter;
    //endregion

    private Context context;

    private LettersListContract.Presenter presenter;

    //region new instance
    public static LettersListFragment newInstance() {

        Bundle args = new Bundle();

        LettersListFragment fragment = new LettersListFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public LettersListFragment() {
        // Required empty public constructor
    }
    //endregion

    //region lifecycle
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;

        presenter = new LettersListPresenter(LetterInjection.provideLettersRepository(context), this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_letters_list, container, false);

        //region UI elements
        RecyclerView mRecyclerView = view.findViewById(R.id.recyclerView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter
        mAdapter = new MyLetterAdapter(context.getResources().getIntArray(R.array.listColors));
        mRecyclerView.setAdapter(mAdapter);

        presenter.loadLetter();

        return view;
    }
    //endregion

    //region implementation LettersListContract.View

    @Override
    public void setLetters(List<Letter> cachedLetters) {
        mAdapter.updateItems(cachedLetters);
    }

    //endregion

}
