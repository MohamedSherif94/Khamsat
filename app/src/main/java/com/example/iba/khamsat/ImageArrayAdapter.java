package com.example.iba.khamsat;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ImageArrayAdapter extends ArrayAdapter<MyImage>{

    public ImageArrayAdapter(Activity context, ArrayList<MyImage> images){
        super(context, 0 , images);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }


        // Get the {@link AndroidFlavor} object located at this position in the list
        MyImage currentImage = getItem(position);
        // Find the TextView in the list_item.xml layout with the ID version_name
        TextView descriptionTextView = (TextView) listItemView.findViewById(R.id.textview_description);
        // Get the version name from the current AndroidFlavor object and
        // set this text on the name TextView
        descriptionTextView.setText(currentImage.getDescription());

        // Find the ImageView in the list_item.xml layout with the ID list_item_icon
        ImageView imageView = (ImageView) listItemView.findViewById(R.id.image);
        // Get the image resource ID from the current AndroidFlavor object and
        // set the image to iconView
        imageView.setImageBitmap(currentImage.getBitmap());

        // Return the whole list item layout (containing 2 TextViews and an ImageView)
        // so that it can be shown in the ListView
        return listItemView;
    }
}
