package com.example.appausa.model;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Log {

    public Log() {
    }

    public void insertlog(final String id, final String accion, final String descrip, String url, final Context x) {
        if (!accion.isEmpty() && !id.isEmpty() && !descrip.isEmpty() && !url.isEmpty()) {
            StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(x, "Problemas de conexion", Toast.LENGTH_LONG).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> param = new HashMap<String, String>();
                    param.put("cusername", id);
                    param.put("caccion", accion);
                    param.put("cdescrip", descrip);
                    return param;
                }
            };
            RequestQueue rq = Volley.newRequestQueue(x);
            rq.add(sr);
        }
    }

    // http://localhost/appausamovil/obtenerLogs.php?id=
    public ArrayList<Data> obtenerLogCuenta(String url, String username, final Context x){
        final ArrayList<Data> lista = new ArrayList<>();
        JsonArrayRequest js = new JsonArrayRequest(url+username, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jo = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jo = response.getJSONObject(i);
                        lista.add(new Data(jo.getString("cuenta"),jo.getString("fecha"),jo.getString("accion"),jo.getString("descrip")));
                    } catch (JSONException je) {
                        Toast.makeText(x, "problemas con la obtención de logs", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(x, "No hay logs asociados al rol del usuario ingresado", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue rq = Volley.newRequestQueue(x);
        rq.add(js);
        return lista;
    }
}
