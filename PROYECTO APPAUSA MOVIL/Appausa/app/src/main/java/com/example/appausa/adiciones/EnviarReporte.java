package com.example.appausa.adiciones;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.example.appausa.R;
import com.example.appausa.model.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class EnviarReporte extends Fragment {

    private static String user, docu,tipos = "NADA";
    private View l;
    private Button b1,b2;
    private TextView t1,t2;
    private ImageView i1;
    private Spinner s;
    private ArrayAdapter<String> a;
    private EditText e1;
    private Uri imageServer = null;
    private ArrayList<String> perfiles = new ArrayList<>();
    RequestQueue rq;
    private Log log = new Log();


    public static EnviarReporte newInstance(String u,String doc) {
        EnviarReporte frag = new EnviarReporte();
        Bundle args = new Bundle();
        user = u;
        docu = doc;
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        l = inflater.inflate(R.layout.enviarreporte, container, false);
        b1 = l.findViewById(R.id.benviarreporte);
        b2 = l.findViewById(R.id.buscard);
        t1 = l.findViewById(R.id.idcuenta);
        t2 = l.findViewById(R.id.idempl);
        t1.setText(user);
        t2.setText(docu);
        b1.setEnabled(false);
        e1 = l.findViewById(R.id.etdescrip);
        s = l.findViewById(R.id.spinnerreporte);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType("image/");
                startActivityForResult(i.createChooser(i,"Seleccione la Aplicacion"),101);            }
        });
        a = new ArrayAdapter(l.getContext(), android.R.layout.simple_spinner_item, getTipos());
        a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(a);
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                if (pos != 0) {
                    tipos = perfiles.get(pos);
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tipos.equals("NADA") || e1.getText().toString().isEmpty() || imageServer.equals(null)){
                    Toast.makeText(l.getContext(), "Todos los campos deben estar llenos", Toast.LENGTH_LONG).show();
                } else {
                    enviarReporte();
                }
            }
        });
        return l;
    }


    public String generateRandomString(String nombre, String apellido) {
        int M = 100, N = 998;
        String cadena = "";
        if (nombre.equals(perfiles.get(1))){
            cadena += "DC-";
        } else if (nombre.equals(perfiles.get(2))) {
            cadena += "IM-";
        } else if (nombre.equals(perfiles.get(3))) {
            cadena += "PF-";
        }
        String DATA_FOR_RANDOM_STRING = apellido;
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(3);
        for (int i = 0; i < 3; i++) {
            int rndCharAt = random.nextInt(DATA_FOR_RANDOM_STRING.length());
            char rndChar = DATA_FOR_RANDOM_STRING.charAt(rndCharAt);
            sb.append(rndChar);
        }
        cadena+= sb.toString() + "-";
        int valorEntero1 = (int) Math.floor(Math.random()*(N-M+1)+M);
        cadena+= String.valueOf(valorEntero1);
        return cadena;
    }

    private void enviarReporte() {
        String idr = generateRandomString(tipos,docu), foto = mConvertImageToString();
        StringRequest sr = new StringRequest(Request.Method.POST, "http://192.168.0.13:80/appausamovil/enviarReporte.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(foto);
                AlertDialog.Builder a = new AlertDialog.Builder(l.getContext());
                a.setMessage("Reporte enviado exitosamente: \n" +
                        "ID: "+idr);
                a.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        log.insertlog(user, "Reporte enviado exitoso", "Se envio el reporte"+idr, "http://192.168.0.13:80/appausamovil/insertarlog.php",l.getContext());
                        clear();
                    }
                });
                AlertDialog r = a.create();
                r.setTitle("Alerta");
                r.show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(l.getContext(), "Problemas en el envio del reporte", Toast.LENGTH_LONG).show();
                log.insertlog(user, "Reporte enviado fallido", "Se trato de enviar el reporte"+"ID REPORTE", "http://192.168.0.13:80/appausamovil/insertarlog.php",l.getContext());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<String,String>();
                param.put("id",idr);
                param.put("idfoto","FS-"+idr);
                param.put("cuenta",user);
                param.put("empleado",docu);
                param.put("tipor",tipos);
                param.put("descrip", e1.getText().toString());
                param.put("fotoreporte",foto);
                return param;
            }
        };
        RequestQueue rq = Volley.newRequestQueue(l.getContext());
        rq.add(sr);
    }

    private void clear() {
        s.setSelection(0);
        e1.setText("");
        imageServer = null;
        tipos = "NADA";
        i1.setImageResource(R.drawable.fotosss);
        b1.setEnabled(false);
    }

    private ArrayList<String> getTipos() {
        perfiles.clear();
        listarTipos("http://192.168.0.13:80/appausamovil/obtenertiposR.php");
        perfiles.add(0,"Selecccione una opción");
        return perfiles;
    }

    private void listarTipos(String s) {
        JsonArrayRequest js = new JsonArrayRequest(s, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jo = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jo = response.getJSONObject(i);
                        perfiles.add(jo.getString("nombre"));
                    } catch (JSONException je) {
                        Toast.makeText(l.getContext(), "No hay existen tipos de reportes--", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(l.getContext(), "No hay existen tipos de reportes", Toast.LENGTH_SHORT).show();
            }
        });
        rq = Volley.newRequestQueue(l.getContext());
        rq.add(js);
    }

    private String mConvertImageToString(){
        try {
            Bitmap bitmap = null;
            bitmap = MediaStore.Images.Media.getBitmap(l.getContext().getContentResolver(),imageServer);
            ByteArrayOutputStream array=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,100,array);
            byte[] imagenByte=array.toByteArray();
            String imagenString= Base64.encodeToString(imagenByte, Base64.DEFAULT);
            return imagenString;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK && data != null){
            Toast.makeText(l.getContext(), "SI", Toast.LENGTH_LONG).show();
            Uri path = data.getData();
            i1 = l.findViewById(R.id.fotodoc);
            i1.setImageURI(path);
            imageServer = path;
            if (!imageServer.equals(null)){
                b1.setEnabled(true);
            }
        } else {
            Toast.makeText(l.getContext(), "NO", Toast.LENGTH_LONG).show();
        }
    }
}

