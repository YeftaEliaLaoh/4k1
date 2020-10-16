package com.example.a4k.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a4k.MainApplication;
import com.example.a4k.R;
import com.example.a4k.tasks.Task;
import com.example.a4k.adapters.UsersAdapter;
import com.example.a4k.commons.CommonHelper;
import com.example.a4k.entities.Result;
import com.example.a4k.events.GetUsersEvent;
import com.example.a4k.listeners.EndlessRecyclerOnScrollListener;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class UsersFragment extends Fragment implements UsersAdapter.OnUserClickListener {

    private List<Result> mUsers = new ArrayList<>();
    private UsersAdapter mUsersAdapter = null;

    ImageView ivErrorMessages;
    RecyclerView recyclerView;
    RelativeLayout viewLoading;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public UsersFragment() {
    }

    public static UsersFragment newInstance() {
        UsersFragment fragment = new UsersFragment();
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        MainApplication.getEventBus().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        MainApplication.getEventBus().unregister(this);
    }

    @Subscribe
    public void OnUsersReceived(GetUsersEvent response) {

        if (response.getError() == null) {

            setItemToRecyclerView(response.getUsers().getResults());

        } else {

            ivErrorMessages.setImageResource(R.drawable.ic_no_internet);
            ivErrorMessages.setVisibility(View.VISIBLE);
        }

        viewLoading.setVisibility(View.GONE);
    }

    /**
     * Set data to recycler view
     *
     * @param results
     */
    private void setItemToRecyclerView(List<Result> results) {

        if (recyclerView != null) {
            if (results != null) {
                if (results.size() > 0) {
                    ivErrorMessages.setVisibility(View.GONE);

                    if (mUsersAdapter == null) {
                        mUsers = results;
                        mUsersAdapter = new UsersAdapter(results, this);
                        recyclerView.setAdapter(mUsersAdapter);
                    } else {
                        mUsers.addAll(results);
                        mUsersAdapter.notifyDataSetChanged();
                    }
                } else {
                    ivErrorMessages.setImageResource(R.drawable.ic_empty);
                    ivErrorMessages.setVisibility(View.VISIBLE);
                }

            } else {
                ivErrorMessages.setImageResource(R.drawable.ic_empty);
                ivErrorMessages.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_common_list, container, false);
        recyclerView = view.findViewById(R.id.list);
        viewLoading = view.findViewById(R.id.view_loading);
        ivErrorMessages = view.findViewById(R.id.iv_error_messages);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        setItemToRecyclerView(null);

        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page > 1 && mUsers.size() < 50) {
                    new CommonHelper().showSnackBar(recyclerView, "Loading more...");
                    new Task().getUsers(current_page);
                }
            }
        });

        getUsers();
        return view;
    }

    void getUsers() {
        viewLoading.setVisibility(View.VISIBLE);
        new Task().getUsers(1);
    }

    @Override
    public void onUserSelected(Result item) {
        new CommonHelper().showSnackBar(recyclerView, "Clicked : " + item.getName().getFirst());
    }
}