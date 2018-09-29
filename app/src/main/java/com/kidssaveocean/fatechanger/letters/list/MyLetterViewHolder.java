package com.kidssaveocean.fatechanger.letters.list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kidssaveocean.fatechanger.R;
import com.kidssaveocean.fatechanger.letters.data.Letter;

public class MyLetterViewHolder extends RecyclerView.ViewHolder {
    ImageView bgColor;
    TextView number;
    TextView country;
    TextView letters;

    public MyLetterViewHolder(View itemView) {
        super(itemView);

        bgColor = itemView.findViewById(R.id.bg_number);
        number = itemView.findViewById(R.id.number);
        country = itemView.findViewById(R.id.city);
        letters = itemView.findViewById(R.id.letters);
    }

    public void bind(int position, int color, Letter letter) {
        number.setText(String.valueOf(position + 1));
        bgColor.setColorFilter(color);
        country.setText(letter.getCountry());
        letters.setText(String.valueOf(letter.getLetters()));
    }


}
