package com.example.appausa.actializaciones;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.appausa.main.Login;
import com.example.appausa.R;
import com.example.appausa.model.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CambiarDatosUsuario extends Fragment {

    public static String user, doc, correou, telu;
    private View l;
    public Button b1, b2;
    EditText t1, t2, t3, t4, t5;
    TextView tcc, tnom, tfecha;
    RequestQueue rq;
    private Log log = new Log();

    public static Fragment newInstance(String username, String d) {
        CambiarDatosUsuario frag = new CambiarDatosUsuario();
        Bundle args = new Bundle();
        user = username;
        doc = d;
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        l = inflater.inflate(R.layout.modificar_usuario, container, false);
        b1 = l.findViewById(R.id.actualizar1);
        b2 = l.findViewById(R.id.actualizar2);
        t1 = l.findViewById(R.id.emailupdate);
        t2 = l.findViewById(R.id.telefonoupdate);
        t3 = l.findViewById(R.id.usernameupdate);
        t4 = l.findViewById(R.id.passup1);
        t5 = l.findViewById(R.id.passup2);
        tnom = l.findViewById(R.id.nomempl);
        tcc = l.findViewById(R.id.docempl);
        tfecha = l.findViewById(R.id.fechnac);
        correou = t1.getText().toString();
        telu = t2.getText().toString();
        cargarDatos("http://192.168.0.13:80/appausamovil/consultarusuario.php?id=" + doc);
        t3.setText(user);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String c = t1.getText().toString(), tel = t2.getText().toString(), ced = tcc.getText().toString();
                if (c.contains("@") && (tel.length() == 7 || tel.length() == 10)) {
                    updateCorreoTelefono(c, tel, ced, "http://192.168.0.13:80/appausamovil/cambiarCorreoTel.php");
                    correou = t1.getText().toString();
                    telu = t2.getText().toString();
                } else {
                    Toast.makeText(l.getContext(), "El correo no contiene un dominio o el telefono no tiene 7 o 10 digitos", Toast.LENGTH_LONG).show();
                    user = t3.getText().toString();
                }
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String c1 = t4.getText().toString(), c2 = t5.getText().toString(), u = t3.getText().toString();
                if (c1.equals(c2) && c1.length() > 7) {
                    AlertDialog.Builder a = new AlertDialog.Builder(l.getContext());
                    a.setMessage("¿Desea finalizar sesion?");
                    a.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    a.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            uodateCredenciales(u, c1, user, "http://192.168.0.13:80/appausamovil/cambiarCredenciales.php");
                        }
                    });
                    AlertDialog r = a.create();
                    r.setTitle("Alerta");
                    r.show();
                } else {
                    Toast.makeText(l.getContext(), "Las contraseñas no coinciden o no es mayor a 8 caracteres", Toast.LENGTH_LONG).show();
                }
            }
        });
        return l;
    }

    private void uodateCredenciales(final String username, final String contrasena, final String u, String s) {
        if (username.isEmpty() || contrasena.isEmpty()) {
            Toast.makeText(l.getContext(), "Los campos de Correo y Telefono deben estar llenos", Toast.LENGTH_LONG).show();
        } else {
            StringRequest sr = new StringRequest(Request.Method.POST, s, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    log.insertlog(u, "Fin Sesión", "Finalizo sesion debido a un cambio de username/contraseña", "http://192.168.0.13:80/appausamovil/insertarlog.php", l.getContext());
                    Intent intent = new Intent(l.getContext(), Login.class);
                    intent.putExtra("mensaje", "Sesión finalizada");
                    startActivity(intent);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(l.getContext(), "Problemas en la actualización de username y contraseña", Toast.LENGTH_LONG).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> param = new HashMap<String, String>();
                    param.put("usuario", u);
                    param.put("usuariom", username);
                    param.put("contrasena", contrasena);
                    return param;
                }
            };
            rq = Volley.newRequestQueue(l.getContext());
            rq.add(sr);
        }
    }

    private void updateCorreoTelefono(final String correo, final String tel, final String cc, String s) {
        if (correo.isEmpty() || tel.isEmpty()) {
            Toast.makeText(l.getContext(), "Los campos de Correo y Telefono deben estar llenos", Toast.LENGTH_LONG).show();
        } else {
            StringRequest sr = new StringRequest(Request.Method.POST, s, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(l.getContext(), "Actualización de correo y telefono correcta", Toast.LENGTH_LONG).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(l.getContext(), "Problemas en la actualización de correo y telefono", Toast.LENGTH_LONG).show();
                    restablecer();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> param = new HashMap<String, String>();
                    param.put("ccc", cc);
                    param.put("ccorreo", correo);
                    param.put("ctel", tel);
                    return param;
                }
            };
            rq = Volley.newRequestQueue(l.getContext());
            rq.add(sr);
        }
    }

    private void cargarDatos(String url) {
        final JsonArrayRequest js = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jo = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jo = response.getJSONObject(i);
                        String ced = jo.getString("cc"), nombre = "", pnom = "", pape = "", tipod = "";
                        if (jo.getString("tipo_doc") == "Cedula Ciudadania") {
                            tipod = "CC";
                        } else if (jo.getString("tipo_doc") == "Cedula Extranjeria") {
                            tipod = "CE";
                        }
                        if (jo.getString("snombre").isEmpty()) {
                            nombre = jo.getString("pnombre") + " " + jo.getString("papellido") + " " + jo.getString("sapellido");
                        } else {
                            nombre = jo.getString("pnombre") + " " + jo.getString("snombre") + " " + jo.getString("papellido") + " " + jo.getString("sapellido");
                        }
                        String fechanam = jo.getString("fecha_nam");
                        String celec = jo.getString("correo_electronico");
                        String telefono = jo.getString("telefono");
                        tcc.setText(tipod + " " + ced);
                        tnom.setText(nombre);
                        tfecha.setText(fechanam);
                        t1.setText(celec);
                        t2.setText(telefono);
                    } catch (JSONException je) {
                        Toast.makeText(l.getContext(), je.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(l.getContext(), "No se encontraron los datos del Usuario", Toast.LENGTH_LONG).show();
            }
        });
        rq = Volley.newRequestQueue(l.getContext());
        rq.add(js);
    }

    public void restablecer() {
        t1.setText(correou);
        t2.setText(telu);
    }
}
