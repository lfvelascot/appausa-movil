package com.example.appausa.actializaciones;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appausa.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CambiarContrasena extends AppCompatActivity {

    Button b1;
    EditText correo;
    Intent x;
    RequestQueue rq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cambiarcontrasena);
        correo = findViewById(R.id.correob);
        b1 = findViewById(R.id.buttoncontrasena);
        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String cc = correo.getText().toString();
                buscarCuenta(cc, "http://192.168.0.13:80/appausamovil/consultacuenta.php?cid=");
            }
        });
    }

    private void buscarCuenta(String user, String url) {
        if (user.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Ingrese el username por favor", Toast.LENGTH_LONG).show();
        } else {
            JsonArrayRequest js = new JsonArrayRequest(url + user, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    JSONObject jo = null;
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            jo = response.getJSONObject(i);
                            String username = jo.getString("username");
                            String contr = jo.getString("contrasena");
                            String estado = jo.getString("estado");
                            String perfil = jo.getString("perfil");
                            String doucmento = jo.getString("usuario");
                            if (estado.equals("ACTIVA")) {
                                x = new Intent(getApplicationContext(),CambiarContrasena2.class);
                                x.putExtra("username",username);
                                startActivity(x);
                            } else if (estado.equals("BLOQUEADA")) {
                                insertlog(username, "Cuenta Bloqueada", "La cuenta se encuentra bloqueada", "http://192.168.0.13:80/appausamovil/insertarlog.php");
                                Toast.makeText(getApplicationContext(), "Su cuenta se encuentra bloqueada, comuniquese con el administrador", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException je) {
                            Toast.makeText(getApplicationContext(), je.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "El username no corresponde a ninguna cuenta", Toast.LENGTH_LONG).show();
                }
            });
            rq = Volley.newRequestQueue(this);
            rq.add(js);
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
                    Toast.makeText(getApplicationContext(), "Problemas de conexion", Toast.LENGTH_LONG).show();;
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> param = new HashMap<String,String>();
                    param.put("cusername",id);
                    param.put("caccion",accion);
                    param.put("cdescrip",descrip);
                    return param;
                }
            };
            RequestQueue rq = Volley.newRequestQueue(this);
            rq.add(sr);
        }
    }
}

