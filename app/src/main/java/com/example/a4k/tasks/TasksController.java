package com.example.a4k.tasks;

import com.android.volley.VolleyError;
import com.example.a4k.commons.web.WebRequestManager;
import com.example.a4k.MainApplication;
import com.example.a4k.constants.TaskConstants;
import com.example.a4k.entities.Profile;
import com.example.a4k.events.GetProfileEvent;
import com.example.a4k.events.GetUsersEvent;

/**
 * Controller class used to control all web and database activities
 */
public class TasksController implements WebRequestManager.WebProcessListener {

    /**
     * Web api success listener
     *
     * @param taskID : described for which task web api succeed
     * @param object : success object
     */
    @Override
    public void onWebProcessSuccess(int taskID, Object object) {

        switch (taskID) {

            case TaskConstants.WS_GET_PROFILE:
                Profile profile = (Profile) object;
                MainApplication.getEventBus().post(new GetProfileEvent(profile));
                break;

            case TaskConstants.WS_GET_USERS:
                Profile users = (Profile) object;
                MainApplication.getEventBus().post(new GetUsersEvent(users));
                break;
        }
    }

    /**
     * Web api failed listener
     *
     * @param error  : web api access error
     * @param taskID : described for which task web api failed
     */
    @Override
    public void onWebProcessFailed(VolleyError error, int taskID) {

        switch (taskID) {

            case TaskConstants.WS_GET_PROFILE:
                MainApplication.getEventBus().post(new GetProfileEvent(error));
                break;

            case TaskConstants.WS_GET_USERS:
                MainApplication.getEventBus().post(new GetUsersEvent(error));
                break;
        }
    }
}