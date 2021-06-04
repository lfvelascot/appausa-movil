package com.example.appausa.Listados;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
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
import com.example.appausa.actializaciones.RevisarReporte;
import com.example.appausa.adapter.ReporteAdapter;
import com.example.appausa.model.Reporte;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VerEmpleado extends Fragment {

    public static String user, empl;
    private ListView listview;
    private View l;
    private TextView t1,t2,t3,t4,t5;
    List<Reporte> lista = new ArrayList<>();
    private RequestQueue rq;

    public static VerEmpleado newInstance(String u,String emple) {
        VerEmpleado frag = new VerEmpleado();
        Bundle args = new Bundle();
        user = u;
        empl = emple;
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        l = inflater.inflate(R.layout.verrempleado, container, false);
        t1 = l.findViewById(R.id.docempl);
        t2 = l.findViewById(R.id.nomempl);
        t3 = l.findViewById(R.id.fechnac);
        t4 = l.findViewById(R.id.correoemp);
        t5 = l.findViewById(R.id.telemp);
        buscarEmpleado();
        listview = l.findViewById(R.id.listreportes);
        obtenerReportes("http://192.168.0.13:80/appausamovil/consultarReportesEmpl.php?id="+empl);
        ReporteAdapter ra = new ReporteAdapter(getActivity(), lista);
        listview.setAdapter(ra);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (lista.get(position).getEstado().equals("En espera")){
                    Fragment fragment = RevisarReporte.newInstance(lista.get(position),user,empl);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.home_content, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                } else {
                    Fragment fragment = new verReporte(lista.get(position));
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.home_content, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            }
        });
        return l;
    }

    private void obtenerReportes(String s) {
        JsonArrayRequest js = null;
        js = new JsonArrayRequest(s, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jo = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jo = response.getJSONObject(i);;
                        lista.add(new Reporte(jo.getString("id"),jo.getString("empleado"),
                                jo.getString("fecha"),jo.getString("tipo_reporte"),jo.getString("estado_reporte"),
                                jo.getString("descrip"),jo.getString("urlimagen")));
                    } catch (JSONException je) {
                        Toast.makeText(l.getContext(), je.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
                ReporteAdapter ra = new ReporteAdapter(l.getContext(), lista);
                listview.setAdapter(ra);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(l.getContext(), "Este empleado no ha enviado ningun reporte", Toast.LENGTH_LONG).show();
            }
        });
        rq = Volley.newRequestQueue(l.getContext());
        rq.add(js);
    }

    public void buscarEmpleado(){
        JsonArrayRequest js = new JsonArrayRequest("http://192.168.0.13:80/appausamovil/consultarusuario.php?id=" + empl, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jo = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jo = response.getJSONObject(i);
                        String ced = jo.getString("cc"), nombre = "", pnom = "", snom = "", pape = "", sape = "", tipod = "", rol = "";
                        if (jo.getString("tipo_doc").equals("Cedula Ciudadania")) {
                            tipod = "CC";
                        } else if (jo.getString("tipo_doc").equals("Cedula Extranjeria")) {
                            tipod = "CE";
                        }
                        if (jo.getString("snombre").isEmpty()) {
                            pnom = jo.getString("pnombre");
                            pape = jo.getString("papellido");
                            sape = jo.getString("sapellido");
                            nombre = pnom + " " + pape + " " + sape;
                        } else {
                            snom = jo.getString("snombre");
                            pape = jo.getString("papellido");
                            sape = jo.getString("sapellido");
                            nombre = pnom + " " + snom + " " + pape + " " + sape;
                        }
                        String fechanam = jo.getString("fecha_nam");
                        String celec = jo.getString("correo_electronico");
                        String telefono = jo.getString("telefono");
                        t1.setText(tipod + ". " + ced);
                        t2.setText(nombre);
                        t3.setText(fechanam);
                        t4.setText(celec);
                        t5.setText(telefono);

                    } catch (JSONException je) {
                        Toast.makeText(l.getContext(), je.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(l.getContext(), "No se encontro un empleado asociado al ID ingresado", Toast.LENGTH_LONG).show();
            }
        });
        rq = Volley.newRequestQueue(l.getContext());
        rq.add(js);
    }
}
