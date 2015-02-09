package com.codepath.gangwal.instagramclient.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.gangwal.instagramclient.R;
import com.makeramen.RoundedTransformationBuilder;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

/**
 * Created by gangwal on 2/8/15.
 */
public class CommentAdapter extends ArrayAdapter<String> {
        private final Context context;

        public CommentAdapter(Context context,  ArrayList<String> objects) {
            super(context, R.layout.comments_view,objects );
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            String  comment = getItem(position);

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.comments_feed, parent, false);
            TextView textView = (TextView) rowView.findViewById(R.id.tvCComment);

            ImageView ivProfilePicture = (ImageView) rowView.findViewById(R.id.ivCProfiePhoto);
            ivProfilePicture.setImageResource(0);
            Transformation transformation = new RoundedTransformationBuilder()
                    .cornerRadiusDp(25)
                    .oval(false)
                    .build();

//            Picasso.with(getContext())
//                    .load(photo.getProfilePicUrl())
//                    .fit()
//                    .transform(transformation)
//                    .into(viewHolder.ivProfilePicture);
            textView.setText(Html.fromHtml(comment));
            // change the icon for Windows and iPhone

            return rowView;
        }
    }


