package com.codepath.gangwal.instagramclient.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.codepath.gangwal.instagramclient.R;
import com.codepath.gangwal.instagramclient.adapters.CommentAdapter;
import com.codepath.gangwal.instagramclient.pojo.Comment;
import com.codepath.gangwal.instagramclient.pojo.InstagramPhoto;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;



public class TaskDialog extends DialogFragment {

    private EditText mTitle;
    private static TextView mDatePicker;
    private static TextView mTimePicker;
    private ItemAddListener mCallback;

    private Spinner mPriority;

    private static InstagramPhoto sTask;
    private static JSONArray sComments;
//    private ItemAddListener mCallback;
    private ImageButton mSubmit;
    private ImageButton mDateButton;
    private ImageButton mTimeButton;

    private ImageButton mDatePickerButton;
    private ImageButton mReset;
    private TextView mDescription;
    private static FragmentManager sFragmentManager;
    private EditText mEditText;
    private ArrayAdapter<String> mListAdapter;

    CommentAdapter cAdapter;
    public static TaskDialog getInstance(InstagramPhoto item, JSONArray comments, FragmentManager fragmentManager){
        TaskDialog frag = new TaskDialog();
        Bundle args = new Bundle();
        args.putString("title", "Title");
        frag.setArguments(args);
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

//        ImageView imageView = (ImageView)v.findViewById(R.id.ivCProfiePhoto);
        TextView comment = (TextView)v.findViewById(R.id.tvCComment);
        ArrayList<String> commentsList = new ArrayList<String>();

        for(int i=0;i<sComments.length();i++)
        {
//            imageView.setImageResource(R.drawable.icon);
            try {
//                comment.setText(sComments.getJSONObject(i).getString("text"));
                String commentText = sComments.getJSONObject(i).getString("text");
                String userName = sComments.getJSONObject(i).getJSONObject("from").getString("username");
                String commentTime = getRelativeTime(sComments.getJSONObject(i).getString("created_time"));
                Comment cmnt = new Comment(userName,commentText,commentTime);
                commentsList.add(cmnt.toString());
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
