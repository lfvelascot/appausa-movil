package com.example.appausa.actializaciones;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
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
import com.example.appausa.extra.DatePickerFragment;
import com.example.appausa.model.Log;
import com.example.appausa.model.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class ModificarUsuario extends Fragment {

    public static String user;
    private EditText e1, e2, e3, e4, e5, e6, e7, e8;
    private TextView t1, t2, t3, t4;
    public Button b2;
    private String cc = "";
    private Log log = new Log();
    private RequestQueue rq;
    private View l;

    public static ModificarUsuario newInstance(String u) {
        ModificarUsuario frag = new ModificarUsuario();
        Bundle args = new Bundle();
        user = u;
        return frag;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        l = inflater.inflate(R.layout.modificarusuario, container, false);
        e1 = l.findViewById(R.id.etbuscarusuario);
        e2 = l.findViewById(R.id.epnombre);
        e3 = l.findViewById(R.id.esnombre);
        e4 = l.findViewById(R.id.epapellido);
        e5 = l.findViewById(R.id.esapellido);
        e6 = l.findViewById(R.id.efechanam);
        e7 = l.findViewById(R.id.emailu);
        e8 = l.findViewById(R.id.telefonou);
        t1 = l.findViewById(R.id.docu);
        t2 = l.findViewById(R.id.nomu);
        t3 = l.findViewById(R.id.fechnacu);
        t4 = l.findViewById(R.id.rolu);
        Button b1 = l.findViewById(R.id.bbuscaru);
        b2 = l.findViewById(R.id.actualizaru);
        clean();
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (e1.getText().toString().isEmpty()) {
                    Toast.makeText(l.getContext(), "El campo de busqueda esta vacio", Toast.LENGTH_SHORT).show();
                } else {
                    buscar(e1.getText().toString());
                }
            }
        });
        e6.setOnClickListener(v -> showDatePickerDialog());
        b2.setOnClickListener(v -> {
            if (notEmpty()) {
                modificar(obtenerValores());
            } else {
                Toast.makeText(l.getContext(), "Todos los campos teben estar llenos", Toast.LENGTH_SHORT).show();
            }
        });
        return l;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private Usuario obtenerValores() {
        return e3.getText().toString().isEmpty() ? new Usuario(cc,
                "", e2.getText().toString(),
                e4.getText().toString(),
                e5.getText().toString(),
                e6.getText().toString(),
                "0",
                e7.getText().toString(),
                e8.getText().toString(),
                t4.getText().toString()) : new Usuario(cc,
                "", e2.getText().toString(),
                e3.getText().toString(),
                e4.getText().toString(),
                e5.getText().toString(),
                e6.getText().toString(),
                "0",
                e7.getText().toString(),
                e8.getText().toString(),
                t4.getText().toString());
    }


    private boolean notEmpty() {
        return !e2.getText().toString().isEmpty() && !e4.getText().toString().isEmpty() &&
                !e5.getText().toString().isEmpty() && !e6.getText().toString().isEmpty() &&
                !e7.getText().toString().isEmpty() && !e8.getText().toString().isEmpty();
    }

    private void clean() {
        cc = "";
        e2.setText("");
        e3.setText("");
        e4.setText("");
        e5.setText("");
        e6.setText("");
        e7.setText("");
        e8.setText("");
        t1.setText("");
        t2.setText("");
        t3.setText("");
        t4.setText("");
    }

    private void unclean() {
        e1.setText("");
        e2.setEnabled(true);
        e3.setEnabled(true);
        e4.setEnabled(true);
        e5.setEnabled(true);
        e6.setEnabled(true);
        e7.setEnabled(true);
        e8.setEnabled(true);
        b2.setEnabled(true);
    }

    private void buscar(final String id) {
        JsonArrayRequest js = new JsonArrayRequest("http://192.168.0.13:80/appausamovil/consultarusuario.php?id=" + id, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jo = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        unclean();
                        jo = response.getJSONObject(i);
                        String ced = jo.getString("cc"), nombre = "", pnom = "", snom = "", pape = "", sape = "", tipod = "", rol = "";
                        cc = ced;
                        if (jo.getString("tipo_doc").equals("Cedula Ciudadania")) {
                            tipod = "CC";
                        } else if (jo.getString("tipo_doc").equals("Cedula Extranjeria")) {
                            tipod = "CE";
                        }
                        rol = jo.getString("rol");
                        if (jo.getString("snombre").isEmpty()) {
                            pnom = jo.getString("pnombre");
                            e2.setText(pnom);
                            pape = jo.getString("papellido");
                            e4.setText(pape);
                            sape = jo.getString("sapellido");
                            e5.setText(sape);
                            nombre = pnom + " " + pape + " " + sape;
                        } else {
                            pnom = jo.getString("pnombre");
                            e2.setText(pnom);
                            snom = jo.getString("snombre");
                            e3.setText(snom);
                            pape = jo.getString("papellido");
                            e4.setText(pape);
                            sape = jo.getString("sapellido");
                            e5.setText(sape);
                            nombre = pnom + " " + snom + " " + pape + " " + sape;
                        }
                        String fechanam = jo.getString("fecha_nam");
                        String celec = jo.getString("correo_electronico");
                        String telefono = jo.getString("telefono");
                        t1.setText(tipod + ". " + ced);
                        t2.setText(nombre);
                        e6.setText(fechanam);
                        e7.setText(celec);
                        e8.setText(telefono);
                        t4.setText(rol);
                        b2.setEnabled(true);
                        Toast.makeText(l.getContext(), "Al final de realizar las modificaciones, de click en actualizar", Toast.LENGTH_LONG).show();
                    } catch (JSONException je) {
                        Toast.makeText(l.getContext(), je.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
                log.insertlog(user, "Usuario Buscado", "Se buscaron los datos del usuario " + id, "http://192.168.0.13:80/appausamovil/insertarlog.php", l.getContext());
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

    private void modificar(final Usuario u) {
        if (u.equals(null)) {
            Toast.makeText(l.getContext(), "Los campos de Correo y Telefono deben estar llenos", Toast.LENGTH_LONG).show();
        } else {
            StringRequest sr = new StringRequest(Request.Method.POST, "http://192.168.0.13:80/appausamovil/modificarUsuario.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(l.getContext(), "Actualización de datos correcta", Toast.LENGTH_LONG).show();
                    clean();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(l.getContext(), "Problemas en la actualización de datos del usuario", Toast.LENGTH_LONG).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> param = new HashMap<String, String>();
                    param.put("cc", u.getCc());
                    param.put("pnom", u.getPnombre());
                    if (u.getSnombre().equals("")) {
                        param.put("snom", "");
                    } else {
                        param.put("snom", u.getSnombre());
                    }
                    param.put("pape", u.getPapellido());
                    param.put("sape", u.getSapellido());
                    param.put("fechanam", u.getFechanam());
                    param.put("correo", u.getCorreoelectronico());
                    param.put("tel", u.getTelefono());
                    return param;
                }
            };
            rq = Volley.newRequestQueue(l.getContext());
            rq.add(sr);
        }
    }

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                final String selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                Date select = null;
                try {
                    select = new SimpleDateFormat("yyyy-MM-dd").parse(selectedDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                e6.setText(new SimpleDateFormat("yyyy-MM-dd").format(select));
            }
        });
        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }
}
