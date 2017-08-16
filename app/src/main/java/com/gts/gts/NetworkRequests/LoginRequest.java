package com.gts.gts.NetworkRequests;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.gts.gts.Utility.Endpoints;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by john.nana on 8/11/2017.
 */

public class LoginRequest extends StringRequest {
    private static final String URL = Endpoints.LOGIN_URL;
    private Map<String, String> params;

    public LoginRequest(String eid, String phone, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.POST, URL, listener, errorListener);

        params = new HashMap<>();
        params.put("eid", eid);
        params.put("phone", phone);
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        return super.parseNetworkResponse(response);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
