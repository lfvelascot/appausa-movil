package com.example.appausa.actializaciones;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appausa.Listados.VerReportesRecibidos;
import com.example.appausa.R;
import com.example.appausa.model.Log;
import com.example.appausa.model.Reporte;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;


public class RevisarReporte extends Fragment {

    private View l;
    private Reporte id_reporte;
    private Button b1;
    private TextView tv1,t2,t3,t4;
    private String estado = "";
    static String user,doc;
    private Log log = new Log();
    private ImageView i1;

    public RevisarReporte(Reporte id) {
        this.id_reporte = id;
    }

    public static RevisarReporte newInstance(Reporte id_reporte, String u, String d) {
        RevisarReporte frag = new RevisarReporte(id_reporte);
        Bundle args = new Bundle();
        user = u;
        doc = d;
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        l = inflater.inflate(R.layout.revisarreporte, container, false);
        b1 = l.findViewById(R.id.brevisarr);
        tv1 = l.findViewById(R.id.tcodigo);
        t2 = l.findViewById(R.id.tfecha);
        t3 = l.findViewById(R.id.idempleado);
        t4 = l.findViewById(R.id.tdescrip);
        i1 = l.findViewById(R.id.soporte);
        Picasso.with(l.getContext()).
                load(id_reporte.getDoc()).
                error(R.drawable.denied_r).into(i1);
        tv1.setText(id_reporte.getId());
        t2.setText(id_reporte.getFecha());
        t3.setText(id_reporte.getEmpleado());
        t4.setText(id_reporte.getDescrip());
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
                }
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
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

    private void cambiarEstadoReporte() {
        StringRequest sr = new StringRequest(Request.Method.POST, "http://192.168.0.13:80/appausamovil/actualizarReporte.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                AlertDialog.Builder a = new AlertDialog.Builder(l.getContext());
                a.setMessage("Reporte respondido exitosamente: \n" +
                        "ID: "+id_reporte.getId());
                a.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        log.insertlog(user, "Reporte modificado exitoso", "Se modifoco el reporte"+id_reporte.getId(), "http://192.168.0.13:80/appausamovil/insertarlog.php",l.getContext());
                        Fragment fragment = VerReportesRecibidos.newInstance(user,doc);
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.home_content, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
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
                log.insertlog(user, "Reporte modificado fallido", "Se trato de evaluar el reporte"+id_reporte.getId(), "http://192.168.0.13:80/appausamovil/insertarlog.php",l.getContext());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<String,String>();
                param.put("id",id_reporte.getId());
                param.put("estado",estado);
                param.put("cuenta",user);
                return param;
            }
        };
        RequestQueue rq = Volley.newRequestQueue(l.getContext());
        rq.add(sr);
    }
}
