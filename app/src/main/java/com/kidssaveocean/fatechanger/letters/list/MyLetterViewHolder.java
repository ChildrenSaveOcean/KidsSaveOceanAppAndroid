package com.kidssaveocean.fatechanger.letters.list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kidssaveocean.fatechanger.R;

public class MyLetterViewHolder extends RecyclerView.ViewHolder {
    ImageView bgColor;
    TextView number;

    public MyLetterViewHolder(View itemView) {
        super(itemView);

        bgColor = itemView.findViewById(R.id.bg_number);
        number = itemView.findViewById(R.id.number);
    }

    public void bind(int position, int color) {
        number.setText(String.valueOf(position + 1));
        bgColor.setColorFilter(color);
    }


}
