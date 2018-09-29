package com.kidssaveocean.fatechanger.letters.list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kidssaveocean.fatechanger.R;
import com.kidssaveocean.fatechanger.letters.data.Letter;

import java.util.ArrayList;
import java.util.List;

public class MyLetterAdapter extends RecyclerView.Adapter<MyLetterViewHolder> {
    int[] colors;

    private List<Letter> items = new ArrayList<>();
    public MyLetterAdapter(int[] colors) {
        this.colors = colors;
    }

    @NonNull
    @Override
    public MyLetterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_letter, parent, false);
        return new MyLetterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyLetterViewHolder holder, int position) {
        holder.bind(position, colors[position % (colors.length - 1)], items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    void updateItems(List<Letter> cachedLetters) {
        items = cachedLetters;
        notifyDataSetChanged();
    }
}
