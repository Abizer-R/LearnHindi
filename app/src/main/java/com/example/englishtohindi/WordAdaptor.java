package com.example.englishtohindi;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class WordAdaptor extends ArrayAdapter<Word> {

    private int colorReference;
    public WordAdaptor(Activity context, ArrayList<Word> words, int colorReference) {
        super(context, 0, words);
        this.colorReference = colorReference;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item,
                    parent,
                    false
            );
        }

        Word currWord = getItem(position);

        TextView hindiTxtView = listItemView.findViewById(R.id.hindiTextView);
        hindiTxtView.setText(currWord.getHindiTranslation());

        TextView englishTxtView = listItemView.findViewById(R.id.englishTextView);
        englishTxtView.setText(currWord.getEnglishTranslation());

        ImageView imageView = listItemView.findViewById(R.id.imageView);
        if(currWord.hasImage()) {
            imageView.setImageResource(currWord.getImageResourcId());
            imageView.setVisibility(View.VISIBLE);
        }
        else {
            imageView.setVisibility(View.GONE);
        }

        View list_item = listItemView.findViewById(R.id.list_item);
        int color = ContextCompat.getColor(getContext(), colorReference);
        GradientDrawable bgShape = (GradientDrawable) list_item.getBackground();
        bgShape.setColor(color);

//        View textContainer = listItemView.findViewById(R.id.textContainer);
//        int color = ContextCompat.getColor(getContext(), colorReference);
//        textContainer.setBackgroundColor(color);

        return listItemView;
    }
}
