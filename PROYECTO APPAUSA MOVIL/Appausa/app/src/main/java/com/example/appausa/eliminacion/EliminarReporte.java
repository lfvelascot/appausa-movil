package com.example.appausa.eliminacion;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.example.appausa.model.Log;
import com.example.appausa.model.Reporte;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EliminarReporte extends Fragment {

    public static String user, doc;
    private ListView listview;
    private EditText e1;
    private String estado = "", urlfoto = "";
    private TextView t1, t2, t3, t4, t6;
    private ImageView i1;
    private Button b1, b2;
    private View l;
    private Reporte id_reporte = null;
    private Log log = new Log();
    private RequestQueue rq;


    public static EliminarReporte newInstance(String u, String d) {
        EliminarReporte frag = new EliminarReporte();
        Bundle args = new Bundle();
        user = u;
        doc = d;
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        l = inflater.inflate(R.layout.eliminarreporte, container, false);
        e1 = l.findViewById(R.id.idreportee);
        b1 = l.findViewById(R.id.buscarreporte2);
        b2 = l.findViewById(R.id.beliminarreporte);
        t1 = l.findViewById(R.id.tcodigo2);
        t2 = l.findViewById(R.id.tfecha2);
        t3 = l.findViewById(R.id.idempl2);
        t4 = l.findViewById(R.id.tdescrip2);
        i1 = l.findViewById(R.id.soporte);
        t6 = l.findViewById(R.id.testado2);
        clear();
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (e1.getText().toString().isEmpty()) {
                    Toast.makeText(l.getContext(), "No ha realizado ninguna modificación", Toast.LENGTH_LONG).show();
                } else {
                    obtenerReporte(e1.getText().toString());
                }
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarEstadoReporte();
            }
        });
        return l;
    }

    void clear (){
        t1.setText("");
        t2.setText("");
        t3.setText("");
        t4.setText("");
        t6.setText("");
        urlfoto = "";
        e1.setText("");
        b2.setEnabled(false);
    }
    private void obtenerReporte(String s) {
        JsonArrayRequest js = null;
        js = new JsonArrayRequest("http://192.168.0.13:80/appausamovil/obtenerReporte.php?id="+s, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jo = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jo = response.getJSONObject(i);
                        id_reporte = new Reporte(jo.getString("id"),
                                jo.getString("empleado"),
                                jo.getString("fecha"),
                                jo.getString("tipo_reporte"),
                                jo.getString("estado_reporte"),
                                jo.getString("descrip"),
                                jo.getString("urlimagen"));
                        t1.setText(id_reporte.getId());
                        t2.setText(id_reporte.getFecha());
                        t3.setText(id_reporte.getEmpleado());
                        t4.setText(id_reporte.getDescrip());
                        t6.setText(id_reporte.getEstado());
                        Picasso.with(l.getContext()).
                                load(id_reporte.getDoc()).
                                error(R.drawable.denied_r).into(i1);
                        e1.setText("");
                        b2.setEnabled(true);
                    } catch (JSONException je) {
                        Toast.makeText(l.getContext(), je.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(l.getContext(), "No se encontro ningun reporte asociado al ID ingresado", Toast.LENGTH_LONG).show();
            }
        });
        rq = Volley.newRequestQueue(l.getContext());
        rq.add(js);
    }


    private void cambiarEstadoReporte() {
        StringRequest sr = new StringRequest(Request.Method.POST, "http://192.168.0.13:80/appausamovil/eliminarReporte.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                AlertDialog.Builder a = new AlertDialog.Builder(l.getContext());
                a.setMessage("Reporte eliminado exitosamente: \n" +
                        "ID: " + id_reporte.getId());
                a.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        log.insertlog(user, "Reporte eliminado exitoso", "Se modifoco el reporte" + id_reporte.getId(), "http://192.168.0.13:80/appausamovil/insertarlog.php", l.getContext());
                    }
                });
                AlertDialog r = a.create();
                r.setTitle("Alerta");
                r.show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(l.getContext(), "Problemas en la eliminacion del reporte", Toast.LENGTH_LONG).show();
                log.insertlog(user, "Reporte eliminado fallido", "Se trato de eliminart el reporte" + id_reporte.getId(), "http://192.168.0.13:80/appausamovil/insertarlog.php", l.getContext());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("id", id_reporte.getId());
                return param;
            }
        };
        RequestQueue rq = Volley.newRequestQueue(l.getContext());
        rq.add(sr);
    }
}