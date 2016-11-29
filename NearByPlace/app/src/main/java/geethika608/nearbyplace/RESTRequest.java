package geethika608.nearbyplace;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


public class RESTRequest {
    public static abstract class RESTAPI {
        public abstract void onSuccess(JSONObject response, boolean isSuccess);

      /*  public abstract void onFailure(String Error, String message);

        public abstract void onTimeout(String message);*/
    }

    String TAG = "RESTAPI";
    ProgressDialog pDialog;
    Timer timer;
    Context context;
    View view;
    String message_type = "snack";
    boolean Want_Progress_dialog = true;
    boolean isMassage = true;

    public RESTRequest(Context context, View view, String message_type) {
        this.context = context;
        this.view = view;
        this.message_type = message_type;
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
    }


    public RESTRequest(Context context) {
        this.context = context;
        this.Want_Progress_dialog = false;
        this.isMassage = false;
    }

    public void POST(String what_is, String end_url, String data, final RESTAPI handler) {

        Log.d(TAG, "POST");
        ShowProgressBar(true);
        JSONObject params = new JSONObject();
        try {
            params.put("data", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = Ipaddress.GetIP() + end_url;
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, params,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("response", response.toString());
                        try {
                            if (response != null) {
                                if (response.get("status").toString().equals("success")) {
                                    handler.onSuccess(response, true);
                                    ShowProgressBar(false);
                                } else {
                                    ShowMSG(response.get("error_message").toString());
                                    ShowProgressBar(false);
                                    handler.onSuccess(null, false);
                                }
                            } else {
                                ShowMSG("Something went wrong");
                                ShowProgressBar(false);
                                handler.onSuccess(null, false);
                            }

                        } catch (JSONException e) {
                            ShowProgressBar(false);
                            handler.onSuccess(null, false);
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                ShowMSG("Something went wrong");
                ShowProgressBar(false);
                handler.onSuccess(null, false);
                Log.d("Volley error", "Error: " + error.getMessage());
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

        };
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Adding request to request queue
        nearbyplace.getInstance().addToRequestQueue(jsonObjReq, what_is);
    }

    public void POST(String what_is, String end_url, JSONObject data, final RESTAPI handler) {
        Log.d(TAG, "POST");
        ShowProgressBar(true);
        JSONObject params = new JSONObject();
        try {
            params.put("data", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = Ipaddress.GetIP() + end_url;
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, params,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("response", response.toString());
                        try {
                            if (response != null) {
                                if (response.get("status").toString().equals("success")) {
                                    handler.onSuccess(response, true);
                                    ShowProgressBar(false);
                                } else {
                                    ShowMSG(response.get("error_message").toString());
                                    ShowProgressBar(false);
                                    handler.onSuccess(null, false);
                                }
                            } else {
                                ShowMSG("Something went wrong");
                                ShowProgressBar(false);
                                handler.onSuccess(null, false);
                            }

                        } catch (JSONException e) {
                            ShowProgressBar(false);
                            handler.onSuccess(null, false);
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                ShowMSG("Something went wrong");
                ShowProgressBar(false);
                handler.onSuccess(null, false);
                Log.d("Volley error", "Error: " + error.getMessage());
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

        };
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Adding request to request queue
        nearbyplace.getInstance().addToRequestQueue(jsonObjReq, what_is);
    }

    public void POST(String what_is, String end_url, JSONArray data, final RESTAPI handler) {
        Log.d(TAG, "POST");
        ShowProgressBar(true);
        JSONObject params = new JSONObject();
        try {
            params.put("data", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = Ipaddress.GetIP() + end_url;
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, params,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("response", response.toString());
                        try {
                            if (response != null) {
                                if (response.get("status").toString().equals("success")) {
                                    handler.onSuccess(response, true);
                                    ShowProgressBar(false);
                                } else {
                                    ShowMSG(response.get("error_message").toString());
                                    ShowProgressBar(false);
                                    handler.onSuccess(null, false);
                                }
                            } else {
                                ShowMSG("Something went wrong");
                                ShowProgressBar(false);
                                handler.onSuccess(null, false);
                            }

                        } catch (JSONException e) {
                            ShowMSG("Something went wrong");
                            ShowProgressBar(false);
                            handler.onSuccess(null, false);
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                ShowMSG("Something went wrong");
                ShowProgressBar(false);
                handler.onSuccess(null, false);
                Log.d("Volley error", "Error: " + error.getMessage());
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

        };
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Adding request to request queue
        nearbyplace.getInstance().addToRequestQueue(jsonObjReq, what_is);
    }


    public void GET(String end_url, String data, final RESTAPI handler) {
        Log.d(TAG, "GET");
        ShowProgressBar(true);
        String url;
        if (data != null) {
            url = Ipaddress.GetIP() + end_url + data;
        } else {
            url = Ipaddress.GetIP() + end_url;
        }

        Log.d("txt", url);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response != null) {
                        if (response.get("status").toString().equals("success")) {
                            handler.onSuccess(response, true);
                            ShowProgressBar(false);
                        } else {
                            ShowMSG(response.get("error_message").toString());
                            ShowProgressBar(false);
                            handler.onSuccess(null, false);
                        }
                    } else {
                        ShowMSG("Something went wrong");
                        ShowProgressBar(false);
                        handler.onSuccess(null, false);
                    }

                } catch (JSONException e) {
                    ShowMSG("Something went wrong");
                    ShowProgressBar(false);
                    handler.onSuccess(null, false);
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                ShowMSG("Something went wrong");
                ShowProgressBar(false);
                handler.onSuccess(new JSONObject(), false);
                VolleyLog.d("fi", "Error: " + error.getMessage());
                String body;
                //get status code here
                String statusCode = String.valueOf(error.networkResponse);
                Log.d("statusCode", statusCode);
                //get response body and parse with appropriate encoding
                if (error.networkResponse.data != null) {
                    try {
                        body = new String(error.networkResponse.data, "UTF-8");
                        Log.d("body", body);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Adding request to request queue
        nearbyplace.getInstance().addToRequestQueue(jsonObjReq);
    }

    public void GETMap(String end_url, final RESTAPI handler) {
        Log.d(TAG, "GETMAP");
        ShowProgressBar(true);
        Log.d("txt", end_url);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                end_url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                if (response != null) {
                    ShowProgressBar(false);
                    handler.onSuccess(response, true);
                } else {
                    ShowProgressBar(false);
                    handler.onSuccess(null, false);
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                ShowMSG("Something went wrong");
                ShowProgressBar(false);
                handler.onSuccess(new JSONObject(), false);
                VolleyLog.d("fi", "Error: " + error.getMessage());
            }
        });
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Adding request to request queue
        nearbyplace.getInstance().addToRequestQueue(jsonObjReq);
    }

    public void PUT(String what_is, String end_url, String data, final RESTAPI handler) {
        Log.d(TAG, "PUT");
        JSONObject params = new JSONObject();
        if (data != null) {
            try {
                params.put("auth", data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            params = null;
        }
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.PUT,
                end_url, params,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        // Log.d("response", response.toString());
                        try {
                            if (response != null) {
                                if (response.get("status").toString().equals("success")) {
                                    handler.onSuccess(response, true);
                                    ShowProgressBar(false);
                                } else {
                                    ShowMSG(response.get("error_message").toString());
                                    ShowProgressBar(false);
                                    handler.onSuccess(null, false);
                                }
                            } else {
                                ShowMSG("Something went wrong");
                                ShowProgressBar(false);
                                handler.onSuccess(null, false);
                            }

                        } catch (JSONException e) {
                            ShowMSG("Something went wrong");
                            ShowProgressBar(false);
                            handler.onSuccess(null, false);
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                ShowMSG("Something went wrong");
                Log.d("Volley error", "Error: " + error.getMessage());
                ShowProgressBar(false);
                handler.onSuccess(null, false);
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

        };
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Adding request to request queue
        nearbyplace.getInstance().addToRequestQueue(jsonObjReq, what_is);
    }

    public void PUT(String what_is, String end_url, JSONObject data, final RESTAPI handler) {
        Log.d(TAG, "PUT");
        JSONObject params = new JSONObject();
        if (data != null) {
            try {
                params.put("auth", data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            params = null;
        }
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.PUT,
                end_url, params,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        // Log.d("response", response.toString());
                        try {
                            if (response != null) {
                                if (response.get("status").toString().equals("success")) {
                                    handler.onSuccess(response, true);
                                    ShowProgressBar(false);
                                } else {
                                    ShowMSG(response.get("error_message").toString());
                                    ShowProgressBar(false);
                                    handler.onSuccess(null, false);
                                }
                            } else {
                                ShowMSG("Something went wrong");
                                ShowProgressBar(false);
                                handler.onSuccess(null, false);
                            }

                        } catch (JSONException e) {
                            ShowMSG("Something went wrong");
                            ShowProgressBar(false);
                            handler.onSuccess(null, false);
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                ShowMSG("Something went wrong");
                Log.d("Volley error", "Error: " + error.getMessage());
                ShowProgressBar(false);
                handler.onSuccess(null, false);
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

        };
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Adding request to request queue
        nearbyplace.getInstance().addToRequestQueue(jsonObjReq, what_is);
    }

    public void PUT(String what_is, String end_url, JSONArray data, final RESTAPI handler) {
        Log.d(TAG, "PUT");
        JSONObject params = new JSONObject();
        if (data != null) {
            try {
                params.put("auth", data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            params = null;
        }
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.PUT,
                end_url, params,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        // Log.d("response", response.toString());
                        try {
                            if (response != null) {
                                if (response.get("status").toString().equals("success")) {
                                    handler.onSuccess(response, true);
                                    ShowProgressBar(false);
                                } else {
                                    ShowMSG(response.get("error_message").toString());
                                    ShowProgressBar(false);
                                    handler.onSuccess(null, false);
                                }
                            } else {
                                ShowMSG("Something went wrong");
                                ShowProgressBar(false);
                                handler.onSuccess(null, false);
                            }

                        } catch (JSONException e) {
                            ShowMSG("Something went wrong");
                            ShowProgressBar(false);
                            handler.onSuccess(null, false);
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                ShowMSG("Something went wrong");
                Log.d("Volley error", "Error: " + error.getMessage());
                ShowProgressBar(false);
                handler.onSuccess(null, false);
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

        };
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Adding request to request queue
        nearbyplace.getInstance().addToRequestQueue(jsonObjReq, what_is);
    }


    public void DELETE(String end_url, String data, final RESTAPI handler) {
        Log.d(TAG, "DELETE");
        ShowProgressBar(true);
        String url;
        if (data != null) {
            url = Ipaddress.GetIP() + end_url + data;
        } else {
            url = Ipaddress.GetIP() + end_url;
        }

        Log.d("txt", url);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.DELETE,
                url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response != null) {
                        if (response.get("status").toString().equals("success")) {
                            handler.onSuccess(response, true);
                        } else {
                            ShowMSG(response.get("error_message").toString());
                            ShowProgressBar(false);
                            handler.onSuccess(null, false);
                        }
                    } else {
                        ShowMSG("Something went wrong");
                        ShowProgressBar(false);
                        handler.onSuccess(null, false);
                    }

                } catch (JSONException e) {
                    ShowMSG("Something went wrong");
                    ShowProgressBar(false);
                    handler.onSuccess(null, false);
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                ShowMSG("Something went wrong");
                ShowProgressBar(false);
                handler.onSuccess(new JSONObject(), false);
                VolleyLog.d("fi", "Error: " + error.getMessage());
            }
        });
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Adding request to request queue
        nearbyplace.getInstance().addToRequestQueue(jsonObjReq);
    }

    public void ShowMSG(String msg) {
        if (isMassage) {
            if (message_type.equals("snack")) {
                new ShowSnackBar(view, msg);
            } else if (message_type.equals("toast")) {
                Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
            }
        }
    }

    public void ShowProgressBar(boolean status) {
        if (Want_Progress_dialog) {
            if (status) {
                pDialog.show();
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        pDialog.cancel();
                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (isMassage) {
                                    if (message_type.equals("snack")) {
                                        new ShowSnackBar(view, "Something went wrong try again later");
                                    } else if (message_type.equals("toast")) {
                                        Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show();
                                    }
                                }
                                nearbyplace.getInstance().cancelPendingRequests(TAG);
                            }
                        });

                    }
                }, 60000);
            } else {
                pDialog.cancel();
                timer.cancel();
            }
        }
    }
}
