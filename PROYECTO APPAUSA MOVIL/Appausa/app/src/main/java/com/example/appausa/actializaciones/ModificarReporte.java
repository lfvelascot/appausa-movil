package com.example.appausa.actializaciones;

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
import android.widget.RadioGroup;
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

public class ModificarReporte extends Fragment {

    public static String user, doc;
    private ListView listview;
    private EditText e1;
    private String estado = "", urlfoto = "";
    private TextView t1, t2, t3, t4, t6;
    private Button b1, b2;
    private View l;
    private Reporte id_reporte = null;
    private ImageView i1;
    private Log log = new Log();
    private RequestQueue rq;


    public static ModificarReporte newInstance(String u, String d) {
        ModificarReporte frag = new ModificarReporte();
        Bundle args = new Bundle();
        user = u;
        doc = d;
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        l = inflater.inflate(R.layout.modificarreporte, container, false);
        e1 = l.findViewById(R.id.idreportem);
        b1 = l.findViewById(R.id.buscarreporte1);
        b2 = l.findViewById(R.id.bmodificarreporte);
        t1 = l.findViewById(R.id.tcodigo1);
        t2 = l.findViewById(R.id.tfecha1);
        t3 = l.findViewById(R.id.idempleado1);
        t4 = l.findViewById(R.id.tdescrip1);
        t6 = l.findViewById(R.id.testado1);
        i1 = l.findViewById(R.id.soporte);
        clear();
        RadioGroup rb = (RadioGroup) l.findViewById(R.id.rgevaluacionr);
        rb.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_aceptado:
                        estado = "Aceptado";
                        break;
                    case R.id.radio_negado:
                        estado = "Negado";
                        break;
                    case R.id.radio_en_espera:
                        estado = "En espera";
                        break;
                }
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (e1.getText().toString().isEmpty()) {
                    Toast.makeText(l.getContext(), "No ha realizado ninguna buequeda previa", Toast.LENGTH_LONG).show();
                } else {
                    obtenerReporte(e1.getText().toString());
                }
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (estado.equals("")) {
                    Toast.makeText(l.getContext(), "No se ha evaluado el reporte", Toast.LENGTH_LONG).show();
                } else {
                    cambiarEstadoReporte();
                }
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
                        urlfoto = id_reporte.getDoc();
                        Picasso.with(l.getContext()).
                                load(urlfoto).
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
        StringRequest sr = new StringRequest(Request.Method.POST, "http://192.168.0.13:80/appausamovil/actualizarReporteA.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                AlertDialog.Builder a = new AlertDialog.Builder(l.getContext());
                a.setMessage("Reporte modificado exitosamente: \n" +
                        "ID: " + id_reporte.getId());
                a.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        log.insertlog(user, "Reporte modificado exitoso", "Se modifoco el reporte" + id_reporte.getId(), "http://192.168.0.13:80/appausamovil/insertarlog.php", l.getContext());
                        clear();
                    }
                });
                AlertDialog r = a.create();
                r.setTitle("Alerta");
                r.show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(l.getContext(), "Problemas en la evaluacion del reporte", Toast.LENGTH_LONG).show();
                log.insertlog(user, "Reporte modificado fallido", "Se trato de evaluar el reporte" + id_reporte.getId(), "http://192.168.0.13:80/appausamovil/insertarlog.php", l.getContext());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("id", id_reporte.getId());
                param.put("estadoa", id_reporte.getEstado());
                param.put("estado", estado);
                param.put("cuenta", user);
                return param;
            }
        };
        RequestQueue rq = Volley.newRequestQueue(l.getContext());
        rq.add(sr);
    }
}

