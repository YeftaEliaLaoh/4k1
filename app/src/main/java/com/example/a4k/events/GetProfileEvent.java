package com.example.a4k.events;

import com.android.volley.VolleyError;
import com.example.a4k.entities.Profile;

public class GetProfileEvent {

    private Profile profile = null;
    private VolleyError error = null;

    public Profile getProfile() {
        return profile;
    }

    public GetProfileEvent(Profile profile) {
        this.profile = profile;
    }

    public GetProfileEvent(VolleyError error) {
        this.error = error;
    }

    public VolleyError getError() {
        return error;
    }
}
