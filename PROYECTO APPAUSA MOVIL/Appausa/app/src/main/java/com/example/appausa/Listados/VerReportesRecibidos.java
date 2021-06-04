package com.example.appausa.Listados;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.appausa.R;
import com.example.appausa.adapter.ReporteAdapter;
import com.example.appausa.model.Reporte;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class VerReportesRecibidos extends Fragment {

    public static String user, doc;
    private ListView listview;
    private EditText e1;
    private Button b1;
    private List<Reporte> lista = new ArrayList<>();
    private View l;
    private RequestQueue rq;


    public static VerReportesRecibidos newInstance(String u, String d) {
        VerReportesRecibidos frag = new VerReportesRecibidos();
        Bundle args = new Bundle();
        user = u;
        doc = d;
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        l = inflater.inflate(R.layout.verreportesenviados, container, false);
        listview = l.findViewById(R.id.renviados);
        lista.clear();
        b1 = l.findViewById(R.id.buscarreporte);
        e1 = l.findViewById(R.id.idreporteb);
        obtenerReportesE("http://192.168.0.13:80/appausamovil/obtenerReportes.php?");
        ReporteAdapter ra = new ReporteAdapter(getActivity(), lista);
        listview.setAdapter(ra);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    Fragment fragment = new verReporte(lista.get(position));
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.home_content, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!e1.getText().toString().isEmpty()){
                    if (lista.size() == 0) {
                        Toast.makeText(l.getContext(), "No ha enviado ningun reporte", Toast.LENGTH_SHORT).show();
                    } else {
                        Reporte r = buscarReporte(e1.getText().toString());
                        if (r.equals(null) && !lista.isEmpty()) {
                            Toast.makeText(l.getContext(), "No se encontro ningun reporte asocisado al ID ingresado", Toast.LENGTH_LONG).show();
                        } else {
                            Fragment fragment = new verReporte(r);
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.home_content, fragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }
                    }
                } else {
                    Toast.makeText(l.getContext(), "Campo de busqueda vacio", Toast.LENGTH_LONG).show();
                }
            }
        });
        return l;
    }

    private Reporte buscarReporte(String id) {
        for (int i = 0;i< lista.size();i++){
            if (lista.get(i).getId().equals(id)){
                return lista.get(i);
            }
        }
        return null;
    }

    private void obtenerReportesE(String s) {
        JsonArrayRequest js = null;
        js = new JsonArrayRequest(s, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jo = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jo = response.getJSONObject(i);
                        Reporte reporte = new Reporte(jo.getString("id"),
                                jo.getString("empleado"),
                                jo.getString("fecha"),
                                jo.getString("tipo_reporte"),
                                jo.getString("estado_reporte"),
                                jo.getString("descrip"),
                                jo.getString("urlimagen"));
                        if (!reporte.getEmpleado().equals(doc)){
                            lista.add(reporte);
                        }
                    } catch (JSONException je) {
                        Toast.makeText(l.getContext(), je.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
                ReporteAdapter ra = new ReporteAdapter(getActivity(), lista);
                listview.setAdapter(ra);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(l.getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        rq = Volley.newRequestQueue(l.getContext());
        rq.add(js);
    }
}

