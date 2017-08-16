package com.gts.gts.NetworkRequests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.gts.gts.Utility.Endpoints;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by john.nana on 8/14/2017.
 */

public class OTPverify extends StringRequest {

    private static final String URL = Endpoints.OTP_VERIFY_URL;
    private Map<String, String> params;

    //e_name, contact, contact_email
    public OTPverify(String eid, String phone,String OTP, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.POST, URL, listener, errorListener);

        params = new HashMap<>();
        params.put("eid", eid);
        params.put("phone", phone);
        params.put("token", OTP);


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
