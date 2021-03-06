package com.rattletech.cityofontario.serviceFunction;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.rattletech.cityofontario.R;
import com.rattletech.cityofontario.config.ApiServiceConstants;
import com.rattletech.cityofontario.utilities.AppPreferences;

import com.rattletech.cityofontario.interfaces.CallbackServiceInterface;


import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by android1 on 6/6/2017.
 */

public class ServiceFunctions {
    Context context;
    CallbackServiceInterface callbackServiceInterface;


    String commonLocalUrl = "http://" + ApiServiceConstants.SERVICE_HOSTIP + ":" + ApiServiceConstants.SERVICE_HOSTPORT + "/";

    AppPreferences pref;
    ProgressDialog progressDialog = null;

    public ServiceFunctions(Context context, CallbackServiceInterface callbackServiceInterface) {
        this.context = context;
        this.callbackServiceInterface = callbackServiceInterface;
        pref = new AppPreferences(context);

    }
    public void serviceCallPOST(final String urlMethodName, JSONObject object) {

        String finalUrl = "";
        boolean gatewayFlag = true;
        commonLocalUrl = "http://" + pref.getLocalHostUrl() + "/";


            finalUrl = commonLocalUrl + urlMethodName;



                try {
                    progressDialog = new ProgressDialog(context);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage(context.getString(R.string.str_loading_please_wait));
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                }catch (Exception e){
                    e.printStackTrace();
                }


            final ProgressDialog finalProgressDialog = progressDialog;
            final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, finalUrl, object,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.e("VOLLEY response", String.valueOf(response));

                            callbackServiceInterface.callbackObjectCall(response);

                                    finalProgressDialog.dismiss();


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                        finalProgressDialog.dismiss();

                    try {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("result", "No Response from Gateway.");
                        callbackServiceInterface.callbackObjectCall(jsonObject);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    error.printStackTrace();
                    NetworkResponse networkResponse = error.networkResponse;
                    if (networkResponse != null && networkResponse.statusCode == 400) {
                    }
                }


            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String>  params = new HashMap<String, String>();
                    params.put("x-api-key", "rRw4jp5GM83TeNyIcWhhN3n4AeraZ0bRarM2hr4W");
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(50 * 1000, 1, 1.0f));
            requestQueue.add(jsonObjReq);

    }


}
