package com.example.appausa.eliminacion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.appausa.model.Comentario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EliminarComentario extends Fragment {

    public static String user;
    private Button b1, b2;
    private EditText ed1, contenido;
    private TextView t1, t2, t3;
    private Comentario x = null;
    private View l;
    private RequestQueue rq;


    public static EliminarComentario newInstance(String u) {
        EliminarComentario frag = new EliminarComentario();
        Bundle args = new Bundle();
        user = u;
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        l = inflater.inflate(R.layout.eliminarcomentario, container, false);
        b1 = l.findViewById(R.id.bbuscarcoment);
        b2 = l.findViewById(R.id.beliminarcomentario);
        b2.setEnabled(false);
        ed1 = l.findViewById(R.id.etbuscarcoment);
        t1 = l.findViewById(R.id.idcom);
        t2 = l.findViewById(R.id.cuentacom);
        t3 = l.findViewById(R.id.fechacom);
        contenido = l.findViewById(R.id.etcomentario);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ed1.getText().toString().isEmpty()) {
                    buscarComentario(ed1.getText().toString(), "http://192.168.0.13/appausamovil/buscarComentario.php?id=");
                } else {
                    Toast.makeText(l.getContext(), "Espacio vacio", Toast.LENGTH_SHORT).show();
                }
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!t1.getText().toString().isEmpty()) {
                    eliminarComentario("http://192.168.0.13/appausamovil/eliminarComentario.php");
                } else {
                    Toast.makeText(l.getContext(), "No se ha realizado una busqueda previamente", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return l;
    }


    private void eliminarComentario( String url) {
        final String idcom = t1.getText().toString();
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(l.getContext(), "El comentario fue modificado exitosamente. ID:" + idcom, Toast.LENGTH_LONG).show();
                insertlog(user, "Comentario eliminado Exitoso", "Se elimino el comentario con ID:" + idcom, "http://192.168.0.13:80/appausamovil/insertarlog.php");
                clear2();
                b2.setEnabled(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(l.getContext(), "Problemas de conexion, trate de nuevo, por favor", Toast.LENGTH_LONG).show();
                ;
                insertlog(user, "Comentario eliminado Fallido", "Se trato de eliminar un comentario con el ID:" + idcom, "http://192.168.0.13:80/appausamovil/insertarlog.php");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("id", idcom);
                return param;
            }
        };
        RequestQueue rq = Volley.newRequestQueue(l.getContext());
        rq.add(sr);
    }

    private void clear1() {
        ed1.setText("");
    }

    private void setData(Comentario xx) {
        t1.setText(xx.getId());
        t2.setText(xx.getCuenta());
        t3.setText(xx.getFecha());
        contenido.setText(xx.getContenido());
        contenido.setEnabled(false);
    }

    private void clear2() {
        t1.setText("");
        t2.setText("");
        t3.setText("");
        contenido.setText("");
        contenido.setEnabled(true);
    }

    private void buscarComentario(final String idcom, String url) {
        if (!idcom.isEmpty()) {
            JsonArrayRequest js = new JsonArrayRequest(url + idcom, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    JSONObject jo = null;
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            jo = response.getJSONObject(i);
                            setData(new Comentario(jo.getString("id"),
                                    jo.getString("fecha"),
                                    jo.getString("cuenta"),
                                    jo.getString("contenido")));
                            insertlog(user, "Comentario Buscado", "Se buscaron los datos del comentario " + idcom, "http://192.168.0.13:80/appausamovil/insertarlog.php");
                            Toast.makeText(l.getContext(), "Si desea eliminar el comentario de click en Eliminar", Toast.LENGTH_LONG).show();
                            b2.setEnabled(true);
                            clear1();
                        } catch (JSONException je) {
                            Toast.makeText(l.getContext(), je.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(l.getContext(), "El ID ingresado no esta asociado a ninguna persona", Toast.LENGTH_LONG).show();
                    insertlog(user, "Comentario Buscado", "Se trataron de buscar un comentario con el ID: " + idcom, "http://192.168.0.13:80/appausamovil/insertarlog.php");
                }
            });
            rq = Volley.newRequestQueue(l.getContext());
            rq.add(js);
        } else {
            Toast.makeText(l.getContext(), "TODOS LOS CAMPOS DEBEN ESTAR LLENOS", Toast.LENGTH_LONG).show();
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
