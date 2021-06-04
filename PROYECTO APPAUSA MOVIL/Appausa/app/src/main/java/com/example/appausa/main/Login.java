package com.example.appausa.main;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import com.example.appausa.actializaciones.CambiarContrasena;
import com.example.appausa.extra.Documento;
import com.example.appausa.extra.SobreLaApp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Login extends AppCompatActivity {

    Button b1;
    EditText c, cc;
    TextView cambiarC;
    Intent x, i;
    RequestQueue rq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        b1 = findViewById(R.id.button);
        c = findViewById(R.id.correo);
        cc = findViewById(R.id.contrasenal);
        x = getIntent();
        String m = x.getStringExtra("mensaje");
        if (m != null) {
            Toast.makeText(getApplicationContext(), m, Toast.LENGTH_LONG).show();
        }
        cambiarC = findViewById(R.id.recuperarC);
        SpannableString content = new SpannableString(cambiarC.getText());
        content.setSpan(new UnderlineSpan(), 0, cambiarC.length(), 0);
        cambiarC.setText(content);
        cambiarC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(Login.this, CambiarContrasena.class);
                startActivity(i);
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String user = c.getText().toString();
                String contr = cc.getText().toString();
                buscarCuenta(user, contr, "http://192.168.0.13:80/appausamovil/consultacuenta.php?cid=");
            }
        });
    }

    public void buscarCuenta(String correo, final String contrasena, String url) {
        if (correo.isEmpty() || contrasena.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Todos los campos deben estar llenos", Toast.LENGTH_LONG).show();
        } else {
            JsonArrayRequest js = new JsonArrayRequest(url + correo, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    JSONObject jo = null;
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            jo = response.getJSONObject(i);
                            String username = jo.getString("username");
                            String contr = jo.getString("contrasena");
                            String estado = jo.getString("estado");
                            String doucmento = jo.getString("usuario");
                            String ul = jo.optString("ultimologin");
                            if (estado.equals("ACTIVA")) {
                                if (contr.equals(contrasena)) {
                                    obtenerRoles(username, doucmento, "http://192.168.0.13:80/appausamovil/consultarusuario.php?id=" + doucmento,ul);
                                } else {
                                    insertlog(username, "Ingreso Fallido", "Contraseña ingresada es erronea", "http://192.168.0.13:80/appausamovil/insertarlog.php");
                                    Toast.makeText(getApplicationContext(), "El correo o la contraseña no coinciden", Toast.LENGTH_LONG).show();
                                }
                            } else if (estado.equals("BLOQUEADA")) {
                                insertlog(username, "Ingreso Fallido", "La cuenta se encuentra bloqueada", "http://192.168.0.13:80/appausamovil/insertarlog.php");
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
                    Toast.makeText(getApplicationContext(), "El correo o la contraseña no coinciden", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(getApplicationContext(), "Problemas de conexion", Toast.LENGTH_LONG).show();
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
            RequestQueue rq = Volley.newRequestQueue(this);
            rq.add(sr);
        }
    }

    private void obtenerRoles(final String username, final String documento, String url, final String ul) {
        final JsonArrayRequest js = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jo = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jo = response.getJSONObject(i);
                        String nombre = "";
                        if (jo.getString("snombre").isEmpty()) {
                            nombre = jo.getString("pnombre") + " " + jo.getString("papellido") + " " + jo.getString("sapellido");
                        } else {
                            nombre = jo.getString("pnombre") + " " + jo.getString("snombre") + " " + jo.getString("papellido") + " " + jo.getString("sapellido");
                        }
                        Intent x = new Intent(Login.this, MainActivity.class);
                        insertlog(username, "Ingreso a la Aplicación", "Ingreso sin problemas a la aplicacion", "http://192.168.0.13:80/appausamovil/insertarlog.php");
                        Toast.makeText(getApplicationContext(), "Login exitoso", Toast.LENGTH_LONG).show();
                        x.putExtra("user", username);
                        x.putExtra("rol", jo.getString("rol"));
                        x.putExtra("doc", documento);
                        x.putExtra("n", nombre);
                        x.putExtra("ul", ul);
                        ultimoLogin(username, "http://192.168.0.13:80/appausamovil/ultimologin.php");
                        startActivity(x);
                    } catch (JSONException je) {
                        Toast.makeText(getApplicationContext(), je.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error de conexion", Toast.LENGTH_LONG).show();
            }
        });
        rq = Volley.newRequestQueue(this);
        rq.add(js);
    }

    private void ultimoLogin(final String username, String url) {
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Problemas en la actualización", Toast.LENGTH_LONG).show();
                ;
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("cusername", username);
                return param;
            }
        };
        RequestQueue rq = Volley.newRequestQueue(this);
        rq.add(sr);

    }

    //Implemetación Menú de opciones
    @Override
    public boolean onCreateOptionsMenu (Menu mimenu){
        getMenuInflater().inflate(R.menu.flotante,mimenu);
        return true;
    }

    //Implementación método acerca de la App
    public void acerca_app(View view){
        i = new Intent(Login.this, SobreLaApp.class);
        startActivity(i);
    }

    //Configuración de opciones de navegación
    @Override public boolean onOptionsItemSelected(MenuItem opcion){
        int id=opcion.getItemId();
        //Ejecuta el metodo acerca_app
        switch (id) {
            case R.id.info_app:
                acerca_app(null);
                return true;
            case R.id.verdoc:
                documento(null);
                return true;
        }
        //Selección de la opción
        return super.onOptionsItemSelected(opcion);
    }

    private void documento(View view){
        i = new Intent(Login.this, Documento.class);
        startActivity(i);
    }

}