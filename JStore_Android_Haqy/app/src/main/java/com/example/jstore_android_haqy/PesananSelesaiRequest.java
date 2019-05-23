package com.example.jstore_android_haqy;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class PesananSelesaiRequest extends StringRequest {
    private static final String FINISH_URL = "http://192.168.43.229:8080/finishtransaction";
    private Map<String, String> params;

    public PesananSelesaiRequest(String id, Response.Listener<String> listener) {
        super(Method.POST, FINISH_URL, listener, null);
        params = new HashMap<>();
        params.put("idinvoice",id);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }
}
