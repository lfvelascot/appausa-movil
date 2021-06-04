package com.example.appausa.eliminacion;

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

public class EliminarUsuario extends Fragment {

    public static String user, ccc;
    private EditText e1;
    private TextView t1, t2, t3, t4,t5,t6;
    public Button b1,b2;
    private String cc = "";
    private Log log = new Log();
    private RequestQueue rq;
    private View l;

    public static EliminarUsuario newInstance(String u, String cc) {
        EliminarUsuario frag = new EliminarUsuario();
        Bundle args = new Bundle();
        user = u;
        ccc = cc;
        return frag;
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        l = inflater.inflate(R.layout.eliminarusuario, container, false);
        e1 = l.findViewById(R.id.etbuscaru);
        t1 = l.findViewById(R.id.docue);
        t2 = l.findViewById(R.id.rolue);
        t3 = l.findViewById(R.id.nomue);
        t4 = l.findViewById(R.id.fechnacue);
        t5 = l.findViewById(R.id.emailue);
        t6 = l.findViewById(R.id.telefonoue);
        b1 = l.findViewById(R.id.bbuscarue);
        b2 = l.findViewById(R.id.eliminaru1);
        clean();
        b1.setOnClickListener(v -> {
            if (e1.getText().toString().isEmpty()) {
                Toast.makeText(l.getContext(), "El campo de busqueda esta vacio", Toast.LENGTH_SHORT).show();
            } else {
                cc = e1.getText().toString();
                buscar(cc);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cc.isEmpty()){
                    Toast.makeText(l.getContext(), "No se ha realizado una busqueda previa", Toast.LENGTH_SHORT).show();
                }else {
                    AlertDialog.Builder a = new AlertDialog.Builder(l.getContext());
                    if (cc == ccc){
                        a.setMessage("Va a eliminar sus datos de usuario, esto no le permitira acceder a la aplicacion. De aceptar para continuar con la eliminación o cancelar para detener el proceso");
                        a.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                clean();
                                dialog.cancel();
                            }
                        });
                        a.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                eliminar(cc);
                                Intent intent = new Intent(l.getContext(), Login.class);
                                intent.putExtra("mensaje","Sesión finalizada");
                                startActivity(intent);
                            }
                        });
                        AlertDialog r = a.create();
                        r.setTitle("Alerta");
                        r.show();
                    } else {
                        a.setMessage("Va a eliminar los datos de un usuario, sus credenciales de acceso y logs. De aceptar para continuar con la eliminación o cancelar para detener el proceso");
                        a.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                clean();
                                dialog.cancel();
                            }
                        });
                        a.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                eliminar(cc);
                            }
                        });
                        AlertDialog r = a.create();
                        r.setTitle("Alerta");
                        r.show();
                    }

                }
            }
        });
        return l;
    }

    private void eliminar(String ccu) {
        StringRequest sr = new StringRequest(Request.Method.POST, "http://192.168.0.13:80/appausamovil/eliminarUsuario.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(l.getContext(), "Eliminacion de datos correcta", Toast.LENGTH_LONG).show();
                clean();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(l.getContext(), "Problemas en la eliminacion de datos del usuario", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("cc", ccu);
                return param;
            }
        };
        rq = Volley.newRequestQueue(l.getContext());
        rq.add(sr);
    }

    private void buscar(String idu) {
        JsonArrayRequest js = new JsonArrayRequest("http://192.168.0.13:80/appausamovil/consultarusuario.php?id=" + idu, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jo = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jo = response.getJSONObject(i);
                        e1.setText("");
                        String ced = jo.getString("cc"), nombre = "", pnom = "", snom = "", pape = "", sape = "", tipod = "", rol = "";
                        if (jo.getString("tipo_doc").equals("Cedula Ciudadania")) {
                            tipod = "CC";
                        } else if (jo.getString("tipo_doc").equals("Cedula Extranjeria")) {
                            tipod = "CE";
                        }
                        rol = jo.getString("rol");
                        if (jo.getString("snombre").isEmpty()) {
                            pnom = jo.getString("pnombre");
                            pape = jo.getString("papellido");
                            sape = jo.getString("sapellido");
                            nombre = pnom + " " + pape + " " + sape;
                        } else {
                            pnom = jo.getString("pnombre");
                            snom = jo.getString("snombre");
                            pape = jo.getString("papellido");
                            sape = jo.getString("sapellido");
                            nombre = pnom + " " + snom + " " + pape + " " + sape;
                        }
                        String fechanam = jo.getString("fecha_nam");
                        String celec = jo.getString("correo_electronico");
                        String telefono = jo.getString("telefono");
                        t1.setText(tipod + ". " + ced);
                        t2.setText(rol);
                        t3.setText(nombre);
                        t4.setText(fechanam);
                        t5.setText(celec);
                        t6.setText(telefono);
                        b2.setEnabled(true);
                    } catch (JSONException je) {
                        Toast.makeText(l.getContext(), je.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
                log.insertlog(user, "Usuario Buscado", "Se buscaron los datos del usuario " + idu, "http://192.168.0.13:80/appausamovil/insertarlog.php", l.getContext());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(l.getContext(), "No se encontro un usuario asociado al ID ingresado", Toast.LENGTH_LONG).show();
            }
        });
        rq = Volley.newRequestQueue(l.getContext());
        rq.add(js);
    }

    private void clean() {
        t1.setText("");
        t2.setText("");
        t3.setText("");
        t4.setText("");
        t5.setText("");
        t6.setText("");
        cc = "";
        b2.setEnabled(false);
    }
}
