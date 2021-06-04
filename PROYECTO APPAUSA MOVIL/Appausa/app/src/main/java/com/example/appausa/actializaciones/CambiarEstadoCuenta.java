package com.example.appausa.actializaciones;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.example.appausa.main.Login;
import com.example.appausa.R;
import com.example.appausa.model.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CambiarEstadoCuenta extends Fragment {

    public static String user;
    private EditText e1;
    private TextView t1, t2, t3, t4, e2;
    private Spinner s2;
    private Button b1, b2;
    private View l;
    private RequestQueue rq;
    private String  estados = "", userm = "";
    private boolean selfdata = false;
    private ArrayAdapter<String> a;
    private Log log = new Log();


    public static CambiarEstadoCuenta newInstance(String u) {
        CambiarEstadoCuenta frag = new CambiarEstadoCuenta();
        Bundle args = new Bundle();
        user = u;
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        l = inflater.inflate(R.layout.cambiarestadocuenta, container, false);
        e1 = l.findViewById(R.id.docusernamem);
        e2 = l.findViewById(R.id.usernammee);
        t1 = l.findViewById(R.id.docuser);
        t2 = l.findViewById(R.id.ultimolog);
        t3 = l.findViewById(R.id.perfilactuals);
        t4 = l.findViewById(R.id.estadoact);
        s2 = l.findViewById(R.id.estadomod);
        b1 = l.findViewById(R.id.buscarcuenta);
        b2 = l.findViewById(R.id.bcuentamod);
        b2.setEnabled(false);
        s2.setSelection(0);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = e1.getText().toString();
                if (!id.isEmpty()) {
                    buscarDatosCuenta("http://192.168.0.13:80/appausamovil/obtenerDatosCuenta.php?id=" + id);
                } else {
                    Toast.makeText(l.getContext(), "Campo de busqueda vacio", Toast.LENGTH_SHORT).show();
                }
            }
        });
        s2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                if (pos != 0) {
                    estados = String.valueOf(parent.getItemAtPosition(pos));
                } else {
                    estados = t4.getText().toString();
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!e2.getText().toString().isEmpty() && !estados.isEmpty()){
                    if (selfdata){
                        AlertDialog.Builder a = new AlertDialog.Builder(l.getContext());
                        a.setMessage("Va a cambiar el estado de su cuenta a "+estados+", por seguridad, le pediremos que vuelva a iniciar sesión. De aceptar para continuar con la modificación o cancelar para detener el proceso");
                        a.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                clear();
                                dialog.cancel();
                            }
                        });
                        a.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                cambiarEstadoCuenta("http://192.168.0.13:80/appausamovil/cambiarEstadoCuenta.php", e2.getText().toString(),estados);
                                Intent intent = new Intent(l.getContext(), Login.class);
                                intent.putExtra("mensaje","Sesión finalizada");
                                startActivity(intent);
                            }
                        });
                        AlertDialog r = a.create();
                        r.setTitle("Alerta");
                        r.show();
                    } else {
                        cambiarEstadoCuenta("http://192.168.0.13:80/appausamovil/cambiarEstadoCuenta.php", e2.getText().toString(),estados);
                    }
                } else {
                    Toast.makeText(l.getContext(), "Revice que todos los campos esten llenos correctamente", Toast.LENGTH_SHORT).show();
                }

            }
        });
        return l;
    }

    private void cambiarEstadoCuenta(String url, final String usuario, final String estados) {
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(l.getContext(), "Los datos se modificaron exitosamente", Toast.LENGTH_LONG).show();
                log.insertlog(user, "Cuenta Modificado Exitoso", "Se modificaron los datos de la cuenta " + usuario, "http://192.168.0.13:80/appausamovil/insertarlog.php",l.getContext());
                clear();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(l.getContext(), "Problemas en la modificación", Toast.LENGTH_LONG).show();
                log.insertlog(user, "Cuenta Modificado Fallido", "Se trataron de modificar los datos de la cuenta " + userm+" sin exito", "http://192.168.0.13:80/appausamovil/insertarlog.php",l.getContext());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<String,String>();
                param.put("cusername",usuario);
                param.put("cestado",estados);
                return param;
            }
        };
        RequestQueue rq = Volley.newRequestQueue(l.getContext());
        rq.add(sr);
    }

    private void clear() {
        e1.setText("");
        e2.setText("");
        t1.setText("");
        t2.setText("");
        t3.setText("");
        t4.setText("");
    }
    private void buscarDatosCuenta(String url) {
        JsonArrayRequest js = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jo = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jo = response.getJSONObject(i);
                        String username = jo.getString("username"),
                                estado = jo.getString("estado"),
                                doucmento = jo.getString("usuario"),
                                perfil = jo.getString("perfil"),
                                ul = jo.optString("ultimologin");
                        if (user.equals(username)){
                            selfdata = true;
                        }
                        e2.setText(username);
                        t1.setText(doucmento);
                        t2.setText(ul);
                        t3.setText(perfil);
                        t4.setText(estado);
                        b2.setEnabled(true);
                        userm = username;
                    } catch (JSONException je) {
                        Toast.makeText(l.getContext(), je.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
                log.insertlog(user, "Cuenta Buscado", "Se buscaron los datos de la cuenta " + userm, "http://192.168.0.13:80/appausamovil/insertarlog.php",l.getContext());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(l.getContext(), "El correo o la contraseña no coinciden", Toast.LENGTH_LONG).show();
            }
        });
        rq = Volley.newRequestQueue(l.getContext());
        rq.add(js);
    }
}
