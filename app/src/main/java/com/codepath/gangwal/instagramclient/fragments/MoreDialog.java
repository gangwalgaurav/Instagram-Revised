package com.codepath.gangwal.instagramclient.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.codepath.gangwal.instagramclient.R;


public class MoreDialog extends DialogFragment {

    private EditText mTitle;

    private ItemAddListener mCallback;

    private static FragmentManager sFragmentManager;


    public static MoreDialog getInstance(FragmentManager fragmentManager){
        MoreDialog frag = new MoreDialog();
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
//        getActivity().findViewById(android.R.id.title).setVisibility(View.GONE);        //setTitle("Something");
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        View v = inflater.inflate(R.layout.more_options, container,false);

        TextView option1 = (TextView) v.findViewById(R.id.tvInappropriate);
        option1.setText("Report Inapporpriate");

        TextView option2 = (TextView) v.findViewById(R.id.tvCopyURL);
        option2.setText("Copy Share URL");
//        try {
//            moreOptions.addView(option1);
//            moreOptions.addView(option2);
//        }
//        catch (Exception e) {
//            Log.e("Exception ", e.getLocalizedMessage());
//        }

        return v;
    }


}
