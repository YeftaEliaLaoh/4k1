package com.example.a4k.fragments;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.a4k.commons.CommonHelper;
import com.example.a4k.R;
import com.example.a4k.entities.Profile;
import com.example.a4k.entities.Result;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.text.Html.FROM_HTML_MODE_LEGACY;

public class ProfileFragment extends Fragment {

    private static final String ARG_PARAM = "profile";
    CircleImageView ivProfilePic;
    private Profile mProfile;

    TextView tvProfileDetails;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(Profile profile) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM, profile);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mProfile = (Profile) getArguments().getSerializable(ARG_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ivProfilePic = view.findViewById(R.id.iv_profile_pic);
        tvProfileDetails = view.findViewById(R.id.tv_profile_details);
        Result results = mProfile.getResults().get(0);
        StringBuilder profileDetails = new StringBuilder();
        profileDetails.append("<B>Name</B><BR>");
        String name = results.getName().getFirst() + " " + results.getName().getLast();
        profileDetails.append(name);
        profileDetails.append("<BR><BR><B>DOB</B><BR>");
        profileDetails.append(results.getDob());
        profileDetails.append("<BR><BR><B>Gender</B><BR>");
        profileDetails.append(results.getGender());
        profileDetails.append("<BR><BR><B>Location</B><BR>");
        String address = results.getLocation().getStreet() + ", " + results.getLocation().getCity() + ",<Br>"
                + results.getLocation().getState() + " " + results.getLocation().getPostcode() + ".";
        profileDetails.append(address);
        profileDetails.append("<BR><BR><B>Contacts</B><BR>");
        profileDetails.append("Phone : ");
        profileDetails.append(results.getPhone());
        profileDetails.append("<BR>Cell : ");
        profileDetails.append(results.getCell());
        tvProfileDetails.setText(Html.fromHtml(profileDetails.toString(), FROM_HTML_MODE_LEGACY));

        new CommonHelper().loadImageAsync(getActivity().getApplicationContext(), results.getPicture().getLarge(), ivProfilePic, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        return view;
    }

}