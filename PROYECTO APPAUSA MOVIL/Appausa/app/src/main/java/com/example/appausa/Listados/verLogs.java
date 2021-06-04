package com.example.appausa.Listados;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.appausa.R;
import com.example.appausa.adapter.LogAdapter;
import com.example.appausa.model.Data;
import com.example.appausa.model.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class verLogs extends Fragment {

    private static String user, cuenta;
    private View l;
    private ListView listview;
    private TextView t1;
    private ArrayList<Data> lista = new ArrayList<>();
    private Log log = new Log();
    private LogAdapter ra;

    public verLogs(String user, String cuenta) {
        this.user = user;
        this.cuenta = cuenta;
    }

    public static verLogs newInstance(String u, String d) {
        verLogs frag = new verLogs(u, d);
        Bundle args = new Bundle();
        user = u;
        cuenta = d;
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        l = inflater.inflate(R.layout.verlogs, container, false);
        listview = l.findViewById(R.id.logsss);
        t1 = l.findViewById(R.id.titulo);
        t1.setText("Registro de actividad cuenta: " + cuenta);
        obtenerLogCuenta("http://192.168.0.13:80/appausamovil/obtenerLogs.php?id=",cuenta);
        ra = new LogAdapter(getActivity(), lista);
        listview.setAdapter(ra);
        return l;
    }

    public void obtenerLogCuenta(String url, String username){
        JsonArrayRequest js = new JsonArrayRequest(url+username, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jo = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jo = response.getJSONObject(i);
                        lista.add(new Data(jo.getString("cuenta"),jo.getString("fecha"),jo.getString("accion"),jo.getString("descrip")));
                    } catch (JSONException je) {
                        Toast.makeText(l.getContext(), "problemas con la obtención de logs", Toast.LENGTH_SHORT).show();
                    }
                }
                ra = new LogAdapter(l.getContext(), lista);
                listview.setAdapter(ra);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(l.getContext(), "No hay logs asociados al rol del usuario ingresado", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue rq = Volley.newRequestQueue(l.getContext());
        rq.add(js);
    }
}
