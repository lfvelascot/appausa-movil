package com.example.appausa.adiciones;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

public class AnadirCuenta extends Fragment {

    public static String user, perfil = "";
    private View l;
    EditText e1;
    Button b1;
    RequestQueue rq;
    TextView t1, t2;

    public static Fragment newInstance(String username) {
        AnadirCuenta frag = new AnadirCuenta();
        Bundle args = new Bundle();
        user = username;
        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        l = inflater.inflate(R.layout.anadircuenta, container, false);
        b1 = l.findViewById(R.id.crearcuenta);
        e1 = l.findViewById(R.id.ccusuario);
        t1 = l.findViewById(R.id.usergen);
        t2 = l.findViewById(R.id.contragen);
        RadioGroup rb = (RadioGroup) l.findViewById(R.id.rgperfilc);
        rb.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.administrador:
                        perfil = "ADMINISTRADOR";
                        break;
                    case R.id.talentohumano:
                        perfil = "TALENTO HUMANO GENERAL";
                        break;
                    case R.id.empleado:
                        perfil = "EMPLEADO";
                        break;
                }
            }

        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cc = e1.getText().toString();
                consultarDatos(cc, perfil, "http://192.168.0.13:80/appausamovil/consultarusuario.php?cid=" + cc);
            }
        });
        return l;
    }

    private void consultarDatos(final String cc, final String perfil, final String url) {
        final JsonArrayRequest js = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jo = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jo = response.getJSONObject(i);
                        String ced = jo.getString("cc"), nombre = "", pnom = "",pape = "";
                        pnom = jo.getString("pnombre");
                        pape = jo.getString("papellido");
                        String telefono = jo.getString("telefono");
                        String rol = jo.getString("rol");
                        consultarRol(perfil,rol,pnom,pape,telefono,ced,"http://192.168.0.13:80/appausamovil/consultarrol.php?cid="+rol+"&cidd="+perfil);
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

    private void consultarRol(final String perfil, String rol, final String nombre, final String apellido, final String telefono, final String cc, String url) {
        if (!perfil.isEmpty() && !rol.isEmpty()){
            final JsonArrayRequest js = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    generarCredenciales(perfil,nombre,apellido,telefono,cc,"http://192.168.0.13:80/appausamovil/insertarcuenta.php?");
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(l.getContext(), "No se encontraron los datos del Usuario", Toast.LENGTH_LONG).show();
                }
            });
            rq = Volley.newRequestQueue(l.getContext());
            rq.add(js);
        } else {

        }

    }

    public static String generateRandomString(int length,String nombre, String apellido, String tel) {
        String DATA_FOR_RANDOM_STRING = nombre + apellido + tel;
        SecureRandom random = new SecureRandom();
        if (length < 1) throw new IllegalArgumentException();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int rndCharAt = random.nextInt(DATA_FOR_RANDOM_STRING.length());
            char rndChar = DATA_FOR_RANDOM_STRING.charAt(rndCharAt);

            sb.append(rndChar);
        }
        return sb.toString();
    }

    public static String generaContrasena(int length,String nombre, String apellido, String cc) {
        String DATA_FOR_RANDOM_STRING = cc+cc+nombre+apellido;
        SecureRandom random = new SecureRandom();
        if (length < 1) throw new IllegalArgumentException();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int rndCharAt = random.nextInt(DATA_FOR_RANDOM_STRING.length());
            char rndChar = DATA_FOR_RANDOM_STRING.charAt(rndCharAt);

            sb.append(rndChar);
        }
        return sb.toString();
    }

    public void generarCredenciales (final String perfil,  String nombre, String apellido, String telefono, final String cc, String url){
        final String username = generateRandomString(12,nombre,apellido,telefono);
        final String contrasena = generaContrasena(8,nombre,apellido,cc);
        if ( !username.isEmpty() && !contrasena.isEmpty()) {
            StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(l.getContext(), "Los datos se guardaron exitosamente", Toast.LENGTH_LONG).show();
                    t1.setText(username);
                    t2.setText(contrasena);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(l.getContext(), "Problemas en la insercion", Toast.LENGTH_LONG).show();;
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> param = new HashMap<String,String>();
                    param.put("cusername",username);
                    param.put("ccontrasena",contrasena);
                    param.put("cperfil",perfil);
                    param.put("cusuario",cc);
                    return param;
                }
            };
            RequestQueue rq = Volley.newRequestQueue(l.getContext());
            rq.add(sr);
        } else {
            Toast.makeText(l.getContext(), "TODOS LOS CAMPOS DEBEN ESTAR LLENOS", Toast.LENGTH_LONG).show();
        }
    }
}
