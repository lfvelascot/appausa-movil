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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ModificarCuenta extends Fragment {

    public static String user;
    private EditText e1,e2,e3,e4;
    private TextView t1,t2,t3,t4;
    private Spinner s,s2;
    private Button b1,b2;
    private ArrayList<String> perfiles = new ArrayList<>();
    private View l;
    private RequestQueue rq;
    private String perfils = "",estados= "", userm = "";
    private boolean selfdata = false;
    private ArrayAdapter<String> a;
    private Log log = new Log();


    public static ModificarCuenta newInstance(String u) {
        ModificarCuenta frag = new ModificarCuenta();
        Bundle args = new Bundle();
        user = u;
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        l = inflater.inflate(R.layout.modificarcuenta, container, false);
        e1 = l.findViewById(R.id.docusernamem);
        e2 = l.findViewById(R.id.usernamemod);
        e3 = l.findViewById(R.id.contrasena1mod);
        e4 = l.findViewById(R.id.contrasena2mod);
        t1 = l.findViewById(R.id.docuser);
        t2 = l.findViewById(R.id.ultimolog);
        t3 = l.findViewById(R.id.perfilactual);
        t4 = l.findViewById(R.id.estadoact);
        s = l.findViewById(R.id.perfilmod);
        s2 = l.findViewById(R.id.estadomod);
        b1 = l.findViewById(R.id.buscarcuenta);
        b2 = l.findViewById(R.id.bcuentamod);
        b2.setEnabled(false);
        s.setSelection(0);
        s2.setSelection(0);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = e1.getText().toString();
                if (!id.isEmpty()){
                    a = new ArrayAdapter(l.getContext(), android.R.layout.simple_spinner_item, getPerfiles(id));
                    a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    s.setAdapter(a);
                    buscarDatosCuenta("http://192.168.0.13:80/appausamovil/obtenerDatosCuenta.php?id="+id);
                } else {
                    Toast.makeText(l.getContext(), "Campo de busqueda vacio", Toast.LENGTH_SHORT).show();
                }
            }
        });
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                if (pos != 0) {
                    perfils = perfiles.get(pos);
                } else {
                    perfils = t3.getText().toString();
                }

            }
            public void onNothingSelected(AdapterView<?> parent) {

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
                if (!e2.getText().toString().isEmpty() &&
                        !e3.getText().toString().isEmpty() &&
                        !e4.getText().toString().isEmpty() &&
                        !perfils.isEmpty() && !estados.isEmpty() &&
                        validarContraseña(e3.getText().toString(),e4.getText().toString())){
                    if (selfdata){
                        AlertDialog.Builder a = new AlertDialog.Builder(l.getContext());
                        a.setMessage("Va a realizar la modificación de sus credenciales de acceso, por seguridad, le pediremos que vuelva a iniciar sesión. De aceptar para continuar con la modificación o cancelar para detener el proceso");
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
                                modificarDatosCuenta("http://192.168.0.13:80/appausamovil/modificarDatosCuenta.php", t1.getText().toString(), e2.getText().toString(), e3.getText().toString(), perfils, estados);
                                Intent intent = new Intent(l.getContext(), Login.class);
                                intent.putExtra("mensaje","Sesión finalizada");
                                startActivity(intent);
                            }
                        });
                        AlertDialog r = a.create();
                        r.setTitle("Alerta");
                        r.show();
                    } else {
                        modificarDatosCuenta("http://192.168.0.13:80/appausamovil/modificarDatosCuenta.php",t1.getText().toString(),e2.getText().toString(),e3.getText().toString(),perfils,estados);
                    }
                } else {
                    Toast.makeText(l.getContext(), "Revice que todos los campos esten llenos correctamente", Toast.LENGTH_SHORT).show();
                }

            }
        });
        return l;
    }

    private boolean validarContraseña(String cc1, String cc2) {
        if (cc1.equals(cc2)){
            if (cc1.length() >= 8) {
                return true;
            } else {
                Toast.makeText(l.getContext(), "La contraseña debe ser una longitud mayor o igual a 8 caracteres", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            Toast.makeText(l.getContext(), "Las contraseñas ingresadas no coiciden", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void modificarDatosCuenta(String url, final String cc, final String username, final String contrasena, final String perfil, final String estado) {
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(l.getContext(), "Los datos se modificaron exitosamente", Toast.LENGTH_LONG).show();
                log.insertlog(user, "Cuenta Modificado Exitoso", "Se modificaron los datos de la cuenta " + username, "http://192.168.0.13:80/appausamovil/insertarlog.php",l.getContext());
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
                param.put("cusername",userm);
                param.put("cusernamem",username);
                param.put("ccontrasena",contrasena);
                param.put("cperfil",perfil);
                param.put("cestado",estado);
                param.put("cusuario",cc);
                return param;
            }
        };
        RequestQueue rq = Volley.newRequestQueue(l.getContext());
        rq.add(sr);
    }

    private void clear() {
        e1.setText("");
        e2.setText("");
        e3.setText("");
        e4.setText("");
        t1.setText("");
        t2.setText("");
        t3.setText("");
        t4.setText("");
        userm = "";
        perfiles.clear();
        a.notifyDataSetChanged();
        s.setSelection(0);
        s2.setSelection(0);
        perfils = "";
        estados = "";
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
                        userm = username;
                        estados = t4.getText().toString();
                        perfils = t3.getText().toString();
                        b2.setEnabled(true);
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

    private ArrayList<String> getPerfiles(String id) {
        perfiles.clear();
        listarTodo("http://192.168.0.13:80/appausamovil/obtenerPerfiles.php?id="+id);
        perfiles.add(0,"Selecccione una opción");
        return perfiles;
    }

    private void listarTodo(String url) {
        JsonArrayRequest js = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jo = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jo = response.getJSONObject(i);
                        perfiles.add(jo.getString("nombre"));
                    } catch (JSONException je) {
                        Toast.makeText(l.getContext(), "No hay perfiles asociados al rol del usuario ingresado", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(l.getContext(), "No hay perfiles asociados al rol del usuario ingresado", Toast.LENGTH_SHORT).show();
            }
        });
        rq = Volley.newRequestQueue(l.getContext());
        rq.add(js);
    }

}
