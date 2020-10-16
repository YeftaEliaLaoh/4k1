package com.example.a4k.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.a4k.MainApplication;
import com.example.a4k.R;
import com.example.a4k.tasks.Task;
import com.example.a4k.commons.CommonHelper;
import com.example.a4k.entities.Profile;
import com.example.a4k.events.GetProfileEvent;
import com.example.a4k.fragments.ProfileFragment;
import com.example.a4k.fragments.UsersFragment;
import com.google.android.material.navigation.NavigationView;
import com.squareup.otto.Subscribe;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{

    private CircleImageView ivProfilePic;
    private TextView tvProfileName;
    private TextView tvProfileEmail;
    private Profile mProfile;

    private DrawerLayout drawer;
    private boolean backPressed = true;

    /**
     * Get profile details event
     *
     * @param response : profile details
     */
    @Subscribe
    public void getProfile( GetProfileEvent response )
    {
        mProfile = response.getProfile();
        new CommonHelper().loadImageAsync(getApplicationContext(), mProfile.getResults().get(0).getPicture().getThumbnail(), ivProfilePic, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        String userName = mProfile.getResults().get(0).getName().getFirst() + " " + mProfile.getResults().get(0).getName().getLast();
        tvProfileName.setText(userName);
        tvProfileEmail.setText(mProfile.getResults().get(0).getEmail());
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        MainApplication.getEventBus().register(this);
    }

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpViews();
        new Task().getProfile();
        new CommonHelper().replaceFragment(getSupportFragmentManager(), R.id.container, UsersFragment.newInstance(), false);
    }

    /**
     * Method used to set up views like toolbar, navigation drawer, etc.
     */
    private void setUpViews()
    {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerLayout = navigationView.getHeaderView(0);
        ivProfilePic = (CircleImageView) headerLayout.findViewById(R.id.iv_profile_pic);
        tvProfileName = (TextView) headerLayout.findViewById(R.id.tv_profile_name);
        tvProfileEmail = (TextView) headerLayout.findViewById(R.id.tv_profile_email);

        ivProfilePic.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick( View v )
            {
                new CommonHelper().replaceFragment(getSupportFragmentManager(), R.id.container, ProfileFragment.newInstance(mProfile), false);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        MainApplication.getEventBus().unregister(this);
    }

    /**
     * Hide drawer or exit application on back press event based on user confirmation
     */
    @Override
    public void onBackPressed()
    {
        if( drawer.isDrawerOpen(GravityCompat.START) )
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            exitOnBackButton();
        }
    }

    /**
     * show exit alert to get exit confirmation from user
     */
    private void exitOnBackButton()
    {
        if( backPressed )
        {
            new CommonHelper().showSnackBar(drawer, getString(R.string.msg_back_to_exit));

            Thread splashThread = new Thread()
            {
                @Override
                public void run()
                {
                    try
                    {
                        backPressed = false;
                        sleep(2000);
                    }
                    catch ( Exception e )
                    {
                        e.getMessage();
                    }
                    finally
                    {
                        backPressed = true;
                    }
                }
            };
            splashThread.start();
        }
        else
        {
            super.onBackPressed();
        }
    }

    /**
     * Navigation menu events
     *
     * @param item
     *
     * @return
     */
    @Override
    public boolean onNavigationItemSelected( MenuItem item )
    {

        switch ( item.getItemId() )
        {
            case R.id.nav_users:
                new CommonHelper().replaceFragment(getSupportFragmentManager(), R.id.container, UsersFragment.newInstance(), false);
                break;
            case R.id.nav_share:
                new CommonHelper().shareTextUrl(this);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}