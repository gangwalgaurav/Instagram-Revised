package com.codepath.gangwal.instagramclient.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.codepath.gangwal.instagramclient.R;
import com.codepath.gangwal.instagramclient.adapters.CommentAdapter;
import com.codepath.gangwal.instagramclient.pojo.Comment;
import com.codepath.gangwal.instagramclient.pojo.InstagramPhoto;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;



public class TaskDialog extends DialogFragment {

    private EditText mTitle;

    private ItemAddListener mCallback;

    private static InstagramPhoto sTask;
    private static JSONArray sComments;
    private static FragmentManager sFragmentManager;


    CommentAdapter cAdapter;
    public static TaskDialog getInstance(InstagramPhoto item, JSONArray comments, FragmentManager fragmentManager){
        TaskDialog frag = new TaskDialog();
        sTask = item;
        sComments = comments;
        sFragmentManager = fragmentManager;
        return frag;
    }



    public interface ItemAddListener {
        public void generateList();
    }

    //@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

//        try {
//            mCallback = (ItemAddListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement ItemAddListener");
//        }
    }

    //@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.comments_view, container);
        getDialog().setTitle(("Comments"));

        ListView lvComments = (ListView)v.findViewById(R.id.lvComments);
        ArrayList<Comment> commentsList = new ArrayList<>();

        for(int i=0;i<sComments.length();i++)
        {
            try {
                String commentText = sComments.getJSONObject(i).getString("text");
                String userName = sComments.getJSONObject(i).getJSONObject("from").getString("username");
                String commentTime = getRelativeTime(sComments.getJSONObject(i).getString("created_time"));
                String userProfilePicture = sComments.getJSONObject(i).getJSONObject("from").getString("profile_picture");
                Comment cmnt = new Comment(userName,commentText,commentTime,userProfilePicture);
                commentsList.add(cmnt);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        cAdapter = new CommentAdapter(getActivity().getBaseContext(),commentsList);

        lvComments.setAdapter(cAdapter);

        return v;
    }

    public String getRelativeTime(String createdTime)
    {
        if(createdTime!=null) {
            CharSequence refCreatedTime = DateUtils.getRelativeTimeSpanString(Long.parseLong(createdTime) * 1000);
            return refCreatedTime.toString();
        }
        else
            return "";
    }

}
