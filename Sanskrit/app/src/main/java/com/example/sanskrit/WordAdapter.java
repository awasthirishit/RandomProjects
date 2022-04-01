package com.example.sanskrit;

import android.app.Activity;
import android.media.Image;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {

    public WordAdapter(Activity context, ArrayList<Word> numbers) {
        super(context, 0, numbers);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Get the {@link AndroidFlavor} object located at this position in the list
        Word currentWord = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID version_name
        TextView english = (TextView) listItemView.findViewById(R.id.english);
        // Get the version name from the current AndroidFlavor object and
        // set this text on the name TextView
        english.setText(currentWord.getmEnglish());

        // Find the TextView in the list_item.xml layout with the ID version_number
        TextView numberTextView = (TextView) listItemView.findViewById(R.id.sanskrit);
        // Get the version number from the current AndroidFlavor object and
        // set this text on the number TextView
        numberTextView.setText(currentWord.getmSanskrit());

        ImageView imageView = (ImageView) listItemView.findViewById(R.id.imageview);
        int imageRes = currentWord.getmImage();
        if ( imageRes <=-1){
            imageView.setVisibility(View.GONE);
        }
        else{
            imageView.setImageResource(imageRes);

        }


        return listItemView;
    }

}