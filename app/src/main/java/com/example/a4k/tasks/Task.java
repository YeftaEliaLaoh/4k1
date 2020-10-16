package com.example.a4k.tasks;

import com.android.volley.Request;
import com.example.a4k.commons.web.WebRequestManager;
import com.example.a4k.MainApplication;
import com.example.a4k.constants.TaskConstants;
import com.example.a4k.constants.WebRequestConstants;
import com.example.a4k.entities.Profile;

/**
 * Represent all tasks including web requests and database operations
 */
public class Task extends TasksController {

    public void getProfile() {
        new WebRequestManager(this).makeRequest(MainApplication.getRequestQueue(), Request.Method.GET,
                TaskConstants.WS_GET_PROFILE,
                WebRequestConstants.END_POINT_PROFILE,
                null,
                Profile.class);
    }

    public void getUsers(int pageNo) {
        new WebRequestManager(this).makeRequest(MainApplication.getRequestQueue(), Request.Method.GET,
                TaskConstants.WS_GET_USERS,
                WebRequestConstants.END_POINT_PROFILE+"/?page=" + pageNo + "&results=10",
                null,
                Profile.class);
    }
}