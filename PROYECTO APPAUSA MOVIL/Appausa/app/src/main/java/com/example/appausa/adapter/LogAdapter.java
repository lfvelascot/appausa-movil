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

public class LogAdapter extends ArrayAdapter<Data> {
    public LogAdapter(Context context, ArrayList<Data> objects) {
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
                    R.layout.list_item_log,
                    parent,
                    false);
        }

        // Referencias UI.
        TextView fecha = (TextView) convertView.findViewById(R.id.fechal);
        TextView accion = (TextView) convertView.findViewById(R.id.accionl);
        TextView descrip = (TextView) convertView.findViewById(R.id.descripl);

        // Lead actual.
        Data lead = getItem(position);

        fecha.setText(lead.getFecha());
        accion.setText(lead.getAccion());
        descrip.setText(lead.getDescrip());
        return convertView;
    }
}