package com.example.appausa.Listados;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appausa.R;
import com.example.appausa.adapter.CuentaAdapter;
import com.example.appausa.model.Cuenta;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VerCuentas extends Fragment {

    public static String user, doc;
    private ListView listview;
    private View l;
    Button b1;
    EditText t1;
    List<Cuenta> lista = new ArrayList<>();
    RequestQueue rq;


    public static VerCuentas newInstance(String u, String d) {
        VerCuentas frag = new VerCuentas();
        Bundle args = new Bundle();
        user = u;
        doc = d;
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        l = inflater.inflate(R.layout.vercuentas, container, false);
        listview = l.findViewById(R.id.cuentas);
        lista.clear();
        obtenerCuentas("http://192.168.0.13:80/appausamovil/consultarcuentas.php");
        CuentaAdapter ra = new CuentaAdapter(getActivity(), lista);
        listview.setAdapter(ra);
        b1 = l.findViewById(R.id.buscarcuenta);
        t1 = l.findViewById(R.id.docusername);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Fragment fragment = new verLogs(user,lista.get(position).getUsername());
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
                if (t1.getText().toString().isEmpty()) {
                    Toast.makeText(l.getContext(), "Campo de busqueda vacio", Toast.LENGTH_SHORT).show();
                } else {
                    buscarCuenta(t1.getText().toString());
                }
            }
        });
        return l;
    }

    private void buscarCuenta(String usuario) {
        Cuenta x = null;
        if (!lista.isEmpty() || lista != null) {
            for (int i = 0; i < lista.size(); i++) {
                if (lista.get(i).getUsuario().equals(usuario)) {
                    x = lista.get(i);
                }
            }
            if (x == null) {
                Toast.makeText(l.getContext(), "Ningun comentario esta asociado al comentario ingresado", Toast.LENGTH_SHORT).show();
            } else {
                insertlog(user, "Cuenta Buscado", "Se buscaron los datos de la cuenta " + usuario, "http://192.168.0.13:80/appausamovil/insertarlog.php");
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
                r.setTitle("Cuenta encontrada");
                r.show();
            }
        } else {
            Toast.makeText(l.getContext(), "No hay cuentas registradas", Toast.LENGTH_SHORT).show();
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

    private void obtenerCuentas( String s) {
        JsonArrayRequest js = null;
        js = new JsonArrayRequest(s, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jo = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jo = response.getJSONObject(i);
                            lista.add(new Cuenta(jo.getString("username"),
                                    jo.getString("estado"),
                                    jo.getString("perfil"),
                                    jo.getString("usuario"),
                                    jo.getString("ultimologin")));
                    } catch (JSONException je) {
                        Toast.makeText(l.getContext(), je.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
                CuentaAdapter ra = new CuentaAdapter(getActivity(), lista);
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

