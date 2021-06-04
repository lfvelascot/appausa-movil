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

public class EliminarCuenta  extends Fragment {

    public static String user;
    private EditText e1;
    private TextView t1,t2,t3,t4,e2;
    private Button b1,b2;
    private View l;
    private RequestQueue rq;
    private boolean selfdata = false;
    private String userm = "";
    private Log log = new Log();


    public static EliminarCuenta newInstance(String u) {
        EliminarCuenta frag = new EliminarCuenta();
        Bundle args = new Bundle();
        user = u;
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        l = inflater.inflate(R.layout.eliminarcuenta, container, false);
        e1 = l.findViewById(R.id.docusernamem);
        e2 = l.findViewById(R.id.usernammee);
        t1 = l.findViewById(R.id.docuser);
        t2 = l.findViewById(R.id.ultimolog);
        t3 = l.findViewById(R.id.perfilactuale);
        t4 = l.findViewById(R.id.estadoact);
        b1 = l.findViewById(R.id.buscarcuenta);
        b2 = l.findViewById(R.id.bcuentaeli);
        b2.setEnabled(false);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = e1.getText().toString();
                if (!id.isEmpty()){
                    buscarDatosCuenta("http://192.168.0.13:80/appausamovil/obtenerDatosCuenta.php?id="+id);
                } else {
                    Toast.makeText(l.getContext(), "Campo de busqueda vacio", Toast.LENGTH_SHORT).show();
                }
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (selfdata){
                        AlertDialog.Builder a = new AlertDialog.Builder(l.getContext());
                        a.setMessage("Va a realizar la eliminación de sus credenciales de acceso, por lo cual se cerrara su sesión y no sé le permitira el acceso. De aceptar para continuar con la eliminación o cancelar para detener el proceso");
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
                                eliminarDatosCuenta("http://192.168.0.13:80/appausamovil/eliminarCuenta.php",e2.getText().toString());
                                Intent intent = new Intent(l.getContext(), Login.class);
                                intent.putExtra("mensaje","Sesión finalizada");
                                startActivity(intent);
                            }
                        });
                        AlertDialog r = a.create();
                        r.setTitle("Alerta");
                        r.show();
                    } else {
                        eliminarDatosCuenta("http://192.168.0.13:80/appausamovil/eliminarCuenta.php",e2.getText().toString());
                    }
                }
        });
        return l;
    }

    private void eliminarDatosCuenta(String url, final String username) {
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(l.getContext(), "Los datos se eliminaron exitosamente", Toast.LENGTH_LONG).show();
                log.insertlog(user, "Cuenta Eliminado Exitoso", "Se ocultaron los datos de la cuenta" + userm, "http://192.168.0.13:80/appausamovil/insertarlog.php",l.getContext());
                clear();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(l.getContext(), "Problemas en la eliminación", Toast.LENGTH_LONG).show();
                log.insertlog(user, "Cuenta Eliminado Fallido", "Se trataron de ocultar los datos de la cuenta" + userm, "http://192.168.0.13:80/appausamovil/insertarlog.php",l.getContext());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<String,String>();
                param.put("cusername",username);
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
