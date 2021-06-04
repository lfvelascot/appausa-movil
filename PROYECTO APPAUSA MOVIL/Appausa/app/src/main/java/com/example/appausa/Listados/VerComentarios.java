package com.example.appausa.Listados;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appausa.R;
import com.example.appausa.adapter.ComentarioAdapter;
import com.example.appausa.model.Comentario;
import com.example.appausa.model.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class VerComentarios extends Fragment {

    public static String user, docu, ult;
    private ListView listview;
    private View l;
    private Button b1;
    private Log log = new Log();
    TextView t1;
    ComentarioAdapter ra;
    List<Comentario> lista = new ArrayList<>(), aux = new ArrayList<>();
    RequestQueue rq;


    public static VerComentarios newInstance(String u, String d, String ultimo) {
        VerComentarios frag = new VerComentarios();
        Bundle args = new Bundle();
        user = u;
        docu = d;
        ult = ultimo;
        return frag;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        l = inflater.inflate(R.layout.vercomentarios, container, false);
        listview = l.findViewById(R.id.lcomentarios);
        obtenerComentarios("http://192.168.0.13:80/appausamovil/consultarcomentarios.php");
        insertlog(user,"Comentarios Vistos","Se listaron todos los comentarios de la APP","http://192.168.0.13:80/appausamovil/insertarlog.php");
        ra = new ComentarioAdapter(getActivity(), lista);
        listview.setAdapter(ra);
        b1 = l.findViewById(R.id.bbuscarcoment);
        t1 = l.findViewById(R.id.etbuscarcoment);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(l.getContext(), "Enviado : " + lista.get(position).getFecha(), Toast.LENGTH_LONG).show();
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (t1.getText().toString().isEmpty()) {
                    Toast.makeText(l.getContext(), "Campo de busqueda vacio", Toast.LENGTH_SHORT).show();
                } else {
                    buscarComentario(t1.getText().toString());
                }
            }
        });
        return l;
    }

    private void obtenerComentarios(String s) {
        JsonArrayRequest js;
        js = new JsonArrayRequest(s, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jo = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jo = response.getJSONObject(i);
                        lista.add(new Comentario(jo.getString("id"),
                                jo.getString("fecha"),
                                jo.getString("cuenta"),
                                jo.getString("contenido")));
                    } catch (JSONException je) {
                        Toast.makeText(l.getContext(), je.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
                ra = new ComentarioAdapter(getActivity(), lista);
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

    public void buscarComentario(String cod) {
        obtenerComentarios("http://192.168.0.13:80/appausamovil/consultarcomentarios.php");
        Comentario x = null;
        if (!lista.isEmpty() || lista != null) {
            for (int i = 0; i < lista.size(); i++) {
                if (lista.get(i).getId().equals(cod)) {
                    x = lista.get(i);
                }
            }
            if (x == null){
                Toast.makeText(l.getContext(), "Ningun comentario esta asociado al comentario ingresado", Toast.LENGTH_SHORT).show();
            } else {
                insertlog(user,"Comentario Buscado","Se buscaron los datos del comentario"+cod,"http://192.168.0.13:80/appausamovil/insertarlog.php");
                AlertDialog.Builder a = new AlertDialog.Builder(l.getContext());
                a.setMessage(x.toString()).
                        setCancelable(false).
                         setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog r = a.create();
                r.setTitle("Comentario encontrado");
                r.show();
            }
        } else {
            Toast.makeText(l.getContext(), "No hay comentarios", Toast.LENGTH_SHORT).show();
        }
    }

    private void insertlog(final String id, final String accion, final String descrip, String url) {
        if (!accion.isEmpty() && !id.isEmpty() && !descrip.isEmpty() && !url.isEmpty()) {
            StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(l.getContext(), "Problemas de conexion", Toast.LENGTH_LONG).show();
                    ;
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
            RequestQueue rq = Volley.newRequestQueue(l.getContext());
            rq.add(sr);
        }
    }

}

