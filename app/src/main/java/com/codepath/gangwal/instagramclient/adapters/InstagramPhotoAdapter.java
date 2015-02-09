package com.codepath.gangwal.instagramclient.adapters;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.gangwal.instagramclient.PhotosActivity;
import com.codepath.gangwal.instagramclient.R;
import com.codepath.gangwal.instagramclient.fragments.MoreDialog;
import com.codepath.gangwal.instagramclient.fragments.TaskDialog;
import com.codepath.gangwal.instagramclient.pojo.InstagramPhoto;
import com.makeramen.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.json.JSONArray;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by gangwal on 2/4/15.
 */



public class InstagramPhotoAdapter extends ArrayAdapter<InstagramPhoto> {

    private class ViewHolder{
        ImageView ivPhoto;
        TextView tvCaption;
        ImageView ivProfilePicture;
        TextView tvUserName;
        ImageView ivCreatedTimeIcon;
        TextView tvCreatedTime;
        TextView tvLocation;
        ImageView ivLocationIcon;
        ImageView ivLikesIcon;
        TextView tvLikesCount;
        TextView tvViewAllComments;
        TextView tvComment1,tvComment2;
        ImageView ivLikeButton;
        ImageView ivCommentButton;
        ImageView ivMoreButton;

    }
    private PhotosActivity mContext;
    public InstagramPhotoAdapter(PhotosActivity context, int resource, List<InstagramPhoto> objects) {
        super(context.getBaseContext(),R.layout.item_feed, objects);
        mContext = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final InstagramPhoto photo = getItem(position);
        final ViewHolder viewHolder;
        if(convertView==null)
        {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_feed,parent,false);
            viewHolder.ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);
            viewHolder.tvCaption=(TextView) convertView.findViewById(R.id.tvCaption);
            viewHolder.ivProfilePicture = (ImageView)convertView.findViewById(R.id.ivProfiePhoto);
            viewHolder.tvUserName = (TextView)convertView.findViewById(R.id.tvUsername);
            viewHolder.ivCreatedTimeIcon = (ImageView)convertView.findViewById(R.id.ivCreatedTimeIcon);
            viewHolder.tvCreatedTime = (TextView)convertView.findViewById(R.id.tvCreatedTime);
            viewHolder.ivLocationIcon = (ImageView)convertView.findViewById(R.id.ivLocationIcon);
            viewHolder.tvLocation = (TextView)convertView.findViewById(R.id.tvLocation);

            viewHolder.ivLikesIcon = (ImageView)convertView.findViewById(R.id.ivLikesIcon);
            viewHolder.tvLikesCount = (TextView)convertView.findViewById(R.id.tvLikesCount);

            viewHolder.tvViewAllComments = (TextView)convertView.findViewById(R.id.tvViewAllComment);
            viewHolder.tvComment1 = (TextView)convertView.findViewById(R.id.tvComment1);
            viewHolder.tvComment2 = (TextView)convertView.findViewById(R.id.tvComment2);
            viewHolder.ivLikeButton = (ImageView)convertView.findViewById(R.id.ivLike);
            viewHolder.ivCommentButton = (ImageView)convertView.findViewById(R.id.ivMakeComment);
            viewHolder.ivMoreButton= (ImageView)convertView.findViewById(R.id.ivMore);
            convertView.setTag(viewHolder);
        }  else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.ivProfilePicture.setImageResource(0);
        Transformation transformation = new RoundedTransformationBuilder()
                .cornerRadiusDp(25)
                .oval(false)
                .build();
        Picasso.with(getContext())
                .load(photo.getProfilePicUrl())
                .placeholder(R.drawable.placeholder)
                .fit()
                .transform(transformation)
                .into(viewHolder.ivProfilePicture);
        viewHolder.tvUserName.setText(Html.fromHtml("<b>"+photo.getUsername()+"</b>"));

        viewHolder.ivCreatedTimeIcon.setImageResource(R.drawable.feed_clock);
        viewHolder.tvCreatedTime.setText(photo.getCreateTime());
        if(photo.getLocation()!=null) {
            viewHolder.ivLocationIcon.setImageResource(R.drawable.feed_location);
            viewHolder.tvLocation.setText(photo.getLocation());
        }
        viewHolder.ivPhoto.setImageResource(0);

        Picasso.with(getContext()).load(photo.getImageUrl()).into(viewHolder.ivPhoto);

        viewHolder.ivLikesIcon.setImageResource(R.drawable.feed_like_small);
        final int likeCount = photo.getLikesCount();
        viewHolder.tvLikesCount.setText(Html.fromHtml("<b> <font color=#2D5B81>" + NumberFormat.getNumberInstance(Locale.US).format(likeCount) + " likes</font></b>"));
        viewHolder.tvLikesCount.setTag(false);

        viewHolder.tvCaption.setText(Html.fromHtml(photo.getCaption().toString()));

        viewHolder.tvViewAllComments.setText(Html.fromHtml("<b> <font color=#A5A9AC>view all " + photo.getCommentsCount() + " comments</font></b>"));
        viewHolder.ivLikeButton.setImageResource(R.drawable.feed_button_like);
        viewHolder.tvViewAllComments.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
               showCommentDialog(mContext, null, photo.getCommentJson());
            }
        });

        viewHolder.ivCommentButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showCommentDialog(mContext, null, photo.getCommentJson());
            }
        });
        final Animation anim = AnimationUtils.loadAnimation(mContext, R.anim.scale);
        viewHolder.ivLikeButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                boolean liked = (Boolean) viewHolder.tvLikesCount.getTag();
                StringTokenizer st = new StringTokenizer(viewHolder.tvLikesCount.getText().toString(), "likes");
                int currentLikeCount = Integer.parseInt(st.nextToken().trim().replace(",",""));
                int likeCountNew;
                int drawLikeImage;

                if(liked)
                {
                    viewHolder.tvLikesCount.setTag(false);
                    likeCountNew = currentLikeCount -1;
                    drawLikeImage = R.drawable.feed_button_like;
                }
                else {
                    viewHolder.tvLikesCount.setTag(true);
                    likeCountNew = currentLikeCount +1;
                    drawLikeImage = R.drawable.feed_button_like_active;
                }
                viewHolder.tvLikesCount.setText(Html.fromHtml("<b> <font color=#2D5B81>" + NumberFormat.getNumberInstance(Locale.US).format(likeCountNew) + " likes</font></b>"));
                viewHolder.ivLikeButton.setImageResource(drawLikeImage);
                viewHolder.ivLikeButton.startAnimation(anim);

            }
        });

        //TODO If condition here
        if(photo.getComment1()!=null)
            viewHolder.tvComment1.setText(Html.fromHtml(photo.getComment1().toString()));
        if(photo.getComment2()!=null)
            viewHolder.tvComment2.setText(Html.fromHtml(photo.getComment2().toString()));

        viewHolder.tvViewAllComments.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showCommentDialog(mContext, null, photo.getCommentJson());
            }
        });

        viewHolder.ivMoreButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showMoreDialog(mContext);
            }
        });

        return convertView;
    }

    private void showMoreDialog(PhotosActivity context){
       MoreDialog dailog = MoreDialog.getInstance(context.getSupportFragmentManager());
        dailog.show(context.getSupportFragmentManager(),"");
    }

    private void showCommentDialog(PhotosActivity context, InstagramPhoto item, JSONArray comments) {
        TaskDialog dailog = TaskDialog.
                getInstance(item, comments, context.getSupportFragmentManager());
        dailog.show(context.getSupportFragmentManager(),"");
    }


}
