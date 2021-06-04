package com.example.appausa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appausa.R;
import com.example.appausa.model.Cuenta;
import com.example.appausa.model.Data;

import java.util.ArrayList;
import java.util.List;

public class CuentaAdapter extends ArrayAdapter<Cuenta> {
    public CuentaAdapter(Context context, List<Cuenta> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtener inflater.
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // ¿Existe el view actual?
        if (null == convertView) {
            convertView = inflater.inflate(
                    R.layout.list_item_cuenta,
                    parent,
                    false);
        }

        // Referencias UI.
        ImageView avatar = (ImageView) convertView.findViewById(R.id.estadoi);
        TextView id_emp = (TextView) convertView.findViewById(R.id.username);
        TextView fecha = (TextView) convertView.findViewById(R.id.docuuser);
        TextView tipo = (TextView) convertView.findViewById(R.id.perfiluser);
        TextView id_r = (TextView) convertView.findViewById(R.id.ultimolog);

        // Lead actual.
        Cuenta lead = getItem(position);

        // Setup. 1. Aceptado 2. Rechazado 3. En Espera
        if (lead.getEstado().equals("ACTIVA")){
            avatar.setImageResource(R.drawable.check_r);
        } else if (lead.getEstado().equals("BLOQUEADA")){
            avatar.setImageResource(R.drawable.denied_r);
        }
        id_emp.setText(lead.getUsername());
        fecha.setText(lead.getUsuario());
        tipo.setText(lead.getPerfil());
        id_r.setText(lead.getUltimologin());
        return convertView;
    }
}