package com.kidssavetheocean.fatechanger.utility;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kidssavetheocean.fatechanger.R;

import java.util.List;


public class GeneralImageListCardView extends CardView {
    private TextView listTitle;
    private RecyclerView listRecyclerView;


    public GeneralImageListCardView(@NonNull Context context) {
        super(context);
        init();
    }

    public GeneralImageListCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GeneralImageListCardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init () {
        inflate(getContext(), R.layout.card_general_list, this);
        this.listTitle = (TextView) findViewById(R.id.card_list_title);
        this.listRecyclerView = (RecyclerView) findViewById(R.id.card_recycler_list);
        listRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void setListTitle (String title) {
        listTitle.setText(title);
    }

    public String getListTitle () {
        return listTitle.getText().toString();
    }

    public void setAdapter (GeneralImageListCardViewAdapter adapter) {
        listRecyclerView.setAdapter(adapter);
    }

    public static class GeneralImageListCard {
        int imageId;
        String titleText;
        String descriptionText;

        public GeneralImageListCard (int imageId, String titleText, String descriptionText) {
            this.imageId = imageId;
            this.titleText = titleText;
            this.descriptionText = descriptionText;
        }
    }

    public static class GeneralImageListCardViewAdapter extends RecyclerView.Adapter<GeneralImageListCardViewAdapter.ViewHolder> {
        private Context context;
        private List<GeneralImageListCard> cardList;

        public GeneralImageListCardViewAdapter (Context context, List<GeneralImageListCard> cardList) {
            this.context = context;
            this.cardList = cardList;
        }

        @NonNull
        @Override
        public GeneralImageListCardViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_list_item_with_image, parent, false);
            return new ViewHolder(cv);
        }

        @Override
        public void onBindViewHolder(@NonNull GeneralImageListCardViewAdapter.ViewHolder holder, int position) {
            GeneralImageListCard card = cardList.get(position);
            holder.title.setText(card.titleText);
            holder.description.setText(card.descriptionText);

            if (card.imageId != 0) {
                Glide.with(context)
                     .load(card.imageId)
//                     .apply(RequestOptions.bitmapTransform(new CropSquareTransformation())) todo fix used 'jp.wasabeef:glide-transformations:3.3.0'
                     .into(holder.cardImage);
            }

            if (cardList.size() - 1 == position) {
                holder.divider.setVisibility(INVISIBLE);
            }
            else {
                holder.divider.setVisibility(VISIBLE);
            }
        }

        @Override
        public int getItemCount() {
            return cardList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView cardImage;
            TextView title;
            TextView description;
            ImageView next;
            View divider;

            public ViewHolder (CardView cardView) {
                super(cardView);
                cardImage = (ImageView) cardView.findViewById(R.id.card_image);
                title = (TextView) cardView.findViewById(R.id.card_title);
                description = (TextView) cardView.findViewById(R.id.card_description);
                next = (ImageView) cardView.findViewById(R.id.card_next);
                divider = cardView.findViewById(R.id.divider);
            }
        }
    }


}
