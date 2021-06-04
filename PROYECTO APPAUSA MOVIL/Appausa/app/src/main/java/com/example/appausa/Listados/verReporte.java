package com.example.appausa.Listados;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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
import com.example.appausa.adapter.HistorialAdapter;
import com.example.appausa.model.Historial;
import com.example.appausa.model.Reporte;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;


public class verReporte extends Fragment {

    private View l;
    private Reporte id_reporte;
    private Button b1;
    private TextView tv1,t2,t3;
    private ArrayList<Historial> lista = new ArrayList<>();
    private ListView listView;
    private ImageView i1;
    private RequestQueue rq;

    public verReporte(Reporte id) {
        this.id_reporte = id;
    }

    public static verReporte newInstance(Reporte id_reporte) {
        verReporte frag = new verReporte(id_reporte);
        Bundle args = new Bundle();
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        l = inflater.inflate(R.layout.verreporte, container, false);
        tv1 = l.findViewById(R.id.tcodigo);
        tv1.setText(id_reporte.getId());
        t2 = l.findViewById(R.id.tfecha);
        t3 = l.findViewById(R.id.tdescrip);
        t2.setText(id_reporte.getFecha());
        t3.setText(id_reporte.getDescrip());
        i1 = l.findViewById(R.id.soporte);
        listView = l.findViewById(R.id.listreportesh);
        Picasso.with(l.getContext()).
                load(id_reporte.getDoc()).
                error(R.drawable.denied_r).into(i1);
        obtenerHistorial("http://192.168.0.13:80/appausamovil/verHistorialR.php?id="+id_reporte.getId());
        return l;
    }

    private void obtenerHistorial( String s) {
        JsonArrayRequest js = null;
        js = new JsonArrayRequest(s, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jo = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jo = response.getJSONObject(i);
                            lista.add(new Historial(jo.getString("id_reporte"),
                                    jo.getString("fecha"),
                                    jo.getString("cuenta"),
                                    jo.getString("estado_previo"),
                                    jo.getString("estado_actual")));

                    } catch (JSONException je) {
                        Toast.makeText(l.getContext(), je.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
                HistorialAdapter ra = new HistorialAdapter(getActivity(), lista);
                listView.setAdapter(ra);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(l.getContext(), "No hay aún historial de este reporte", Toast.LENGTH_LONG).show();
            }
        });
        rq = Volley.newRequestQueue(l.getContext());
        rq.add(js);
    }
}
