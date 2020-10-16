package com.example.a4k.events;

import com.android.volley.VolleyError;
import com.example.a4k.entities.Profile;

public class GetUsersEvent {

    private Profile profile = null;
    private VolleyError error = null;

    public Profile getUsers() {
        return profile;
    }

    public GetUsersEvent(Profile profile) {
        this.profile = profile;
    }

    public GetUsersEvent(VolleyError error) {
        this.error = error;
    }

    public VolleyError getError() {
        return error;
    }
}
