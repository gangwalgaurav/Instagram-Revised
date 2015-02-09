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
import com.codepath.gangwal.instagramclient.pojo.Comment;
import com.makeramen.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

/**
 * Created by gangwal on 2/8/15.
 */
public class CommentAdapter extends ArrayAdapter<Comment> {

    private class ViewHolder{
        ImageView ivProfilePicture;
        TextView tvCommentText;
        TextView tvCreatedTime;
    }

        private final Context context;

        public CommentAdapter(Context context,  ArrayList<Comment> objects) {
            super(context, R.layout.comments_view,objects);
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Comment  comment = getItem(position);
            final ViewHolder viewHolder;
            if(convertView==null)
            {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.comments_feed,parent,false);
                //TODO Might need to reuse the old Views
                viewHolder.tvCommentText = (TextView)convertView.findViewById(R.id.tvCComment);
                viewHolder.tvCreatedTime = (TextView) convertView.findViewById(R.id.tvCCreatedTime);
                viewHolder.ivProfilePicture = (ImageView)convertView.findViewById(R.id.ivCProfiePhoto);

                convertView.setTag(viewHolder);
            }  else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            Transformation transformation = new RoundedTransformationBuilder()
                    .cornerRadiusDp(25)
                    .oval(false)
                    .build();
            Picasso.with(getContext())
                    .load(comment.getUserProfilePic())
                    .placeholder(R.drawable.placeholder)
                    .fit()
                    .transform(transformation)
                    .into(viewHolder.ivProfilePicture);


            viewHolder.tvCommentText.setText(Html.fromHtml(comment.toString().trim()));
            viewHolder.tvCreatedTime.setText(Html.fromHtml(comment.getCreatedTime()));

            return convertView;
        }
    }


