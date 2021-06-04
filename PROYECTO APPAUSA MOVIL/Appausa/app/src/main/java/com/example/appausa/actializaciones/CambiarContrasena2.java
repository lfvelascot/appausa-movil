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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appausa.main.Login;
import com.example.appausa.R;

import java.util.HashMap;
import java.util.Map;

public class CambiarContrasena2 extends AppCompatActivity {

    Button b1;
    EditText con1, con2;
    Intent o, c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cambiarcontrasena2);
        o = getIntent();
        final String username = o.getStringExtra("username");
        con1 = findViewById(R.id.contrasena1);
        con2 = findViewById(R.id.contrasena2);
        b1 = findViewById(R.id.buttonccc);
        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String cc = con1.getText().toString(), ccc = con2.getText().toString();
                if (ccc.equals(cc) && cc.length() >= 8) {
                    cambiarContrasena(username, cc, "http://192.168.0.13:80/appausamovil/cambiarContrasena.php");
                } else {
                    Toast.makeText(getApplicationContext(), "Las contraseñas no coinciden o la longitud no es mayor a 8 caracteres", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void cambiarContrasena(final String username, final String cc, String url) {
        if (!username.isEmpty() && !cc.isEmpty()) {
            StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    insertlog(username, "Cambio de Contraseña Exitoso", "Se restablecio la contraseña", "http://192.168.0.13:80/appausamovil/insertarlog.php");
                    Toast.makeText(getApplicationContext(), "Contraseña restablecida correctamente", Toast.LENGTH_LONG).show();
                    c = new Intent(CambiarContrasena2.this, Login.class);
                    startActivity(c);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Problemas en la actualización.\nVuelva a intentar", Toast.LENGTH_LONG).show();
                    c = new Intent(CambiarContrasena2.this, Login.class);
                    startActivity(c);
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> param = new HashMap<String, String>();
                    param.put("cusername", username);
                    param.put("ccontrasena", cc);
                    return param;
                }
            };
            RequestQueue rq = Volley.newRequestQueue(this);
            rq.add(sr);
        } else {
            Toast.makeText(getApplicationContext(), "No se realizo una busqueda previa", Toast.LENGTH_LONG).show();
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
}
