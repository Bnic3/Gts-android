package com.gts.gts.NetworkRequests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by john.nana on 8/10/2017.
 */

public class SignupRequest extends StringRequest {
    private static final String URL = "https://gts-bnic3.c9users.io/api/estate";
    private Map<String, String> params;

    //e_name, contact, contact_email
    public SignupRequest(String ename, String phone, String email, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.POST, URL, listener, errorListener);

        params = new HashMap<>();
        params.put("e_name", ename);
        params.put("contact", phone);
        params.put("contact_email", email);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
