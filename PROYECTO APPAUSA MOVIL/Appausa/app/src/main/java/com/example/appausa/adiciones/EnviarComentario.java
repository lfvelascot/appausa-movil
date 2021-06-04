package com.example.appausa.adiciones;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appausa.R;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

public class EnviarComentario extends Fragment {

    private View l;
    private Button b1, b2;
    private EditText ed1;
    public static String user;


    public static EnviarComentario newInstance(String u) {
        EnviarComentario frag = new EnviarComentario();
        Bundle args = new Bundle();
        user = u;
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        l = inflater.inflate(R.layout.enviarcomentario, container, false);
        b1 = l.findViewById(R.id.benviarcomentario);
        ed1 = l.findViewById(R.id.etcomentario);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ed1.getText().toString().isEmpty()){
                    String idc = generateRandomString();
                    enviarComentario(ed1.getText().toString(),idc, "http://192.168.0.13/appausamovil/enviarComentario.php");
                } else {
                    Toast.makeText(l.getContext(), "Espacio vacio", Toast.LENGTH_LONG).show();
                }
                clear();
            }
        });
        return l;
    }

    private void enviarComentario(final String comentario, final String idc, String url) {
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(l.getContext(), "El comentario fue enviado exitosamente. ID:"+idc, Toast.LENGTH_LONG).show();
                insertlog(user,"Comentario Enviado","Se envio el comentario con ID:"+idc,"http://192.168.0.13:80/appausamovil/insertarlog.php");
                clear();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(l.getContext(), "Problemas de conexion, trate de nuevo, por favor", Toast.LENGTH_LONG).show();;
                insertlog(user,"Comentario No Enviado","Se trato de enviar un comentario con el ID:"+idc,"http://192.168.0.13:80/appausamovil/insertarlog.php");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<String,String>();
                param.put("id",idc);
                param.put("cuenta",user);
                param.put("contenido",comentario);
                return param;
            }
        };
        RequestQueue rq = Volley.newRequestQueue(l.getContext());
        rq.add(sr);
    }

    public static String generateRandomString() {
        int length = 6;
        String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
        String CHAR_UPPER = CHAR_LOWER.toUpperCase();
        String NUMBER = "0123456789";
        String DATA_FOR_RANDOM_STRING = CHAR_UPPER + NUMBER + "-";
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

    public void clear() {
        ed1.setText("");
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
            RequestQueue rq = Volley.newRequestQueue(l.getContext());
            rq.add(sr);
        }
    }
}

