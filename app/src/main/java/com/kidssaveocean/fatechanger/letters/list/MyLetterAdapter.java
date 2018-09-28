package com.kidssaveocean.fatechanger.letters.list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kidssaveocean.fatechanger.R;

public class MyLetterAdapter extends RecyclerView.Adapter<MyLetterViewHolder> {
    int[] colors;

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
        holder.bind(position, colors[position]);
    }

    @Override
    public int getItemCount() {
        return colors.length;
    }
}
